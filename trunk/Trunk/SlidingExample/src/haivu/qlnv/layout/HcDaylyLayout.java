package haivu.qlnv.layout;

import haivu.qlnv.R;
import haivu.qlnv.adapter.AllAdapter;
import haivu.qlnv.object.Empl;
import haivu.qlnv.object.HcOj;
import haivu.qlnv.utils.IdialogDate;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.Mutils;
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

public class HcDaylyLayout extends BaseActivity implements Mcon.Group {
	protected RelativeLayout lay_calenda;
	protected ListView lv_sang, lv_chieu;
	protected ArrayList<BaseObject> dataRoot;
	protected BaseObject dataLine;
	protected ArrayList<BaseObject> dataLvSang = new ArrayList<BaseObject>();
	protected ArrayList<BaseObject> dataLvChieu = new ArrayList<BaseObject>();;
	protected Context mct;
	protected TextView tv_calendar;
	protected RelativeLayout root_calendar;;
	protected IdialogDate idialogDate;
	protected AllAdapter adapterSang;
	protected AllAdapter adapterChieu;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mct = HcDaylyLayout.this;
		setContentView(R.layout.hanhchinh_dayly);
		initViewHcDaylyLayout();
		

	}

	private void initViewHcDaylyLayout() {
		lay_calenda = (RelativeLayout) findViewById(R.id.lay_calenda);
		lv_sang = (ListView) findViewById(R.id.lv_sang);
		lv_chieu = (ListView) findViewById(R.id.lv_chieu);
		tv_calendar = (TextView) findViewById(R.id.tv_calendar);
		root_calendar = (RelativeLayout) findViewById(R.id.root_calendar);

	}

	protected void initData() {
		dataRoot = Sdata.hcDayly;
		dataLine = Sdata.hcDayly_dataline;

		String keyDate = dataLine.get(Empl.START_DATE);
		filterDate(keyDate);
		tv_calendar.setText(dataLine.get(HcOj.START_DATE));
		
		showToast(dataLine.get(Empl.ROW_ID));
	}

	protected void filterDate(String keyDate) {

		if (keyDate != null) {
			dataLvSang.clear();
			dataLvChieu.clear();
			for (BaseObject oj : dataRoot) {
				if (keyDate.equalsIgnoreCase(oj.get(Empl.START_DATE))) {
					String keySesson = oj.get(Empl.SESSION);
					if (Empl.KEY_SANG.equalsIgnoreCase(keySesson))
						dataLvSang.add(oj);
					else
						dataLvChieu.add(oj);

				}

			}
			
			if(dataLvSang.size()==0){
				lv_sang.setVisibility(View.GONE);
				
			}
			
			if(dataLvChieu.size()==0){
				lv_chieu.setVisibility(View.GONE);
			}

			adapterSang = new AllAdapter(mct, R.layout.item_list_all, dataLvSang, NHOM_HANH_CHINH);
			adapterChieu = new AllAdapter(mct, R.layout.item_list_all, dataLvChieu, NHOM_HANH_CHINH);

			lv_sang.setAdapter(adapterSang);
			lv_chieu.setAdapter(adapterChieu);
			
			Mutils.setListViewHeightBasedOnChildren(lv_chieu);
			Mutils.setListViewHeightBasedOnChildren(lv_sang);

		}

	}
	
	

}
