package com.esbr.feirafacilsmartphone.server;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class Request {	
	
	public static String getAllProducts(){
		String url = "http://supermercadoideal.herokuapp.com/mobileService";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get =  new HttpGet(url+"/getAllProducts");
            HttpResponse httpResponse = client.execute(get);
            String response = EntityUtils.toString(httpResponse.getEntity());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}