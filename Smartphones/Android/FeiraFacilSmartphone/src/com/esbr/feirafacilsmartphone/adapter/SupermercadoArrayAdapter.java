package com.esbr.feirafacilsmartphone.adapter;

import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.supermercado.Supermercado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class SupermercadoArrayAdapter extends ArrayAdapter<Supermercado>{
	private final Context context;
	private final Supermercado[] supermercados;

	public SupermercadoArrayAdapter(Context context, Supermercado[] supermercados) {
		super(context, android.R.layout.simple_list_item_1, supermercados);
		this.context = context;
		this.supermercados = supermercados;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.supermercado, parent, false);
		TextView nomeTV = (TextView) rowView.findViewById(R.id.nomeSupermercado);
		
		nomeTV.setText(supermercados[position].getNome());
 
		return rowView;
	}
}
