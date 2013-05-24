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
		WeaponsDBOpenHelper.COLUMN_PARSEID,
		WeaponsDBOpenHelper.COLUMN_NAME,
		WeaponsDBOpenHelper.COLUMN_TYPE,
		WeaponsDBOpenHelper.COLUMN_HANDS,
		WeaponsDBOpenHelper.COLUMN_DAMAGE,
		WeaponsDBOpenHelper.COLUMN_QUANTITY,
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
		Log.i(TAG, "Create Weapon Function in WeaponDataSource");
		ContentValues values = new ContentValues();
		values.put(WeaponsDBOpenHelper.COLUMN_ID, weapon.getId());
		Log.i(TAG, "Create ID : " + String.valueOf(weapon.getId()));
		values.put(WeaponsDBOpenHelper.COLUMN_PARSEID, weapon.getParseId());
		Log.i(TAG, "Create PARSEID : " + weapon.getParseId());
		values.put(WeaponsDBOpenHelper.COLUMN_NAME, weapon.getName());
		Log.i(TAG, "Create NAME : " + weapon.getName());
		values.put(WeaponsDBOpenHelper.COLUMN_TYPE, weapon.getType());
		Log.i(TAG, "Create TYPE : " + String.valueOf(weapon.getType()));
		values.put(WeaponsDBOpenHelper.COLUMN_HANDS, weapon.getHands());
		Log.i(TAG, "Create HANDS : " + String.valueOf(weapon.getHands()));
		values.put(WeaponsDBOpenHelper.COLUMN_DAMAGE, weapon.getDamage());
		Log.i(TAG, "Create DAMAGE : " + String.valueOf(weapon.getDamage()));
		values.put(WeaponsDBOpenHelper.COLUMN_QUANTITY, weapon.getQuantity());
		Log.i(TAG, "Create QUANTITY : " + String.valueOf(weapon.getQuantity()));
		long insertid = database.insert(WeaponsDBOpenHelper.TABLE_WEAPONS, null, values); // GET AUTO ID
		
		weapon.setId(insertid); // SET the ID of the new weapon with the Auto generated one
		return weapon;
	};
	
	public List<Weapon> findAllNoFilter(){
		Log.i(TAG, "**START find all no Filter");
		List<Weapon> weapons = new ArrayList<Weapon>();
		Cursor c = database.query(WeaponsDBOpenHelper.TABLE_WEAPONS, allColumns, null, null, null, null, null);
		Log.i(TAG, "Weapons List Returned " + c.getCount() + " rows");
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				Weapon weapon = new Weapon();
				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
				weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
				weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
				Log.i(TAG, "findAll quantity call : " + c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
				weapons.add(weapon);
			}
		}
		return weapons;
	} // END LIST WEAPON
	
	public List<Weapon> filterALLSortby(String sort){
		Log.i(TAG, "**START filterALLSortby function");
		Log.i(TAG, sort);
		List<Weapon> weaponsByType = new ArrayList<Weapon>();
		//database.execSQL("SELECT * FROM weapons WHERE weapons.type = 1");
		Cursor c = database.query
				(
				WeaponsDBOpenHelper.TABLE_WEAPONS, 
				allColumns, 
				null, 
				null, 
				null, 
				null, 
				sort
				);

		//Log.i(TAG, "Filtered Weapons Returned " + c.getCount() );
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				Weapon weapon = new Weapon();
				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
				weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
				weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
				weaponsByType.add(weapon);
				//Log.i(TAG, weapon.getName());
			}
		}
		return weaponsByType;
	} // END LIST WEAPON
	
	public List<Weapon> findAll(String sort){
		Log.i(TAG, "**START find all with sort");
		Log.i(TAG, sort);
		List<Weapon> weapons = new ArrayList<Weapon>();
		Cursor c = database.query(WeaponsDBOpenHelper.TABLE_WEAPONS, allColumns, null, null, null, null, null);
		Log.i(TAG, "Weapons List Returned " + c.getCount() + " rows");
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				Weapon weapon = new Weapon();
				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
				weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
				weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
				//Log.i(TAG, "findAll quantity call : " + c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
				weapons.add(weapon);
			}
		}
		return weapons;
	} // END LIST WEAPON
	
	public void updateItemQuant(int quant, int id){
		ContentValues args = new ContentValues();
		args.put(WeaponsDBOpenHelper.COLUMN_QUANTITY, quant);
		database.update(WeaponsDBOpenHelper.TABLE_WEAPONS, args, WeaponsDBOpenHelper.COLUMN_ID + "=" + id, null);
	}
	
	public List<Weapon> preFilterALL(String sort){
		//Log.i(TAG, "**START preFilter ALL with sort");
		if(sort.equals("ID")){
			Log.i(TAG, "***SORT ALL ID***");
			return filterALLSortby(WeaponsDBOpenHelper.COLUMN_ID);
		}
		if(sort.equals("Damage")){
			Log.i(TAG, "***SORT ALL DAMAGE***");
			return filterALLSortby(WeaponsDBOpenHelper.COLUMN_DAMAGE);
		}
		if(sort.equals("Alpha")){
			Log.i(TAG, "***SORT ALL ALPHA***");
			return filterALLSortby(WeaponsDBOpenHelper.COLUMN_NAME);
		}
		return null;
	}
	
	public List<Weapon> preFilterByType(int type, String sort){
		//int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();  <--------  COOL FOR DEBUGGING
		//Log.i(TAG, "**START preFilterByType with sort" + "LINE#: " + String.valueOf(lineNumber));
		if(sort.equals("ID")){
			Log.i(TAG, "***SORT ID***");
			return filterbyTypeSortby(type, WeaponsDBOpenHelper.COLUMN_ID);
		}
		if(sort.equals("Damage")){
			Log.i(TAG, "***SORT DAMAGE***");
			return filterbyTypeSortby(type, WeaponsDBOpenHelper.COLUMN_DAMAGE);
		}
		if(sort.equals("Alpha")){
			Log.i(TAG, "***SORT ALPHA***");
			return filterbyTypeSortby(type, WeaponsDBOpenHelper.COLUMN_NAME);
		}
		return null;
	}
	

	
	
	public List<Weapon> filterbyTypeSortby(int type, String sort){
		Log.i(TAG, "**START filterByType with sort");
		Log.i(TAG, sort + " plus type: " + type);
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
				sort
				);

		//Log.i(TAG, "Filtered Weapons Returned " + c.getCount() );
		
		if(c.getCount() > 0){
			while(c.moveToNext()){
				Weapon weapon = new Weapon();
				weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
				weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
				weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
				weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
				weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
				weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
				weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
				weaponsByType.add(weapon);
				//Log.i(TAG, weapon.getName());
			}
		}
		return weaponsByType;
	} // END LIST WEAPON


	public void updateAnItem(long longID, int wepDwn) {
		// TODO Auto-generated method stub
		Log.i(TAG, "UPDATE LOCAL: " + String.valueOf(wepDwn) + " & " + String.valueOf(longID));
		ContentValues cvUpdate = new ContentValues();
		//cvUpdate.put(WeaponsDBOpenHelper.COLUMN_ID, longID);
		cvUpdate.put(WeaponsDBOpenHelper.COLUMN_QUANTITY, wepDwn);
		database.update(WeaponsDBOpenHelper.TABLE_WEAPONS, cvUpdate, WeaponsDBOpenHelper.COLUMN_ID + "=" + longID, null);
		Log.i(TAG, "UPDATE LOCAL: DONE");
	}
	


	public void PushNewWeaponToLocal(long longID, String name, int type,
			int hands, int damage, int inStock) {
		ContentValues cvPush = new ContentValues();
		cvPush.put(WeaponsDBOpenHelper.COLUMN_ID, longID);
		cvPush.put(WeaponsDBOpenHelper.COLUMN_NAME, name);
		cvPush.put(WeaponsDBOpenHelper.COLUMN_TYPE, type);
		cvPush.put(WeaponsDBOpenHelper.COLUMN_HANDS, hands);
		cvPush.put(WeaponsDBOpenHelper.COLUMN_DAMAGE, damage);
		cvPush.put(WeaponsDBOpenHelper.COLUMN_QUANTITY, inStock);
		database.insert(WeaponsDBOpenHelper.TABLE_WEAPONS, null, cvPush);
		Log.i(TAG, "PUSH to PARSE DONE: DONE");
		
	}
	
	public void deleteTableAndRebuild(){
		database.execSQL("DROP TABLE IF EXISTS " + WeaponsDBOpenHelper.TABLE_WEAPONS);
		Log.i(TAG, "DROP TABLE");
		database.execSQL(WeaponsDBOpenHelper.TABLE_CREATE);
		Log.i(TAG, "REBUILD TABLE");
	}
	
