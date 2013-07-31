package com.kdragon.ibdhelper;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kdragon.ibdhelper.MyMedFragment.RemoteDataTask;
import com.kdragon.other.ScheduleClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TimePicker;
import android.widget.Toast;



public class MySymptomsFragment extends Fragment{
	
	int flarePainValue = 0;
	int bmPainValue = 0;
	CheckBox _flareCheckbox;
	CheckBox _bmCheckbox;
	Date today;
	Calendar c;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_symptoms, container, false);
		 c = Calendar.getInstance();

		    // set the calendar to start of today
		    c.set(Calendar.HOUR_OF_DAY, 0);
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    c.set(Calendar.MILLISECOND, 0);

		    today = c.getTime();
		    

		    

		    // test your condition
		    
		ParseQuery<ParseObject> query = ParseQuery.getQuery("bm");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		        	
		        	for (ParseObject med : scoreList) {
		        		Date createdAt = med.getDate("date");
		        		c.setTime(createdAt);
		        		c.set(Calendar.HOUR_OF_DAY, 0);
		    		    c.set(Calendar.MINUTE, 0);
		    		    c.set(Calendar.SECOND, 0);
		    		    c.set(Calendar.MILLISECOND, 0);
		    		    // and get that as a Date
		    		    Date dateSpecified = c.getTime();
		        		
		        		 if (dateSpecified.before(today)) {

		     		        Log.i(" date is previou", createdAt.toString());
		     		    } else if (dateSpecified.equals(today)) {

		     		        Log.i(" date is today ", createdAt.toString());
		     		    } 
		     		             else if (dateSpecified.after(today)) {

		     		        Log.i(" date is future date ", createdAt.toString());
		     		    }
		            }
		        	
		            Log.d("score", "Retrieved " + scoreList.size() + " scores");
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }
		});
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
        LayoutInflater inflater = (LayoutInflater) MySymptomsFragment.this
                .getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.fragment_add_bm,
                (ViewGroup) getActivity().findViewById(R.id.SymptomAddLayout));
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
        SeekBar flareControl = (SeekBar) layout.findViewById(R.id.seekBar2);
        SeekBar bmControl = (SeekBar) layout.findViewById(R.id.SeekBar01);
        _flareCheckbox = (CheckBox)layout.findViewById(R.id.flareCheckBox);
        _bmCheckbox = (CheckBox)layout.findViewById(R.id.bmCheckBox);
        
        
        flareControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        	int progressChanged;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
            	flarePainValue = progressChanged;
            }
        });
        
        bmControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        	int progressChanged;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
            	bmPainValue = progressChanged;
            }
        });
 
        
        
        submit.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		
        		
        		if (_flareCheckbox.isChecked()) {
        			ParseObject flareObj = new ParseObject("flare");
        			flareObj.put("pain", flarePainValue);
        			flareObj.saveEventually();
        			
        		}
        		if (_bmCheckbox.isChecked()) {
        			ParseObject bmObj = new ParseObject("bm");
        			bmObj.put("pain", bmPainValue);
        			bmObj.saveInBackground();
        		}
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
	
	

}
