package com.klusman.cross_platform_android;

import com.klusman.cross_platform_android.R;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class AddWeaponActivity extends Activity {

	private static final String TAG = "WEAPONS_DATABASE";
	EditText ID;
	EditText Name;
	int Hands = 1;
	EditText Damage;
	EditText Stock;
	Button save;
	RadioGroup radio;
	RadioGroup radio2;
	int selectedRadio;
	Context context;
	
	int Pos = 1;
	String name2;
	int hands2;
	String damage;
	int damage2;
	String stock;
	int stock2;
	String id;
	int id2;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "KOHAaQRdCYXrO1RNBYF3iTSOoxrgTfXRsFUpMdhN", "P3BgADyELTJe2ZyJFUs5cqabAagdVtg517VG2YHf"); 
		ParseAnalytics.trackAppOpened(getIntent());
		context = this;
		setContentView(R.layout.add_wep_activity); 
		formBuild();
		buildRadioGrp();
		
	}
	
	private void buildRadioGrp(){
		radio = (RadioGroup)findViewById(R.id.radioGrpNew);
		radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup arg0, int id) {
				switch (id) {
				case R.id.radioSword:
					Log.i(TAG, "sword");
					Pos = 1;
					break;
					
				case R.id.radioAxe:
					Log.i(TAG, "axe");
					Pos = 2;
					break;
					
				case R.id.radioFlail:
					Log.i(TAG, "flial");
					Pos = 3;
					break;
					
				case R.id.radioBow:
					Log.i(TAG, "bow");
					Pos = 4;
					break;
					
				default:
					Log.i(TAG, "ERROR RADIO GROUP FUNCTION");
					break;
				}
			}
		});
		
		radio2 = (RadioGroup)findViewById(R.id.radioGrpHands);
		radio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup arg0, int id) {
				switch (id) {
				case R.id.radio1H:
					Log.i(TAG, "1 Handed");
					Hands = 1;
					break;
					
				case R.id.radio2H:
					Log.i(TAG, "2 Handed");
					Hands = 2;
					break;
					
				default:
					Log.i(TAG, "ERROR RADIO GROUP FUNCTION");
					break;
				}
			}
		});
	}
	
	private void formBuild(){
		ID = (EditText)findViewById(R.id.etWepID);
		id = ID.getText().toString();
		
		Name = (EditText)findViewById(R.id.etWepName);
		name2 = Name.getText().toString();
		
		Damage = (EditText)findViewById(R.id.etWepDamage);
		damage = Damage.getText().toString();
		
		Stock = (EditText)findViewById(R.id.etWepStock);
		stock = Stock.getText().toString();
		
		
		save = (Button)findViewById(R.id.btnWepSave);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.i(TAG, "SAVE BUTTON HIT");
				validateAndConvert();
				
			}
		});
		
	}
	
	public boolean validateAndConvert(){
		if((ID.getText().length() <= 3) || (ID.getText().length() >= 5)){
			//ID.setTextColor(Color.parseColor("#FF0000"));
			myToast("Please Enter a 4 Digit Weapon ID");
			return false;
		}
			ID.setTextColor(Color.parseColor("#7CFC00"));
			id2 = Integer.parseInt((ID.getText().toString()));
			
		if(Name.getText().length() <= 2){
			//Name.setTextColor(Color.parseColor("#FF0000"));
			myToast("Please Enter a Valid Weapon Name");
			return false;
		}
			Name.setTextColor(Color.parseColor("#7CFC00"));
			name2 = Name.getText().toString();
			
		if(Damage.getText().length() <= 0 ){
			//Damage.setTextColor(Color.parseColor("#FF0000"));
			myToast("Please Enter a Weapon Damage Amount");
			return false;
		}
			Damage.setTextColor(Color.parseColor("#7CFC00"));
			damage2 = Integer.parseInt(Damage.getText().toString());
			
		if(Stock.getText().length() <= 0 ){
			//Stock.setTextColor(Color.parseColor("#FF0000"));
			myToast("Please Enter Amount in Stock");
			return false;
		}
		Stock.setTextColor(Color.parseColor("#7CFC00"));
		stock2 = Integer.parseInt(Stock.getText().toString());
		
		//pushNewWep(id2, name2, Pos, hands2, damage2, stock2);
		addWeapon2Parse(id2, name2, Pos, hands2, damage2, stock2);
		return true;
		
}

	public void addWeapon2Parse(int id, String name, int type, int hands, int damage, int quantity){
		Log.i("TAG", "Weapon " + name + " being saved to PARSE");
		ParseObject wepObject = new ParseObject("weaponsTablePARSE");
		wepObject.put("wID", id);
		wepObject.put("wName", name);
		wepObject.put("wType", type);
		wepObject.put("wHands", hands);
		wepObject.put("wDamage", damage);
		wepObject.put("wQuantity", quantity);
		wepObject.saveInBackground();
		AddWeaponActivity.this.finish();
	}
	
//	private void pushNewWep(long longID, String name, int type, int hands, int damage, int inStock){
//		WeaponDataSource updt = new WeaponDataSource(context);
//		updt.open();
//		updt.PushNewWeaponToLocal(longID, name, type, hands, damage, inStock);
//		updt.close();			
//		AddWeaponActivity.this.finish();
//	}
	
	
	public void myToast(String text){  
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(AddWeaponActivity.this, textIN, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};// end myToast

}
