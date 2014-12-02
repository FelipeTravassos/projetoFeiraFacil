package com.esbr.feirafacilsmartphone.supermercado;

public class Produto {
	private String nome;
	private String desc;
	private Double preco;
	
	public Produto(String nome, String desc, Double preco) {
		setNome(nome);
		setDesc(desc);
		setPreco(preco);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
