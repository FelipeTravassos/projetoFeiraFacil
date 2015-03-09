package com.esbr.feirafacilsmartphone.adapter;

import java.util.ArrayList;

import com.esbr.feirafacilsmartphone.CarrinhoActivity;
import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CarrinhoArrayAdapter extends ArrayAdapter<Produto>{
	
	private final Context context;
	private final ArrayList<Produto> values;
	private AlertDialog alerta; 
 
	public CarrinhoArrayAdapter(Context context, ArrayList<Produto> values) {
		super(context, android.R.layout.simple_list_item_1, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		final View rowView = inflater.inflate(R.layout.prod_carrinho, parent, false);
		
		TextView nomeProdutoCarrinho = (TextView) rowView.findViewById(R.id.nomeProdCarrinho);
		TextView descricaoProdutoCarrinho = (TextView) rowView.findViewById(R.id.descProdCarrinho);
		TextView quantidadeProdutoCarrinho = (TextView) rowView.findViewById(R.id.quantidadeProdCarrinho);
		TextView precoProdutoCarrinho = (TextView) rowView.findViewById(R.id.precoProdCarrinho);
		ImageView imagemProdutoCarrinho = (ImageView) rowView.findViewById(R.id.imgProdCarrinho);
		
		Produto produtoCarrinho = values.get(position);
		
		nomeProdutoCarrinho.setText(produtoCarrinho.getNome());
		descricaoProdutoCarrinho.setText(produtoCarrinho.getDescricao());
		quantidadeProdutoCarrinho.setText(String.format("%02d",(produtoCarrinho.getQuantidade())));
		precoProdutoCarrinho.setText("R$ " + String.format("%.2f", (produtoCarrinho.getPreco()*produtoCarrinho.getQuantidade())).replace(".", ","));
		
		imagemProdutoCarrinho.setImageBitmap(produtoCarrinho.getImagem());
		
		Button adicionaQuantidadeItem = (Button) rowView.findViewById(R.id.buttonAddProdCarrinho);
		adicionaQuantidadeItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView quantidadeAddProd = (TextView) rowView.findViewById(R.id.quantidadeProdCarrinho);
				TextView precoProduto = (TextView) rowView.findViewById(R.id.precoProdCarrinho);

				Produto produto = values.get(position);
				
				produto.setQuantidade(produto.getQuantidade()+1);
				
				quantidadeAddProd.setText(String.format("%02d",(produto.getQuantidade())));
				precoProduto.setText("R$ " + String.format("%.2f", produto.getPreco()*produto.getQuantidade()).replace(".", ","));
			}
		});
		
		Button removeQuantidadeItem = (Button) rowView.findViewById(R.id.buttonDelProdCarrinho);
		removeQuantidadeItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				TextView precoProduto = (TextView) rowView.findViewById(R.id.precoProdCarrinho);
				TextView quantidadeAddProd = (TextView) rowView.findViewById(R.id.quantidadeProdCarrinho);
				
				Produto produto = values.get(position);
				
				if (produto.getQuantidade() == 1) {
					removerItemCarrinho(produto,context);
				} else {
					produto.setQuantidade(produto.getQuantidade()-1);
				}
				
				quantidadeAddProd.setText(String.format("%02d",(produto.getQuantidade())));
				precoProduto.setText("R$ " + String.format("%.2f", produto.getPreco()*produto.getQuantidade()).replace(".", ","));
			}
		});
		

		Button removerItemCarrinho = (Button) rowView.findViewById(R.id.removeAllProdCarrinho);
		removerItemCarrinho.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Produto produto = values.get(position);
				removerItemCarrinho(produto, context);
			}
		});
		
		return rowView;
	}
	
	
	private void removerItemCarrinho(final Produto produto, final Context context) { 
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(produto.getNome()); 
		builder.setIcon(resize(context, R.drawable.icone_fazerfeira_telapromocoes,50));
		builder.setMessage("Deseja realmente remover este item do carrinho?");
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) { 
				arg0.cancel();
			} 
		});
		
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface arg0, int arg1) {
				Carrinho.getInstance().removerItemCarrinho(produto);
				notifyDataSetChanged();
				Toast.makeText(context, "Concluído", Toast.LENGTH_SHORT).show();
			} 
		}); 
		alerta = builder.create(); 
		alerta.show();
	}
	
	private Drawable resize(Context context, int id, int scale) {
		Drawable image = context.getResources().getDrawable(id);
	    Bitmap b = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, scale, scale, false);
	    return new BitmapDrawable(context.getResources(), bitmapResized);
	}
}
