package haivu.qlnv.adapter;

import haivu.qlnv.R;
import haivu.qlnv.object.HanhChinhObject;
import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HanhChinhAdapter extends BaseAdapter {
	private ArrayList<HanhChinhObject> listHanhChinh;
	private FragmentActivity activity;
	LayoutInflater inflater;

	public HanhChinhAdapter(FragmentActivity activity,
			ArrayList<HanhChinhObject> listHanhChinh) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.listHanhChinh = listHanhChinh;
		inflater = (LayoutInflater) this.activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listHanhChinh.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listHanhChinh.get(position);
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
		if (convertView == null) {
			view = inflater.inflate(R.layout.item_list_hanhchinh, null);
		}
		TextView tvItem_date_HC = (TextView) view
				.findViewById(R.id.tvItem_date_HC);
		tvItem_date_HC.setText(listHanhChinh.get(position).getDay());
		TextView tvItem_content_HC = (TextView) view
				.findViewById(R.id.tvItem_content_HC);
		tvItem_content_HC.setText(listHanhChinh.get(position).getName() + ": "
				+ listHanhChinh.get(position).getContent_work());
		return view;
	}
}
