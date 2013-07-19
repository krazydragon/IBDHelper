/*
 * project	IBDHelper
 * 
 * package	com.kdragon.ibdhelper
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Jul 15, 2013
 */
package com.kdragon.ibdhelper;

import com.kdragon.other.EmailRetriever;
import com.kdragon.other.WebInterface;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener{
	
	Button _loginButton;
	Button _regButton;
	Button _skipButton;
	EditText _emailEditText;
	String _username;
	String _password;
	
	
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);
			
			Boolean connected = WebInterface.getConnectionStatus(this);
      		
	      	if(!connected){
	    			Crouton.makeText(this, "No network found!", Style.ALERT).show();
	     		}
			//find and listen to buttons
			_loginButton = (Button)findViewById(R.id.loginButton);
			_regButton = (Button)findViewById(R.id.RegisterScreenButton);
			_skipButton= (Button)findViewById(R.id.SkipRegisterScreenButton);
			_loginButton.setOnClickListener(this);
			_regButton.setOnClickListener(this);
			_skipButton.setOnClickListener(this);
			
			
			
			
	}

	@Override
	public void onClick(View v) {
		
		EditText verifyPasswordInput = (EditText)findViewById(R.id.verifyPassword);
		
		//Login old user, Register new user, or skip and test application. 
		if(v == _loginButton){
			
			
			EditText usernameInput = (EditText)findViewById(R.id.loginUsername);
			EditText passwordInput = (EditText)findViewById(R.id.loginPassword);
			
			
			if(verifyPasswordInput.getVisibility() != View.VISIBLE){
				ParseUser.logInInBackground(usernameInput.getText().toString(), passwordInput.getText().toString(), new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	finish();
					    } else {
					    	Log.i("login","failed");
					    }
					  }
					});
			}else{
				
				setContentView(R.layout.fragment_edituser);
				Button signupButton = (Button)findViewById(R.id.SignupButton);
				signupButton.setOnClickListener(this);
				_emailEditText = (EditText)findViewById(R.id.regEmail);
				_emailEditText.setText(EmailRetriever.getEmail(this));
				_username = usernameInput.getText().toString();
				_password = passwordInput.getText().toString();
			}
			
					
			
		}else if(v == _regButton){
			
			if(verifyPasswordInput.getVisibility() != View.VISIBLE){
				verifyPasswordInput.setVisibility(View.VISIBLE);
			}else{
				verifyPasswordInput.setVisibility(View.INVISIBLE);
			}
			
			
		}else if (v == _skipButton){
			
			Crouton.makeText(this, "Skipped!", Style.ALERT).show();
			ParseAnonymousUtils.logIn(new LogInCallback() {
				  @Override
				  public void done(ParseUser user, ParseException e) {
				    if (e != null) {
				      //Log.d("MyApp", "Anonymous login failed.");
				    } else {
				      Log.d("MyApp", "Anonymous user logged in.");
				    	finish();
				    }
				  }
				});
			
		}else{
			ParseUser user = new ParseUser();
			
			user.setUsername(_username);
			user.setPassword(_password);
			user.setEmail(_emailEditText.getText().toString());
			user.signUpInBackground(new SignUpCallback() {
				  public void done(ParseException e) {
				    if (e == null) {
				      // Hooray! Let them use the app now.
				    	finish();
				    } else {
				      // Sign up didn't succeed. Look at the ParseException
				      // to figure out what went wrong
				    	Log.e("signup", e.toString());
				    }
				  }
				});
			
			
		}
		
	}
}
