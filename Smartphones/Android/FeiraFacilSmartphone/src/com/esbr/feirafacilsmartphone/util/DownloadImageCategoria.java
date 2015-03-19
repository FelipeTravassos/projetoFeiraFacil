package com.esbr.feirafacilsmartphone.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.esbr.feirafacilsmartphone.adapter.DrawerAdapter;
import com.esbr.feirafacilsmartphone.adapter.DrawerItem;
import com.esbr.feirafacilsmartphone.adapter.PromoArrayAdapter;
import com.esbr.feirafacilsmartphone.supermercado.Categoria;
import com.esbr.feirafacilsmartphone.supermercado.Produto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

public class DownloadImageCategoria extends AsyncTask<String, Void, Bitmap> {
    
	private DrawerItem drawerItem;
	private String pathToSave;
	private DrawerAdapter adapter;
	
    public DownloadImageCategoria(DrawerItem drawerItem, DrawerAdapter drawerAdapter) {
    	this.drawerItem = drawerItem;
    	this.adapter = drawerAdapter;
    	pathToSave = Environment.getExternalStorageDirectory().getPath() + "/feirafacil/imagens/categorias";
    }
    
    @Override
    protected void onPreExecute() {

    }
    
    @Override
    protected Bitmap doInBackground(String... urls) {
    	
    	String pathImageProduto = pathToSave + "/" + drawerItem.getTitle() + ".png";
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

				File pictureFile = new File(pathToSave, drawerItem.getTitle() + ".png");

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
    	drawerItem.setImagem(result);
        adapter.notifyDataSetChanged();
    }
}
