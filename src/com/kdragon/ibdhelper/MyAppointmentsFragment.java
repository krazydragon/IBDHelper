package com.kdragon.ibdhelper;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kdragon.ibdhelper.MyMedFragment.RemoteDataTask;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;



public class MyAppointmentsFragment extends Fragment{
	
	ViewPager mViewPager;
	ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
	
	private EditText _appName;
	private EditText _appDesciption;
	private EditText _appLocation;
	private TimePicker timePicker;
	private DatePicker datePicker;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_appointments, container, false);
		
		
		new RemoteDataTask().execute();
		setHasOptionsMenu(true);
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
        LayoutInflater inflater = (LayoutInflater) MyAppointmentsFragment.this
                .getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.fragment_add_appointment,
                (ViewGroup) getActivity().findViewById(R.id.AppointmentAddLayout));
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        pw = new PopupWindow(layout, width, height, true);
        // display the popup in the center
       
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);//Intent loginIntent = new Intent(this, LoginActivity.class);
        
        Button submit = (Button)layout.findViewById(R.id.addButton);
        Button cancel =(Button)layout.findViewById(R.id.cancelButton);
       
        _appName = (EditText)layout.findViewById(R.id.nameInput);
		_appDesciption = (EditText)layout.findViewById(R.id.descriptionInput);
		_appLocation = (EditText)layout.findViewById(R.id.locationInput);
		
        datePicker = (DatePicker) layout.findViewById(R.id.AppDatePicker);
        timePicker = (TimePicker) layout.findViewById(R.id.timePicker);
        submit.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		ParseObject appObj = new ParseObject("appointment");
        		appObj.put("appName", _appName.getText().toString());
        		appObj.put("appDesciption", _appDesciption.getText().toString());
        		appObj.put("appLocation", _appLocation.getText().toString());
        		
        		int year = datePicker.getYear();
        		int month = datePicker.getMonth();
        		int day = datePicker.getDayOfMonth();
        		int hour = timePicker.getCurrentHour();
        		int minute = timePicker.getCurrentMinute();
        		
        		Calendar c = Calendar.getInstance();
        	    
        		c.set(Calendar.YEAR, year);
        		c.set(Calendar.MONTH, month);
        		c.set(Calendar.DAY_OF_MONTH, day);
        		c.set(Calendar.HOUR_OF_DAY, hour);
        		c.set(Calendar.MINUTE, minute);
        		c.set(Calendar.SECOND, 0);
        		Date d = c.getTime();
        		
        		//scheduleClient.setAlarmForNotification(c);
        		appObj.put("appTime", d);
        		appObj.saveInBackground();
        		pw.dismiss();
        		
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
                    "appointment");
            query.addAscendingOrder("appDate");
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
            listview = (ListView) getActivity().findViewById(R.id.appListView);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item);
            // Retrieve object "name" from Parse.com database
            for (ParseObject med : ob) {
                adapter.add((String) med.get("appName"));
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
                   
                    i.putExtra("name", ob.get(position).getString("appName").toString());
                    i.putExtra("desciption", ob.get(position).getString("appDesciption").toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }
	

    
}
