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

import java.util.Calendar;

import com.kdragon.other.ScheduleClient;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddDetailActivity extends Activity{
	
	EditText _medName;
	EditText _medDesciption;
	CheckBox _medCheckbox;
	 // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
    // This is the date picker used to select the date for our notification
    private TimePicker picker;
	
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
			
			_medName = (EditText)findViewById(R.id.medName);
			_medDesciption = (EditText)findViewById(R.id.medDescription);
			_medCheckbox = (CheckBox)findViewById(R.id.MedCheckBox);
			
			// Create a new service client and bind our activity to this service
	        scheduleClient = new ScheduleClient(this);
	        scheduleClient.doBindService();
	 
	        
	        picker = (TimePicker) findViewById(R.id.medTimePicker2);
			
		}
		
		Button saveButton = (Button)findViewById(R.id.addButton);
		saveButton.setOnClickListener(onClickListener);
	
	}
	
	private OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(final View v) {
                 
        	ParseObject medObj = new ParseObject("medicine");
        	medObj.put("medName", _medName.getText().toString());
        	medObj.put("medDesciption", _medDesciption.getText().toString());
        	Boolean checked = false;
        	
        	if (_medCheckbox.isChecked()) {
        		checked = true;
            }

        	medObj.put("medCheckbox", checked);
        	//medObj.saveInBackground();
        	
        	// Get the date from our datepicker
            int hour = picker.getCurrentHour();
            int minute = picker.getCurrentMinute();
            
            // Create a new calendar set to the date chosen
           
            Calendar c = Calendar.getInstance();
            
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            
            scheduleClient.setAlarmForNotification(c);
           
            
        }
    };
    
    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }
}
