package com.esbr.feirafacilsmartphone.adapter;

import android.graphics.Bitmap;

public class DrawerItem {
	
	private String title;
    private int icon;
    private String tipo;
	private Bitmap imagem;

    public DrawerItem() {
    }

    public DrawerItem(
        final String title, final int icon, final String tipo) {
        this.title = title;
        this.icon = icon;
        this.tipo = tipo;
        this.setImagem(null);
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

	public Bitmap getImagem() {
		return imagem;
	}

	public void setImagem(Bitmap imagem) {
		this.imagem = imagem;
	}
}
