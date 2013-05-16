package com.klusman.cross_platform_wk2;


import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class customCellAdapter extends BaseAdapter {

		private JSONArray items;
	    private Context context;
	    
	    
	    public customCellAdapter(Context context, JSONArray array)
	    {
	        super();
	        this.items = array;
	        this.context = context;
	    }

	    @Override
	    public int getCount() {
	        return items.length();
	    }

	    @Override
	    public Object getItem(int position) {
	        return null;
	    }

	    @Override
	    public long getItemId(int position) {
	        return 0;

	    }@Override
	    public View getView(int position, View convertView, ViewGroup parent) 
	    {
	        View v = convertView;
	        TextView Title;
	        TextView Hands;
	        TextView Type;
	        TextView Damage;
	        TextView ID;
	        //Log.i("LISTVIEW", "Checking Position" + position);
	        
	        
	        
	        
	        try
	        {       
	            if(!items.isNull(position))
	            {
	                JSONObject item = items.getJSONObject(position);
	                if (v == null) {
	                    v = LayoutInflater.from(context).inflate(R.layout.custom_cell, null);
	                }           
	                
	                Title = (TextView) v.findViewById(R.id.WeaponName);
	                Hands = (TextView) v.findViewById(R.id.hands);
	                Type = (TextView) v.findViewById(R.id.type);
	                ID = (TextView) v.findViewById(R.id.id);
	                Damage = (TextView) v.findViewById(R.id.damage);
	                

	                if(Title != null)
	                {
	                	Title.setText(item.getString("date"));
	                	Hands.setText(item.getString("tempMaxF") );
	                
	                }
	            }else{
	                return null;
	            }
	        }
	        catch(Exception e)
	        {
	            Log.e("num", "LIST ERROR! " + e.toString());
	        }
	        return v;
	    }
	
}