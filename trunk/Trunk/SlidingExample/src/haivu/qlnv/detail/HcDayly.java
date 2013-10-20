package haivu.qlnv.detail;

import haivu.qlnv.layout.HcDaylyLayout;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.IdialogDate;
import android.os.Bundle;

public class HcDayly extends HcDaylyLayout {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		idialogDate=new IdialogDate() {
			
			@Override
			public void onSet(String text_tv, String text_value) {
				tv_calendar.setText(text_tv);
				filterDate(text_value);
			}
		};
		
		root_calendar.setOnClickListener(DialogUtils.datePickerListener(idialogDate, mct));
		
	}

	

}
