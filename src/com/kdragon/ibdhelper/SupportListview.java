package com.kdragon.ibdhelper;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SupportListview extends Activity{
	ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    String catagory;
    
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_meds);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    catagory = extras.getString("catagory");
		    new RemoteDataTask().execute();
		}
	}
	
	// RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SupportListview.this);
            // Set progressdialog title
            mProgressDialog.setTitle("");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
 
      
 
        @Override
        protected Void doInBackground(Void... params) {
            // Locate the class table named "Country" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(catagory);
            query.orderByDescending("_created_at");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
        	 listview = (ListView) findViewById(R.id.medListView);
             // Pass the results into an ArrayAdapter
             adapter = new ArrayAdapter<String>(SupportListview.this,
                     R.layout.listview_item);
             // Retrieve object "name" from Parse.com database
             for (ParseObject links : ob) {
                 adapter.add((String) links.get("name"));
             }
             // Binds the Adapter to the ListView
             listview.setAdapter(adapter);
             // Close the progressdialog
             mProgressDialog.dismiss();
             // Capture button clicks on ListView items
             listview.setOnItemClickListener(new OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view,
                         int position, long id) {
              
                     Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ob.get(position).getString("uri")
                             .toString()));
                     startActivity(i);
                 }
             });
        }
    }
}
