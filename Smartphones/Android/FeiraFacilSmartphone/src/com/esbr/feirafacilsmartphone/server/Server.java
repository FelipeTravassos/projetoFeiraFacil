package com.esbr.feirafacilsmartphone.server;

import com.esbr.feirafacilsmartphone.supermercado.Supermercado;

public class Server {

	public int getTotalDeSupermercados() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Supermercado[] getSupermercados() {
		Supermercado[] supermercados = new Supermercado[3];
		supermercados[0] = new Supermercado("Ideal");
		supermercados[1] = new Supermercado("Rede Compras");
		supermercados[2] = new Supermercado("Bompreço");
		return supermercados;
	}

}
