package com.pmrodrigues.ellasa.models;

import com.google.gson.annotations.SerializedName;
import com.pmrodrigues.ellasa.Constante;
import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.repositories.utils.FilterName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.annotations.*;
import org.joda.time.DateTime;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Marceloo on 13/10/2014.
 */
@Entity
@Table
@XStreamAlias("pedido")
@FilterDefs({
        @FilterDef(name = FilterName.FILTRO_POR_VENDEDOR, parameters = @ParamDef(name = FilterName.FILTRO_POR_VENDEDOR, type = "long")),
        @FilterDef(name = FilterName.FILTRO_POR_LOJA, parameters = @ParamDef(name = FilterName.FILTRO_POR_LOJA, type = "long"))
})
@Filters({
        @Filter(name = FilterName.FILTRO_POR_VENDEDOR, condition = "vendedor_id = :vendedor"),
        @Filter(name = FilterName.FILTRO_POR_LOJA, condition = "exists ( select 1 from lojistas l where l.loja_id = loja_id and l.usuario_id = :loja)")
})
public class Pedido implements Serializable {

    @Transient
    @SerializedName("pagamento")
    private OrdemPagamentoCartaoCredito dadosPagamento;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, targetEntity = Cliente.class)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "enderecoentrega_id")
    private EnderecoCliente enderecoEntrega;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id", nullable = false)
    private final Collection<ItemPedido> itens = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "loja_id")
    private Loja loja;

    @Column
    private String codigoTransacao;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private StatusPagamento status = StatusPagamento.EM_ABERTO;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataCompra = Constante.DATA_INICIAL;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataEntrega = DateTime.now().toDate(); //NOPMD

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataCriacaco")
    private Date dataCriacao; //NOPMD

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataAlteracao; //NOPMD

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor; //NOPMD

    @Column(name = "akatus_transaction_code")
    private String codigoReferencia;

    @Column(name = "data_aprovacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAprovacao;

    @Column(name = "motivo")
    private String motivo;

    @PrePersist
    public void onInsert() {
        dataCriacao = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
        dataCompra = DateTime.now().toDate();
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
    }

    public OrdemPagamento getDadosPagamento() {
        return dadosPagamento;
    }

    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setEnderecoEntrega(final EnderecoCliente enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public EnderecoCliente getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public Collection<ItemPedido> getItens() {
        return itens;
    }

    public void setLoja(final Loja loja) {
        this.loja = loja;
    }

    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public void calcula(final List<Taxa> taxas) {

        for (final ItemPedido item : this.itens) {
            for (final Taxa taxa : taxas) {
                item.geraComissao(taxa);
            }
        }
    }

    public BigDecimal getTotal() {
        BigDecimal valor = BigDecimal.ZERO;
        for (final ItemPedido item : itens) {
            valor = valor.add(item.getValor());
        }

        return valor;
    }

    public void adicionar(final Produto produto) {
        this.itens.add(new ItemPedido(produto, 1L));
    }

    public void setStatus(final StatusPagamento status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void associar(final Usuario usuario) {
        this.vendedor = usuario;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setCodigoReferencia(final String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setMotivo(final String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setDataAprovacao(final Date dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Date getDataAprovacao() {
        return dataAprovacao;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setDataCompra(final Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Date getDataCompra() {
        return dataCompra;
    }
}
