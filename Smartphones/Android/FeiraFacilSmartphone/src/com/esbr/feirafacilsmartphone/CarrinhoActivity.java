package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;

import com.esbr.feirafacilsmartphone.adapter.CarrinhoArrayAdapter;
import com.esbr.feirafacilsmartphone.supermercado.Produto;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CarrinhoActivity extends Activity {

	
	private AlertDialog alerta; 
	
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_carrinho);
		
		
		lv = (ListView) findViewById(R.id.list_prod_carrinho);
		lv.setAdapter(new CarrinhoArrayAdapter(this,Carrinho.getInstance().getListaItems())); 

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
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	private void limparCarrinho() {
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Limpar Carrinho");
		builder.setIcon(resize(R.drawable.icone_fazerfeira_telapromocoes,50));
		builder.setMessage("Deseja realmente remover todos os itens do carrinho?");
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				arg0.cancel();
			} 
		});
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) {
				Carrinho.getInstance().limparCarrinho();
				Toast.makeText(CarrinhoActivity.this, "Carrinho Limpo", Toast.LENGTH_SHORT).show();
				CarrinhoArrayAdapter adapter = new CarrinhoArrayAdapter(CarrinhoActivity.this,Carrinho.getInstance().getListaItems());
				adapter.notifyDataSetChanged();
				lv.setAdapter(adapter); 
				
			} 
		}); 
		alerta = builder.create(); 
		alerta.show();
	}
	
	private Drawable resize(int id, int scale) {
		Drawable image = getResources().getDrawable(id);
	    Bitmap b = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, scale, scale, false);
	    return new BitmapDrawable(getResources(), bitmapResized);
	}
}
