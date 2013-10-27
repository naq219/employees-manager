package haivu.qlnv.detail;

import haivu.qlnv.R;
import haivu.qlnv.layout.NVDaylyLayout;
import haivu.qlnv.object.Empl;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.Mutils;
import android.os.Bundle;

public class NVDayly extends NVDaylyLayout {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		root_calendar.setOnClickListener(DialogUtils.datePickerListener(idialogDate, mct));

	}

	@Override
	protected void onResume() {
		super.onResume();
		isFromNVDayly=true;
		initData();

		lv_nhieungay.setOnItemClickListener(Mutils.onClickListView(mct, dataLvnhieungay,Empl.ROW_ID));
		lv_tungngay.setOnItemClickListener(Mutils.onClickListView(mct, dataLvtungngay,Empl.ROW_ID));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}
	
	@Override
	protected void onPause() {
		isFromNVDayly=false;
		super.onPause();
	}

}
