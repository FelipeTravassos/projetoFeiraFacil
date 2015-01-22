package com.esbr.feirafacilsmartphone.user.listas;

public class Produto {
	private String nome;
	private String id;
	private double quantidade;
	
	public Produto(String nome, String id, double quantidade) {
		setNome(nome);
		setId(id);
		setQuantidade(quantidade);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
}
