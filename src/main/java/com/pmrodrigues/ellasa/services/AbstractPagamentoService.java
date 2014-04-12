package com.pmrodrigues.ellasa.services;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.pmrodrigues.ellasa.models.Endereco;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.models.Telefone;
import com.pmrodrigues.ellasa.pagamentos.Akatus;
import com.pmrodrigues.ellasa.pagamentos.Akatus.Environment;
import com.pmrodrigues.ellasa.pagamentos.entity.Address;
import com.pmrodrigues.ellasa.pagamentos.entity.Address.Country;
import com.pmrodrigues.ellasa.pagamentos.entity.Address.State;
import com.pmrodrigues.ellasa.pagamentos.entity.Payer;
import com.pmrodrigues.ellasa.pagamentos.entity.Phone;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.pagamentos.v1.cart.CartOperation;
import com.pmrodrigues.ellasa.pagamentos.v1.cart.CartResponse;


@PropertySource("classpath:akatus.properties")
public abstract class AbstractPagamentoService implements PagamentoService {

	private final static Logger LOGGER = Logger
			.getLogger(AbstractPagamentoService.class);

	private CartOperation carrinho;

	@Value("${AUTH_USER}")
	private String user;

	@Value("${AUTH_PASSWORD}")
	private String key;

	@Value("${AKATUR_URL}")
	private String enviroment;

	protected Recebedor getRecebedor() {
		return new Recebedor(user, key);
	}

	protected Address criarEndereco(final Endereco endereco) {
		final Address address = new Address();
		address.setCity(endereco.getCidade());
		address.setComplement(endereco.getComplemento());
		address.setCountry(Country.BRA);
		address.setNeighbourhood(endereco.getBairro());
		address.setNumber(Integer.parseInt(endereco.getNumero()));
		address.setState(State.valueOf(endereco.getEstado().getUf()));
		address.setStreet(endereco.getLogradouro());
		address.setType(Address.Type.SHIPPING);
		address.setZip(endereco.getCep());
		return address;
	}

	protected Payer criarPagador(final Franqueado franqueado) {
		final Payer pagador = new Payer();
		pagador.setEmail(franqueado.getEmail());
		pagador.setName(franqueado.getNome());
		return pagador;
	}

	protected Transaction criarTransacao(final OrdemPagamento pagamento) {
		final Recebedor recebedor = this.getRecebedor();
		this.carrinho = new Akatus(Environment.valueOf(enviroment),
				recebedor.getEmail(),
				recebedor.getApiKey()).cart();

		final Payer pagador = criarPagador(pagamento.getContrato()
				.getFranqueado());

		final Address address = criarEndereco(pagamento.getContrato()
				.getFranqueado().getEndereco());

		pagador.addAddress(address);

		if (pagamento.getContrato().getFranqueado().getCelular() != null
				&& !GenericValidator.isBlankOrNull(pagamento.getContrato()
						.getFranqueado().getCelular().getDdd())
				&& !GenericValidator.isBlankOrNull(pagamento.getContrato()
						.getFranqueado().getCelular().getNumero())) {
			Phone phone = createTelefone(pagamento.getContrato()
					.getFranqueado().getCelular());
			pagador.addPhone(phone);
		}

		if (pagamento.getContrato().getFranqueado().getResidencial().getDdd() != null
				&& !GenericValidator.isBlankOrNull(pagamento.getContrato()
						.getFranqueado().getResidencial().getDdd())
				&& !GenericValidator.isBlankOrNull(pagamento.getContrato()
						.getFranqueado().getResidencial().getNumero())) {
			Phone phone = createTelefone(pagamento.getContrato()
					.getFranqueado().getResidencial());
			pagador.addPhone(phone);
		}

		carrinho.setPayer(pagador);
		carrinho.addProduct(pagamento.getCarrinho(), pagamento.getDescricao(),
				pagamento.getValor().doubleValue(), 0D, 1, 0D);

		final Transaction trans = carrinho.getTransaction();
		trans.setReference(pagamento.getCarrinho());
		trans.setInstallments(1);

		return trans;
	}

	private Phone createTelefone(final Telefone telefone) {
		Phone phone = new Phone();
		phone.setNumber(telefone.getDdd() + telefone.getNumero());
		phone.setType(Phone.Type.RESIDENTIAL);
		return phone;
	}

	protected void execute(final OrdemPagamento pagamento) {

		LOGGER.info("Mandando ordem de pagamento para a akatus");
		final CartResponse response = (CartResponse) this.carrinho.execute();
		LOGGER.info("Ordem enviada com sucesso");
		pagamento.setCodigo(response.getTransaction());
		pagamento.setStatus(response.getStatus());
		pagamento.setMotivo(response.getDescription());
		pagamento.setDocumento(response.getReturnURL());

	}

}
