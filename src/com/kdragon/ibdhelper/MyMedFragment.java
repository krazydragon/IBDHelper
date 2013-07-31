package com.kdragon.ibdhelper;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.kdragon.other.ScheduleClient;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;



public class MyMedFragment extends Fragment{
	
	
	ViewPager mViewPager;
	 ListView listview;
    List<ParseObject> ob;
   ProgressDialog mProgressDialog;
   ArrayAdapter<String> adapter;
   Context context;
   EditText _medName;
	EditText _medDesciption;
	CheckBox _medCheckbox;
    private ScheduleClient scheduleClient;
    // This is the date picker used to select the date for our notification
    private TimePicker picker;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_meds, container, false);
		
		setHasOptionsMenu(true);
		
		new RemoteDataTask().execute();
		return view;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.menu_add:
			
			addPopup();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void addPopup(){
		final PopupWindow pw;
		//We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) MyMedFragment.this
                .getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.fragment_add_med,
                (ViewGroup) getActivity().findViewById(R.id.AppointmentAddLayout));
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        pw = new PopupWindow(layout, width, height, true);
        // display the popup in the center
       
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        
        Button submit = (Button)layout.findViewById(R.id.addButton);
        Button cancel =(Button)layout.findViewById(R.id.cancelButton);
        _medName = (EditText)layout.findViewById(R.id.nameInput);
		_medDesciption = (EditText)layout.findViewById(R.id.descriptionInput);
		_medCheckbox = (CheckBox)layout.findViewById(R.id.MedCheckBox);
		
		// Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(getActivity());
        scheduleClient.doBindService();
 
        
        picker = (TimePicker) layout.findViewById(R.id.medTimePicker2);
        submit.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		ParseObject medObj = new ParseObject("medicine");
        		//medObj.put("medName", _medName.getText().toString());
        		//medObj.put("medDesciption", _medDesciption.getText().toString());
        		Boolean checked = false;
	
        		if (_medCheckbox.isChecked()) {
        			checked = true;
        		}

        		medObj.put("medCheckbox", checked);
        		
	
        		// Get the date from our datepicker
        		int hour = picker.getCurrentHour();
        		int minute = picker.getCurrentMinute();
    
        		// Create a new calendar set to the date chosen
   
        		Calendar c = Calendar.getInstance();
    
        		c.set(Calendar.HOUR_OF_DAY, hour);
        		c.set(Calendar.MINUTE, minute);
        		c.set(Calendar.SECOND, 0);
        		Date d = c.getTime();
        		
        		//scheduleClient.setAlarmForNotification(c);
        		medObj.put("Time1", d);
        		medObj.saveInBackground();
        		pw.dismiss();
        		new RemoteDataTask().execute();
        	}
        	}
       );

        cancel.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		pw.dismiss();
 
        	}
        	}
        );
        
        
        
	}
	
	
	
	
	// RemoteDataTask AsyncTask
    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
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
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "medicine");
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
            listview = (ListView) getActivity().findViewById(R.id.medListView);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item);
            // Retrieve object "name" from Parse.com database
            for (ParseObject med : ob) {
                adapter.add((String) med.get("medName"));
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
                    // Send single item click data to SingleItemView Class
                    Intent i = new Intent(getActivity(),
                            AddListActivity.class);
                   
                    i.putExtra("name", ob.get(position).getString("medName").toString());
                    i.putExtra("desciption", ob.get(position).getString("medDesciption").toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }
   
}
