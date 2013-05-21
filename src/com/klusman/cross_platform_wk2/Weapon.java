package com.klusman.cross_platform_wk2;

import android.util.Log;

public class Weapon {
	 	public long id;
	    public String name;
	    public int type;
	    public int hands;
	    public int damage;
	    public int quantity;
	    private static final String TAG = "WEAPONS_DATABASE";
	    
//////////////	    
	    public long getId(){
	    	return id;
	    }
	    
	    public void setId(long id){
	    	this.id = id;
	    }
	    
/////////////
	    public String getName(){
	    	
	    	return name;
	    }
	    
	    public void setName(String name){
	    	Log.i(TAG, "WEAPON CLASS setNAME : " + String.valueOf(name));
	    	this.name = name;
	    }
	    
////////////
	    public int getType(){
	    	return type;
	    }
	    
	    public void setType(int type){
	    	this.type = type;
	    }
	    
////////////
	    public int getHands(){
	    	return hands;
	    }
	    
	    public void setHands(int hands){
	    	this.hands = hands;
	    }
	    
////////////
	    public int getDamage(){
	    	return damage;
	    }
	    
	    public void setDamage(int damage){
	    	this.damage = damage;
	    }
	    
////////////
	    public int getQuantity(){
	    	return quantity;
	    }
	    
	    public void setQuantity(int quantity){
	    	Log.i(TAG, "WEAPON CLASS quantity : " + String.valueOf(quantity));
	    	this.quantity = quantity;
	    }
}  // END WEAPON CLASS
