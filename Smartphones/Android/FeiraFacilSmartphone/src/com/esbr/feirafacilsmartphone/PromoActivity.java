	package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.adapter.DrawerAdapter;
import com.esbr.feirafacilsmartphone.adapter.DrawerItem;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class PromoActivity extends Activity implements ListView.OnItemClickListener{

	private ListView lv;
	private ArrayList<Produto> values;
	private ArrayList<String> categoriasSelecionadas;
	private ArrayList<String> categorias;
	private PromoArrayAdapter adapter;
	private AlertDialog alerta;
	private String mailFacebook;
	private String nameFacebook;
	
	private DrawerLayout drawer;
	private ActionBarDrawerToggle toggle;
	private ListView listView;	
	private CharSequence drawerTitle;
	private CharSequence title;	
	private DrawerAdapter drawerAdapter;
	private ArrayList<String> menuTitles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.fragment_promo);
		
		getActionBar().setTitle(R.string.app_name);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    getActionBar().setHomeButtonEnabled(true);	    
		
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
		
		
		
		mailFacebook = getIntent().getBundleExtra("bundle").getString("email");
		nameFacebook =  getIntent().getBundleExtra("bundle").getString("name");
		
		drawerTitle = "Menu";
		completeMenu();
		listView = (ListView) findViewById(R.id.list_view_drawer);
		listView.setAdapter(drawerAdapter);
		listView.setOnItemClickListener(this);
		
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		toggle = new ActionBarDrawerToggle(this, drawer,R.drawable.ic_drawer,
				 R.string.drawer_open,R.string.drawer_close) {

			@Override
			public void onDrawerClosed(final View view) {
				getActionBar().setSubtitle(title);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(final View view) {
				getActionBar().setSubtitle(drawerTitle);
				invalidateOptionsMenu();
			}
		};
		
		drawer.setDrawerListener(toggle);
		
		if (savedInstanceState == null) {
			selectedItem(2);
		}
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
				myIntent.putExtra("email", mailFacebook);
				myIntent.putExtra("nome", nameFacebook);
				startActivity(myIntent);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void completeMenu() {
		menuTitles = new ArrayList<String>();
		
		menuTitles.add("nameFacebook");
		menuTitles.add("Categorias");		
		
		drawerAdapter = new DrawerAdapter(this);
		
		drawerAdapter.adicionarInformacaoUsuario(nameFacebook, R.drawable.caixa_foto);
		drawerAdapter.adicionarMenu(menuTitles.get(1));	
		
		
		
		for (int i = 0; i < categorias.size(); i++) {
			menuTitles.add(categorias.get(i).toString());
			drawerAdapter.adicionarCategoria(menuTitles.get(i+2), R.drawable.caixa_foto);
			
		}	
	}
	
	@Override
	public final void onItemClick(final AdapterView<?> parent, final View view,
			final int position, final long id) {
		selectedItem(position);

	}
	
	private void selectedItem(final int position) {
		
		DrawerItem item = (DrawerItem)drawerAdapter.getItem(position);
		String category = item.getTitle();		
		ArrayList<Produto> produtosFiltrados = getProductCategory(category);		
		
		adapter = new PromoArrayAdapter(PromoActivity.this, produtosFiltrados);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();		
		
		drawerAdapter.resetarCheck();
		setCustomTitle(menuTitles.get(position));
		drawerAdapter.setChecked(position, true);
		drawer.closeDrawer(listView);
	}
	
	private void setCustomTitle(final String newtitle) {
		this.title = newtitle;
		getActionBar().setSubtitle(title);

	}
	
	private ArrayList<Produto> getProductCategory(String category){
		ArrayList<Produto> produtosFiltrados = new ArrayList<Produto>();
		for (Produto produto : values) {
			if (category.equals(produto.getCategoria())) {
				produtosFiltrados.add(produto);
			}
		}		
		return produtosFiltrados;
		
	}
	
	
}
