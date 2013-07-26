/*
 * project	IBDHelper
 * 
 * package	com.kdragon.ibdhelper
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Jul 17, 2013
 */
package com.kdragon.ibdhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SupportActivity extends Activity implements OnClickListener {
	Button _linksButton;
	Button _groupsButton;
	Button _newsButton;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_support);
		_linksButton = (Button)findViewById(R.id.linksButton);
		_groupsButton = (Button)findViewById(R.id.groupsButton);
		_newsButton= (Button)findViewById(R.id.newsButton);
		_linksButton.setOnClickListener(this);
		_groupsButton.setOnClickListener(this);
		_newsButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		Intent supportDetailIntent = new Intent(this, SupportListview.class);
		if(v == _linksButton){
			supportDetailIntent.putExtra("catagory", "links");
			
			
		}else if (v == _groupsButton){
			supportDetailIntent.putExtra("catagory", "groups");
			
		}else{
			supportDetailIntent.putExtra("catagory", "news");
		}
		
		startActivity(supportDetailIntent);
	}
	
	
}
