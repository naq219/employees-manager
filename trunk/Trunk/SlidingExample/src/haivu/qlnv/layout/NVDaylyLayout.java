package haivu.qlnv.layout;

import haivu.qlnv.R;
import haivu.qlnv.adapter.AllAdapter;
import haivu.qlnv.object.Empl;
import haivu.qlnv.object.HcOj;
import haivu.qlnv.utils.IdialogDate;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.Sdata;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.ui.BaseActivity;

public class NVDaylyLayout extends BaseActivity implements Mcon.Group {
	protected RelativeLayout lay_calenda;
	protected ListView lv_sang, lv_chieu;
	protected ArrayList<BaseObject> dataRoot;
	protected BaseObject dataLine;
	protected ArrayList<BaseObject> dataLvSang = new ArrayList<BaseObject>();
	protected ArrayList<BaseObject> dataLvChieu = new ArrayList<BaseObject>();;
	protected Context mct;
	protected TextView tv_calendar,tvTitle;
	protected RelativeLayout root_calendar;;
	protected IdialogDate idialogDate;
	protected AllAdapter adapterSang;
	protected AllAdapter adapterChieu;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mct = NVDaylyLayout.this;
		setContentView(R.layout.hanhchinh_dayly);
		initViewHcDaylyLayout();
		

	}

	private void initViewHcDaylyLayout() {
		lay_calenda = (RelativeLayout) findViewById(R.id.lay_calenda);
		lay_calenda.setVisibility(View.GONE);
		lv_sang = (ListView) findViewById(R.id.lv_sang);
		lv_chieu = (ListView) findViewById(R.id.lv_chieu);
		tv_calendar = (TextView) findViewById(R.id.tv_calendar);
		root_calendar = (RelativeLayout) findViewById(R.id.root_calendar);
		tvTitle=(TextView) findViewById(R.id.tvTitle);

	}

	protected void initData() {
		dataRoot = Sdata.hcDayly;
		dataLine = Sdata.hcDayly_dataline;

		String keyName = dataLine.get(Empl.NAME);
		filterDate(keyName);
		tv_calendar.setText("Thời gian thực hiện:\n"+dataLine.get(HcOj.START_DATE)+" đến "+dataLine.get(HcOj.END_DATE));
		tvTitle.setText(dataLine.get(HcOj.NAME)+"--"+dataLine.get(HcOj.GROUP));
		showToast(dataLine.get(Empl.ROW_ID));
	}

	protected void filterDate(String keyName) {

		if (keyName != null) {
			dataLvSang.clear();
			dataLvChieu.clear();
			for (BaseObject oj : dataRoot) {
				if (keyName.equalsIgnoreCase(oj.get(Empl.NAME))) {
					String keyManyDay = oj.get(Empl.MANYDAY);
					if (Empl.KEY_ONE_DAY.equalsIgnoreCase(keyManyDay))
						dataLvChieu.add(oj);
					else
						dataLvSang.add(oj);

				}

			}

			adapterSang = new AllAdapter(mct, R.layout.item_list_all, dataLvSang, NHOM_HANH_CHINH);
			adapterChieu = new AllAdapter(mct, R.layout.item_list_all, dataLvChieu, NHOM_HANH_CHINH);

			lv_sang.setAdapter(adapterSang);
			lv_chieu.setAdapter(adapterChieu);

		}

	}
	
	

}
