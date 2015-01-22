package com.esbr.feirafacilsmartphone.user.listas;

import java.util.List;

public class ListaProdutos {
	private String nome;
	private List<Produto> produtos;
	
	public ListaProdutos(String nome, List<Produto> produtos) {
		setNome(nome);
		setProdutos(produtos);
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getNomeLista() {
		return this.nome;
	}
	
	public List<Produto> getProdutos() {
		return this.produtos;
	}
	
	public int getQuantidadeDeItems() {
		return this.produtos.size();
	}
	
}
