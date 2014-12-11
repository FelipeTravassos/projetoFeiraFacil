package com.esbr.feirafacilsmartphone.adapter;

import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PromoArrayAdapter extends ArrayAdapter<Produto>{
	private final Context context;
	private final Produto[] values;
 
	public PromoArrayAdapter(Context context, Produto[] values) {
		super(context, android.R.layout.simple_list_item_1, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.prod_promo, parent, false);
		TextView nomeTV = (TextView) rowView.findViewById(R.id.nomeProdPromo);
		TextView descTV = (TextView) rowView.findViewById(R.id.descProdPromo);
		TextView precoTV = (TextView) rowView.findViewById(R.id.precoProdPromo);
		
		nomeTV.setText(values[position].getNome());
		descTV.setText(values[position].getDesc());
		precoTV.setText("R$ " + values[position].getPreco());
 
		return rowView;
	}
}