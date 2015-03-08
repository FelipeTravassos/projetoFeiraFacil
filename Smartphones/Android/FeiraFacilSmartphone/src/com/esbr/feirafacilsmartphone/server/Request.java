package com.esbr.feirafacilsmartphone.server;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class Request {	
	
	public static String getAllProducts(){
		
		String url = "http://54.94.179.210:8080";
        String response = "";
        
		try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get =  new HttpGet(url+"/produtos");
            HttpResponse httpResponse = client.execute(get);
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}