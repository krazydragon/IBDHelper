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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class IBDHelperMainActivity extends Activity{

	

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	
	
	
	
	 // This is a handle so that we can call methods on our service


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ibdhelper_main);
		
		
		Parse.initialize(this, "07VGNwJCzX84xlbvRaUNKYqL4YRqAhTFWd4eD1nr", "OPrDeprGorSx8FjX6UALpeh2wtYsjpDouGQo76Gc");
		
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		//ParseUser.logOut();
		ParseACL defaultACL = new ParseACL();
		ParseACL.setDefaultACL(defaultACL, true);
		
		
		FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        
        
        
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			Crouton.makeText(this, "found!", Style.ALERT).show();
			transaction.replace(R.id.mainFragment,new MyDayFragment() );
			transaction.commit();
			//new RemoteDataTask().execute();
			
		} else {
			
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivityForResult(loginIntent,1);
			
			
		}
		

		
	    
	    /*
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
		
		*/
		
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
		
	    lv2.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	
	    	            
	    		FragmentManager manager = getFragmentManager();
	            FragmentTransaction transaction = manager.beginTransaction();
	            Fragment frag = null;
	            switch (position) {
	            case 1:
	                frag = new MyMedFragment();
	                break;

	            case 2:
	                frag = new MyFoodFragment();
	                break;

	            case 3:
	                frag = new MyAppointmentsFragment();
	                break;

	            case 4:
	                frag = new MySymptomsFragment();
	                break;
	            default:
	                frag = new MyDayFragment();
	                break;
	            }
	            transaction.replace(R.id.mainFragment, frag);
	            transaction.commit();
	    		
	    	            
	    	         }});

		
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
					 tabLayout = R.layout.fragment_symptoms;
					 break;
			}
			View rootView = inflater.inflate(tabLayout,
					container, false);
			
			
			return rootView;
		}
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		           
		         String msg = data.getStringExtra("msg"); 
		         Crouton.makeText(this, msg, Style.INFO).show();
		        
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
	

	
	
}
