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
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.Layout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    
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
        
        uiHelper = new UiLifecycleHelper(this, callBack);
		uiHelper.onCreate(savedInstanceState);
		
		LoginButton login = (LoginButton)findViewById(R.id.authButton);
		login.setPublishPermissions(Arrays.asList("email","public_profile","user_friends"));

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mProgressView = findViewById(R.id.login_progress);
        
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
       /*Button loginWithFacebook = (Button) findViewById(R.id.vincular_facebook);
        loginWithFacebook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginWithFacebook();
            }
        });*/
    }

    private void attemptLoginWithFacebook() {
    	AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Deseja fazer login com o facebook?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder1.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
	}
    
    public void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);
        	Intent myIntent = new Intent(getBaseContext(), PromoActivity.class);
        	startActivity(myIntent);
        	finish();
        	//faz login
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
    
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.criar_conta) {
        	popUpCriarConta();
        	Toast toast = Toast.makeText(getApplicationContext(), "Criar conta", Toast.LENGTH_LONG);
			toast.show();
            return true;
        }else if (id == R.id.esqueci_senha) {
        	popUpEsqueciSenha();
            return true;
        }else if (id == R.id.login_com_face) {
        	Toast toast = Toast.makeText(getApplicationContext(), "Login com facebook", Toast.LENGTH_LONG);
			toast.show();
            return true;
        }else if (id == R.id.login_com_google) {
        	Toast toast = Toast.makeText(getApplicationContext(), "Login com google", Toast.LENGTH_LONG);
			toast.show();
            return true;
        }else
        return super.onOptionsItemSelected(item);
    }
    
    private void popUpCriarConta(){
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
    	alertDialog.setTitle(R.string.criar_conta);

    	LayoutInflater inflater = this.getLayoutInflater();
    	alertDialog.setView(inflater.inflate(R.layout.criar_conta, null));
    			
        alertDialog.setPositiveButton(R.string.salvar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                    }
                });
        alertDialog.setNegativeButton(R.string.voltar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
    
    /**
     * Create popup if clicked 'I forgot my password'
     */
    private void popUpEsqueciSenha(){
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
    	alertDialog.setTitle(R.string.esqueci_senha);
        alertDialog.setMessage(R.string.digite_seu_email);
        EditText input = new EditText(this);
        alertDialog.setView(input);
        alertDialog.setPositiveButton(R.string.recuperar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                    }
                });
        alertDialog.setNegativeButton(R.string.voltar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
    
    @Override
	protected void onResume(){
		super.onResume();
		Session session = Session.getActiveSession();
		if(session != null && (session.isClosed() || session.isOpened())) {
			onSessionStateChanged(session, session.getState(), null);
		}
		uiHelper.onResume();
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
						bundle.putCharSequence("name", user.getFirstName());
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
