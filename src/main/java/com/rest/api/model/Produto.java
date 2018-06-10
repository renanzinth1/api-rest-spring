package com.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Produtos")
public class Produto {

	@Id
	private Long codigo;
	
	private String nome;
	
	@Column(name = "codigo_barras")
	private String codigoBarras;

	public Produto() {
		super();
	}

	public Produto(Long codigo, String nome, String codigoBarras) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.codigoBarras = codigoBarras;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

}
