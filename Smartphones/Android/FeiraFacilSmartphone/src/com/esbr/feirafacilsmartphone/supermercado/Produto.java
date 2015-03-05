package com.esbr.feirafacilsmartphone.supermercado;

import java.io.Serializable;

public class Produto implements Serializable{
	private String nome;
	private String categoria;
	private float preco;
	
	public Produto(String nome, float preco, String categoria) {
		setNome(nome);
		setPreco(preco);
		setCategoria(categoria);
		
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