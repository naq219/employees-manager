package haivu.qlnv;

import haivu.qlnv.database.Database;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	public void OnclickinsertandmoreNV(View v) {
		Log.i("", "HERE");
		Database db = new Database(this);
		// lấy groupid và repeat thay vào đây
		int groupid = 2;
		String repeat = "2";

		int memberid = db.Ismembership("" + membername.getText());

		if (memberid == 0)
			memberid = db.insertmembership("" + membername.getText(), groupid);
		Log.i("",
				"membershipid:" + memberid + " - membername: "
						+ membername.getText());

		parentid = db
				.Iswork("" + membername.getText(), "" + content1.getText());

		if (parentid == 0)
			workid = db.insertwork(0, memberid, "" + content1.getText(),
					""+startdate.getText(), ""+enddate.getText(), 0, "", "", 0, repeat);
		Log.i("", "parentid:" + parentid + " - membershipid: " + memberid
				+ " - content:" + content1.getText() + " - startdate:"
				+ startdate.getText());

		String ed = "";
		if (moreday.isChecked())
			ed = "" + endtime.getText();
		db.insertwork(parentid, memberid, "" + content2.getText(), ""
				+ starttime.getText(), ed, 0, "", "", 0, repeat);

		Log.i("", "parentid:" + parentid + " - membershipid: " + memberid
				+ " - content2:" + content2.getText() + " - startdate2:"
				+ starttime.getText());
		db.closeConnect();
	}

	// Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	// String s = formatter.format("24/08/1990");
	//
	// Log.i("", "date: " + s);

	// database db = new database(this);

	//
	// insert table
	//
	// db.insertgroup("dddddgfsdgfsd");
	// db.insertgroup("vrml");
	// db.insertmembership("NgĂ´ VÄƒn TĂ­nh", 2);
	// db.insertmembership("VÅ© Háº£i", 3);
	// db.insertmembership("Nguyá»…n Quang Duy", 2);
	// db.insertmembership("BĂ¹i VÄƒn NhiĂªm", 1);
	//
	// db.insertwork(0, 2, "CĂ´ng viá»‡c 1 cá»§a nhĂ¢n viĂªn 1", "10/10/2013",
	// "",
	// 1,
	// "9:10", "10:10", 1, 3);
	// db.insertwork(1, 2, "má»™t pháº§n cĂ´ng viá»‡c cá»§a nhĂ¢n viĂªn 1",
	// "10/10/2013",
	// "", 2, "9:10", "10:10", 0, 3);
	// db.insertwork(0, 1, "CĂ´ng viá»‡c 1 cá»§a nhĂ¢n viĂªn hĂ nh chĂ­nh",
	// "13/10/2013", "", 0, "9:10", "10:10", 1, 0);

	//
	// update table
	//
	// if (db.updategroup("Sá»­a Chá»¯a", 2))
	// Log.i("update", "Groupid=2,groupname=sá»­a chá»¯a");
	// if (db.updatemembership("tĂ­nh ngĂ´ vÄƒn", 2, 2))
	// Log.i("update", "membershipid=2,membername=tĂ­nh ngĂ´ vÄƒn,groupid=2");
	//
	// String[] column = { "content", "int:parentid" };
	// String[] value = { "nhĂ¢n viĂªn 1 cĂ³ cĂ´ng viá»‡c cha lĂ  1", "0" };
	// if (db.updatework(column, value, 2))
	// Log.i("update", "content,parent");

	//
	// delete table
	//
	// if(db.deletework(1))
	// Log.i("deleted", "deleted one row in table work, workid=1");
	// if(db.deletemembership(1))
	// Log.i("daleted",
	// "deleted one row in table membership, membershipid=1");
	// if(db.deletegroup(1))
	// Log.i("daleted", "deleted one row in table group, groupid=1");

	// ArrayList<Group> listgroup = db.listgroup();
	// for (int i = 0; i < listgroup.size(); i++) {
	// Group group = listgroup.get(i);
	// Log.i("SQL ERROR", "groupname: " + group.getGroupname()
	// + " - numberMember: " + group.getNumberMember());
	// }
	//
	// ArrayList<Work> listwork = db.listwork(2, "10/10/2013");
	// String morning = "";
	// String afternoon = "";
	// int s=listwork.size();
	// for (int i = 0; i < s; i++) {
	// Work work = listwork.get(i);
	// if (work.getMorning() == 1) {
	// morning += "content: " + work.getContent() + " - starttime: "
	// + work.getStarttime() + " - endtime: "
	// + work.getEndtime() + " morning: " + work.getMorning()+" \n";
	// }else{
	// afternoon += "content: " + work.getContent() + " - starttime: "
	// + work.getStarttime() + " - endtime: "
	// + work.getEndtime() + " morning: " + work.getMorning()+" \n";
	// }
	// }
	// Log.i("morning",morning);
	// Log.i("afternoon",afternoon);
	// ############ thĂªm cĂ´ng viá»‡c
	// parentid: qui Ä‘á»‹nh cĂ´ng viá»‡c nĂ y thuá»™c cáº¥p báº­c bao cá»§a
	// cĂ´ng viá»‡c nĂ o:
	// type: int
	// +giĂ¡ trá»‹ khĂ¡c 0, tá»©c lĂ  cĂ´ng viá»‡c Ä‘áº¥y lĂ  con cá»§a cĂ´ng
	// viá»‡c cha cĂ³
	// workid lĂ  giĂ¡ trá»‹ khĂ¡c 0 Ä‘áº¥y
	// +giĂ¡ trá»‹ báº±ng 0 tá»©c nĂ³ lĂ  cĂ´ng viá»‡c cha
	//
	// membershipid: mĂ£ nhĂ¢n viĂªn - type: int
	// content: ná»™i dung cĂ´ng viá»‡c - type: String
	// startdate: ngĂ y báº¯t Ä‘áº§u - type: String
	// enÄ‘ate: ngĂ y káº¿t thĂºc - type: String
	// +náº¿u lĂ  nhĂ¢n viĂªn hĂ nh chĂ­nh sáº½ ko cĂ³ giĂ¡ trá»‹ trong
	// trÆ°á»?ng nĂ y tá»©c lĂ 
	// rá»—ng

	// remindful: nháº¯c nhá»Ÿ trÆ°á»›c bao nhiĂªu phĂºt tuá»³ vĂ o qui Æ°á»›c -
	// type :int
	// repeat: nháº¯c láº¡i, giĂ¡ trá»‹ tuá»³ quy Æ°á»›c - type: int
	// +náº¿u lĂ  nhĂ¢n viĂªn hĂ nh chĂ­nh thĂ¬ Ä‘iá»?n giĂ¡ trá»‹: 1-buá»•i
	// sĂ¡ng, 0-buá»•i
	// chiá»?u
	// +cĂ²n ko pháº£i thĂ¬ sáº½ cĂ³ giĂ¡ trá»‹ khĂ¡c vá»›i 2 giĂ¡ trá»‹ kia

	// db.insertwork(parentid, membershipid, content, startdate, enddate,
	// morning, starttime, endtime, remindful, repeat)

	// db.insertwork(0, 2, "CĂ´ng viá»‡c 1 cá»§a nhĂ¢n viĂªn 1", "1/10/2013",
	// "13/10/2013", 2, "9:10", "10:10", 1, 3);
	// db.insertwork(1, 2, "má»™t pháº§n cĂ´ng viá»‡c cá»§a nhĂ¢n viĂªn 1",
	// "1/10/2013", "7/10/2013", 2, "9:10", "10:10", 1, 3);
	// db.insertwork(0, 1, "CĂ´ng viá»‡c 1 cá»§a nhĂ¢n viĂªn hĂ nh chĂ­nh",
	// "13/10/2013", "", 1, "9:10", "10:10", 1, 0);
	// }

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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(this, MainActivity.class));
	}
}
