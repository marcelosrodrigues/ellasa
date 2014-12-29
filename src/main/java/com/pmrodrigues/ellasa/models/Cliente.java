package com.pmrodrigues.ellasa.models;

import com.pmrodrigues.ellasa.enumarations.Genero;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="cliente")
@NamedQueries({@NamedQuery(name = "Cliente.All", query = "SELECT c FROM Cliente c inner join fetch c.endereco e inner join fetch e.estado ORDER BY c.id ASC")})
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String primeiroNome;
	
	@Column
	private String ultimoNome;
	
	@Column
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date dataNascimento;

	@OneToOne(mappedBy="cliente" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	@JoinColumn(name="cliente_id")
	private EnderecoCliente endereco;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataCriacaco = DateTime.now().toDate();

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataAlteracao = DateTime.now().toDate();

    @Enumerated(EnumType.ORDINAL)
    private Genero sexo;


    @PrePersist
    public void onInsert() {
        dataCriacaco = DateTime.now().toDate();
        dataAlteracao = DateTime.now().toDate();
    }

    @PreUpdate
    public void onUpdate() {
        dataAlteracao = DateTime.now().toDate();
    }

    public Long getId() {
        return id;
    }

    public EnderecoCliente getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoCliente endereco) {
        this.endereco = endereco;
        this.endereco.setCliente(this);
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return primeiroNome + " " + ultimoNome;
    }


    public void setPrimeiroNome(final String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public void setUltimoNome(final String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setDataNascimento(final Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public boolean isNovo() {
        return this.id != null && 0L != this.id;
    }
}
