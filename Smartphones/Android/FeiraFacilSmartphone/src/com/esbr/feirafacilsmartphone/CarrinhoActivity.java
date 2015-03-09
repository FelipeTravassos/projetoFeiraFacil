package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esbr.feirafacilsmartphone.adapter.CarrinhoArrayAdapter;
import com.esbr.feirafacilsmartphone.server.TaskAllCategorias;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CarrinhoActivity extends Activity {
	
	private CarrinhoArrayAdapter adapter;
	
	private ListView lv;
	
	private AlertDialog alerta;

	private ArrayList<String> categorias;
	
	private ArrayList<String> categoriasSelecionadas; 
	
	ArrayList<Produto> produtosFiltrados;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_carrinho);
		
		lv = (ListView) findViewById(R.id.list_prod_carrinho);
		adapter = new CarrinhoArrayAdapter(this,Carrinho.getInstance().getListaItems());
		lv.setAdapter(adapter); 
		
		categorias = new ArrayList<String>();
		categoriasSelecionadas = new ArrayList<String>();
		produtosFiltrados = new ArrayList<Produto>();
		
		try {
			String resultCategorias = new TaskAllCategorias().execute().get();
			JSONArray jsonCategorias = new JSONArray(resultCategorias);
			for (int i = 0; i < jsonCategorias.length(); i++) {
		       	JSONObject jsonObj = jsonCategorias.getJSONObject(i);
		       	
		       	String categoria = jsonObj.get("nome").toString();
		       	categorias.add(categoria);
		       	categoriasSelecionadas.add(categoria);
			}
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
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
		case R.id.selecionarFiltroCarrinho:
			selecionarFiltro();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void limparCarrinho() { 
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(CarrinhoActivity.this);
		builder.setTitle("Limpar Carrinho"); 
		builder.setMessage("Deseja realmente remover todos os itens do carrinho?");
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				arg0.cancel();
			} 
		});
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) {
				Carrinho.getInstance().limparCarrinho();
				adapter = (CarrinhoArrayAdapter) lv.getAdapter();
				adapter.clear();
				adapter.notifyDataSetChanged();
				Toast.makeText(CarrinhoActivity.this, "Concluído", Toast.LENGTH_SHORT).show();
			} 
		}); 
		alerta = builder.create(); 
		alerta.show();
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
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(CarrinhoActivity.this);
		builder.setCancelable(false);
		builder.setPositiveButton("Selecionar", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				produtosFiltrados = new ArrayList<Produto>();
				for (Produto produto : Carrinho.getInstance().getListaItems()) {
					if (categoriasSelecionadas.contains(produto.getCategoria())) {
						produtosFiltrados.add(produto);
					}
				}
				
				CarrinhoArrayAdapter adapterProdutosFiltrados = new CarrinhoArrayAdapter(CarrinhoActivity.this, produtosFiltrados);
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
