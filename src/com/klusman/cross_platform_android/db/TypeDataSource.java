package com.klusman.cross_platform_android.db;

import java.util.ArrayList;
import java.util.List;

import com.klusman.cross_platform_android.WeaponType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TypeDataSource {
	private static final String TAG = "WEAPONS_DATABASE";
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		WeaponsDBOpenHelper.COLUMN_TYPE_ID,
		WeaponsDBOpenHelper.COLUMN_TYPE_NAME
	};
	
	private static final String[] nameColumn = {
		WeaponsDBOpenHelper.COLUMN_TYPE_NAME
	};
	
	public TypeDataSource(Context context){
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
	
	public WeaponType create(WeaponType wType){
		ContentValues values = new ContentValues();
		values.put(WeaponsDBOpenHelper.COLUMN_TYPE_NAME, wType.getName());
		long insertid = database.insert(WeaponsDBOpenHelper.TABLE_TYPES, null, values); // GET AUTO ID
		
		wType.setId(insertid); // SET the ID of the new weapon with the Auto generated one
		return wType;
	};
	
	public List<String> buildTypeArray(){
		Cursor c = database.query(WeaponsDBOpenHelper.TABLE_TYPES, nameColumn, null, null, null, null, null);
		//Log.i(TAG, "tArray Returned " + c.getCount() + " rows");
		
		 
        List<String> types = new ArrayList<String>();
        
        if(c.getCount() > 0){
        	while(c.moveToNext()){
        		types.add(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE_NAME)));
        		//Log.i(TAG, "LIST ITEM: " + c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE_NAME)));

        	}
        }
        Log.i(TAG, "LIST TYPES SIZE: " + types.size());
		return types;
		
	}
	
	public List<WeaponType> findAll(){
		List<WeaponType> types = new ArrayList<WeaponType>();
		Cursor c = database.query(WeaponsDBOpenHelper.TABLE_TYPES, allColumns, null, null, null, null, null);
		//Log.i(TAG, "Types List Returned " + c.getCount() + " rows");
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				WeaponType type = new WeaponType();
				type.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE_ID)));
				type.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE_NAME)));
				types.add(type);
				//Log.i(TAG, type.getName());
			}
		}
		return types;
	} // END LIST WEAPON
}
