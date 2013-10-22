package haivu.qlnv;

import haivu.qlnv.database.DbSupport;
import haivu.qlnv.object.Empl;
import haivu.qlnv.object.HcOj;
import haivu.qlnv.utils.DbTable;
import haivu.qlnv.utils.Mutils;

import java.util.ArrayList;
import java.util.Calendar;

import android.R.integer;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.telpoo.frame.database.BaseDBSupport;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.ui.BaseActivity;
import com.telpoo.frame.utils.Mlog;

public class InsertHCActivity extends BaseActivity implements OnClickListener {
	public static BaseDBSupport db = null;
	private Context mct = null;

	TextView tvCalendar_hanhChinh, tvBegin_time, tvEnd_time, tvRepeat, title;
	Calendar calendar;
	ListView lvDayOfWeek;
	ImageView btnBeginTime, btnEndTime, btnDate;
	TimePickerDialog timePickerDialog;
	DatePickerDialog datePickerDialog;
	RadioButton radSang, radChieu;
	Spinner spnReminder;
	Button btnSave, btnSaveAndMore;
	EditText edtContent;
	String startDate;// yyyy-mm-dd
	String startTime, endTime;
	int morning = 1; // morning =1 , afternoon = 0;
	boolean isSelected_startTime = false, isSelected_endTime = false;

