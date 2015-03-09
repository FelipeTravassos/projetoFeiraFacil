package com.esbr.feirafacilsmartphone.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import com.esbr.feirafacilsmartphone.R;
import com.esbr.feirafacilsmartphone.supermercado.Carrinho;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class MailTask extends AsyncTask<Message, Void, Void>{
	private Activity activity;
    private ProgressDialog progressDialog = null;
	
	 public MailTask(
		        final Activity act) {
		        this.activity = act;
		        progressDialog = ProgressDialog.show(act,
		                act.getText(R.string.please_wait),
		                act.getText(R.string.sending_order), true);
		    }

	 @Override
	 protected final void onPreExecute() {
		 super.onPreExecute();
	 }

	 @Override
	 protected final void onPostExecute(final Void aVoid) {
		 super.onPostExecute(aVoid);
		 Carrinho.getInstance().limparCarrinho();;
		        progressDialog.dismiss();
		        Toast.makeText(activity.getApplicationContext(), R.string.sucess_order,
		                Toast.LENGTH_LONG).show();
		        
		        activity.finish();
		    }
		    
	@Override
	protected final Void doInBackground(Message... messages) {
		try {
            Transport.send(messages[0]);
        } catch (MessagingException e) {
            Log.e(e.toString(), e.getMessage());
        }
        return null;
		
	}

}