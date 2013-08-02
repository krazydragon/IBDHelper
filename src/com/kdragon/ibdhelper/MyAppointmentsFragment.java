package com.kdragon.ibdhelper;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kdragon.ibdhelper.MyMedFragment.RemoteDataTask;
import com.kdragon.other.WebInterface;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;



public class MyAppointmentsFragment extends Fragment {
	
	ViewPager mViewPager;
	ListView todayListview;
	ListView tomorrowListview;
	ListView yesterdayListview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> todayAdapter ;
    ArrayAdapter<String> futureAdapter;
    ArrayAdapter<String> pastAdapter;
	
	private EditText _appName;
	private EditText _appDesciption;
	private EditText _appLocation;
	private TimePicker timePicker;
	private DatePicker datePicker;
	Calendar c;
	Date today;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_appointments, container, false);
		todayListview = (ListView) getActivity().findViewById(R.id.appTodayListView);
		tomorrowListview = (ListView) getActivity().findViewById(R.id.appFutureListView);
		yesterdayListview = (ListView) getActivity().findViewById(R.id.appPastListView);
		TextView todayView = (TextView)view.findViewById(R.id.appTodaytext);
		TextView futureView = (TextView)view.findViewById(R.id.appFutureText);
		TextView pastView = (TextView)view.findViewById(R.id.appPastText);
		c = Calendar.getInstance();
		 // set the calendar to start of today
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);

	    today = c.getTime();
		todayView.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		if(todayListview.getVisibility() == View.VISIBLE)
        			todayListview.setVisibility(View.GONE);
                else
                	todayListview.setVisibility(View.VISIBLE);
        		
        		
 
        	}
        	}
        );
		
		
        
        
        
	
		Boolean connected = WebInterface.getConnectionStatus(getActivity());
		
		if(!connected){
			
			view = (LinearLayout) inflater.inflate(R.layout.noservice, container, false);
		}else{
			new RemoteDataTask().execute();
			futureView.setOnClickListener(new Button.OnClickListener(){

	        	@Override
	        	public void onClick(View v) {
	        		// TODO Auto-generated method stub
	        		if(tomorrowListview.getVisibility() == View.VISIBLE)
	        			tomorrowListview.setVisibility(View.GONE);
	                else
	                	tomorrowListview.setVisibility(View.VISIBLE);
	        		
	        		
	 
	        	}
	        	}
	        );
			
			pastView.setOnClickListener(new Button.OnClickListener(){

	        	@Override
	        	public void onClick(View v) {
	        		// TODO Auto-generated method stub
	        		if(yesterdayListview.getVisibility() == View.VISIBLE)
	        			yesterdayListview.setVisibility(View.GONE);
	                else
	                	yesterdayListview.setVisibility(View.VISIBLE);
	        		
	        		
	 
	        	}
	        	}
	        );
		}
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
            query.orderByDescending("appDate");
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
        	todayListview = (ListView) getActivity().findViewById(R.id.appTodayListView);
        	tomorrowListview = (ListView) getActivity().findViewById(R.id.appFutureListView);
    		yesterdayListview = (ListView) getActivity().findViewById(R.id.appPastListView);
            // Pass the results into an ArrayAdapter
            todayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item);
            futureAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item);
            pastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item);
        	

		   
            
            // Retrieve object "name" from Parse.com database
            for (ParseObject app : ob) {
            	Date appDate = app.getDate("appTime");
        		c.setTime(appDate);
        		c.set(Calendar.HOUR_OF_DAY, 0);
    		    c.set(Calendar.MINUTE, 0);
    		    c.set(Calendar.SECOND, 0);
    		    c.set(Calendar.MILLISECOND, 0);
    		    // and get that as a Date
    		    Date dateSpecified = c.getTime();
        		
        		 if (dateSpecified.before(today)) {
        			 pastAdapter.add((String) app.get("appName"));
     		       
     		    } else if (dateSpecified.equals(today)) {
     		    	todayAdapter.add((String) app.get("appName"));
     		        
     		    } 
     		             else if (dateSpecified.after(today)) {
     		            	futureAdapter.add((String) app.get("appName"));
     		            	 
     		        
     		    }
        		 
            }
            // Binds the Adapter to the ListView
            todayListview.setAdapter(todayAdapter);
            tomorrowListview.setAdapter(pastAdapter);
            yesterdayListview.setAdapter(futureAdapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            todayListview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                    Intent i = new Intent(getActivity(),
                            AddListActivity.class);
                   
                    i.putExtra("name", ob.get(position).getString("appName").toString());
                    i.putExtra("desciption", ob.get(position).getString("appDesciption").toString());
                    i.putExtra("time", ob.get(position).getDate("appTime").toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
            tomorrowListview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                    Intent i = new Intent(getActivity(),
                            AddListActivity.class);
                   
                    i.putExtra("name", ob.get(position).getString("appName").toString());
                    i.putExtra("desciption", ob.get(position).getString("appDesciption").toString());
                    i.putExtra("time", ob.get(position).getDate("appTime").toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
            yesterdayListview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                    Intent i = new Intent(getActivity(),
                            AddListActivity.class);
                   
                    i.putExtra("name", ob.get(position).getString("appName").toString());
                    i.putExtra("desciption", ob.get(position).getString("appDesciption").toString());
                    i.putExtra("time", ob.get(position).getDate("appTime").toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }

	
	

    
}
