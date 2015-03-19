package com.esbr.feirafacilsmartphone;

import java.util.Arrays;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class LoginActivity extends Activity {
    
    private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callBack = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChanged(session, state, exception);
			
		}
	};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        uiHelper = new UiLifecycleHelper(this, callBack);
		uiHelper.onCreate(savedInstanceState);
		
		LoginButton login = (LoginButton)findViewById(R.id.authButton);
		login.setPublishPermissions(Arrays.asList("email","public_profile","user_friends"));
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    
    @Override
	protected void onResume(){
		super.onResume();
		Session session = Session.getActiveSession();
		if(session != null && (session.isClosed() || session.isOpened())) {
			onSessionStateChanged(session, session.getState(), null);
		}
		
	}
	
	@Override
	protected void onPause(){
		super.onPause();		
		uiHelper.onPause();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle bundle){
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	public void onSessionStateChanged(Session session, SessionState state, Exception exception){
		if(session!=null && session.isOpened()){
			Log.i("Script", "usuario conectado");
			
			Request.newMeRequest(session,new Request.GraphUserCallback(){
				@Override
				public void onCompleted(GraphUser user, Response response){
					if(user!=null){
						Intent myIntent = new Intent(getBaseContext(), PromoActivity.class);
						Bundle bundle = new Bundle();
						bundle.putCharSequence("name", user.getName());
						bundle.putCharSequence("email", user.getProperty("email").toString());
						myIntent.putExtra("bundle", bundle);
			        	startActivity(myIntent);
			        	finish();
					}
				}
			}).executeAsync();
		}else
			Log.i("Script", "usuario não conectado");
	}
}
