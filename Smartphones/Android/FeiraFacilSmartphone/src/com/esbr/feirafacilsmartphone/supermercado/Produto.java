package com.esbr.feirafacilsmartphone.supermercado;

import java.io.Serializable;

public class Produto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6632714485299171607L;
	
	private String nome;
	private String descricao;
	private float preco;
	private String categoria;
	private int quantidade;
	
	public Produto(String nome, String descricao, float preco, String categoria, int quantidade) {
		setNome(nome);
		setDescricao(descricao);
		setPreco(preco);
		setCategoria(categoria);
		setQuantidade(quantidade);
		
	}
	
	public void calcularValor() {
		preco = quantidade * preco;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return quantidade;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}