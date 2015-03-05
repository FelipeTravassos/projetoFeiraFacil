package com.esbr.feirafacilsmartphone.supermercado;

import java.io.Serializable;
import java.util.ArrayList;

public class Carrinho implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2102348107872371518L;

	private float valor_total;
	
	private ArrayList<Produto> listaItens;
	
	private static Carrinho instance = null;
	
	protected Carrinho() {
		setValor_total(0);
		listaItens = new ArrayList<Produto>();
		setListaItems(listaItens);
	}

	public static Carrinho getInstance() {
		if (instance == null) {
			instance = new Carrinho();
		}
		return instance;
	}

	public float getValor_total() {
		return valor_total;
	}


	public void setValor_total(float i) {
		this.valor_total = i;
	}


	public ArrayList<Produto> getListaItems() {
		return listaItens;
	}
	
	public void adicionarItemCarrinho(Produto produto) {
		valor_total += produto.getQuantidade()*produto.getPreco();
		listaItens.add(produto);
	}
	
	public void removerItemCarrinho(Produto produto) {
		valor_total -= produto.getQuantidade()*produto.getPreco();			
		listaItens.remove(produto);
	}

	public void setListaItems(ArrayList<Produto> listaItems) {
		this.listaItens = listaItems;
	}

	public void limparCarrinho() {
		setValor_total(0);
		listaItens = new ArrayList<Produto>();
		setListaItems(listaItens);		
	}


	
}
