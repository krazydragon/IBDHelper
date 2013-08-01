package com.kdragon.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
 
    @Override
    public void onReceive(final Context context, final Intent intent) {
 
        
        Boolean connected = WebInterface.getConnectionStatus(context);
  		
      	if(!connected){
      		Toast.makeText(context,"Internet is gone!", Toast.LENGTH_SHORT).show();
    			
     		}else{
     			Toast.makeText(context,"Internet is back", Toast.LENGTH_SHORT).show();
     		}
    }
}