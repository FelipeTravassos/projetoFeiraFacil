package com.esbr.feirafacilsmartphone.supermercado;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Produto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6632714485299171607L;
	
	private String id;
	private String nome;
	private String descricao;
	private float preco;
	private String categoria;
	private int quantidade;
	private String imagemLink;
	private Bitmap imagem;
	
	public Produto(String id, String nome, String descricao, float preco, String categoria, int quantidade, String imagemLink) {
		setId(id);
		setNome(nome);
		setDescricao(descricao);
		setPreco(preco);
		setCategoria(categoria);
		setQuantidade(quantidade);
		setImagemLink(imagemLink);
		setImagem(null);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setImagem(Bitmap imagem) {
		this.imagem = imagem;
	}
	
	public Bitmap getImagem() {
		return imagem;
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
	
	public String getImagemLink() {
		return this.imagemLink;
	}
	
	public void setImagemLink(String imagemLink) {
		this.imagemLink = imagemLink;
	}
	
}