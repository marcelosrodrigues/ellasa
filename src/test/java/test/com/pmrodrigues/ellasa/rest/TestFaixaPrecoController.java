package test.com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.ellasa.models.FaixaPreco;
import com.pmrodrigues.ellasa.repositories.FaixaRepository;
import com.pmrodrigues.ellasa.rest.FaixaPrecoController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestFaixaPrecoController
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    private FaixaPrecoController service;

    @Autowired
    private FaixaRepository repository;

    @Before
    public void setup() {
        service = new FaixaPrecoController(repository, new MockResult());
    }

    @Test
    public void deveListarTodasAsSecoes() {

        @SuppressWarnings("deprecation")
        final Long count = this.jdbcTemplate
                .queryForLong("select count(1) from faixa_preco");

        final List<FaixaPreco> faixas = service.faixas();
        assertEquals(count, Long.valueOf(faixas.size()));


    }

}
