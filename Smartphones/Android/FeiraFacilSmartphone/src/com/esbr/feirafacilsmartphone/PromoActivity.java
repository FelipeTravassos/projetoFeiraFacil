package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.experimental.categories.Categories.CategoryFilter;

import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
import com.esbr.feirafacilsmartphone.server.TaskAllCategorias;
import com.esbr.feirafacilsmartphone.server.TaskAllProdutos;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;
import com.esbr.feirafacilsmartphone.supermercado.Produto;
import com.esbr.feirafacilsmartphone.util.DownloadImage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class PromoActivity extends Activity {

	private ListView lv;
	private ArrayList<Produto> values;
	private ArrayList<String> categoriasSelecionadas;
	private ArrayList<String> categorias;
	private PromoArrayAdapter adapter;
	private AlertDialog alerta;
	private String mail;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.fragment_promo);

	    getActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv = (ListView) findViewById(R.id.list_prod_promo);
		values = new ArrayList<Produto>();
		categorias = new ArrayList<String>();
		categoriasSelecionadas = new ArrayList<String>();
		
		adapter = new PromoArrayAdapter(this,values);
		lv.setAdapter(adapter);
		
		try {
			String resultProdutos = new TaskAllProdutos().execute().get();
			JSONArray jsonProdutos = new JSONArray(resultProdutos);
			for (int i = 0; i < jsonProdutos.length(); i++) {
		       	JSONObject jsonObj = jsonProdutos.getJSONObject(i);
		       	
		       	String id = jsonObj.get("id").toString();
		       	String nomeProduto = jsonObj.get("nome").toString();
		       	String descricaoProduto = jsonObj.get("descricao").toString();
		       	float valorUnitarioProduto = Float.parseFloat(jsonObj.get("preco").toString());
		       	String categoriaProduto = jsonObj.get("categoria").toString();
		       	String imagemLink = jsonObj.get("imagemLink").toString();
		       	
		       	if (!categorias.contains(categoriaProduto)) {
		       		categorias.add(categoriaProduto);
		       		categoriasSelecionadas.add(categoriaProduto);
		       	}
		       	
		       	Produto produto = new Produto(id, nomeProduto, descricaoProduto, valorUnitarioProduto, categoriaProduto, 0, imagemLink);
		       	
		       	new DownloadImage(produto, adapter).execute(imagemLink);
		       	
		       	values.add(produto);
		    }
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mail = getIntent().getBundleExtra("bundle").getString("email");
		name =  getIntent().getBundleExtra("bundle").getString("name");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.promo, menu);
		return true;
	}

	
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
			case R.id.vizualizarCarrinho:
				Intent myIntent = new Intent(getBaseContext(), CarrinhoActivity.class);
				myIntent.putExtra("email", mail);
				myIntent.putExtra("nome", name);
				startActivity(myIntent);
				return true;
				
			case R.id.selecionarFiltroCarrinho:
				selecionarFiltro();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	
	private void selecionarFiltro() {
		
		final String[] items = categorias.toArray(new String[categorias.size()]);
		boolean [] selected = new boolean[categorias.size()];
		
		for (int i = 0; i < categorias.size(); i++) {
			if (categoriasSelecionadas.contains(items[i])) {
				selected[i] = true;
			} else {
				selected[i] = false;
			}
			
		}
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(PromoActivity.this);
		builder.setCancelable(false);
		builder.setPositiveButton("Selecionar", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList<Produto> produtosFiltrados = new ArrayList<Produto>();
				for (Produto produto : values) {
					if (categoriasSelecionadas.contains(produto.getCategoria())) {
						produtosFiltrados.add(produto);
					}
				}
				
				PromoArrayAdapter adapterProdutosFiltrados = new PromoArrayAdapter(PromoActivity.this, produtosFiltrados);
				lv.setAdapter(adapterProdutosFiltrados);
				adapterProdutosFiltrados.notifyDataSetChanged();
				
			}
		});
		
		builder.setTitle("Exibir produtos por categoria").setMultiChoiceItems(items, selected, new OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				String categoria = items[which];
				if (isChecked) {
					categoriasSelecionadas.add(categoria);
				} else {
					categoriasSelecionadas.remove(categoria);
				}
				
			}
		});
		alerta = builder.create();
		alerta.show();
	}
	
}
