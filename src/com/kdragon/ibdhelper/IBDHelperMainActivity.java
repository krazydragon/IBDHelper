/*
 * project	IBDHelper
 * 
 * package	com.kdragon.ibdhelper
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Jul 15, 2013
 */
package com.kdragon.ibdhelper;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kdragon.other.ScheduleClient;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class IBDHelperMainActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	 ListView listview;
     List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    Context context;
    EditText _medName;
	EditText _medDesciption;
	CheckBox _medCheckbox;
	
	
	
	 // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
    // This is the date picker used to select the date for our notification
    private TimePicker picker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ibdhelper_main);
		
		Parse.initialize(this, "07VGNwJCzX84xlbvRaUNKYqL4YRqAhTFWd4eD1nr", "OPrDeprGorSx8FjX6UALpeh2wtYsjpDouGQo76Gc");
		
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		//ParseUser.logOut();
		ParseACL defaultACL = new ParseACL();
		ParseACL.setDefaultACL(defaultACL, true);
		
		
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			//Crouton.makeText(this, "found!", Style.ALERT).show();
			
			new RemoteDataTask().execute();
			
		} else {
			
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivityForResult(loginIntent,1);
			
			
		}
		

		
	    
	    
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
			
			
			
		}
		
		
		
		SlidingMenu menu;
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidth(5);
		menu.setFadeDegree(0.0f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setBehindWidth(500);
		menu.setMenu(R.layout.menu_frame);
		
		ListView lv2 = (ListView) findViewById(R.id.listView1);
	    
	    
	    String[] items2 = new String[]{"My Day","My Medicine","My Foods","My Appointments", "My Symtoms"};
	    ArrayAdapter<String> adapter2 =  new ArrayAdapter<String>(IBDHelperMainActivity.this ,android.R.layout.simple_list_item_1, android.R.id.text1, items2);
	    lv2.setAdapter(adapter2);
		
	    
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ibdhelper_main, menu);
		
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.menu_add:
			
			addPopup();
			/*Intent addIntent = new Intent(this, AddListActivity.class);
			
			startActivity(addIntent);*/
			return true;
		case R.id.action_settings:
	        
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
			return true;
		case R.id.menu_support:
	        
			Intent supportIntent = new Intent(this, SupportActivity.class);
			startActivity(supportIntent);
			return true;
		case R.id.menu_logout:
			ParseUser.logOut();
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
    


	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_OBJECT, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		public static final String ARG_OBJECT = "object";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			Bundle args = getArguments();
			int position = args.getInt(ARG_OBJECT);
			
			int tabLayout = 0;
			switch (position) {
				case 0:
					tabLayout = R.layout.fragment_meds;
				     break;
				 case 1:
				     tabLayout = R.layout.fragment_meds;
				     
				     
				        break;
				 case 2:
					 tabLayout = R.layout.fragment_foods;
					 break;
				 case 3:
				     tabLayout = R.layout.fragment_appointments;
				        break;
				 case 4:
					 tabLayout = R.layout.fragment_symtoms;
					 break;
			}
			View rootView = inflater.inflate(tabLayout,
					container, false);
			
			
			return rootView;
		}
		
	}
	
	

	private void addPopup(){
		final PopupWindow pw;
		//We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) IBDHelperMainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.fragment_add_med,
                (ViewGroup) findViewById(R.id.MedicineAddLayout));
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        pw = new PopupWindow(layout, width, height, true);
        // display the popup in the center
       
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);//Intent loginIntent = new Intent(this, LoginActivity.class);
        
        Button submit = (Button)layout.findViewById(R.id.addButton);
        Button cancel =(Button)layout.findViewById(R.id.cancelButton);
        _medName = (EditText)layout.findViewById(R.id.medName);
		_medDesciption = (EditText)layout.findViewById(R.id.medDescription);
		_medCheckbox = (CheckBox)layout.findViewById(R.id.MedCheckBox);
		
		// Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
 
        
        picker = (TimePicker) layout.findViewById(R.id.medTimePicker2);
        submit.setOnClickListener(new Button.OnClickListener(){

@Override
public void onClick(View v) {
 // TODO Auto-generated method stub
	
	
	ParseObject medObj = new ParseObject("medicine");
	medObj.put("medName", _medName.getText().toString());
	medObj.put("medDesciption", _medDesciption.getText().toString());
	Boolean checked = false;
	
	if (_medCheckbox.isChecked()) {
		checked = true;
    }

	medObj.put("medCheckbox", checked);
	medObj.saveInBackground();
	
	// Get the date from our datepicker
    int hour = picker.getCurrentHour();
    int minute = picker.getCurrentMinute();
    
    // Create a new calendar set to the date chosen
   
    Calendar c = Calendar.getInstance();
    
    c.set(Calendar.HOUR_OF_DAY, hour);
    c.set(Calendar.MINUTE, minute);
    c.set(Calendar.SECOND, 0);
    
    scheduleClient.setAlarmForNotification(c);
 pw.dismiss();
 new RemoteDataTask().execute();
}});

        cancel.setOnClickListener(new Button.OnClickListener(){

@Override
public void onClick(View v) {
 // TODO Auto-generated method stub

 pw.dismiss();
 
}});
        
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		           
		         String msg = data.getStringExtra("msg"); 
		         Crouton.makeText(this, msg, Style.INFO).show();
		         new RemoteDataTask().execute();
		         if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
		        	  //show sign in button
		        	} else {
		        	  //show log out button
		        	}
		         
		 		
		     }
		     if (resultCode == RESULT_CANCELED) {    
		         
		    	Intent loginIntent = new Intent(this, LoginActivity.class);
				startActivityForResult(loginIntent,1);		
		     }
		  }
		}
	
	
	
	// RemoteDataTask AsyncTask
    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(IBDHelperMainActivity.this);
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
            listview = (ListView) findViewById(R.id.medListView);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(IBDHelperMainActivity.this, R.layout.listview_item);
            // Retrieve object "name" from Parse.com database
            for (ParseObject country : ob) {
                adapter.add((String) country.get("medName"));
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
                    Intent i = new Intent(IBDHelperMainActivity.this,
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
