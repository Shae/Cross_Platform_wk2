package com.klusman.cross_platform_android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.klusman.cross_platform_android.db.TypeDataSource;
import com.klusman.cross_platform_android.db.WeaponDataSource;
import com.klusman.cross_platform_android.db.WeaponsDBOpenHelper;
import com.klusman.cross_platform_android.R;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;


import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {

	private static final String TAG = "WEAPONS_DATABASE";
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	WeaponDataSource datasourceWeapon;
	TypeDataSource datasourceType;
	Spinner sp;
	int spPos = 0;
	List<Weapon> weapons;
	List<Weapon> weapons2;
	ProgressDialog dialog;
	ProgressDialog dialog2;
	RadioGroup radioGrp;
	int selectedRadio;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main); 
		//deleteDB();  // TO CLEAR DB AND START OVER

		Parse.initialize(this, "KOHAaQRdCYXrO1RNBYF3iTSOoxrgTfXRsFUpMdhN", "P3BgADyELTJe2ZyJFUs5cqabAagdVtg517VG2YHf"); 
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseAnalytics.trackAppOpened(getIntent());

		
		datasourceType = new TypeDataSource(this);
		datasourceType.open();
		datasourceWeapon = new WeaponDataSource(this);
		datasourceWeapon.open();


		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Updating Inventory. Please wait...");
		dialog.setIndeterminate(true);
		
		dialog2 = new ProgressDialog(this);
		dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog2.setMessage("Loading Inventory...");
		dialog2.setIndeterminate(true);
		//buildParseData();  // TO AUTO FILL PARSE.COM
		
		// Check to see if there are any Types in the Weapon Types list
		List<WeaponType> types = datasourceType.findAll();
		if(types.size() == 0){
			createTypesData();
		}
		
		buildRadioGrp();
		buildSpinner();

		// Check to see if there are and Weapons in the list
		weapons = datasourceWeapon.findAllNoFilter();
		if(weapons.size() == 0){
			Log.i(TAG, "Get PARSE table data");
			get();
		}
	} // END onCreate


	////////////////////////////////////////////////////////
	///////////////////  FORM BUILDERS ///////////////////// 
	////////////////////////////////////////////////////////

	private void listBuilder(){
		
		lv = (ListView)findViewById(R.id.list);
		lv.setAdapter( new WeaponListCellAdapter(this, weapons));
	}

	private void buildRadioGrp(){
		radioGrp = (RadioGroup)findViewById(R.id.radioGrp);
		radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup arg0, int id) {
				switch (id) {
				case R.id.radioNone:
					Log.i(TAG, "ID");
					MoveRadioUpdate(spPos, "ID");
					break;
				case R.id.radioDamage:
					Log.i(TAG, "DMG");
					MoveRadioUpdate(spPos, "Damage");
					break;
				case R.id.radioAlpha:
					Log.i(TAG, "ALPHA");
					MoveRadioUpdate(spPos, "Alpha");
					break;
				default:
					Log.i(TAG, "ERROR RADIO GROUP FUNCTION");
					break;
				}
			}
		});
	}

	private void buildSpinner(){
		sp = (Spinner)findViewById(R.id.sp1);
		sp.setGravity(17);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String item = sp.getItemAtPosition(position).toString();
				spPos = position;
				selectedRadio = radioGrp.getCheckedRadioButtonId();
				Log.i(TAG, "onItemSeleted Spinner: " + item);
				RadioButton rb = (RadioButton) findViewById(selectedRadio);
				String rbText = (String) rb.getText();
				Log.i(TAG, "onItemSeleted Spinner: " + rbText);

				switch(position){

				case 0: 
					weapons = datasourceWeapon.preFilterALL(rbText);
					listBuilder();
					break;

				case 1:
					weapons = datasourceWeapon.preFilterByType(1, rbText);
					listBuilder();
					break;

				case 2:
					weapons = datasourceWeapon.preFilterByType(2, rbText);
					listBuilder();
					break;

				case 3:
					weapons = datasourceWeapon.preFilterByType(3, rbText);
					listBuilder();
					break;

				case 4:
					weapons = datasourceWeapon.preFilterByType(4, rbText);    
					listBuilder();
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

		List<String> typeList = new ArrayList<String>();
		typeList.add("SHOW ALL");
		typeList.addAll(datasourceType.buildTypeArray());
		if(typeList.size() > 0){
			ArrayAdapter<String> typeListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
			typeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			sp.setAdapter(typeListAdapter);
		}else{
			Log.i(TAG, "LIST IS EMPTY");
		}
	}


	////////////////////////////////////////////////////////
	///////////////  STATUS CHANGE FUNCTIONS  //////////////
	////////////////////////////////////////////////////////

	@Override
	protected void onResume() {
		super.onResume();
		datasourceType.open();
		datasourceWeapon.open();	
		resetWindow();
		countParse();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasourceType.close();
		datasourceWeapon.close();
	}
	
	public void myToast(String text){  
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(MainActivity.this, textIN, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};// end myToast

	////////////////////////////////////////////////////////
	/////////////////  DATA MANIPULATION   /////////////////
	//////////////////////////////////////////////////////// 

	private void createTypesData(){
		WeaponType type = new WeaponType();
		type.setName("SWORD");
		type = datasourceType.create(type);
		//Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());

		type = new WeaponType();
		type.setName("AXE");
		type = datasourceType.create(type);
		//Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());

		type = new WeaponType();
		type.setName("FLAIL");
		type = datasourceType.create(type);
		//Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());

		type = new WeaponType();
		type.setName("MISSLE");
		type = datasourceType.create(type);
		//Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());
	}
	
	private void get(){
		dialog.show();
		ParseQuery q = new ParseQuery("weaponsTablePARSE");
		q.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					
					int x = objects.size();

					for ( int i = 0; i < x; i++){  // Sending Object ID's to kids list
						Log.i(TAG, "OBJECT BUILD");
						
						String ParseID = objects.get(i).getObjectId().toString();  // to get the actual parse OBJECT ID
						
						int _id = (Integer) objects.get(i).get("wID");
						//Log.i(TAG, String.valueOf(_id));
						
						String name = (String) objects.get(i).get("wName");
						//String parseID = (String) objects.get(i).get("objectId");
						
						//Log.i(TAG, ParseID);
						int type = (Integer) objects.get(i).get("wType");
						//Log.i(TAG, String.valueOf(type));
						int hands = (Integer) objects.get(i).get("wHands");
						//Log.i(TAG, String.valueOf(hands));
						int damage = (Integer) objects.get(i).get("wDamage");
						//Log.i(TAG, String.valueOf(damage));
						int quantity = (Integer) objects.get(i).get("wQuantity");
						//Log.i(TAG, String.valueOf(quantity));	
						
						String dateOf = (String) objects.get(i).getUpdatedAt().toString();
						SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
						Date dateUpdated = null;
						try {
							dateUpdated = format.parse(dateOf);
							//Log.i(TAG, "*DATED UPDATED: " + String.valueOf(dateUpdated));  //Works
						} catch (java.text.ParseException e1) {
							e1.printStackTrace();
						}
						
						String updatedAt = dateUpdated.toString();

						//Log.i(TAG, "updated at : " + String.valueOf(updatedAt));

						
						Weapon weapon = new Weapon();
						weapon.setParseId(ParseID);
						weapon.setId(_id);
						weapon.setName(name);
						weapon.setType(type);
						weapon.setHands(hands);
						weapon.setDamage(damage);
						weapon.setQuantity(quantity);
						weapon.setDateUpdated(updatedAt);
						weapon = datasourceWeapon.create(weapon);
						//Log.i(TAG, "Weapons created " + name + ", parseId: " + ParseID + ", updated at: " + updatedAt);
					} // end for loop
			}else {
				String ee = e.toString();
				Log.i("ERROR from PARSE", ee);
			}  // END IF 
				resetWindow();
				dialog.dismiss();
			} // end done
		}); // end find in BG	
	} // end GET function
	
	private void MoveRadioUpdate(int pos, String sort){
		switch(pos){

		case 0: 
			//Log.i(TAG, "Radio Updated to: " + sort);
			weapons = datasourceWeapon.preFilterALL(sort);
			listBuilder();
			break;

		case 1:
			//Log.i(TAG, "Radio Updated to: " + sort);
			weapons = datasourceWeapon.preFilterByType(1, sort);
			listBuilder();
			break;

		case 2:
			//Log.i(TAG, "Radio Updated to: " + sort);
			weapons = datasourceWeapon.preFilterByType(2, sort);
			listBuilder();
			break;

		case 3:
			//Log.i(TAG, "Radio Updated to: " + sort);
			weapons = datasourceWeapon.preFilterByType(3, sort);
			listBuilder();
			break;

		case 4:
			//Log.i(TAG, "Radio Updated to: " + sort);
			weapons = datasourceWeapon.preFilterByType(4, sort);
			listBuilder();
			break;
		}
	}

	public void addWeapon2Parse(int id, String name, int type, int hands, int damage, int quantity){
		final String name2 = name;
		Log.i("TAG", "Weapon " + name + " being saved to PARSE");
		ParseObject wepObject = new ParseObject("weaponsTablePARSE");
		wepObject.put("wID", id);
		wepObject.put("wName", name);
		wepObject.put("wType", type);
		wepObject.put("wHands", hands);
		wepObject.put("wDamage", damage);
		wepObject.put("wQuantity", quantity);
		wepObject.saveEventually(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				Log.i("TAG", "Weapon " + name2 + " has been been saved");
				myToast(name2 + " Saved to Parse Table");
				
			}
		});  // save when connection available
	}

	public void countParse(){
		ParseQuery query = new ParseQuery("weaponsTablePARSE");
		query.whereExists("wID");
		query.countInBackground(new CountCallback() {
		  public void done(int count, ParseException e) {
		    if (e == null) {
		      // The count request succeeded. Log the count
		      Log.i(TAG, "TOTAL PARSE ROWS: " + count );
		      Log.i(TAG, "Parse count: " + count + " Local Count: " +  weapons.size());
		      if(count != weapons.size()){
		    	  Log.i(TAG, "NUMBERS DIF");
		    	  deleteDBandRebuild();
		    	  //get();
		      }
		    } else {
		      // The request failed
		    }
		  }
		});
	}
	
	private void resetWindow(){	//Used in the getParseTable Function
		sp.setSelection(0);  // reset Spinner to all
		weapons = datasourceWeapon.preFilterALL("ID");  // reset data to all
		Log.i(TAG, "REFRESH WEAPON DATA");
		listBuilder();
	}

	private void deleteDB(){  // To clear local DB and refresh everything
		this.deleteDatabase(WeaponsDBOpenHelper.DATABASE_NAME);
		Log.i(TAG, "DATABASE DELETED AS REQUESTED : LINE 371");
	}

	private void deleteDBandRebuild(){
		datasourceWeapon.deleteTableAndRebuild();
		get();
		
	}
	////////////////////////////////////////////////////////
	//////////////////  OLD TRASH CODE   ///////////////////
	////////////////////////////////////////////////////////


	


}


