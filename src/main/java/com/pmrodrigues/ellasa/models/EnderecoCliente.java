package com.pmrodrigues.ellasa.models;

import com.pmrodrigues.ellasa.enumarations.Tipo;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "endereco")
public class EnderecoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //NOPMD

    @ManyToOne(optional = false, cascade = CascadeType.ALL, targetEntity = Cliente.class)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; //NOPMD

    @Column
    private String logradouro;

    @Column
    private String bairro;

    @Column
    private String cep;

    @Column
    private String cidade;

    @Column
    private String telefone;

    @Column
    private String celular;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataCriacaco")
    private Date dataCriacao = DateTime.now().toDate(); //NOPMD

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataAlteracao = DateTime.now().toDate(); //NOPMD

    @Enumerated
    private Tipo tipo = Tipo.RESIDENCIAL; //NOPMD


    @PrePersist
    public void onInsert() {
        dataCriacao = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getCep() {
        return cep;
    }

    public String getCelular() {
        return celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setEstado(final Estado estado) {
        this.estado = estado;
    }


    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public void setCep(final String CEP) {
        this.cep = CEP;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public void setCelular(final String celular) {
        this.celular = celular;
    }

    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }
}
