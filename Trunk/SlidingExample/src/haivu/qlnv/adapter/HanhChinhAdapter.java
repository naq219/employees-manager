package haivu.qlnv.adapter;

import haivu.qlnv.R;
import haivu.qlnv.object.Empl;
import haivu.qlnv.utils.Mcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.telpoo.frame.object.BaseObject;

public class HanhChinhAdapter extends ArrayAdapter<BaseObject> {

	

	private List<BaseObject> listHanhChinh;
	private Context mcontext;
	private final LayoutInflater inflater;
	int group;
	int resource;
	
	public HanhChinhAdapter(Context context, int textViewResourceId, List<BaseObject> objects,int group) {
		super(context, textViewResourceId, objects);
		this.resource=textViewResourceId;
		this.listHanhChinh=objects;
		this.group=group;
		this.mcontext=context;
		this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listHanhChinh.size();
	}

	@Override
	public BaseObject getItem(int position) {
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
		View view = convertView;

		switch (group) {
		case Mcon.Group.NHOM_HANH_CHINH:
			return getViewHC(view,position,parent);

		default:
			break;
		}

		return view;
	}

	View getViewHC(View view, int position,ViewGroup parent) {
		View convertView1 = view;
		if (convertView1 == null) {
			convertView1 = inflater.inflate(resource, parent,false);
		}
		TextView tvItem_date_HC = (TextView) convertView1.findViewById(R.id.tvItem_date_HC);
		tvItem_date_HC.setText(listHanhChinh.get(position).get(Empl.START_DATE));
		TextView tvItem_content_HC = (TextView) convertView1.findViewById(R.id.tvItem_content_HC);
		tvItem_content_HC.setText(listHanhChinh.get(position).get(Empl.CONTENT));
		
		ImageView img=(ImageView) convertView1.findViewById(R.id.alarm);
		if(!"0".equalsIgnoreCase(listHanhChinh.get(position).get(Empl.ALERT))){
			img.setVisibility(View.VISIBLE);
		}
		
		return convertView1;
	}
	
	public void SetItems(ArrayList<BaseObject> items) {
		clear();
		AddAll1(items);
	}

	private void AddAll1(ArrayList<BaseObject> items) {
		if (items != null) {
			if(items.size()>0){
				for (BaseObject item : items) {
					add(item);
				}
			}
		}
	}
}
