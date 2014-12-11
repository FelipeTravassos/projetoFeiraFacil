package com.esbr.feirafacilsmartphone.supermercado;

public class Supermercado {
	String nome;
	String img;
	
	public Supermercado(String nome) {
		setNome(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
