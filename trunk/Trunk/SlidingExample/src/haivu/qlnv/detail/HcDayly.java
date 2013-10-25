package haivu.qlnv.detail;

import haivu.qlnv.InsertHCActivity;
import haivu.qlnv.R;
import haivu.qlnv.layout.HcDaylyLayout;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.IdialogDate;
import haivu.qlnv.utils.Mutils;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.telpoo.frame.object.BaseObject;

public class HcDayly extends HcDaylyLayout {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		idialogDate=new IdialogDate() {
			
			@Override
			public void onSet(String text_tv, String text_value) {
				tv_calendar.setText(text_tv);
				filterDate(text_value);
			}
		};
		
		root_calendar.setOnClickListener(DialogUtils.datePickerListener(idialogDate, mct));
		
		
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
		lv_sang.setOnItemClickListener(Mutils.onClickListView(mct, dataLvSang));
		lv_chieu.setOnItemClickListener(Mutils.onClickListView(mct, dataLvChieu));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

}
