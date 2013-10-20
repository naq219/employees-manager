package haivu.qlnv;


import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class InsertNVActivity extends Activity implements OnClickListener {
	private TextView startdate;
	private TextView enddate;
	private TextView starttime;
	private TextView endtime;
	private RadioButton oneday;
	private RadioButton moreday;
	private EditText membername;
	private EditText content1;
	private EditText content2;
	private Spinner repeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_insert_nhanvien);

		ImageView btnstartdate1 = (ImageView) findViewById(R.id.btnCalendar_nhanvien1);
		btnstartdate1.setOnClickListener(this);
		ImageView btnenddate1 = (ImageView) findViewById(R.id.btnCalendar_nhanvien2);
		btnenddate1.setOnClickListener(this);

		ImageView btnstartdate2 = (ImageView) findViewById(R.id.btnBeginTime_nv);
		btnstartdate2.setOnClickListener(this);
		ImageView btnenddate2 = (ImageView) findViewById(R.id.btnEndTime_nv);
		btnenddate2.setOnClickListener(this);

		startdate = (TextView) findViewById(R.id.tvCalendar_nhanvien1);
		enddate = (TextView) findViewById(R.id.tvCalendar_nhanvien2);
		starttime = (TextView) findViewById(R.id.tvBeginTime_nv);
		endtime = (TextView) findViewById(R.id.tvEndTime_nv);

		oneday = (RadioButton) findViewById(R.id.radOneDay);
		moreday = (RadioButton) findViewById(R.id.radMoreday);

		membername = (EditText) findViewById(R.id.edtInsertName_nv);
		content1 = (EditText) findViewById(R.id.edtInsertWork_nv1);
		content2 = (EditText) findViewById(R.id.edtInsertWork_nv2);
		repeat = (Spinner) findViewById(R.id.spnNhactruocNV);
	}

	public void Onclickmoreday(View v) {
		if (((RadioButton) v).isChecked()) {
			RelativeLayout gr2 = (RelativeLayout) findViewById(R.id.gr2);
			gr2.setVisibility(View.VISIBLE);
		}
	}

	public void Onclickoneday(View v) {
		if (((RadioButton) v).isChecked()) {
			RelativeLayout gr2 = (RelativeLayout) findViewById(R.id.gr2);
			gr2.setVisibility(View.INVISIBLE);
		}
	}

	int parentid = 0;
	int workid = 0;

//	public void OnclickinsertandmoreNV(View v) {
//		Log.i("", "HERE");
//		// lấy groupid và repeat thay vào đây
//		int groupid = 2;
//		String repeat = "2";
//
//		int memberid = db.Ismembership("" + membername.getText());
//
//		if (memberid == 0)
//			memberid = db.insertmembership("" + membername.getText(), groupid);
//		Log.i("",
//				"membershipid:" + memberid + " - membername: "
//						+ membername.getText());
//
//		parentid = db
//				.Iswork("" + membername.getText(), "" + content1.getText());
//
//		if (parentid == 0)
//			workid = db.insertwork(0, memberid, "" + content1.getText(),
//					""+startdate.getText(), ""+enddate.getText(), 0, "", "", 0, repeat);
//		Log.i("", "parentid:" + parentid + " - membershipid: " + memberid
//				+ " - content:" + content1.getText() + " - startdate:"
//				+ startdate.getText());
//
//		String ed = "";
//		if (moreday.isChecked())
//			ed = "" + endtime.getText();
//		db.insertwork(parentid, memberid, "" + content2.getText(), ""
//				+ starttime.getText(), ed, 0, "", "", 0, repeat);
//
//		Log.i("", "parentid:" + parentid + " - membershipid: " + memberid
//				+ " - content2:" + content2.getText() + " - startdate2:"
//				+ starttime.getText());
//		db.closeConnect();
//	}

	
	@Override
	public void onClick(final View v) {
		Calendar calendar = Calendar.getInstance();

		DatePickerDialog datepicker = new DatePickerDialog(
				this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						switch (v.getId()) {
						case R.id.btnCalendar_nhanvien1:
							TextView tvstartdate1 = (TextView) findViewById(R.id.tvCalendar_nhanvien1);
							tvstartdate1.setText(year + "-" + (monthOfYear + 1)
									+ "-" + dayOfMonth);
							break;
						case R.id.btnCalendar_nhanvien2:
							TextView tvenddate1 = (TextView) findViewById(R.id.tvCalendar_nhanvien2);
							tvenddate1.setText(year + "-" + (monthOfYear + 1)
									+ "-" + dayOfMonth);
							break;
						case R.id.btnBeginTime_nv:
							TextView tvstartdate2 = (TextView) findViewById(R.id.tvBeginTime_nv);
							tvstartdate2.setText(year + "-" + (monthOfYear + 1)
									+ "-" + dayOfMonth);
							break;
						case R.id.btnEndTime_nv:
							TextView tvenddate2 = (TextView) findViewById(R.id.tvEndTime_nv);
							tvenddate2.setText(year + "-" + (monthOfYear + 1)
									+ "-" + dayOfMonth);
							break;
						default:
							break;
						}
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datepicker.show();
	}

}
