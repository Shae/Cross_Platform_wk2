package com.klusman.cross_platform_wk2;

public class Weapon {
	 	public long id;
	    public String name;
	    public int type;
	    public int hands;
	    public int damage;
	    
	    
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
}  // END WEAPON CLASS