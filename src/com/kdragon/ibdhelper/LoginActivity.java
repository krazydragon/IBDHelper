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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.kdragon.other.EmailRetriever;
import com.kdragon.other.WebInterface;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validate.ConfirmValidate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class LoginActivity extends Activity implements OnClickListener{
	
	private Button _loginButton;
	private Button _regButton;
	private Button _skipButton;
	private EditText _regEmailInput;
	private EditText _usernameInput;
	private EditText _passwordInput;
	private EditText _regUserInput;
	private EditText _regPassInput;
	private EditText _regFNameInput;
	private EditText _regLNameInput;
	private EditText _regWeightInput;
	private Form _loginForm;
	private Form _regForm;
	private String _msg;
	
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);
			
			Boolean connected = WebInterface.getConnectionStatus(this);
      		
	      	if(!connected){
	    			Crouton.makeText(this, "No network found!", Style.ALERT).show();
	     		}
	      	_loginForm = new Form();
			//find and listen to login page
			_loginButton = (Button)findViewById(R.id.loginButton);
			_regButton = (Button)findViewById(R.id.RegisterScreenButton);
			_skipButton= (Button)findViewById(R.id.SkipRegisterScreenButton);
			_loginButton.setOnClickListener(this);
			_regButton.setOnClickListener(this);
			_skipButton.setOnClickListener(this);
			_usernameInput = (EditText)findViewById(R.id.loginUsername);
			_passwordInput = (EditText)findViewById(R.id.loginPassword);
			
			//check user input
			Validate username = new Validate(_usernameInput);
			Validate password = new Validate(_passwordInput);

			username.addValidator(new NotEmptyValidator(this)); 
			password.addValidator(new NotEmptyValidator(this)); 
			
			_loginForm.addValidates(username);
			_loginForm.addValidates(password);
			
	}

	@Override
	public void onClick(View v) {
		
		
		//Login old user, Register new user, or skip and test application. 
		if(v == _loginButton){
			
			if(_loginForm.validate()){
				//Toast.makeText(getApplicationContext(), "Valid Form", Toast.LENGTH_LONG).show();
				ParseUser.logInInBackground(_usernameInput.getText().toString(), _passwordInput.getText().toString(), new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	Log.i("login","worked");
					    	_msg = "Welcome back " + _usernameInput.getText().toString() + "!";
						      loginUser();
					    } else {
					    	//show error msg
					    	Log.i("login",e.toString());
					    	Crouton.makeText(LoginActivity.this,  e.toString(), Style.ALERT).show();
					    }
					  }
					});
			}
			
			
			//loginUser();
			
		}else if (v == _skipButton){
			
			
			ParseAnonymousUtils.logIn(new LogInCallback() {
				  @Override
				  public void done(ParseUser user, ParseException e) {
				    if (e != null) {
				      
				    } else {
				      Log.d("MyApp", "Anonymous user logged in.");
				      _msg = "Anonymous user logged in!";
				      loginUser();
				    }
				  }
				});
			 

			
		}else if(v == _regButton){
			
			setContentView(R.layout.fragment_edituser);
			Button signupButton = (Button)findViewById(R.id.SignupButton);
			signupButton.setOnClickListener(this);
			
			_regForm = new Form();
			
			_regUserInput = (EditText)findViewById(R.id.regUsername);
			_regPassInput = (EditText)findViewById(R.id.descriptionInput);
			EditText regRePassInput = (EditText)findViewById(R.id.regRePassword);
			_regEmailInput = (EditText)findViewById(R.id.regEmail);
			_regFNameInput = (EditText)findViewById(R.id.regFirstName);
			_regLNameInput = (EditText)findViewById(R.id.regLastName);
			_regWeightInput = (EditText)findViewById(R.id.nameInput);
			
			_regEmailInput.setText(EmailRetriever.getEmail(this));
			
			Validate regUser = new Validate(_regUserInput);
			Validate regEmail = new Validate(_regEmailInput);
			Validate regFName = new Validate(_regFNameInput);
			Validate regLName = new Validate(_regLNameInput);
			Validate regWeight = new Validate(_regWeightInput);
			ConfirmValidate confirmPass = new ConfirmValidate(_regPassInput, regRePassInput);
			
			regUser.addValidator(new NotEmptyValidator(this));
			regEmail.addValidator(new NotEmptyValidator(this));
			regFName.addValidator(new NotEmptyValidator(this));
			regLName.addValidator(new NotEmptyValidator(this));
			regWeight.addValidator(new NotEmptyValidator(this));
			
			_regForm.addValidates(regUser);
			_regForm.addValidates(regEmail);
			_regForm.addValidates(regFName);
			_regForm.addValidates(regLName);
			_regForm.addValidates(regWeight);
			_regForm.addValidates(confirmPass);

	}else{
			ParseUser user = new ParseUser();
			

			if(_regForm.validate()){
				user.setUsername(_regUserInput.getText().toString());
				user.setPassword(_regPassInput.getText().toString());
				user.setEmail(_regEmailInput.getText().toString());
				user.put("firstName", _regFNameInput.getText().toString());
				user.put("lastName", _regLNameInput.getText().toString());
				user.put("weight", _regWeightInput.getText().toString());
				user.signUpInBackground(new SignUpCallback() {
					  public void done(ParseException e) {
					    if (e == null) {
					      _msg = "Thank you for registering " + _regUserInput.getText().toString() + "!";
					      loginUser();
					    } else {
					      // Sign up didn't succeed. Look at the ParseException
					      // to figure out what went wrong
					    	Crouton.makeText(LoginActivity.this,  e.toString(), Style.ALERT).show();
					    }
					  }
					});
			}
			
			
			
			
		}
		
	}
	//go back to main page
	private void loginUser(){
		Intent returnIntent = new Intent();
		 returnIntent.putExtra("msg",_msg);
		 setResult(RESULT_OK, returnIntent);    
		 finish();
	}
	
	 
}
