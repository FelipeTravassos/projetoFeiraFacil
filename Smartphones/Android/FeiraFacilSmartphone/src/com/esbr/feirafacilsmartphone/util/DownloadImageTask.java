package com.esbr.feirafacilsmartphone.util;

import java.io.InputStream;

import com.esbr.feirafacilsmartphone.PromoActivity;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    
	private Produto produto;
	private ProgressDialog progressDialog;
	private Context context;
	
    public DownloadImageTask(Produto produto, final Context context) {
    	this.produto = produto;
    	this.context = context;
    }
    
    @Override
    protected void onPreExecute() {
    	progressDialog = new ProgressDialog(context);
    	progressDialog.setTitle("Feira Fácil");
    	progressDialog.setMessage("Carregando produtos...");
    	progressDialog.setIndeterminate(false);
    	progressDialog.show();
    }
    
    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        produto.setImagem(result);
        progressDialog.dismiss();
    }
    

    
    	
}
