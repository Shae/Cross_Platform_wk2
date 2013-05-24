package com.klusman.cross_platform_wk2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WeaponsDBOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "WEAPONS_DATABASE";

	public static final String DATABASE_NAME = "weapons.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_WEAPONS = "wpnsTable";
	public static final String COLUMN_PARSEID = "parseID";
	public static final String COLUMN_ID = "weaponID";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_HANDS = "hands";
	public static final String COLUMN_DAMAGE = "damage";
	public static final String COLUMN_QUANTITY = "quantity";
	
	public static final String TABLE_TYPES = "types";
	public static final String COLUMN_TYPE_ID = "typeID";
	public static final String COLUMN_TYPE_NAME = "typeName";

	
	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_WEAPONS + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_PARSEID + " TEXT NOT NULL, " +
			COLUMN_NAME + " TEXT NOT NULL, " +
			COLUMN_TYPE + " INTEGER NOT NULL, " +
			COLUMN_HANDS + " INTEGER NOT NULL, " +
			COLUMN_DAMAGE + " INTEGER NOT NULL, " +
			COLUMN_QUANTITY + " INTERGER NOT NULL " +
			")";
			
	private static final String TABLE_CREATE_TYPE = "CREATE TABLE " + TABLE_TYPES + " (" +
			COLUMN_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TYPE_NAME + " TEXT NOT NULL " +
			")";
	
	
	public WeaponsDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_TYPE);
		Log.i(TAG, TABLE_TYPES + " Table has been created");
		
		db.execSQL(TABLE_CREATE);
		Log.i(TAG, DATABASE_NAME + "Table has been created");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEAPONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
		Log.i(TAG, " Dropping older DB Version " + oldVersion + " for new Version " + newVersion + ".");
		onCreate(db);

	}

}
