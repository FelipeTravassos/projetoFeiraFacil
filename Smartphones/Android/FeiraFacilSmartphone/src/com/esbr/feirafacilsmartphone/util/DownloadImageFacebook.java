package com.esbr.feirafacilsmartphone.util;

import java.io.InputStream;

import com.esbr.feirafacilsmartphone.adapter.DrawerAdapter;
import com.esbr.feirafacilsmartphone.adapter.DrawerItem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class DownloadImageFacebook extends AsyncTask<String, Void, Bitmap> {
    
	private DrawerItem drawerItem;
	private DrawerAdapter adapter;
	
    public DownloadImageFacebook(DrawerItem drawerItem, DrawerAdapter adapter) {
    	this.drawerItem = drawerItem;
    	this.adapter = adapter;
    }
    
    @Override
    protected void onPreExecute() {

    }
    
    @Override
    protected Bitmap doInBackground(String... urls) {
    	Bitmap mIcon11 = null;
    	
			String urldisplay = urls[0];
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
    	drawerItem.setImagem(result);
        adapter.notifyDataSetChanged();
    }
}
