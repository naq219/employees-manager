package haivu.qlnv;

import haivu.qlnv.object.Empl;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.Sdata;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.telpoo.frame.ui.BaseActivity;

public class Alarm extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		((TextView) findViewById(R.id.content)).setText(Sdata.alarm_oj.get(Empl.CONTENT));
		((TextView) findViewById(R.id.date)).setText(Sdata.alarm_oj.get(Empl.START_DATE));
		((TextView) findViewById(R.id.time)).setText(Sdata.alarm_oj.get(Empl.START_TIME) + "  -  " + Sdata.alarm_oj.get(Empl.END_TIME));


	}

	public void onClickFinish(View v) {
		finish();
	}

}
