package com.esbr.feirafacilsmartphone.adapter;

import java.util.ArrayList;

import com.esbr.feirafacilsmartphone.PromoActivity;
import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PromoArrayAdapter extends ArrayAdapter<Produto>{
	
	private final Context context;
	private final ArrayList<Produto> values;
 
	public PromoArrayAdapter(Context context, ArrayList<Produto> values) {
		super(context, android.R.layout.simple_list_item_1, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		final View rowView = inflater.inflate(R.layout.prod_promo, parent, false);
		
		TextView nomeProduto = (TextView) rowView.findViewById(R.id.nomeProdPromo);
		TextView precoProduto = (TextView) rowView.findViewById(R.id.precoProdPromo);
		TextView descricaoProduto = (TextView) rowView.findViewById(R.id.descProdPromo);
		
		nomeProduto.setText(values.get(position).getNome());
		precoProduto.setText("R$ " + String.format("%.2f", values.get(position).getPreco()).replace(".", ","));
		descricaoProduto.setText(values.get(position).getDescricao());
		
		Button adicionaQuantidadeItem = (Button) rowView.findViewById(R.id.buttonAddProd);
		adicionaQuantidadeItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView quantidadeAddProd = (TextView) rowView.findViewById(R.id.quantidadeAddProd);
				TextView precoProduto = (TextView) rowView.findViewById(R.id.precoProdPromo);

				Produto produto = values.get(position);
				produto.setQuantidade(produto.getQuantidade() + 1);
				
				quantidadeAddProd.setText(String.format("%02d", produto.getQuantidade()));
				precoProduto.setText("R$ " + String.format("%.2f", produto.getPreco()*produto.getQuantidade()).replace(".", ","));
			}
		});
		
		Button removeQuantidadeItem = (Button) rowView.findViewById(R.id.buttonDelProd);
		removeQuantidadeItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Produto produto = values.get(position);
				if (produto.getQuantidade() == 1) {
					
				} else {
					TextView quantidadeAddProd = (TextView) rowView.findViewById(R.id.quantidadeAddProd);
					TextView precoProduto = (TextView) rowView.findViewById(R.id.precoProdPromo);

					Produto produto2 = values.get(position);
					produto2.setQuantidade(produto2.getQuantidade() - 1);
					
					quantidadeAddProd.setText(String.format("%02d", produto2.getQuantidade()));
					precoProduto.setText("R$ " + String.format("%.2f", produto2.getPreco()*produto2.getQuantidade()).replace(".", ","));
				}
			}
		});
		

		Button adicionarItemCarrinho = (Button) rowView.findViewById(R.id.addProdCarrinho);
		adicionarItemCarrinho.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Produto produto = values.get(position);
				Carrinho.getInstance().adicionarItemCarrinho(produto);
				if (produto.getQuantidade() == 1) {
					Toast.makeText(context, "Item adicionado no carrinho", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Itens adicionados no carrinho", Toast.LENGTH_SHORT).show();
				}
			}
		});
	
		return rowView;
	}
}
