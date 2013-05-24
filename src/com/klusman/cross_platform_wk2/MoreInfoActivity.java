package com.klusman.cross_platform_wk2;


import com.klusman.cross_platform_wk2.db.WeaponDataSource;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MoreInfoActivity extends Activity {
	String name;
	String parseID;
	String id;
	String type;
	String hands;
	String damage;
	int wepDwn;
	long longID;
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;


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
			name = extras.getString("NAME");
			parseID = extras.getString("PARSEID");
			_name.setText(extras.getString("NAME"));	
			_id.setText("ID: " + extras.getString("ID"));
			id = extras.getString("ID");
			longID = Long.parseLong(id);
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
		if(quantity == 1){
			_quant.setTextColor(getResources().getColor(R.color.yellow));
			_quant.setText("In Stock:" + String.valueOf(quantity));
		}
		if(quantity == 2){
			_quant.setTextColor(getResources().getColor(R.color.yellow));
			_quant.setText("In Stock:" + String.valueOf(quantity));
		}
		if(quantity >= 3){
			_quant.setTextColor(getResources().getColor(R.color.sa_green));
			_quant.setText("In Stock:" + String.valueOf(quantity));
		}
		if(quantity <= 0){
			_quant.setText("OUT OF STOCK");
			_purchaseBtn.setEnabled(false);
			_quant.setTextColor(getResources().getColor(R.color.red));

		}


		// add button listener
		_purchaseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle("Confirm Purchase");

				// set dialog message
				alertDialogBuilder
				.setMessage("Purchase 1 " + name)
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						upParse();
						
						WeaponDataSource updt = new WeaponDataSource(context);
						updt.open();
						updt.updateAnItem(longID, wepDwn);
						updt.close();			
						MoreInfoActivity.this.finish();
						
					}
				})
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case (R.id.menuADDwep):
			Log.i(TAG, "ADD MENU BUTTON CLICKED");
			Intent intent = new Intent(MoreInfoActivity.this, AddWeaponActivity.class);
			startActivity(intent);
			
			return true;
		case (R.id.menuDELETEwep):
			Log.i(TAG, "DELETE MENU BUTTON CLICKED");
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Confirm DELETE");

		// set dialog message
		alertDialogBuilder
		.setMessage("DELETE " + name)
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				
				
				ParseObject.createWithoutData("weaponsTablePARSE", parseID).deleteEventually();
				Log.i(TAG, "Deleting data from parse");
				MoreInfoActivity.this.finish();
			}
		})
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
			return true;
		case (R.id.action_settings):
			Log.i(TAG, "SETTINGS MENU BUTTON CLICKED");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}  
	
	private void upParse(){
		wepDwn = quantity - 1;
		Log.i(TAG, "Starts parse update");
		ParseQuery query = new ParseQuery("weaponsTablePARSE");
		query.getInBackground(parseID, new GetCallback() {
			public void done(final ParseObject object, ParseException e) {
				if (e == null) {
					Log.i(TAG, "Object from Parse: " + object.get("wName"));
					object.put("wQuantity", wepDwn);
					object.saveInBackground(new SaveCallback() {
						public void done(ParseException e) {
							Log.i(TAG, "Parse Item ID: " + parseID + " updated quantity from: " + String.valueOf(quantity) + " to: " + String.valueOf((quantity - 1)));
						}
					});
				}
				else { 
					e.printStackTrace();
				}
			}
		});
	}




}// END Activity






