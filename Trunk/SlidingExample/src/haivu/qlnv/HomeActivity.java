package haivu.qlnv;

import haivu.qlnv.adapter.AllAdapter;
import haivu.qlnv.adapter.MenuListviewAdapter;
import haivu.qlnv.database.DbSupport;
import haivu.qlnv.detail.HcDayly;
import haivu.qlnv.detail.NVDayly;
import haivu.qlnv.object.Empl;
import haivu.qlnv.utils.DbTable;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.Mutils;
import haivu.qlnv.utils.Sdata;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.ui.BaseActivity;
import com.telpoo.frame.utils.BUtils;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.Utils;

public class HomeActivity extends MainActivity implements OnItemClickListener, Mcon.Group {
	OnClickListener clickListener;

	int count_data = 0;
	//ArrayList<BaseObject> data;

	ArrayList<BaseObject> dataHC;
	AllAdapter allAdapter = null;
	ArrayList<BaseObject> curData;
	HashMap<Integer, ArrayList<BaseObject>> hmData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void onResume() {
		updateUI();
		super.onResume();
	}

	private void init() {
		/*PowerManager pM = (PowerManager)getSystemService(getBaseContext().POWER_SERVICE);
		WakeLock wl = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "wakeLock");
		wl.acquire();
		Sdata.wakelock = wl;*/
		
		AlarmBroatcast.RegisterAlarmBroadcast(getApplicationContext());

		Utils.saveStringSPR(Mcon.spr.GROUP, NHOM_HANH_CHINH + "", HomeActivity.this);
		// init database
		boolean init = DbSupport.init(DbTable.tables, DbTable.keys, getBaseContext(), Mcon.dbPath, 3);
		if (!init)
			Mlog.E("init database fail!!");

		initView();
		IniOnclick();
		btnAdd_menu.setOnClickListener(clickListener);
		btnMenu.setOnClickListener(clickListener);
		btnSearch.setOnClickListener(clickListener);
		allAdapter = new AllAdapter(mct, R.layout.item_list_all, new ArrayList<BaseObject>(), curentGroup);

		lvContent.setOnItemClickListener(this);

		lv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				curentGroup = position;
				Utils.saveStringSPR(Mcon.spr.GROUP, curentGroup + "", getBaseContext());
				updateUI();
				menu.toggle();

			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (curentGroup) {
		case NHOM_HANH_CHINH:
			Sdata.hcDayly = curData;
			Sdata.hcDayly_dataline = curData.get(position);
			startActivity(new Intent(mct, HcDayly.class));
			break;
		case NHOM_HOP_DONG:
		case NHOM_KE_TOAN:
		case NHOM_KINH_DOANH:
		case NHOM_LAP_DAT:
		case NHOM_THUC_TAP:
			Sdata.hcDayly = curData;
			Sdata.hcDayly_dataline = curData.get(position);
			startActivity(new Intent(mct, NVDayly.class));

		default:
			break;
		}
	}

	void IniOnclick() {
		clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.btnAdd_menu:
					DialogUtils.showDialogChoose(mct);
					break;

				case R.id.btnSearch:
					if (stt_search) {
						edtSearch.setVisibility(View.GONE);
						BUtils.hideKeyboard(mct, edtSearch);
						tvTitle.setVisibility(View.VISIBLE);
						stt_search = !stt_search;
					} else {
						tvTitle.setVisibility(View.INVISIBLE);
						edtSearch.setVisibility(View.VISIBLE);
						stt_search = !stt_search;
						edtSearch.requestFocus();
					}
					break;
				case R.id.btnMenu:
					if (menu.isMenuShowing()) {
						menu.showContent(true);
					} else {
						menu.showMenu(true);
					}
					break;
				}

			}
		};
	}

	public void updateUI() {
		showProgressDialog(mct);
		Mutils.updateData();
		//data = DbSupport.getAllOfTable(DbTable.EMPL, Empl.keys_include_rowId);
		hmData = Sdata.hmData;//Mutils.filterData(data);

		curData = hmData.get(curentGroup);
		Sdata.hcDayly = curData;

		count_data = curData.size();

		tvNumber_title.setText(count_data + "");

		allAdapter.SetItems(curData);
		allAdapter.notifyDataSetChanged();
		lvContent.setAdapter(allAdapter);

		MenuListviewAdapter adapter = new MenuListviewAdapter(mct, R.layout.itemlist_menu, arMenu, hmData);
		lv_menu.setAdapter(adapter);

		tvTitle.setText(Mcon.nameGroup[curentGroup]);

		AlarmBroatcast.setAlarm(hmData.get(NHOM_HANH_CHINH));
		closeProgressDialog();
	}

}
