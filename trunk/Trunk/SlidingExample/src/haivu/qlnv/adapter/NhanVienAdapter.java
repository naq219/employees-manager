/*package haivu.qlnv.adapter;

import haivu.qlnv.R;
import haivu.qlnv.object.NhanVienObject;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NhanVienAdapter extends BaseAdapter {
	private ArrayList<NhanVienObject> listNhanVien;
	private FragmentActivity activity;
	LayoutInflater inflater;

	public NhanVienAdapter(FragmentActivity activity,
			ArrayList<NhanVienObject> _listNhanVien) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.listNhanVien = _listNhanVien;
		inflater = (LayoutInflater) this.activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listNhanVien.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listNhanVien.get(position);
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
			view = inflater.inflate(R.layout.item_list_nhanvien, null);
		}
		TextView tvItem_date_HC = (TextView) view
				.findViewById(R.id.tvItem_Name_nv);
		tvItem_date_HC.setText(listNhanVien.get(position).getName());
		TextView tvItem_content_HC = (TextView) view
				.findViewById(R.id.tvItem_content_nv);
		tvItem_content_HC.setText(listNhanVien.get(position).getDay() + ": "
				+ listNhanVien.get(position).getContent_work());
		return view;
	}
}
*/