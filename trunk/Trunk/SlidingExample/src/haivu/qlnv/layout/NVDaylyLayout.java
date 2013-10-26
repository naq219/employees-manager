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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.ui.BaseActivity;

public class NVDaylyLayout extends BaseActivity implements Mcon.Group {
	protected RelativeLayout lay_calenda;
	protected ListView lv_nhieungay, lv_tungngay;
	protected ArrayList<BaseObject> dataRoot;
	protected BaseObject dataLine;
	protected ArrayList<BaseObject> dataLvnhieungay = new ArrayList<BaseObject>();
	protected ArrayList<BaseObject> dataLvtungngay = new ArrayList<BaseObject>();;
	protected Context mct;
	protected TextView tv_calendar, tvTitle, tv_sang, tv_chieu;
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
		lv_nhieungay = (ListView) findViewById(R.id.lv_sang);
		lv_tungngay = (ListView) findViewById(R.id.lv_chieu);
		tv_calendar = (TextView) findViewById(R.id.tv_calendar);
		root_calendar = (RelativeLayout) findViewById(R.id.root_calendar);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tv_sang = (TextView) findViewById(R.id.tv_sang);
		tv_chieu = (TextView) findViewById(R.id.tv_chieu);
		tv_sang.setText("Nhiều ngày");
		tv_chieu.setText("Từng ngày");
		findViewById(R.id.btnCalendar_hanhchinh).setVisibility(View.GONE);

	}

	public void initData() {
		dataRoot = Sdata.hcDayly;
		dataLine = Sdata.hcDayly_dataline;

		String keyName = dataLine.get(Empl.NAME);
		filterDate(keyName);
		tv_calendar.setText("Thời gian thực hiện:\n" + dataLine.get(HcOj.START_DATE) + " đến " + dataLine.get(HcOj.END_DATE));
		tvTitle.setText(dataLine.get(HcOj.NAME) + "--" + dataLine.get(HcOj.GROUP));

	}

	protected void filterDate(String keyName) {

		if (keyName != null) {
			dataLvnhieungay.clear();
			dataLvtungngay.clear();
			for (BaseObject oj : dataRoot) {
				if (keyName.equalsIgnoreCase(oj.get(Empl.NAME))) {
					String keyManyDay = oj.get(Empl.MANYDAY);
					if (Empl.KEY_ONE_DAY.equalsIgnoreCase(keyManyDay))
						dataLvtungngay.add(oj);
					else
						dataLvnhieungay.add(oj);

				}

			}
			
			if(dataLvnhieungay.size()==0){
				lv_nhieungay.setVisibility(View.GONE);
				
			}
			
			if(dataLvtungngay.size()==0){
				lv_tungngay.setVisibility(View.GONE);
			}

			adapterSang = new AllAdapter(mct, R.layout.item_list_all, dataLvnhieungay, NHOM_HANH_CHINH);
			adapterChieu = new AllAdapter(mct, R.layout.item_list_all, dataLvtungngay, NHOM_HANH_CHINH);

			lv_nhieungay.setAdapter(adapterSang);
			lv_tungngay.setAdapter(adapterChieu);
			
			Mutils.setListViewHeightBasedOnChildren(lv_nhieungay);
			Mutils.setListViewHeightBasedOnChildren(lv_tungngay);

		}


	}

	private void setparamsmall(View v) {
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f);

		v.setLayoutParams(param);
	}

}
