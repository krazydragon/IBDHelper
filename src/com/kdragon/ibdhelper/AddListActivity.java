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

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class AddListActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item_detail);
    
        TextView detailName =(TextView)findViewById(R.id.detailName);
        TextView detailDesciption =(TextView)findViewById(R.id.detailText);
        TextView detailTime =(TextView)findViewById(R.id.detailTime);
    	
    	Intent intent = getIntent();
        detailName.setText(intent.getStringExtra("name"));
        detailDesciption.setText(intent.getStringExtra("desciption"));
        detailTime.setText(intent.getStringExtra("time"));
        
        Button back = (Button)findViewById(R.id.backButton);
        
        
        back.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		finish();
 
        	}
        	}
        );
        
        
        
        
    }
    
    
}
