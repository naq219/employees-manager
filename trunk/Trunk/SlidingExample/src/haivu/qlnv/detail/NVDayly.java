package haivu.qlnv.detail;

import haivu.qlnv.InsertNVActivity;
import haivu.qlnv.layout.NVDaylyLayout;
import haivu.qlnv.utils.Mutils;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.telpoo.frame.object.BaseObject;

public class NVDayly extends NVDaylyLayout {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*idialogDate=new IdialogDate() {
			
			@Override
			public void onSet(String text_tv, String text_value) {
				tv_calendar.setText(text_tv);
				filterDate(text_value);
			}
		};*/
		
		//root_calendar.setOnClickListener(DialogUtils.datePickerListener(idialogDate, mct));
		
		
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
		lv_sang.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				BaseObject oj=new BaseObject();
				oj=dataLvSang.get(position);
				Mutils.startActivity(mct, InsertNVActivity.class, oj,1);
			}
		});
		lv_chieu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				BaseObject oj=new BaseObject();
				oj=dataLvChieu.get(position);
				Mutils.startActivity(mct, InsertNVActivity.class, oj,1);
				
			}
		});
	}

	

}