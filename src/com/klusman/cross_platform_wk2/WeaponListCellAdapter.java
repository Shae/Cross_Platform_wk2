package com.klusman.cross_platform_wk2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class WeaponListCellAdapter extends ArrayAdapter<Weapon> {

	private static final String TAG = "WEAPONS_DATABASE";
	Context _context;
	private List<Weapon> _weaponList ;
	
	public WeaponListCellAdapter(Context context, List<Weapon> weapons) {
		super(context, R.layout.cell_custom_weapon, weapons);
		this._context = context;
		this._weaponList = weapons;
		Log.i(TAG, "weaponList size:  for Cells: " + String.valueOf(weapons.size()));
		
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.cell_custom_weapon, parent, false);
		TextView tvname = (TextView) rowView.findViewById(R.id.tvWepName);
		TextView tvid = (TextView) rowView.findViewById(R.id.tvID);
		TextView tvhands = (TextView) rowView.findViewById(R.id.tvHands);
		TextView tvtype = (TextView) rowView.findViewById(R.id.tvType);
		TextView tvdamage = (TextView) rowView.findViewById(R.id.tvDamage);
		TextView tvquantity = (TextView) rowView.findViewById(R.id.tvStock);

		final String name = _weaponList.get(position).getName();
		final String id = String.valueOf(_weaponList.get(position).getId());
		final String type = String.valueOf(_weaponList.get(position).getType());
		final String hands = String.valueOf(_weaponList.get(position).getHands());
		final String dam = String.valueOf(_weaponList.get(position).getDamage());
		final String quant = String.valueOf(_weaponList.get(position).getQuantity());
	
		tvname.setText(name);
		tvid.setText(id);
		tvhands.setText(hands);
		tvtype.setText(type);
		tvdamage.setText(dam);
		tvquantity.setText(quant);
		
		rowView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(_context, MoreInfoActivity.class);
				intent.putExtra("ID", id);
				intent.putExtra("NAME", name);
				intent.putExtra("TYPE", type);
				intent.putExtra("HANDS", hands);
				intent.putExtra("DAMAGE", dam);
				intent.putExtra("QUANTITY", quant);	
				_context.startActivity(intent);
				
			}
		});

		return rowView;
	}
	
}
