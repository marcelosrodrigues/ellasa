package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.annotations.Before;
import com.pmrodrigues.ellasa.annotations.CRUD;
import com.pmrodrigues.ellasa.annotations.Insert;
import com.pmrodrigues.ellasa.annotations.Update;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.ellasa.models.Atributo;
import com.pmrodrigues.ellasa.models.Imagem;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
import com.pmrodrigues.ellasa.sessionscope.Atributos;
import com.pmrodrigues.ellasa.sessionscope.Imagens;
import org.apache.log4j.Logger;

import static java.lang.String.format;


/**
 * Created by Marceloo on 05/02/2015.
 */
@Resource
@CRUD
public class ProdutoController extends AbstractCRUDController<Produto> {

    private final Imagens imagens;
    private final ShoppingRepository shoppingRepository;
    private final SecaoRepository secaoRepository;

    private static final Logger logging = Logger.getLogger(ProdutoController.class);
    private final Atributos atributos;

    public ProdutoController(final ProdutoRepository repository, final ShoppingRepository shoppingRepository,
                             final SecaoRepository secaoRepository, final Result result, final Validator validator,
                             final Imagens imagens, final Atributos atributos) {
        super(repository, result, validator);
        this.imagens = imagens;
        this.shoppingRepository = shoppingRepository;
        this.secaoRepository = secaoRepository;
        this.atributos = atributos;
    }

    @Before
    public void before() {
        this.getResult().include(Constante.DEPARTAMENTOS, secaoRepository.list());
        this.getResult().include(Constante.LOJAS, shoppingRepository.list());
    }

    @Insert
    public void insert(final Produto produto) {
        logging.debug(format("Iniciando o salvamento do produto %s", produto));

        adicionarLista(produto);

        this.getRepository().add(produto);
        this.imagens.apagar();
        logging.debug(format("Produto %s salvo com sucesso", produto));
    }

    public void adicionarLista(final Produto produto) {
        for (final Imagem imagem : this.imagens.getImagens()) {
            produto.adicionar(imagem);
        }

        for (final Atributo atributo : this.atributos.getAtributos()) {
            produto.adicionar(atributo);
        }
    }

    @Update
    public void update(final Produto produto) {
        logging.debug(format("Iniciando o salvamento do produto %s", produto));

        adicionarLista(produto);

        this.getRepository().set(produto);
        this.imagens.apagar();
        logging.debug(format("Produto %s salvo com sucesso", produto));
    }
}
