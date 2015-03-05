package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;

import com.esbr.feirafacilsmartphone.adapter.CarrinhoArrayAdapter;
import com.esbr.feirafacilsmartphone.supermercado.Produto;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CarrinhoActivity extends Activity {

	public Carrinho carrinho ;
	
	private AlertDialog alerta; 
	
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_carrinho);
		
		//Intent intent = getIntent();
		//carrinho = (Carrinho) intent.getSerializableExtra("carrinho");
		carrinho = Carrinho.getInstance();
		
		lv = (ListView) findViewById(R.id.list_prod_carrinho);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
            
			@Override
  			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				removerItemCarrinho(carrinho.getListaItems().get(position));
				
			}
        });
			
		
		lv.setAdapter(new CarrinhoArrayAdapter(this,carrinho.getListaItems())); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carrinho, menu);
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
	
	
	private void removerItemCarrinho(final Produto produto) { 
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(produto.getNome()); 
		builder.setMessage("Deseja remover este item do carrinho?");
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				arg0.cancel();
			} 
		});
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				carrinho.removerItemCarrinho(produto);
				Toast.makeText(CarrinhoActivity.this, "Item removido do carrinho", Toast.LENGTH_SHORT).show();
				CarrinhoArrayAdapter adapter = new CarrinhoArrayAdapter(CarrinhoActivity.this,carrinho.getListaItems());
				adapter.notifyDataSetChanged();
				lv.setAdapter(adapter); 
				
			} 
		}); 
		alerta = builder.create(); 
		alerta.show();
	}

}
