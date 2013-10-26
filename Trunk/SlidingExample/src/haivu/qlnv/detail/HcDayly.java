package haivu.qlnv.detail;

import haivu.qlnv.R;
import haivu.qlnv.layout.HcDaylyLayout;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.IdialogDate;
import haivu.qlnv.utils.Mutils;
import android.os.Bundle;

public class HcDayly extends HcDaylyLayout {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		idialogDate = new IdialogDate() {

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
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

}
