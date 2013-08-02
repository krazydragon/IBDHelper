/*
 * project	IBDHelper
 * 
 * package	com.kdragon.ibdhelper
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Jul 18, 2013
 */
package com.kdragon.ibdhelper;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	ParseUser currentUser;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		 
		 Button user =(Button)findViewById(R.id.Button03);
		 
		 user.setOnClickListener(new Button.OnClickListener(){

	        	@Override
	        	public void onClick(View v) {
	        		// TODO Auto-generated method stub

	        		addUserPopup();
	 
	        	}
	        	}
	        );
	}

	private void addUserPopup(){
		
		final PopupWindow pw;
		//We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) SettingsActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.userinfo,
                (ViewGroup) findViewById(R.id.userInfoView));
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        pw = new PopupWindow(layout, width, height, true);
        // display the popup in the center
       
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);//Intent loginIntent = new Intent(this, LoginActivity.class);
        
        
        Button cancel =(Button)layout.findViewById(R.id.button1);
       
        TextView userName = (TextView)layout.findViewById(R.id.usernameView);
        TextView name = (TextView)layout.findViewById(R.id.textView4);
        TextView email = (TextView)layout.findViewById(R.id.textView5);
        
        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
			//not working
			//userName.setText(currentUser.getUsername());
	        //email.setText(currentUser.getEmail());
	        name.setText(currentUser.getString("firstName")+ " " +currentUser.getString("lastName"));
		}
        

        cancel.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		pw.dismiss();
 
        	}
        	}
        );
        
        
        
	}
}