	// Naq219
	ArrayList<BaseObject> ojAdd = new ArrayList<BaseObject>();
	int timeAlert = 0;
	int[] arrayAlert = { 0, 5, 10, 20, 30, 60 };
	int action = 0;
	BaseObject ojEdit = new BaseObject();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_insert_hanhchinh);
		calendar = Calendar.getInstance();

		mct = InsertHCActivity.this;
		db = DbSupport.getInstance(mct);
		initView();
		initData();
	}

	private void initData() {
		Intent it = getIntent();
		action = it.getIntExtra("key", 0);
		if (action == 1) // update
		{
			ojEdit = it.getExtras().getParcelable("boj");
			title.setText("SỬA THÔNG TIN NV" );
			btnSaveAndMore.setText("Hủy");
			btnSave.setText("Cập nhật");
			
			edtContent.setText(ojEdit.get(Empl.CONTENT));
			
			startTime=ojEdit.get(Empl.START_TIME);
			tvBegin_time.setText(startTime);
			endTime=ojEdit.get(Empl.END_TIME);
			tvEnd_time.setText(endTime);
			
			startDate=ojEdit.get(Empl.START_DATE);
			tvCalendar_hanhChinh.setText(startDate);
			
			isSelected_endTime=true;
			isSelected_startTime=true;
			
			try {
				timeAlert=Integer.parseInt(ojEdit.get(Empl.ALERT));
				
			} catch (Exception e) {
				Mlog.E("=5645656=timeAlert=Integer.parseInt(ojEdit.get(Empl.ALERT));"+e);
			}
			
			if(timeAlert!=0){
				spnReminder.setContentDescription(timeAlert+" Phút");
			}
			spnReminder.setContentDescription(timeAlert+" Phút");
			if("1".equalsIgnoreCase(ojEdit.get(Empl.SESSION))) radChieu.setChecked(true);
			else radSang.setChecked(true);
			
		}

	}

	public void initView() {
		btnBeginTime = (ImageView) findViewById(R.id.btnBeginTime_hc);

		title = (TextView) findViewById(R.id.title);
		btnBeginTime.setOnClickListener(this);
		btnEndTime = (ImageView) findViewById(R.id.btnEndTime_hc);
		btnEndTime.setOnClickListener(this);
		btnDate = (ImageView) findViewById(R.id.btnCalendar_hanhchinh);
		btnDate.setOnClickListener(this);
		btnSaveAndMore = (Button) findViewById(R.id.btnLuuVaThem_hc);
		btnSave = (Button) findViewById(R.id.btnLuu_hc);
		btnSave.setOnClickListener(this);
		btnSaveAndMore.setOnClickListener(this);
		edtContent = (EditText) findViewById(R.id.edtInsertWork_hc);

		tvBegin_time = (TextView) findViewById(R.id.tvBeginTime_hc);
		tvEnd_time = (TextView) findViewById(R.id.tvEndTime_hc);
		radChieu = (RadioButton) findViewById(R.id.radChieu);
		radSang = (RadioButton) findViewById(R.id.radSang);
		spnReminder = (Spinner) findViewById(R.id.spnNhactruoc);
		tvRepeat = (TextView) findViewById(R.id.tvLaplai);
		tvRepeat.setOnClickListener(this);
		ArrayAdapter<String> adapterReminder = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, this.getResources().getStringArray(R.array.reminder));
		spnReminder.setAdapter(adapterReminder);
		spnReminder.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				timeAlert = arrayAlert[arg2];

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		tvCalendar_hanhChinh = (TextView) findViewById(R.id.tvCalendar_hanhchinh);
		startDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
		tvCalendar_hanhChinh.setText(Mutils.pasreDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)) + ": " + calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1)
				+ "/" + calendar.get(Calendar.YEAR));
	}

	public void showTimePicker(final TextView tv) {
		timePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				if (tv == tvBegin_time) {
					tv.setText("Từ " + hourOfDay + ":" + minute);
					if (0 <= hourOfDay && hourOfDay <= 12) {
						radSang.setChecked(true);
						morning = 1;
					} else {
						radChieu.setChecked(true);
						morning = 0;
					}
					startTime = hourOfDay + ":" + minute;
					isSelected_startTime = true;
				} else if (tv == tvEnd_time) {
					tv.setText("Đến " + hourOfDay + ":" + minute);
					endTime = hourOfDay + ":" + minute;
					isSelected_endTime = true;
				}

			}
		}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
		timePickerDialog.show();
	}

	public void showDatePicker() {

		datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				Calendar c = Calendar.getInstance();
				c.set(year, monthOfYear, dayOfMonth);

				if (c.get(Calendar.DAY_OF_WEEK) == 1) {
					tvCalendar_hanhChinh.setText("Chủ nhật: " + convert(dayOfMonth) + "/" + convert(monthOfYear + 1) + "/" + year);
				} else {
					tvCalendar_hanhChinh.setText("Thứ " + c.get(Calendar.DAY_OF_WEEK) + ": " + convert(dayOfMonth) + "/" + convert(monthOfYear + 1) + "/" + year);
				}

				startDate = year + "-" + convert(monthOfYear + 1) + "-" + convert(dayOfMonth);
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();
	}

	public String convert(int i) {
		if (i < 10) {
			return "0" + i;
		}
		return "" + i;
	}

	public void showDialog() {
		Dialog dlDay = new Dialog(this);
		dlDay.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlDay.setContentView(R.layout.dialog_day_of_week);
		// dlDay.
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnBeginTime_hc:
			showTimePicker(tvBegin_time);
			break;
		case R.id.btnEndTime_hc:
			showTimePicker(tvEnd_time);
			break;
		case R.id.btnCalendar_hanhchinh:
			showDatePicker();
			break;
		case R.id.btnLuu_hc:
			exeSave(0);
			break;
		case R.id.btnLuuVaThem_hc:
			if (action == 0)
				exeSave(1);
			else
				finish();
			break;
		}
	}

	void exeSave(int where) {
		if (isSelected_startTime) {

			if (edtContent.getText().toString().equals("") || edtContent.getText().toString() == null) {
				Toast.makeText(this, "Chưa nhập nội dung công việc!", Toast.LENGTH_SHORT).show();
			} else {

				BaseObject oj = new HcOj();
				oj.set(Empl.CONTENT, edtContent.getText() + "");
				oj.set(Empl.START_DATE, startDate);
				oj.set(Empl.START_TIME, startTime);
				oj.set(Empl.END_TIME, endTime);
				oj.set(Empl.END_DATE, startDate);
				oj.set(Empl.ALERT, timeAlert + "");

				if (action == 1)
					oj.set(Empl.ROW_ID, ojEdit.get(Empl.ROW_ID));

				if (radChieu.isChecked())
					oj.set(Empl.SESSION, 1 + "");
				else
					oj.set(Empl.SESSION, 0 + "");
				ojAdd.add(oj);

				insertDb(ojAdd);
				if (where == 0)
					finish();
				else {
					edtContent.setText("");
				}

			}

		} else {
			Toast.makeText(this, "Chưa chọn giờ bắt đầu!", Toast.LENGTH_SHORT).show();
		}
	}

	void insertDb(ArrayList<BaseObject> ojAdd) {
		
		for (String key : Empl.keys_include_rowId) {
			Mlog.T(key+"=="+ojAdd.get(0).get(key));
		}
		
		boolean adddb;
		if (action == 0)
			adddb = DbSupport.addToTable(ojAdd, DbTable.EMPL, false, null);
		else
			adddb = DbSupport.update(ojAdd.get(0), DbTable.EMPL, Empl.ROW_ID);
		if (!adddb)
			showToast("Có lỗi xảy ra");
		else
			showToast("Thêm thành công!");
		ojAdd = new ArrayList<BaseObject>();
	}

}
