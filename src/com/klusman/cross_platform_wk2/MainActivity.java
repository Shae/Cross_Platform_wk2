package com.klusman.cross_platform_wk2;


import java.util.ArrayList;
import java.util.List;

import com.klusman.cross_platform_wk2.db.TypeDataSource;
import com.klusman.cross_platform_wk2.db.WeaponDataSource;
import com.klusman.cross_platform_wk2.db.WeaponsDBOpenHelper;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "WEAPONS_DATABASE";
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	WeaponDataSource datasourceWeapon;
	TypeDataSource datasourceType;
	Spinner sp;
	List<Weapon> weapons;
	List<Weapon> weapons2;
	TableLayout table;
	ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main); 
        deleteDB();  // TO CLEAR DB AND START OVER

        Parse.initialize(this, "KOHAaQRdCYXrO1RNBYF3iTSOoxrgTfXRsFUpMdhN", "P3BgADyELTJe2ZyJFUs5cqabAagdVtg517VG2YHf"); 
		ParseAnalytics.trackAppOpened(getIntent());
        
        datasourceType = new TypeDataSource(this);
        datasourceType.open();
        datasourceWeapon = new WeaponDataSource(this);
        datasourceWeapon.open();
        
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Updating Inventory. Please wait...");
        dialog.setIndeterminate(true);
        
        // Check to see if there are and Weapons in the list
        weapons = datasourceWeapon.findAll();
        if(weapons.size() == 0){
        	getParseTable();
        }
        weapons2 = datasourceWeapon.filterbyType(1);
        Log.i(TAG, "WEAPONS 2 SIZE: " + String.valueOf(weapons2.size()));
 
        // Check to see if there are any Types in the Weapon Types list
        List<WeaponType> types = datasourceType.findAll();
        if(types.size() == 0){
        	createTypesData();
        }


        table = (TableLayout) findViewById(R.id.tableLoyout);
        //ArrayAdapter<Weapon> weaponListAdapter = new ArrayAdapter<Weapon>(this, R.layout.custom_cell, weapons);
        
        
        
        buildSpinner();
        
       // buildParseData();  // to auto fill parse
        
    } // END onCreate
    
    
    public void addWeapon2Parse(int id, String name, int type, int hands, int damage, int quantity){
    	Log.i("TAG", "Weapon " + name + " being saved to PARSE");
		ParseObject wepObject = new ParseObject("weaponsTable");
		wepObject.put("wID", id);
		wepObject.put("wName", name);
		wepObject.put("wType", type);
		wepObject.put("wHands", hands);
		wepObject.put("wDamage", damage);
		wepObject.put("wQuantity", quantity);
		wepObject.saveInBackground();
    }
    
    private void resetWindow(){
		table.removeAllViews();  // clear table views
		sp.setSelection(0);  // reset Spinner to all
		weapons = datasourceWeapon.findAll();  // reset data to all
        tableBuilder();  // rebuild table with all data
    }
    
    private void deleteDB(){
    	this.deleteDatabase(WeaponsDBOpenHelper.DATABASE_NAME);
    	Log.i(TAG, "DATABASE DELETED AS REQUESTED : LINE 53");
    }
    
   private void tableBuilder(){
	   	table = (TableLayout) findViewById(R.id.tableLoyout);
	   	//Log.i(TAG, "Weapon list size: " + String.valueOf(weapons.size()));
	   	LayoutParams T_params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
	   	LayoutParams T_params2 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
	   	
	   
	   /////  TITLE ROW  /////
	   	TableRow titlerow = new TableRow(this);
	   	TextView t11 = new TextView(this);
	   	t11.setLayoutParams(T_params);
	   	TextView t21 = new TextView(this);
	   	t21.setLayoutParams(T_params2);
	   	TextView t31 = new TextView(this);
	   	t31.setLayoutParams(T_params);
  		TextView t41 = new TextView(this);
  		t41.setLayoutParams(T_params);
  		TextView t51 = new TextView(this);
  		t51.setLayoutParams(T_params);
  		TextView t61 = new TextView(this);
  		t61.setLayoutParams(T_params);
  		
  		t11.setText("ID");		
		t21.setText("NAME");
		t31.setText("TYPE");
		t41.setText("HANDS");
		t51.setText("DMG");
		t61.setText("QTY");
  		
		titlerow.addView(t11);
		titlerow.addView(t21);
		titlerow.addView(t31);
		titlerow.addView(t41);
		titlerow.addView(t51);
		titlerow.addView(t61);
		
	   table.addView(titlerow,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	   
	   
	   //////  TABLE DATA  //////
	   for(int i = 0; i < weapons.size(); i++){
		   Weapon wpn = weapons.get(i);
		   
		   LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		   LayoutParams params2 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
		   // create a new TableRow
		   TableRow row = new TableRow(this);
		   
		   // create a new TextView
		   
		   TextView t1 = new TextView(this);
		   		t1.setLayoutParams(params);
		   TextView t2 = new TextView(this);
		   		t2.setLayoutParams(params2);
		   TextView t3 = new TextView(this);
		   		t3.setLayoutParams(params);
		   TextView t4 = new TextView(this);
		   		t4.setLayoutParams(params);
		   TextView t5 = new TextView(this);
		   		t5.setLayoutParams(params);
		   TextView t6 = new TextView(this);
		   		t6.setLayoutParams(params);


			t1.setText(String.valueOf(wpn.getId()));		
			t2.setText(wpn.getName());
			t3.setText(String.valueOf(wpn.getType()));
			t4.setText(String.valueOf(wpn.getHands()));
			t5.setText(String.valueOf(wpn.getDamage()));
			t6.setText(String.valueOf(wpn.getQuantity()));


			// add the TextView and the CheckBox to the new TableRow
			row.addView(t1);
			row.addView(t2);
			row.addView(t3);
			row.addView(t4);
			row.addView(t5);
			row.addView(t6);
							
					
			// add the TableRow to the TableLayout
			table.addView(row,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	   }    
   };
  

    
    private void buildSpinner(){
    	
    	sp = (Spinner)findViewById(R.id.sp1);
    	sp.setOnItemSelectedListener(new OnItemSelectedListener() {
    		
    	    @Override
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	String item = sp.getItemAtPosition(position).toString();
    	     
    	    	    //Toast.makeText(sp.getContext(), "You selected Type: " + item,
    	    	    		
   	    	        //Toast.LENGTH_LONG).show();
    	    	    
    	    	    switch(position){

    	    	    case 0: 
    	    	    	weapons = datasourceWeapon.findAll();
    	    	    	table.removeAllViews();
    	    	    	tableBuilder();
    	    	        break;

    	    	    case 1:
    	    	    	weapons = datasourceWeapon.filterbyType(1);
    	    	    	table.removeAllViews();
    	    	    	tableBuilder();
    	    	        break;
    	    	         
    	    	    case 2:
    	    	    	weapons = datasourceWeapon.filterbyType(2);
    	    	    	table.removeAllViews();
    	    	    	tableBuilder();
   	    	         	break;
   	    	         
    	    	    case 3:
    	    	    	weapons = datasourceWeapon.filterbyType(3);
    	    	    	table.removeAllViews();
    	    	    	tableBuilder();
   	    	         	break;
   	    	         	
    	    	    case 4:
    	    	    	weapons = datasourceWeapon.filterbyType(4);
    	    	    	table.removeAllViews();
    	    	    	tableBuilder();
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

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	datasourceType.open();
    	datasourceWeapon.open();
    	
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	datasourceType.close();
    	datasourceWeapon.close();
    }
    
    
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
    
    
    private void buildParseData(){  // to Populate PARSE.com
    	Parse.initialize(this, "KOHAaQRdCYXrO1RNBYF3iTSOoxrgTfXRsFUpMdhN", "P3BgADyELTJe2ZyJFUs5cqabAagdVtg517VG2YHf"); 
    	addWeapon2Parse(1001, "Long Sword", 1, 1, 45, 5);
    	addWeapon2Parse(1002, "Short Sword", 1, 1, 25, 3);
    	addWeapon2Parse(1003, "Rusty Dagger", 1, 1, 10, 1);
    	addWeapon2Parse(1004, "Great Sword", 1, 2, 90, 1);
    	addWeapon2Parse(2001, "Hatchet", 2, 1, 15, 1);
    	addWeapon2Parse(2002, "Hand Axe", 2, 1, 30, 3);
    	addWeapon2Parse(2003, "Beared Axe", 2, 1, 55, 5);
    	addWeapon2Parse(2004, "Great Axe", 2, 2, 95, 1);
    	addWeapon2Parse(3001, "Morning Star", 3, 1, 40, 4);
    	addWeapon2Parse(3002, "Pill Flail", 3, 2, 120, 2);
    	addWeapon2Parse(3003, "Double Flail", 3, 1, 60, 1);
    	addWeapon2Parse(4001, "Short Bow", 4, 2, 45, 3);
    	addWeapon2Parse(4002, "Long Bow", 4, 2, 90, 2);
    	addWeapon2Parse(4003, "Crossbow", 4, 2, 115, 1);	
    	
    }
 
    
    private void getParseTable(){
    	//Log.i(TAG, "OBJECT ID TEST : Step1");
    	dialog.show();
    		ParseQuery query = new ParseQuery("weaponsTable");
    		query.findInBackground(
    				
    				new FindCallback() {
    					
    					@Override
    					public void done(List<ParseObject> objects, ParseException e) {
    						if (e == null) {
    							//Log.i(TAG, "OBJECT ID TEST : Step2");
    							int x = objects.size();
    							
    							for ( int i = 0; i < x; i++){  // Sending Object ID's to kids list
    								Log.i(TAG, "OBJECT BUILD");
    								//String o = objects.get(i).getObjectId().toString();  // to get the actual parse OBJECT ID
    								int _id = (Integer) objects.get(i).get("wID");
    								//Log.i(TAG, String.valueOf(_id));
    								String name = (String) objects.get(i).get("wName");
    								//Log.i(TAG, name);
    								int type = (Integer) objects.get(i).get("wType");
    								//Log.i(TAG, String.valueOf(type));
    								int hands = (Integer) objects.get(i).get("wHands");
    								//Log.i(TAG, String.valueOf(hands));
    								int damage = (Integer) objects.get(i).get("wDamage");
    								//Log.i(TAG, String.valueOf(damage));
    								int quantity = (Integer) objects.get(i).get("wQuantity");
    								//Log.i(TAG, String.valueOf(quantity));
    								
    								Weapon weapon = new Weapon();
    								weapon.setId(_id);
    						    	weapon.setName(name);
    						    	weapon.setType(type);
    						    	weapon.setHands(hands);
    						    	weapon.setDamage(damage);
    						    	weapon.setQuantity(quantity);
    						    	weapon = datasourceWeapon.create(weapon);
    						    	//Log.i(TAG, "Weapons created with id " + name);

    							} // END FOR LOOP
    							
    						} else {
    							String ee = e.toString();
    							Log.i("ERROR from PARSE", ee);
    						}  // END IF
    						resetWindow();
    						dialog.dismiss();
    					}
    				});
    	}  // END getParseTable
    	
   
}


