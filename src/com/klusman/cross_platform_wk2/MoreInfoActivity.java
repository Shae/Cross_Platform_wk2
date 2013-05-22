package com.klusman.cross_platform_wk2;


import com.parse.Parse;
import com.parse.ParseAnalytics;
import android.app.Activity;
import android.os.Bundle;


public class MoreInfoActivity extends Activity {

	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.more_info_activity); 
        //deleteDB();  // TO CLEAR DB AND START OVER

        Parse.initialize(this, "KOHAaQRdCYXrO1RNBYF3iTSOoxrgTfXRsFUpMdhN", "P3BgADyELTJe2ZyJFUs5cqabAagdVtg517VG2YHf"); 
		ParseAnalytics.trackAppOpened(getIntent());
      
    } // END onCreate
    
}
