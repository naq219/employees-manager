package haivu.qlnv.detail;

import haivu.qlnv.InsertNVActivity;
import haivu.qlnv.R;
import haivu.qlnv.layout.NVDaylyLayout;
import haivu.qlnv.utils.DialogUtils;
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
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

		root_calendar.setOnClickListener(DialogUtils.datePickerListener(idialogDate, mct));

	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();

		lv_nhieungay.setOnItemClickListener(Mutils.onClickListView(mct, dataLvnhieungay));
		lv_tungngay.setOnItemClickListener(Mutils.onClickListView(mct, dataLvtungngay));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

}