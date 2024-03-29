package haivu.qlnv.adapter;

import haivu.qlnv.R;
import haivu.qlnv.detail.NVDayly;
import haivu.qlnv.object.AdapterOj;
import haivu.qlnv.object.Empl;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.Mutils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Utils;

public class AllAdapter extends ArrayAdapter<BaseObject> {

	private List<BaseObject> data;
	private Context mcontext;
	private final LayoutInflater inflater;
	int group;
	int resource;

	public AllAdapter(Context context, int textViewResourceId, ArrayList<BaseObject> objects, int group) {
		super(context, textViewResourceId, objects);
		this.resource = textViewResourceId;
		this.data =objects;// Mutils.convertOj2AdapterOj(objects);
		this.group = group;
		this.mcontext = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public BaseObject getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		switch (group) {
		case Mcon.Group.NHOM_HANH_CHINH:
			return getViewHC(view, position, parent);

		default:
			break;
		}

		return view;
	}

	View getViewHC(View view, int position, ViewGroup parent) {
		BaseObject dataA = data.get(position);
		ArrayList<BaseObject> dataAs=new ArrayList<BaseObject>();
		dataAs.add(dataA);
		ArrayList<BaseObject> adapteroj=Mutils.convertOj2AdapterOj(dataAs);
		BaseObject ok = adapteroj.get(0);
		View convertView1 = view;
		if (convertView1 == null) {
			convertView1 = inflater.inflate(resource, parent, false);
		}
		TextView time = (TextView) convertView1.findViewById(R.id.time);
		time.setText(ok.get(AdapterOj.TIME));
		

		TextView content = (TextView) convertView1.findViewById(R.id.content);
		content.setText(ok.get(AdapterOj.CONTENT));
		
		/*TextView count = (TextView) convertView1.findViewById(R.id.count);
		count.setText(ok.get(AdapterOj.CONTENT));*/
		
		
		
		ImageView img = (ImageView) convertView1.findViewById(R.id.alarm);
		img.setVisibility(View.INVISIBLE);
		if (!"0".equalsIgnoreCase(ok.get(Empl.ALERT))) {
			img.setVisibility(View.VISIBLE);
		} else {
			
		}
		Calendar cal;
		if((Mcon.Group.NHOM_HANH_CHINH+"").equalsIgnoreCase(dataA.get(Empl.GROUP)))
			cal=Mutils.convertString2calendar(dataA.get(Empl.START_DATE)+"."+dataA.get(Empl.START_TIME),"yyyy-MM-dd.HH:mm");
		else {
			
			cal=Mutils.convertString2calendar(dataA.get(Empl.START_DATE),"yyyy-MM-dd");
			if(!NVDayly.isFromNVDayly){
			
				TextView count = (TextView) convertView1.findViewById(R.id.count);
				String countdt = ok.get(AdapterOj.COUNT);
				if (!"0".equalsIgnoreCase(countdt)){
					TextView mcount = (TextView) convertView1.findViewById(R.id.count);
					mcount.setVisibility(View.VISIBLE);
					mcount.setText(countdt);
				}
			}
			
		}
		
		if(System.currentTimeMillis()-cal.getTimeInMillis()>0){
			time.setPaintFlags(time.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			content.setPaintFlags(time.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//			time.setTypeface(null, Typeface.ITALIC);
//			content.setTypeface(null, Typeface.ITALIC);

		}

		return convertView1;
	}

	public void SetItems(ArrayList<BaseObject> items) {
		clear();
		AddAll1(items);
	}

	private void AddAll1(ArrayList<BaseObject> items) {
		if (items != null) {
			if (items.size() > 0) {
				for (BaseObject item : items) {
					add(item);
				}
			}
		}
	}
}
