package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
import com.esbr.feirafacilsmartphone.server.TaskAllProducts;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_promo);

	    getActionBar().setDisplayHomeAsUpEnabled(true);

		
		ListView lv = (ListView) findViewById(R.id.list_prod_promo);
		String result;
		JSONArray json;
		ArrayList<Produto> values = new ArrayList<Produto>();
		Log.i("teste", "teste");
		try {
			
			result = new TaskAllProducts().execute().get();
			json = new JSONArray(result);
			
			for (int i = 0; i < json.length(); i++) {
		       	JSONObject jsonObj = json.getJSONObject(i);
		       	Produto obj = new Produto(jsonObj.get("name").toString(), Float.parseFloat(jsonObj.get("value").toString()),jsonObj.get("id_category").toString());
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
		
		return super.onOptionsItemSelected(item);
	}

	
	
	
	

}
