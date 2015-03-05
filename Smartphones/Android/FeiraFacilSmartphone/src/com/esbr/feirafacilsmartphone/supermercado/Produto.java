package com.esbr.feirafacilsmartphone.supermercado;

import java.io.Serializable;

public class Produto implements Serializable {
	
	private String nome;
	private String categoria;
	private float preco;
	private int quantidade;
	
	public Produto(String nome, float preco, String categoria, int quantidade) {
		setQuantidade(quantidade);
		setNome(nome);
		setPreco(preco);
		setCategoria(categoria);
		
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
}