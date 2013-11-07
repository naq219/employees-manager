package haivu.qlnv;

import haivu.qlnv.database.DbSupport;
import haivu.qlnv.object.Empl;
import haivu.qlnv.object.NvOj;
import haivu.qlnv.task.TaskType;
import haivu.qlnv.task.TaskUser1;
import haivu.qlnv.utils.DbTable;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.Mutils;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.ui.BaseActivity;
import com.telpoo.frame.utils.Mlog;

public class InsertNVActivity extends BaseActivity implements OnClickListener, Mcon.Group {
	private TextView startdate;
	private TextView enddate;
	private TextView starttime;
	private TextView endtime;
	private RadioButton oneday;
	private RadioButton moreday;
	private EditText membername;
	// private EditText ed_title_content;
	private EditText ed_content;
	private Spinner spnReminder;
	private boolean flag_start_date, flag_end_date, flag_start_time, flag_end_time;
	private int group = 1;
	private int action = 0;
	BaseObject ojEdit = new BaseObject();
	Button btnLuuVaThem_nv, btnLuu_nv;
	String start_date = "Bắt đầu", start_time = "Bắt đầu", end_date = "Kết thúc", end_time = "kết thúc";
	int timeAlert = 0;
	int[] arrayAlert = { 0, 5, 10, 20, 30, 60 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_insert_nhanvien);
		

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initView();
		initGroup();
		initData();
	}

	private void initData() {
		Intent it = getIntent();
		action = it.getIntExtra("key", 0);

		if (action == 1) {
			ojEdit = it.getExtras().getParcelable("boj");
			membername.setText(ojEdit.get(Empl.NAME));
			startdate.setText(ojEdit.get(Empl.START_DATE));
			enddate.setText(ojEdit.get(Empl.END_DATE));
			starttime.setText(ojEdit.get(Empl.START_TIME));
			endtime.setText(ojEdit.get(Empl.END_TIME));
			// ed_title_content.setText(ojEdit.get(Empl.TITLE_CONTENT));
			ed_content.setText(ojEdit.get(Empl.CONTENT));
			flag_start_date = flag_end_date = flag_start_time = flag_end_time = true;
			btnLuuVaThem_nv = (Button) findViewById(R.id.btnLuuVaThem_nv);
			btnLuu_nv = (Button) findViewById(R.id.btnLuu_nv);
			btnLuuVaThem_nv.setText("Hủy");
			btnLuu_nv.setText("Cập nhật");

			try {
				group = Integer.parseInt(ojEdit.get(Empl.GROUP));
			} catch (Exception e) {
				Mlog.E("34234234 = Integer.parseInt(ojEdit.get(Empl.GROUP))= " + e);
			}

			if (Empl.KEY_ONE_DAY.equalsIgnoreCase(ojEdit.get(Empl.MANYDAY))) {
				oneday.setChecked(true);
			}

		}

	}

	private void initGroup() {
		group = getIntent().getIntExtra("group", 10);
		flag_start_date = flag_end_date = flag_start_time = flag_end_time = true;
		Calendar cal = Calendar.getInstance();

		switch (group) {
		case NHOM_HOP_DONG:
			membername.setHint("Tên nhân viên hợp đồng");
			flag_start_date = flag_end_date = flag_start_time = flag_end_time = false;
			break;
		case NHOM_KE_TOAN:
			membername.setHint("Tên nhân viên kế toán");

			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			start_date = Mutils.convertCalendar2String(cal);
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			end_date = Mutils.convertCalendar2String(cal);
			start_time = start_date;
			end_time = end_date;

			break;
		case NHOM_KINH_DOANH:
			membername.setHint("Tên nhân viên kinh doanh");

			int currQuarter = (int) (cal.get(Calendar.MONTH)) / 3 + 1;

			cal.set(cal.get(Calendar.YEAR), 3 * currQuarter - 3, 1);
			start_date = Mutils.convertCalendar2String(cal);

			cal.set(cal.get(Calendar.YEAR), 3 * currQuarter, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			end_date = Mutils.convertCalendar2String(cal);
			start_time = start_date;
			end_time = end_date;
			break;
		case NHOM_LAP_DAT:
			membername.setHint("Tên nhân viên lắp đặt");

			cal.set(Calendar.DAY_OF_MONTH, 1);
			start_date = Mutils.convertCalendar2String(cal);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			end_date = Mutils.convertCalendar2String(cal);
			start_time = start_date;
			end_time = end_date;

			break;
		case NHOM_THUC_TAP:
			membername.setHint("Tên nhân viên thực tập");
			flag_start_date = flag_end_date = flag_start_time = flag_end_time = false;
			break;

		default:
			break;
		}

		startdate.setText(start_date);
		enddate.setText(end_date);
		starttime.setText(start_date);
		endtime.setText(end_date);

	}

	private void initView() {
		RelativeLayout btnstartdate1 = (RelativeLayout) findViewById(R.id.btn_start_date);
		btnstartdate1.setOnClickListener(this);
		RelativeLayout btnenddate1 = (RelativeLayout) findViewById(R.id.lay_end_date);
		btnenddate1.setOnClickListener(this);

		LinearLayout btnstartdate2 = (LinearLayout) findViewById(R.id.btnBeginTime_nv);
		btnstartdate2.setOnClickListener(this);
		RelativeLayout btnenddate2 = (RelativeLayout) findViewById(R.id.btnEndTime_nv);
		btnenddate2.setOnClickListener(this);

		startdate = (TextView) findViewById(R.id.tv_start_date);
		enddate = (TextView) findViewById(R.id.tv_end_date);
		starttime = (TextView) findViewById(R.id.tv_start_time);
		endtime = (TextView) findViewById(R.id.tv_end_time);

		oneday = (RadioButton) findViewById(R.id.radOneDay);
		moreday = (RadioButton) findViewById(R.id.radMoreday);

		membername = (EditText) findViewById(R.id.edtInsertName_nv);
		// ed_title_content = (EditText) findViewById(R.id.ed_title_content);
		ed_content = (EditText) findViewById(R.id.ed_content);
		spnReminder = (Spinner) findViewById(R.id.spnNhactruocNV);

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

		oneday.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// flag_end_time = isChecked;

			}
		});

	}

	public void Onclickmoreday(View v) {
		if (((RadioButton) v).isChecked()) {
			RelativeLayout gr2 = (RelativeLayout) findViewById(R.id.btnEndTime_nv);
			gr2.setVisibility(View.VISIBLE);
		}
	}

	public void Onclickoneday(View v) {
		if (((RadioButton) v).isChecked()) {
			RelativeLayout gr2 = (RelativeLayout) findViewById(R.id.btnEndTime_nv);
			gr2.setVisibility(View.GONE);
		}
	}

	int parentid = 0;
	int workid = 0;

	@Override
	public void onClick(final View v) {
		Calendar calendar = Calendar.getInstance();

		DatePickerDialog datepicker = new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				switch (v.getId()) {
				case R.id.btn_start_date:
					TextView tvstartdate1 = (TextView) findViewById(R.id.tv_start_date);

					start_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

					if (flag_end_date && Mutils.compareTime(start_date, end_date, Mcon.dateFormat)) {
						showToast("Ngày bắt đầu không được lớn hơn ngày kết thúc");
						flag_start_date = false;
						return;
					}
					tvstartdate1.setText(start_date);
					flag_start_date = true;

					break;

				case R.id.lay_end_date:

					TextView tvenddate1 = (TextView) findViewById(R.id.tv_end_date);
					end_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

					if (!flag_start_date) {
						showToast("Nhập ngày bắt đầu trước");
						flag_end_date = false;
						return;
					}

					if (Mutils.compareTime(start_date, end_date, Mcon.dateFormat)) {
						showToast("Ngày bắt đầu không được lớn hơn ngày kết thúc");
						flag_end_date = false;
						return;
					}
					tvenddate1.setText(end_date);
					flag_end_date = true;

					break;
				case R.id.btnBeginTime_nv:

					if (!flag_start_date || !flag_end_date) {
						showToast("Dữ liệu trước chưa nhập đầy đủ");
						flag_start_time = false;
						return;
					}

					TextView tvstartdate2 = (TextView) findViewById(R.id.tv_start_time);
					start_time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
					if (Mutils.compareTime(start_date, start_time, Mcon.dateFormat) || Mutils.compareTime(start_time, end_date, Mcon.dateFormat)) {
						showToast("Ngày phải nằm trong khoảng ngày bắt đầu và ngày kết thúc của công việc");
						flag_start_time = false;
						return;
					}
					if (flag_end_time)
						if (Mutils.compareTime(start_time, end_time, Mcon.dateFormat)) {
							showToast("Ngày bắt đầu không được lớn hơn ngày kết thúc");
							flag_start_time = false;
							return;
						}

					tvstartdate2.setText(start_time);
					flag_start_time = true;

					break;
				case R.id.btnEndTime_nv:
					end_time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
					if (!flag_start_date || !flag_end_date) {
						showToast("Dữ liệu trước chưa nhập đầy đủ");
						flag_end_time = false;
						return;
					}

					if (flag_start_time)
						if (Mutils.compareTime(start_time, end_time, Mcon.dateFormat)) {
							showToast("Ngày bắt đầu không được lớn hơn ngày kết thúc");
							flag_end_time = false;
							return;
						}

					
					

					if (Mutils.compareTime(start_date, end_time, Mcon.dateFormat) || Mutils.compareTime(end_time, end_date, Mcon.dateFormat))

					{
						showToast("Ngày phải nằm trong khoảng ngày bắt đầu và ngày kết thúc của công việc");
						flag_end_time = false;
						return;
					}
					TextView tvenddate2 = (TextView) findViewById(R.id.tv_end_time);
					tvenddate2.setText(end_time);
					flag_end_time = true;
					break;
				default:
					break;
				}
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datepicker.show();
	}

	public void OnclickinsertNV(View v) {

		if (exeSave())
			finish();
	}

	public void onClickSaveAndNew(View v) {
		if (action == 0) {

			exeSave();
			ed_content.setText("");
		} else
			finish();
	}

	private Boolean exeSave() {

		if (!flag_start_date || !flag_end_date || !flag_start_time || !flag_end_time) {
			showToast("Cần phải nhập đầy đủ thông tin");
			return false;
		}

		if (ed_content.getText().toString().length() == 0) {
			showToast("Cần phải nhập đầy đủ thông tin");
			return false;
		}

		BaseObject oj = new NvOj();
		oj.set(NvOj.ALERT, timeAlert + "");
		oj.set(NvOj.CONTENT, ed_content.getText() + "");

		oj.set(NvOj.END_DATE, enddate.getText() + "");
		oj.set(NvOj.END_TIME, endtime.getText() + "");
		oj.set(NvOj.GROUP, group + "");
		if (moreday.isChecked())
			oj.set(NvOj.MANYDAY, 0 + "");
		else

			oj.set(NvOj.MANYDAY, 1 + "");
		oj.set(NvOj.NAME, membername.getText() + "");
		oj.set(NvOj.START_DATE, startdate.getText() + "");
		oj.set(NvOj.START_TIME, starttime.getText() + "");
		// oj.set(NvOj.TITLE_CONTENT, ed_title_content.getText() + "");
		if (action == 1)
			oj.set(NvOj.ROW_ID, ojEdit.get(Empl.ROW_ID));

		ArrayList<BaseObject> ojs = new ArrayList<BaseObject>();
		ojs.add(oj);
		insertDb(ojs);
		return true;

	}

	void insertDb(ArrayList<BaseObject> ojAdd) {
		boolean adddb;
		if (action == 0)
			adddb = DbSupport.addToTable(ojAdd, DbTable.EMPL, false, null);
		else
			adddb = DbSupport.update(ojAdd.get(0), DbTable.EMPL, Empl.ROW_ID);
		if (!adddb)
			showToast("Có lỗi xảy ra!");
		else
			showToast("Thành công!");
		ojAdd = new ArrayList<BaseObject>();

		TaskUser1 taskUser1 = new TaskUser1(model, TaskType.TASK_UPDATE_DATA, null, getBaseContext());
		model.exeTask(null, taskUser1);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		finish();
	}

}
