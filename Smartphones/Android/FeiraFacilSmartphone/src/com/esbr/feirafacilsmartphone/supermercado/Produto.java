package com.esbr.feirafacilsmartphone.supermercado;

import java.util.*;

public class Produto {

	private String nome;
	private double preco;
	private boolean estaEmPromocao = false; // TODO pensar melhor na necessidade disso
	private CategoriaDeProduto tipo;
	
	public Produto(String nome, double preco, CategoriaDeProduto tipo) {
		this.nome = nome;
		this.preco = preco;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	//TODO preco mais baixo nao quer dizer que esta em promocao
//	public void setPreco(double preco) {
//		if(preco < this.preco){
//			estaEmPromocao = true;
//		}
//		this.preco = preco;
//	}
	public void setPreco(double preco) {
		this.preco = preco;
	}	

	public CategoriaDeProduto getTipo() {
		return tipo;
	}
	
	public void setTipo(CategoriaDeProduto tipo) {
		this.tipo = tipo;
	}
	
	public boolean estaEmPromocao(){
		return estaEmPromocao;
	}
	
	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", preço=" + preco + ", tipo=" + tipo + "]";
	}

}