//	public List<Weapon> filterbyType(int type){
//	List<Weapon> weaponsByType = new ArrayList<Weapon>();
//	//database.execSQL("SELECT * FROM weapons WHERE weapons.type = 1");
//	Cursor c = database.query
//			(
//			WeaponsDBOpenHelper.TABLE_WEAPONS, 
//			allColumns, 
//			WeaponsDBOpenHelper.COLUMN_TYPE + "=?", 
//			new String[] { String.valueOf(type) }, 
//			null, 
//			null, 
//			null
//			);
//
//	//Log.i(TAG, "Filtered Weapons Returned " + c.getCount() );
//	
//	if(c.getCount() > 0){
//		while(c.moveToNext()){
//			Weapon weapon = new Weapon();
//			weapon.setId(c.getLong(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_ID)));
//			weapon.setParseId(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_PARSEID)));
//			weapon.setName(c.getString(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_NAME)));
//			weapon.setType(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_TYPE)));
//			weapon.setHands(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_HANDS)));
//			weapon.setDamage(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_DAMAGE)));
//			weapon.setQuantity(c.getInt(c.getColumnIndex(WeaponsDBOpenHelper.COLUMN_QUANTITY)));
//			weaponsByType.add(weapon);
//			//Log.i(TAG, weapon.getName());
//		}
//	}
//	return weaponsByType;
//} // END LIST WEAPON
	
}
