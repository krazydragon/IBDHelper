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

public class AddDetailActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent addListIntent = getIntent();
		String string = addListIntent.getStringExtra("item");
		String[] addMenuList = getResources().getStringArray(R.array.add_list);
		
		
		
		if(string.equals(addMenuList[0])){
			setContentView(R.layout.fragment_add_food);
		}else if(string.equals(addMenuList[1])){
			setContentView(R.layout.fragment_add_flare);			
		}else if(string.equals(addMenuList[2])){
			setContentView(R.layout.fragment_add_bm);
		}else if(string.equals(addMenuList[3])){
			setContentView(R.layout.fragment_add_appointment);
		}else{
			setContentView(R.layout.fragment_add_med);			
		}
		

	
	}
}
