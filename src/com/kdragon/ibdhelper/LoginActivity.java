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

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.os.Bundle;

public class LoginActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);
			
			Crouton.makeText(this, EmailRetriever.getEmail(this), Style.ALERT).show();
	}
}
