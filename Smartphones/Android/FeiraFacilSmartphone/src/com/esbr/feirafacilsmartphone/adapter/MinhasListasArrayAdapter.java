package com.esbr.feirafacilsmartphone.adapter;

import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.user.listas.ListaProdutos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MinhasListasArrayAdapter extends ArrayAdapter<ListaProdutos>{
	private final Context context;
	private final ListaProdutos[] values;
 
	public MinhasListasArrayAdapter(Context context, ListaProdutos[] values) {
		super(context, android.R.layout.simple_list_item_1, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.lista_de_compra, parent, false);
		TextView quantidadeItemsTV = (TextView) rowView.findViewById(R.id.quantidadeItems);
		TextView nomeListaTV = (TextView) rowView.findViewById(R.id.nomeDaLista);
		
		nomeListaTV.setText(values[position].getNomeLista());
		quantidadeItemsTV.setText(""+values[position].getQuantidadeDeItems());
 
		return rowView;
	}
}
