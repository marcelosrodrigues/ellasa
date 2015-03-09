package test.com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.util.test.MockResult;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import com.pmrodrigues.ellasa.rest.SecaoController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestSecaoController
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    private SecaoController service;

    @Autowired
    private SecaoRepository repository;

    @Before
    public void setup() {
        service = new SecaoController(repository, new MockResult());
        jdbcTemplate.query("select secao_id from areas_vendas a inner join secao on id = secao_id where secao = 'teste'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestSecaoController.this.jdbcTemplate.update("delete from areas_vendas where secao_id = ?", rs.getInt("secao_id"));
                TestSecaoController.this.jdbcTemplate.update("delete from secao where id = ?", rs.getInt("secao_id"));

                return null;
            }
        });
        jdbcTemplate.update("insert into secao ( secao ) values ( 'teste' )");
    }

    @After
    public void after() {
        jdbcTemplate.query("select secao_id from areas_vendas a inner join secao on id = secao_id where secao = 'teste'", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestSecaoController.this.jdbcTemplate.update("delete from areas_vendas where secao_id = ?", rs.getInt("secao_id"));
                TestSecaoController.this.jdbcTemplate.update("delete from secao where id = ?", rs.getInt("secao_id"));
                return null;
            }
        });
    }

    @Test
    public void deveListarTodasAsSecoesPorLoja() {
        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final Loja loja = new Loja();
        loja.setId(id);

        final Long count = this.jdbcTemplate.queryForObject("select count(id) from secao inner join areas_vendas on id = secao_id where produto_id = ?", Long.class, id);

        List<Secao> secoes = service.secoes(loja);
        assertEquals(count, Long.valueOf(secoes.size()));
    }

    @Test
    public void pesquisarSecaoPorNome() {
        final Long count = this.jdbcTemplate.queryForObject("select count(id) from secao where secao like 'Vest%'", Long.class);
        List<Secao> secoes = service.pesquisarPorNome("Vest");
        assertEquals(count, Long.valueOf(secoes.size()));
    }


}
