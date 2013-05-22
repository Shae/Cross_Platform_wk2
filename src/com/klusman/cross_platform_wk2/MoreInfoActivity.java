package com.klusman.cross_platform_wk2;


import com.parse.Parse;
import com.parse.ParseAnalytics;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MoreInfoActivity extends Activity {

	TextView _name ;
	TextView _id ;
	TextView _type ;
	TextView _hands ;
	TextView _dam ;
	TextView _quant ;
	int quantity;
	Context context;
	private static final String TAG = "WEAPONS_DATABASE";
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        context = this;
        setContentView(R.layout.more_info_activity); 
        //deleteDB();  // TO CLEAR DB AND START OVER
    	 _name = (TextView)findViewById(R.id.infoName);
    	 _id = (TextView)findViewById(R.id.infoID);
    	 _type = (TextView)findViewById(R.id.infoType);
    	 _hands = (TextView)findViewById(R.id.infoHands);
    	 _dam = (TextView)findViewById(R.id.infoDamage);
    	 _quant = (TextView)findViewById(R.id.infoQuant);
    	 Button _purchaseBtn = (Button)findViewById(R.id.btnBasket);
        
        Parse.initialize(this, "KOHAaQRdCYXrO1RNBYF3iTSOoxrgTfXRsFUpMdhN", "P3BgADyELTJe2ZyJFUs5cqabAagdVtg517VG2YHf"); 
		ParseAnalytics.trackAppOpened(getIntent());
      
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			_name.setText(extras.getString("NAME"));	
			_id.setText("ID: " + extras.getString("ID"));
			_type.setText( "Type: " + extras.getString("TYPE"));
			_hands.setText(extras.getString("HANDS") + " Handed Weapon");
			_dam.setText("Damage: " + extras.getString("DAMAGE"));
			_quant.setText("In Stock:" + extras.getString("QUANTITY"));
			try {
			    quantity = Integer.parseInt(extras.getString("QUANTITY"));
			} catch(NumberFormatException nfe) {
			   Log.i(TAG, "Error. MoreInfoActivity: String to Int convert");
			} 
			
			
		}
		if(quantity <= 1){
			_quant.setTextColor(getResources().getColor(R.color.red));
		}
		if(quantity == 2){
			_quant.setTextColor(getResources().getColor(R.color.yellow));
		}
		if(quantity >= 3){
			_quant.setTextColor(getResources().getColor(R.color.sa_green));
		}
		if(quantity <= 0){
			_quant.setText("OUT OF STOCK");
			_purchaseBtn.setEnabled(false);
			
		}
		
		
		 
		// add button listener
		_purchaseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
		 
					// set title
					alertDialogBuilder.setTitle("Your Title");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Click yes to exit!")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								MoreInfoActivity.this.finish();
							}
						  })
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
					}
				
			});
	
		
		
		
    } // END onCreate
	
}
	
	
	

