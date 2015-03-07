package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
import com.esbr.feirafacilsmartphone.server.TaskAllProducts;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PromoActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	private AlertDialog alerta; 
	
	private ArrayList<Produto> values = new ArrayList<Produto>();
	
	private Carrinho carrinho = Carrinho.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_promo);

	    getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ListView lv = (ListView) findViewById(R.id.list_prod_promo);
		
		String result;
		JSONArray json;

		
		Log.i("teste", "teste");
		
		try {
			
			result = new TaskAllProducts().execute().get();
			json = new JSONArray(result);
			
			for (int i = 0; i < json.length(); i++) {
		       	JSONObject jsonObj = json.getJSONObject(i);
		       	String nomeProduto = jsonObj.get("name").toString();
		       	String descricaoProduto = "Colocar Descricao no JSON";
		       	float valorUnitarioProduto = Float.parseFloat(jsonObj.get("value").toString());
		       	String categoriaProduto = jsonObj.get("id_category").toString();
		       	Produto obj = new Produto(nomeProduto, descricaoProduto, valorUnitarioProduto, categoriaProduto,0);
		       	values.add(obj);

		       	
		    }
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			
			e1.printStackTrace();
		} catch (JSONException e) {
			
			e.printStackTrace();
		}        

		
			
		lv.setAdapter(new PromoArrayAdapter(this,values )); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.promo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.selectsuper){
			
			Intent myIntent = new Intent(getBaseContext(), CarrinhoActivity.class);
			
			startActivity(myIntent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void adicionarItemCarrinho(final Produto produto) { 
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(produto.getNome()); 
		builder.setIcon(resize(R.drawable.icone_confirmarfeiraselecionado,50));
		builder.setMessage("Deseja adicionar este item ao carrinho?");
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				arg0.cancel();
			} 
		});
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				if (carrinho.getListaItems().contains(produto)) {
					Toast.makeText(PromoActivity.this, "Este item já se encontra no carrinho", Toast.LENGTH_SHORT).show();
				} else {
					//colocar dialog para selecionar a quantidade
					// exemplo: produto.setQuantidade(5);
					
					carrinho.adicionarItemCarrinho(produto);
					Toast.makeText(PromoActivity.this, "Item adicionado ao carrinho", Toast.LENGTH_SHORT).show();
				}
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
