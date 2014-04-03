package com.pmrodrigues.ellasa.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;

@Entity
@Table
public class OrdemPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private final String carrinho = RandomStringUtils.randomAlphanumeric(20)
			.toUpperCase();

	@ManyToOne(optional = false)
	@JoinColumn(name = "contrato_id")
	private Contrato contrato;

	@Column(nullable = false)
	private BigDecimal valor;

	@Column(nullable = true)
	private String codigo;

	@Column(nullable = true)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private final Date dataGeracao = DateTime.now().toDate();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvio;

	@Column(nullable = false)
	private String descricao;

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

}
