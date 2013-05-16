package com.klusman.cross_platform_wk2;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.klusman.cross_platform_wk2.db.TypeDataSource;
import com.klusman.cross_platform_wk2.db.WeaponDataSource;
import com.klusman.cross_platform_wk2.db.WeaponsDBOpenHelper;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);  // MUST BE BEFORE ANY LAYOUT CALLS  GRRRRRRR
       // File database = getApplicationContext().getDatabasePath(WeaponsDBOpenHelper.DATABASE_NAME);
        //deleteDB();

        datasourceType = new TypeDataSource(this);
        datasourceType.open();
        datasourceWeapon = new WeaponDataSource(this);
        datasourceWeapon.open();
       
        
       //ListAdapter list = (ListAdapter)findViewById(R.id.listView1);

        
        
        // Check to see if there are and Weapons in the list
        weapons = datasourceWeapon.findAll();
        if(weapons.size() == 0){
        	createWeaponData();
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
        tableBuilder();
        
    }
    
    private void deleteDB(){
   
    	this.deleteDatabase(WeaponsDBOpenHelper.DATABASE_NAME);
    	Log.i(TAG, "DB DELETED");

    }
    
   private void tableBuilder(){
	   table = (TableLayout) findViewById(R.id.tableLoyout);
	   Log.i(TAG, "Weapon list size: " + String.valueOf(weapons.size()));
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
  		
  		t11.setText("ID");		
		t21.setText("NAME");
		t31.setText("TYPE");
		t41.setText("HANDS");
		t51.setText("DAMAGE");
  		
		titlerow.addView(t11);
		titlerow.addView(t21);
		titlerow.addView(t31);
		titlerow.addView(t41);
		titlerow.addView(t51);
		
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


			t1.setText(String.valueOf(wpn.getId()));		
			t2.setText(wpn.getName());
			t3.setText(String.valueOf(wpn.getType()));
			t4.setText(String.valueOf(wpn.getHands()));
			t5.setText(String.valueOf(wpn.getDamage()));


			// add the TextView and the CheckBox to the new TableRow
			row.addView(t1);
			row.addView(t2);
			row.addView(t3);
			row.addView(t4);
			row.addView(t5);
							
					
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
    	     
    	    	    Toast.makeText(sp.getContext(), "You selected Type: " + item,
    	    	    		
   	    	        Toast.LENGTH_LONG).show();
    	    	    
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
        // Inflate the menu; this adds items to the action bar if it is present.
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
    	Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());
    	
    	type = new WeaponType();
    	type.setName("AXE");
    	type = datasourceType.create(type);
    	Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());
    	
    	type = new WeaponType();
    	type.setName("FLAIL");
    	type = datasourceType.create(type);
    	Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());
    	
    	type = new WeaponType();
    	type.setName("MISSLE");
    	type = datasourceType.create(type);
    	Log.i(TAG, "Type created with id: " + type.getId() + " named: " + type.getName());
    }
    
    
    private void createWeaponData(){
    	Weapon weapon = new Weapon();
    	weapon.setName("Long Sword");
    	weapon.setType(1);
    	weapon.setHands(1);
    	weapon.setDamage(30);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Short Axe");
    	weapon.setType(2);
    	weapon.setHands(1);
    	weapon.setDamage(25);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Pill Flail");
    	weapon.setType(3);
    	weapon.setHands(2);
    	weapon.setDamage(75);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Long Bow");
    	weapon.setType(4);
    	weapon.setHands(2);
    	weapon.setDamage(45);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Dagger");
    	weapon.setType(1);
    	weapon.setHands(1);
    	weapon.setDamage(15);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Crossbow");
    	weapon.setType(4);
    	weapon.setHands(2);
    	weapon.setDamage(150);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Morning Star");
    	weapon.setType(3);
    	weapon.setHands(1);
    	weapon.setDamage(45);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Blowgun");
    	weapon.setType(4);
    	weapon.setHands(2);
    	weapon.setDamage(15);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Short Sword");
    	weapon.setType(1);
    	weapon.setHands(1);
    	weapon.setDamage(25);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    	
    	weapon = new Weapon();
    	weapon.setName("Glass Axe");
    	weapon.setType(2);
    	weapon.setHands(2);
    	weapon.setDamage(110);
    	weapon = datasourceWeapon.create(weapon);
    	Log.i(TAG, "Weapons created with id " + weapon.getId());
    }
}


