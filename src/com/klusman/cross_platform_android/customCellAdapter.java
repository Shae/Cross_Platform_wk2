package com.klusman.cross_platform_android;


import org.json.JSONArray;
import org.json.JSONObject;

import com.klusman.cross_platform_android.R;

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
        TextView Title;
        TextView Hands;
        TextView Type;
        TextView Damage;
        TextView ID;
        TextView Quantity;
	    
	    
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

	        //Log.i("LISTVIEW", "Checking Position" + position);
	        try
	        {       
	            if(!items.isNull(position))
	            {
	                JSONObject item = items.getJSONObject(position);
	                if (v == null) {
	                    v = LayoutInflater.from(context).inflate(R.layout.cell_custom_weapon, null);
	                }           
	                
	                Title = (TextView) v.findViewById(R.id.tvWepName);
	                Hands = (TextView) v.findViewById(R.id.tvHands);
	                Type = (TextView) v.findViewById(R.id.tvType);
	                ID = (TextView) v.findViewById(R.id.tvID);
	                Damage = (TextView) v.findViewById(R.id.tvDamage);
	                Quantity = (TextView)v.findViewById(R.id.tvStock);
	                

	                if(Title != null)
	                {
	                	Title.setText(item.getString("name"));
	                	Hands.setText(item.getString("hands"));
	                	Type.setText(item.getString("type"));
	                	ID.setText(item.getString("ID"));
	                	Damage.setText(item.getString("damage"));
	                	Quantity.setText(item.getString("quantity"));
	                
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