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
import com.esbr.feirafacilsmartphone.supermercado.Categoria;
import com.esbr.feirafacilsmartphone.supermercado.Produto;
import com.esbr.feirafacilsmartphone.util.DownloadImage;
import com.esbr.feirafacilsmartphone.util.DownloadImageCategoria;
import com.esbr.feirafacilsmartphone.util.DownloadImageFacebook;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PromoActivity extends Activity implements ListView.OnItemClickListener{

	private ListView lv;
	private ArrayList<Produto> values;
	private ArrayList<Categoria> categoriasSelecionadas;
	private ArrayList<Categoria> categorias;
	private PromoArrayAdapter adapter;
	private String mailFacebook;
	private String nameFacebook;
	private String userFacebookId;
	
	private DrawerLayout drawer;
	private ActionBarDrawerToggle toggle;
	private ListView listView;	
	private CharSequence drawerTitle;
	private CharSequence title;	
	private DrawerAdapter drawerAdapter;
	
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
		categorias = new ArrayList<Categoria>();
		categoriasSelecionadas = new ArrayList<Categoria>();		
		
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
		       		       	
		       	Produto produto = new Produto(id, nomeProduto, descricaoProduto, valorUnitarioProduto, categoriaProduto, 0, imagemLink);
		       	
		       	new DownloadImage(produto, adapter).execute(imagemLink).get();
		       	
		       	values.add(produto);
		    }
			
			
			String resultCategorias = new TaskAllCategorias().execute().get();
			JSONArray jsonCategorias = new JSONArray(resultCategorias);
			for (int i = 0; i < jsonCategorias.length(); i++) {
		       	JSONObject jsonObj = jsonCategorias.getJSONObject(i);
		       	
		       	String nomeCategoria = jsonObj.get("nome").toString();
		       	String imagemLink = jsonObj.get("imagemLink").toString();
		       	
		       	Categoria categoria = new Categoria(nomeCategoria, imagemLink);
		       	
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
		
		mailFacebook = getIntent().getBundleExtra("bundle").getString("emailFacebook");
		nameFacebook =  getIntent().getBundleExtra("bundle").getString("nameFacebook");
		userFacebookId = getIntent().getBundleExtra("bundle").getString("userFacebookId");
		
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
				if (toggle.onOptionsItemSelected(item)) {
					return true;
				}
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void completeMenu() {
		
		drawerAdapter = new DrawerAdapter(this);
		
		DrawerItem infoUser = new DrawerItem(nameFacebook, 0, "informacao_usuario");
		new DownloadImageFacebook(infoUser, drawerAdapter).execute("https://graph.facebook.com/"+userFacebookId+"/picture?type=normal");
	
		drawerAdapter.adicionarInformacaoUsuario(infoUser);
		
		drawerAdapter.adicionarMenu("Categorias");
		for (int i = 0; i < categorias.size(); i++) {
			
			DrawerItem categoria = new DrawerItem(categorias.get(i).getNomeCategoria(), 0, "categoria");
			
			new DownloadImageCategoria(categoria, drawerAdapter).execute(categorias.get(i).getImagemLinkCategoria());
			drawerAdapter.adicionarCategoria(categoria);
		}
		
		drawerAdapter.adicionarMenu("Pedidos");
		//drawerAdapter.adicionarCategoria("Solicitados", R.drawable.ic_timer_orange);
		//drawerAdapter.adicionarCategoria("Concluídos", R.drawable.ic_done_orange);
		
	}
	
	@Override
	public final void onItemClick(final AdapterView<?> parent, final View view,
			final int position, final long id) {
		selectedItem(position);

	}
	
	private void selectedItem(final int position) {
		
		DrawerItem item = (DrawerItem)drawerAdapter.getItem(position);
		String titleMenu = item.getTitle();		
		
		drawerAdapter.resetarCheck();
		boolean selectedCategoria = false;
		for (Categoria categoria : categorias) {
			if (categoria.getNomeCategoria().equalsIgnoreCase(titleMenu)) {
				selectedCategoria = true;
				break;
			}
		}
		
		if (selectedCategoria) {
			
			ArrayList<Produto> produtosFiltrados = getProductCategory(titleMenu);		
			
			adapter = new PromoArrayAdapter(PromoActivity.this, produtosFiltrados);
			lv.setAdapter(adapter);
			adapter.notifyDataSetChanged();		
			
			getActionBar().setTitle(titleMenu);
			getActionBar().setSubtitle("Categorias");
			
		} else if (titleMenu.equalsIgnoreCase("Solicitados") || titleMenu.equalsIgnoreCase("Concluídos")) {
			getActionBar().setTitle(titleMenu);
			getActionBar().setSubtitle("Pedidos");
		}
		
		;
		drawerAdapter.setChecked(position, true);
		drawer.closeDrawer(listView);
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
