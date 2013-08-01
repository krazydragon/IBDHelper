package com.kdragon.ibdhelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kdragon.other.WebInterface;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MyDayFragment extends Fragment{
	
	Date today;
	Calendar c;
	TextView waterCups;
	TextView flarePain;
	TextView flareNumber;
	TextView bmPain;
	TextView bmNumber;
	TextView appNumber;
	int appList = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		Boolean connected = WebInterface.getConnectionStatus(getActivity());
  		
      	if(!connected){
    			Crouton.makeText(getActivity(), "No network found some values may not be updated!", Style.ALERT).show();
     		}
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_day, container, false);
		
		bmNumber = (TextView)view.findViewById(R.id.bmQtyView);
	    bmPain = (TextView)view.findViewById(R.id.bmPainView);
		flareNumber = (TextView)view.findViewById(R.id.flareQtyView1);
	    flarePain = (TextView)view.findViewById(R.id.flarePainView);
	    waterCups  = (TextView)view.findViewById(R.id.waterCupsView);
	    appNumber = (TextView)view.findViewById(R.id.aptView);
		
		c = Calendar.getInstance();

	    // set the calendar to start of today
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);

	    today = c.getTime();
	    
		appQuery();
		flareQuery();
		waterQuery();
		bmQuery();
		
		return view;
	}

	private int getPain(int size, int total){
		int painLevel = total/size;
		return painLevel;
	}
	

	public void flareQuery(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("flare");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> flareDays, ParseException e) {
		        if (e == null) {
		        	
		        	for (ParseObject flare : flareDays) {
		        		Date createdAt = flare.getCreatedAt();
		        		c.setTime(createdAt);
		        		c.set(Calendar.HOUR_OF_DAY, 0);
		    		    c.set(Calendar.MINUTE, 0);
		    		    c.set(Calendar.SECOND, 0);
		    		    c.set(Calendar.MILLISECOND, 0);
		    		    // and get that as a Date
		    		    Date dateSpecified = c.getTime();
		    		    
		    		    
		        		
		        		 if (dateSpecified.equals(today)) {

		        			int flarePainValue = flare.getInt("pain");
		        			int flareCount = flare.getInt("size");
		        			flareNumber.setText(Integer.toString(flareCount));
				        	flarePain.setText(Integer.toString(getPain(flareCount, flarePainValue)));
		        			
		     		        
		     		    }
		        	
		        }
		        	}
		        
		    }
		});
		
	}
		public void bmQuery(){
			ParseQuery<ParseObject> query = ParseQuery.getQuery("bm");
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> bmDays, ParseException e) {
			        if (e == null) {
			        	
			        	for (ParseObject bm : bmDays) {
			        		Date createdAt = bm.getCreatedAt();
			        		c.setTime(createdAt);
			        		c.set(Calendar.HOUR_OF_DAY, 0);
			    		    c.set(Calendar.MINUTE, 0);
			    		    c.set(Calendar.SECOND, 0);
			    		    c.set(Calendar.MILLISECOND, 0);
			    		    // and get that as a Date
			    		    Date dateSpecified = c.getTime();
			    		    
			    		    
			        		
			        		 if (dateSpecified.equals(today)) {

			        			int bmPainValue = bm.getInt("pain");
			        			int bmCount = bm.getInt("size");
			        			bmNumber.setText(Integer.toString(bmCount));
					        	bmPain.setText(Integer.toString(getPain(bmCount, bmPainValue)));
			        			
			     		        
			     		    }
			        	
			        }
			        	}
			        
			    }
			});
		
		
	}
	public void waterQuery(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("water");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> flareDays, ParseException e) {
		        if (e == null) {
		        	Log.i("", Integer.toString(flareDays.size()));
		        	for (ParseObject water : flareDays) {
		        		Date createdAt = water.getCreatedAt();
		        		c.setTime(createdAt);
		        		c.set(Calendar.HOUR_OF_DAY, 0);
		    		    c.set(Calendar.MINUTE, 0);
		    		    c.set(Calendar.SECOND, 0);
		    		    c.set(Calendar.MILLISECOND, 0);
		    		    // and get that as a Date
		    		    Date dateSpecified = c.getTime();
		        		
		        		 if (dateSpecified.equals(today)) {

		        			 
				        	waterCups.setText(Integer.toString(getPain(8, water.getInt("amount"))));
		        			
		     		        
		     		    }
		        	
		        	
		        }}
		        
		    }
		});
		
		
	}
	public void appQuery(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("appointment");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> flareDays, ParseException e) {
		        if (e == null) {
		        	
		        	for (ParseObject app : flareDays) {
		        		Date createdAt = app.getDate("appTime");;
		        		c.setTime(createdAt);
		        		c.set(Calendar.HOUR_OF_DAY, 0);
		    		    c.set(Calendar.MINUTE, 0);
		    		    c.set(Calendar.SECOND, 0);
		    		    c.set(Calendar.MILLISECOND, 0);
		    		    // and get that as a Date
		    		    Date dateSpecified = c.getTime();
		        		
		        		 if (dateSpecified.equals(today)) {

		        			appList ++;
				        	
		        			
		     		        
		     		    }
		        	
		        	
		        }
		        	if(appList != 0){
		        	appNumber.setText(Integer.toString(appList));	
		        	}
		        }
		        
		    }
		});
		
    		 
        
		
	}
}
