package com.esbr.feirafacilsmartphone.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    
	private Produto produto;
	private String pathToSave;
	private PromoArrayAdapter adapter;
	
    public DownloadImage(Produto produto, PromoArrayAdapter adapter) {
    	this.produto = produto;
    	this.adapter = adapter;
    	pathToSave = Environment.getExternalStorageDirectory().getPath() + "/feirafacil/imagens/produtos";
    }
    
    @Override
    protected void onPreExecute() {

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
        adapter.notifyDataSetChanged();
    }
}
