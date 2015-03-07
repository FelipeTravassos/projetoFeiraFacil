package com.esbr.feirafacilsmartphone;

import com.esbr.feirafacilsmartphone.adapter.CarrinhoArrayAdapter;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class CarrinhoActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_carrinho);
		
		ListView lv = (ListView) findViewById(R.id.list_prod_carrinho);
		lv.setAdapter(new CarrinhoArrayAdapter(this,Carrinho.getInstance().getListaItems())); 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carrinho, menu);
		return true;
	}

	
}
