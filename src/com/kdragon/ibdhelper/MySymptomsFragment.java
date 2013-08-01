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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;



public class MySymptomsFragment extends Fragment{
	
	int flareCount = 0;
	int flareListNum = 0;
	int bmCount = 0;
	int bmListNum = 0;
	int flarePainValue = 0;
	int bmPainValue = 0;
	CheckBox _flareCheckbox;
	CheckBox _bmCheckbox;
	Date today;
	Calendar c;
	List<ParseObject> flareList;
	TextView flareDate;
	TextView flareNumber;
	TextView flarePain;
	List<ParseObject> bmList;
	TextView bmDate;
	TextView bmNumber;
	TextView bmPain;
	ParseObject bmObj; 
	ParseObject flareObj;
	int bmNewPainValue = 5;
	int flareNewPainValue = 5;
	ImageButton flareBack;
	ImageButton flareFoward;
	ImageButton bmBack;
	ImageButton bmFoward;
	PopupWindow pw;
	

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_symptoms, container, false);
		
		Boolean connected = WebInterface.getConnectionStatus(getActivity());
		if(!connected){
			Crouton.makeText(getActivity(), "No network found some values may be overwritten!", Style.ALERT).show();
		}
		flareBack = (ImageButton)view.findViewById(R.id.imageButton1);
		flareFoward = (ImageButton)view.findViewById(R.id.imageButton2);
		
		bmBack = (ImageButton)view.findViewById(R.id.bmImageButton1);
		bmFoward = (ImageButton)view.findViewById(R.id.bmIageButton2);
		
		flareDate = (TextView)view.findViewById(R.id.flareDateView);
		flareNumber = (TextView)view.findViewById(R.id.flareNumberText);
		flarePain = (TextView)view.findViewById(R.id.flarePainText);
		
		bmDate = (TextView)view.findViewById(R.id.bmDateView);
		bmNumber = (TextView)view.findViewById(R.id.bmNumberText);
		bmPain = (TextView)view.findViewById(R.id.bmPainText);
		
		flareBack.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		if(flareListNum < flareList.size()){
        			
        			flareListNum ++;
        			
        			flareFoward.setVisibility(View.VISIBLE);
        			
        				
        				if(flareListNum == (flareList.size() - 1)){
            				flareBack.setVisibility(View.INVISIBLE);
            			}
            				flareDate.setText(flareList.get(flareListNum).getCreatedAt().toString());
            				flareNumber.setText(Integer.toString(flareList.get(flareListNum).getInt("size")));
            				flarePain.setText(Integer.toString(getPain(flareList.get(flareListNum).getInt("size"), flareList.get(flareListNum).getInt("pain"))));
            			
        	
        		}
        		
 
        	}
        	}
        );
		
		flareFoward.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		if(flareListNum > 0){
        			flareBack.setVisibility(View.VISIBLE);
        			flareListNum --;
        			
        			
        			
        				flareDate.setText(flareList.get(flareListNum).getDate("date").toString());
        				if(flareListNum == 0){
            				flareFoward.setVisibility(View.INVISIBLE);
            			}
            				flareDate.setText(flareList.get(flareListNum).getCreatedAt().toString());
            				flareNumber.setText(Integer.toString(flareList.get(flareListNum).getInt("size")));
            				flarePain.setText(Integer.toString(getPain(flareList.get(flareListNum).getInt("size"), flareList.get(flareListNum).getInt("pain"))));
            			
        			
        			
        			
        			
        			
        		}
        		
 
        	}
        	}
        );
		
		bmBack.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		if(bmListNum < bmList.size()){
        			
        			bmListNum ++;
        			
        			bmFoward.setVisibility(View.VISIBLE);
        			
        				
        				if(bmListNum == (bmList.size() - 1)){
            				bmBack.setVisibility(View.INVISIBLE);
            			}
            				bmDate.setText(bmList.get(bmListNum).getCreatedAt().toString());
            				bmNumber.setText(Integer.toString(bmList.get(bmListNum).getInt("size")));
            				bmPain.setText(Integer.toString(getPain(bmList.get(bmListNum).getInt("size"), bmList.get(bmListNum).getInt("pain"))));
            			
        	
        		}
        		
 
        	}
        	}
        );
		
		bmFoward.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		if(bmListNum > 0){
        			bmBack.setVisibility(View.VISIBLE);
        			bmListNum --;
        			
        			
        			
        				bmDate.setText(bmList.get(bmListNum).getDate("date").toString());
        				if(bmListNum == 0){
            				bmFoward.setVisibility(View.INVISIBLE);
            			}
            				bmDate.setText(flareList.get(bmListNum).getCreatedAt().toString());
            				bmNumber.setText(Integer.toString(bmList.get(bmListNum).getInt("size")));
            				bmPain.setText(Integer.toString(getPain(bmList.get(bmListNum).getInt("size"), bmList.get(bmListNum).getInt("pain"))));
            			
        			
        			
        			
        			
        			
        		}
        		
 
        	}
        	}
        );
		 c = Calendar.getInstance();

		    // set the calendar to start of today
		    c.set(Calendar.HOUR_OF_DAY, 0);
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    c.set(Calendar.MILLISECOND, 0);

		    today = c.getTime();
		    bmObj = null;

		    flareDate.setText(today.toString());
		    flareQuery();
		    bmQuery();

		    // test your condition
		    
		
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
		
		if(bmObj == null){
			bmObj = new ParseObject("bm");
			bmObj.put("pain", bmPainValue);
			bmObj.put("size", 0);
		}
		if(flareObj == null){
			flareObj = new ParseObject("flare");
			flareObj.put("pain", flarePainValue);
			flareObj.put("size", 0);
		}
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
            	flareNewPainValue = progressChanged;
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
            	bmNewPainValue = progressChanged;
            }
        });
 
        
        
        submit.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		
        		
        		if (_flareCheckbox.isChecked()) {
        			
        			flareObj.increment("size");
        			flareObj.increment("pain", flareNewPainValue);
        			flareObj.saveEventually();
        			flareCount++;
        			flarePainValue = flarePainValue + flareNewPainValue;
        			flareNumber.setText(Integer.toString(flareCount));
    				flarePain.setText(Integer.toString(getPain(flareCount, flarePainValue)));
        			
        		}
        		if (_bmCheckbox.isChecked()) {
        			bmObj.increment("size");
        			bmObj.increment("pain", bmNewPainValue);
        			bmObj.saveEventually();
        			bmCount++;
        			bmPainValue = bmPainValue + bmNewPainValue;
        			bmNumber.setText(Integer.toString(bmCount));
    				bmPain.setText(Integer.toString(getPain(bmCount, bmPainValue)));
        			
        			
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
	private int getPain(int size, int total){
		int painLevel = total/size;
		return painLevel;
	}
	
	public void flareQuery(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("flare");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> flareDays, ParseException e) {
		        if (e == null) {
		        	Log.i("", Integer.toString(flareDays.size()));
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

		        			 flareObj = flare;
		        			flarePainValue = flareObj.getInt("pain");
		        			flareCount = flareObj.getInt("size");
		        			flareNumber.setText(Integer.toString(flareCount));
				        	flarePain.setText(Integer.toString(getPain(flareCount, flarePainValue)));
		        			
		     		        
		     		    }else if (dateSpecified.before(today)&&(flareObj != null)) {
		     		    	flareBack.setVisibility(View.VISIBLE);
		        		 
		            }
		        	


		        	flareList = flareDays;
		        	
		        	
		        	
		        }}
		        
		    }
		});
		
		
	}
	
	public void bmQuery(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("bm");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> bmDays, ParseException e) {
		        if (e == null) {
		        	Log.i("", Integer.toString(bmDays.size()));
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

		        			 bmObj = bm;
		        			bmPainValue = bmObj.getInt("pain");
		        			bmCount = bmObj.getInt("size");
		        			bmNumber.setText(Integer.toString(bmCount));
				        	bmPain.setText(Integer.toString(getPain(bmCount, bmPainValue)));
		        			
		     		        
		     		    }else if (dateSpecified.before(today)&&(bmObj != null)) {
		     		    	bmBack.setVisibility(View.VISIBLE);
		        		 
		            }
		        	


		        	bmList = bmDays;
		        	
		        	
		        	
		        }}
		        
		    }
		});
		flareDate.setText("Today");
		bmDate.setText("Today");
		
	}
	
}
