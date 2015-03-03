package com.esbr.feirafacilsmartphone.server;

import android.os.AsyncTask;

public class TaskAllProducts extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		
		return Request.getAllProducts();
	}

}
