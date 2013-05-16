package com.klusman.cross_platform_wk2.db;

import java.util.ArrayList;
import java.util.List;

import com.klusman.cross_platform_wk2.Weapon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WeaponDataSource {
	
	private static final String TAG = "WEAPONS_DATABASE";
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	
	public static final String[] allColumns = {
		WeaponsDBOpenHelper.COLUMN_ID,
		WeaponsDBOpenHelper.COLUMN_NAME,
		WeaponsDBOpenHelper.COLUMN_TYPE,
		WeaponsDBOpenHelper.COLUMN_HANDS,
		WeaponsDBOpenHelper.COLUMN_DAMAGE
	};
	
	public WeaponDataSource(Context context){
        dbHelper = new WeaponsDBOpenHelper(context);
	}
	
	
	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.i(TAG , "Database OPENED");
	}
	
	public void close(){
		Log.i(TAG , "Database CLOSED");
		dbHelper.close();
	}
	
	public Weapon create(Weapon weapon){
		ContentValues values = new ContentValues();
		values.put(WeaponsDBOpenHelper.COLUMN_NAME, weapon.getName());
		values.put(WeaponsDBOpenHelper.COLUMN_TYPE, weapon.getType());
		values.put(WeaponsDBOpenHelper.COLUMN_HANDS, weapon.getHands());
		values.put(WeaponsDBOpenHelper.COLUMN_DAMAGE, weapon.getDamage());
		long insertid = database.insert(WeaponsDBOpenHelper.TABLE_WEAPONS, null, values); // GET AUTO ID
		
		weapon.setId(insertid); // SET the ID of the new weapon with the Auto generated one
		return weapon;
	};
	
	
	
	public List<Weapon> findAll(){
		List<Weapon> weapons = new ArrayList<Weapon>();
		Cursor c = database.query(WeaponsDBOpenHelper.TABLE_WEAPONS, allColumns, null, null, null, null, null);
		Log.i(TAG, "Weapons List Returned " + c.getCount() + " rows");
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				Weapon weapon = new Weapon();
				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
				weapons.add(weapon);
			}
		}
		return weapons;
	} // END LIST WEAPON
	
	public List<Weapon> filterbyType(int type){
		List<Weapon> weaponsByType = new ArrayList<Weapon>();
		//database.execSQL("SELECT * FROM weapons WHERE weapons.type = 1");
		Cursor c = database.query
				(
				WeaponsDBOpenHelper.TABLE_WEAPONS, 
				allColumns, 
				WeaponsDBOpenHelper.COLUMN_TYPE + "=?", 
				new String[] { String.valueOf(type) }, 
				null, 
				null, 
				null
				);

		Log.i(TAG, "Filtered Weapons Returned " + c.getCount() );
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				Weapon weapon = new Weapon();
				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
				weaponsByType.add(weapon);
				Log.i(TAG, weapon.getName());
			}
		}
		return weaponsByType;
	} // END LIST WEAPON
	
	
	
	
	
}
