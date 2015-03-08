package com.esbr.feirafacilsmartphone.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esbr.feirafacilsmartphone.PromoActivity;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    
	private Produto produto;
	private ProgressDialog progressDialog;
	private Context context;
	String pathToSave;
	
    public DownloadImage(Produto produto, final Context context) {
    	this.produto = produto;
    	this.context = context;
    	pathToSave = Environment.getExternalStorageDirectory().getPath() + "/feirafacil/imagens/produtos";
    }
    
    @Override
    protected void onPreExecute() {
    	progressDialog = new ProgressDialog(context);
    	progressDialog.setTitle("Feira Fácil");
    	progressDialog.setMessage("Carregando imagem...");
    	progressDialog.setIndeterminate(false);
    	progressDialog.show();
    }
    
    @Override
    protected Bitmap doInBackground(String... urls) {
    	
    	String pathImageProduto = pathToSave + "/" + produto.getId() + ".png";
    	File imagemCache = new File(pathImageProduto);
    	
    	Bitmap mIcon11 = null;
    	
    	if (imagemCache.exists()) {
    		mIcon11 = BitmapFactory.decodeFile(imagemCache.getAbsolutePath());
		} else {
			String urldisplay = urls[0];
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				File dir = new File(pathToSave);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				File pictureFile = new File(pathToSave, produto.getId() + ".png");

				FileOutputStream fos = new FileOutputStream(pictureFile);
				mIcon11.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        produto.setImagem(result);
        progressDialog.dismiss();
       
    }
}
