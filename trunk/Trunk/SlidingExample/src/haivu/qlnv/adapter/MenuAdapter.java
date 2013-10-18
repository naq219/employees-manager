package haivu.qlnv.adapter;

import haivu.qlnv.R;
import haivu.qlnv.object.MenuObject;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	private ArrayList<MenuObject> listMenu;
	private FragmentActivity activity;
	LayoutInflater inflater;

	public MenuAdapter(FragmentActivity activity, ArrayList<MenuObject> listMenu) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.listMenu = listMenu;
		inflater = (LayoutInflater) this.activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMenu.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listMenu.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.item_nhom_nhan_vien, null);
		TextView tvNhom = (TextView) view.findViewById(R.id.tvNhom_itemMenu);
		tvNhom.setText(listMenu.get(position).getLoai());
		TextView tvNumber = (TextView) view
				.findViewById(R.id.tvNumber_itemMenu);
		tvNumber.setText(listMenu.get(position).getSo());

		return view;
	}
}
