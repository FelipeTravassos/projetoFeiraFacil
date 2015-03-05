package com.esbr.feirafacilsmartphone.adapter;

import java.util.ArrayList;

import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CarrinhoArrayAdapter extends ArrayAdapter<Produto>{
	private final Context context;
	private final ArrayList<Produto> values;
 
	public CarrinhoArrayAdapter(Context context, ArrayList<Produto> values) {
		super(context, android.R.layout.simple_list_item_1, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.prod_carrinho, parent, false);
		TextView nomeProduto = (TextView) rowView.findViewById(R.id.nomeProdCarrinho);
		//TextView descTV = (TextView) rowView.findViewById(R.id.descProdPromo);
		TextView precoProduto = (TextView) rowView.findViewById(R.id.precoProdCarrinho);
		TextView quantidadeProduto = (TextView) rowView.findViewById(R.id.quantidadeProdCarrinho);
		
		nomeProduto.setText(values.get(position).getNome());
		//descTV.setText(values[position].getCategoria());
		precoProduto.setText("R$ " + values.get(position).getPreco() * values.get(position).getQuantidade());
		quantidadeProduto.setText("qnt. " + values.get(position).getQuantidade());
 
		return rowView;
	}
}
