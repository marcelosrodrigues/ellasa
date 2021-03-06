package com.pmrodrigues.ellasa.models;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.validator.GenericValidator;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class OrdemPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private final String carrinho = RandomStringUtils.randomAlphanumeric(20)
			.toUpperCase();

	@ManyToOne(optional = false)
	@JoinColumn(name = "meiopagamento_id")
	private MeioPagamento meioPagamento;

	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "contrato_id")
	private Contrato contrato;

	@Column(nullable = false)
	private BigDecimal valor;

	@Column(nullable = true)
	private String codigo;

	@Column(nullable = true)
	private String status;

	@Column(nullable = true)
	private String motivo;

	@Temporal(TemporalType.TIMESTAMP)
	private final Date dataGeracao = DateTime.now().toDate(); //NOPMD

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvio; //NOPMD

	@Column(nullable = false)
	private String descricao;

	@Column
	private String documento;

    @ManyToOne(optional = true)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public OrdemPagamento(final MeioPagamento meiopagamento,
			final Contrato contrato, final BigDecimal valor) {
		this();
		this.meioPagamento = meiopagamento;
		this.contrato = contrato;
		this.valor = valor;
	}

	public OrdemPagamento() {
		super();
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(final Contrato contrato) {
		this.contrato = contrato;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(final BigDecimal valor) {
		this.valor = valor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public String getCarrinho() {
		return carrinho;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return this.status;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

    public void setMeioPagamento(final MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
	}

	public boolean isSucesso() {
		return !GenericValidator.isBlankOrNull(status)
				&& !"erro".equalsIgnoreCase(status);
	}

	public void setMotivo(final String motivo) {
		this.motivo = motivo;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setDocumento(final String documento) {
		this.documento = documento;
	}

	public String getDocumento() {
		return this.documento;
	}

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(final Pedido pedido) {
        this.pedido = pedido;
    }
}
