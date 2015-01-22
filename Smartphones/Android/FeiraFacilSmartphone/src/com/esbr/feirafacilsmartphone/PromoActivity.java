package com.esbr.feirafacilsmartphone;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
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

public class PromoActivity extends Activity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	int selected;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_promo);

	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);						
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.promo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.selectsuper) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.selecionar_supermercado);
	        builder.setMessage(getNomeSupermercado())
	               .setPositiveButton(R.string.selecionar, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   Intent myIntent = new Intent(getBaseContext(), SupermercadoActivity.class);
		                   myIntent.putExtra("SUPERMERCADO", getNomeSupermercado());
		                   startActivity(myIntent);
	                   }
	               })
	               .setNegativeButton(R.string.voltar, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        AlertDialog dialog = builder.create();
	        dialog.show();
	        return true;
		}else if (id == R.id.ajuda) {
			Toast toast = Toast.makeText(getApplicationContext(), "Ajuda", Toast.LENGTH_LONG);
			toast.show();
			return true;
		}else if (id == R.id.painel_usuario) {
			Toast toast = Toast.makeText(getApplicationContext(), "Painel usuário", Toast.LENGTH_LONG);
			toast.show();
			return true;
		}else if (id == R.id.sobre) {
			Toast toast = Toast.makeText(getApplicationContext(), "Sobre", Toast.LENGTH_LONG);
			toast.show();
			return true;
		}else if (id == R.id.action_settings) {
			Toast toast = Toast.makeText(getApplicationContext(), "Configurações", Toast.LENGTH_LONG);
			toast.show();
			return true;
		}else if(id == android.R.id.home){
			try{
				NavUtils.navigateUpFromSameTask(this);
			}catch(Exception e){	}
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String getNomeSupermercado() {
		Locale l = Locale.getDefault();
		switch (selected) {
		case 0:	
			return getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return getString(R.string.title_section2).toUpperCase(l);
		case 2:
			return getString(R.string.title_section3).toUpperCase(l);
		}
		return null;
	}
	
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		selected = tab.getPosition();
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:	
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_promo,
					container, false);

			ListView lv = (ListView) rootView.findViewById(R.id.list_prod_promo);

			Produto[] values = new Produto[10];
			values[0] = (new Produto("Produto 1","Descrição do produto a", 140.0));
			values[1] = (new Produto("Produto 2","Descrição do produto b", 40.0));
			values[2] = (new Produto("Produto 3","Descrição do produto c", 1.25));
			values[3] = (new Produto("Produto 4","Descrição do produto d", 0.75));
			values[4] = (new Produto("Produto 5","Descrição do produto e", 90.0));
			values[5] = (new Produto("Produto 6","Descrição do produto f", 79.90));
			values[6] = (new Produto("Produto 7","Descrição do produto g", 250.0));
			values[7] = (new Produto("Produto 8","Descrição do produto h", 50.0));
			values[8] = (new Produto("Produto 9","Descrição do produto i", 76.34));
			values[9] = (new Produto("Produto 0","Descrição do produto j", 2.38));
			
			lv.setAdapter(new PromoArrayAdapter(getActivity(), values)); 
			
			return rootView;
		}
	}

}
