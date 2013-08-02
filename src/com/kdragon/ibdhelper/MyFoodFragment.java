package com.kdragon.ibdhelper;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kdragon.ibdhelper.MyMedFragment.RemoteDataTask;
import com.kdragon.other.WebInterface;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;



public class MyFoodFragment extends Fragment{
	int waterCount = 0;
	int waterListNum = 0;
	int waterAmountValue = 0;
	Date today;
	Calendar c;
	List<ParseObject> waterList;
	TextView waterDate;
	TextView waterPain;
	ParseObject waterObj;
	int waterNewAmountValue = 5;
	ImageButton waterBack;
	ImageButton waterFoward;
	PopupWindow pw;
	

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_foods, container, false);
		
		
		Boolean connected = WebInterface.getConnectionStatus(getActivity());
		if(!connected){
			Crouton.makeText(getActivity(), "No network found some values may be overwritten!", Style.ALERT).show();
 		}
		
		waterBack = (ImageButton)view.findViewById(R.id.imageButton1);
		waterFoward = (ImageButton)view.findViewById(R.id.imageButton2);
		
		
		waterDate = (TextView)view.findViewById(R.id.flareDateView);
		waterPain = (TextView)view.findViewById(R.id.flarePainText);
		
		waterBack.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		if(waterListNum < waterList.size()){
        			
        			waterListNum ++;
        			
        			waterFoward.setVisibility(View.VISIBLE);
        			
        				
        				if(waterListNum == (waterList.size() - 1)){
        					waterBack.setVisibility(View.INVISIBLE);
            			}
        				waterDate.setText(waterList.get(waterListNum).getCreatedAt().toString());
        				waterPain.setText(Integer.toString(getPain(waterList.get(waterListNum).getInt("size"), waterList.get(waterListNum).getInt("amount"))));
            			
        	
        		}
        		
 
        	}
        	}
        );
		
		waterFoward.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub

        		if(waterListNum > 0){
        			waterBack.setVisibility(View.VISIBLE);
        			waterListNum --;
        			
        			
        			
        			waterDate.setText(waterList.get(waterListNum).getDate("date").toString());
        				if(waterListNum == 0){
        					waterFoward.setVisibility(View.INVISIBLE);
            			}
        				waterDate.setText(waterList.get(waterListNum).getCreatedAt().toString());
        				waterPain.setText(Integer.toString(getPain(waterList.get(waterListNum).getInt("size"), waterList.get(waterListNum).getInt("amount"))));
            			
        			
        			
        			
        			
        			
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
		    waterObj = null;

		    waterDate.setText(today.toString());
		    flareQuery();

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
		
		
		if(waterObj == null){
			waterObj = new ParseObject("water");
			waterObj.put("amount", waterAmountValue);
			waterObj.put("size", 0);
		}
		//We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) MyFoodFragment.this
                .getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.fragment_add_food,
                (ViewGroup) getActivity().findViewById(R.id.FoodAddLayout));
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
        final EditText waterInput = (EditText) layout.findViewById(R.id.waterText1);

        
        submit.setOnClickListener(new Button.OnClickListener(){

        	@Override
        	public void onClick(View v) {
        		
        		if(waterInput.getText().toString().trim().length() > 0){

        			// TODO Auto-generated method stub
            		
            		waterObj.increment("amount", waterNewAmountValue);
            		waterObj.saveEventually();
            			waterCount++;
            			waterNewAmountValue = Integer.parseInt(waterInput.getText().toString());
            			waterAmountValue = waterAmountValue + waterNewAmountValue;
            			waterPain.setText(Integer.toString(getPain(waterCount, waterAmountValue)));
            			
            		
            		
            		pw.dismiss();

   				}

   				else {

   					Toast.makeText(getActivity(),"Input can not be blank!", Toast.LENGTH_SHORT).show();
   					
   				}
        		
        		
        		
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
		int painLevel = total/8;
		return painLevel;
	}
	
	public void flareQuery(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("water");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> flareDays, ParseException e) {
		        if (e == null) {
		        	
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

		        			 waterObj = water;
		        			waterAmountValue = waterObj.getInt("amount");
		        			waterCount = waterObj.getInt("size");
				        	waterPain.setText(Integer.toString(getPain(waterCount, waterAmountValue)));
		        			
		     		        
		     		    }else if (dateSpecified.before(today)&&(waterObj != null)) {
		     		    	waterBack.setVisibility(View.VISIBLE);
		        		 
		            }
		        	


		        	waterList = flareDays;
		        	waterDate.setText(waterList.get(waterListNum).getCreatedAt().toString());
		        	
		        	
		        }}
		        
		    }
		});
		
		
	}
	
	
}
