package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
import com.esbr.feirafacilsmartphone.server.TaskAllProducts;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class PromoActivity extends Activity {

	private ArrayList<Produto> values = new ArrayList<Produto>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_promo);

	    getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ListView lv = (ListView) findViewById(R.id.list_prod_promo);
		
		try {
			
			String result = new TaskAllProducts().execute().get();
			JSONArray json = new JSONArray(result);
			
			for (int i = 0; i < json.length(); i++) {
		       	JSONObject jsonObj = json.getJSONObject(i);
		       	String nomeProduto = jsonObj.get("name").toString();
		       	String descricaoProduto = "Colocar Descricao no JSON";
		       	float valorUnitarioProduto = Float.parseFloat(jsonObj.get("value").toString());
		       	String categoriaProduto = jsonObj.get("id_category").toString();
		       	
		       	Produto produto = new Produto(nomeProduto, descricaoProduto, valorUnitarioProduto, categoriaProduto,0);
		       	values.add(produto);
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
	
	

}
