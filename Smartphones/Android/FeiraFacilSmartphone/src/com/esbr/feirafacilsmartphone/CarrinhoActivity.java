package com.esbr.feirafacilsmartphone;

import com.esbr.feirafacilsmartphone.adapter.CarrinhoArrayAdapter;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CarrinhoActivity extends Activity {
	
	private CarrinhoArrayAdapter adapter;
	
	private ListView lv;
	
	private AlertDialog alerta; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_carrinho);
		
		lv = (ListView) findViewById(R.id.list_prod_carrinho);
		adapter = new CarrinhoArrayAdapter(this,Carrinho.getInstance().getListaItems());
		lv.setAdapter(adapter); 
		
		Button buttonConfirmarFeira = (Button) findViewById(R.id.buttonConfirmarFeira);
		buttonConfirmarFeira.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Carrinho.getInstance().getListaItems().size() == 0) {
					Toast.makeText(CarrinhoActivity.this, "Nenhum item no carrinho", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.carrinho, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.limparCarrinho:
			if (Carrinho.getInstance().getListaItems().size() != 0) {
				limparCarrinho();
			} else {
				Toast.makeText(CarrinhoActivity.this, "Nenhum item no carrinho", Toast.LENGTH_SHORT).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void limparCarrinho() { 
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(CarrinhoActivity.this);
		builder.setTitle("Limpar Carrinho"); 
		builder.setMessage("Deseja realmente remover este item do carrinho?");
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				arg0.cancel();
			} 
		});
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) {
				Carrinho.getInstance().limparCarrinho();
				adapter.clear();
				adapter.notifyDataSetChanged();
				Toast.makeText(CarrinhoActivity.this, "Concluído", Toast.LENGTH_SHORT).show();
			} 
		}); 
		alerta = builder.create(); 
		alerta.show();
	}
}
