package com.esbr.feirafacilsmartphone.server;

import android.os.AsyncTask;

public class TaskAllCategorias extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		
		return Request.getCategorias();
	}

}
