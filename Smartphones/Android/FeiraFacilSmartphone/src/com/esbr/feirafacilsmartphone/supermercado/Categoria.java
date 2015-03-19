package com.esbr.feirafacilsmartphone.supermercado;

import android.graphics.Bitmap;

public class Categoria {
	
	private String nomeCategoria;
	private String imagemLinkCategoria;
	private Bitmap imagemCategoria;
	private int quantidadeItems;
	
	public Categoria(String nomeCategoria, String imagemLinkCategoria) {
		setNomeCategoria(nomeCategoria);
		setImagemLinkCategoria(imagemLinkCategoria);
		setImagemCategoria(null);
		setQuantidadeItems(0);
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}


	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}


	public String getImagemLinkCategoria() {
		return imagemLinkCategoria;
	}


	public void setImagemLinkCategoria(String imagemLinkCategoria) {
		this.imagemLinkCategoria = imagemLinkCategoria;
	}


	public Bitmap getImagemCategoria() {
		return imagemCategoria;
	}


	public void setImagemCategoria(Bitmap imagemCategoria) {
		this.imagemCategoria = imagemCategoria;
	}

	public int getQuantidadeItems() {
		return quantidadeItems;
	}

	public void setQuantidadeItems(int quantidadeItems) {
		this.quantidadeItems = quantidadeItems;
	}

	public void addQuantidadeItem() {
		quantidadeItems += 1;
	}
}
