package com.esbr.feirafacilsmartphone;

import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
import com.esbr.feirafacilsmartphone.adapter.SupermercadoArrayAdapter;
import com.esbr.feirafacilsmartphone.server.Server;
import com.esbr.feirafacilsmartphone.supermercado.Produto;
import com.esbr.feirafacilsmartphone.supermercado.Supermercado;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SelectSupermarketActivity extends ListActivity  {

	ListView listaSupermercados;
	Server server = new Server();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_supermarket);
		
		listaSupermercados = (ListView) findViewById(R.id.listSupermercados);
		populaListaSupermercados();
	}

	private void populaListaSupermercados() {
		Supermercado[] supermercados = server.getSupermercados();
		listaSupermercados.setAdapter(new SupermercadoArrayAdapter(this, supermercados)); 
	}

	@Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	 
	  	//Pegar o item clicado
	  	Object o = this.getListAdapter().getItem(position);
	  	String lstrEstado = o.toString();
	  
	  	//Apresentar o item clicado
	  	Toast.makeText(this, "Você clicou no estado : " + lstrEstado, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_supermarket, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
