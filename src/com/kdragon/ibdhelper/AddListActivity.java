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
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AddListActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        // storing string resources into Array
        String[] add_menu = getResources().getStringArray(R.array.add_list);
         
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_add_menu, R.id.label, add_menu));
         
        ListView lv = getListView();
        final Intent addDetailIntent = new Intent(this, AddDetailActivity.class);
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
               
              // selected item
             String menuItem = ((TextView) view).getText().toString();
            
        	
            
			addDetailIntent.putExtra("item", menuItem);
			startActivity(addDetailIntent);
             
          }
        });
    }
}
