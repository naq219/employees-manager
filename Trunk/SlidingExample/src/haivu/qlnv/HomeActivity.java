package haivu.qlnv;

import haivu.qlnv.adapter.AllAdapter;
import haivu.qlnv.adapter.MenuListviewAdapter;
import haivu.qlnv.database.DbSupport;
import haivu.qlnv.detail.HcDayly;
import haivu.qlnv.detail.NVDayly;
import haivu.qlnv.task.TaskType;
import haivu.qlnv.task.TaskUser1;
import haivu.qlnv.utils.DbTable;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.Mutils;
import haivu.qlnv.utils.Sdata;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.BUtils;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.Utils;

public class HomeActivity extends MainActivity implements OnItemClickListener, Mcon.Group,TaskType {
	OnClickListener clickListener;

	int count_data = 0;
	// ArrayList<BaseObject> data;

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
		updateData();
		super.onResume();
	}

	private void init() {

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
				updateData();
				menu.toggle();

			}
		});

		ed_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				openSearch(s.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// if(s.length()==0){
				// lvContent.clearTextFilter();
				// }

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
						closeSearch();
					} else {
						openSearch("");
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

	void openSearch(String key) {
		tvTitle.setVisibility(View.INVISIBLE);
		ed_search.setVisibility(View.VISIBLE);
		stt_search = !stt_search;
		ed_search.requestFocus();
		
		if (key.length() == 0)
			updateLvContent(curData);
		else
			{
			Sdata.key_search=key;
			TaskUser1 taskUser1=new TaskUser1(model, TASK_SEARCH, null, mct);
			model.exeTask(null, taskUser1);
			}
		
	}

	void closeSearch() {
		ed_search.setVisibility(View.GONE);
		BUtils.hideKeyboard(mct, ed_search);
		tvTitle.setVisibility(View.VISIBLE);
		stt_search = !stt_search;
		updateLvContent(curData);
	}

	public void updateData() {
		showProgressDialog(mct);
		
		TaskUser1 taskUser1=new TaskUser1(model, TASK_UPDATE_DATA	, null, mct);
		model.exeTask(null, taskUser1);
		
		
	}
	
	public void updateUI(){

		// data = DbSupport.getAllOfTable(DbTable.EMPL,
		// Empl.keys_include_rowId);
		hmData = Sdata.hmData;// Mutils.filterData(data);

		curData = hmData.get(curentGroup);
		Sdata.hcDayly = curData;

		count_data = curData.size();

		tvNumber_title.setText(count_data + "");

		updateLvContent(curData);

		MenuListviewAdapter adapter = new MenuListviewAdapter(mct, R.layout.itemlist_menu, arMenu, hmData);
		lv_menu.setAdapter(adapter);

		tvTitle.setText(Mcon.nameGroup[curentGroup]);

		AlarmBroatcast.setAlarm(hmData.get(NHOM_HANH_CHINH));
		closeProgressDialog();
	}
		

	private void updateLvContent(ArrayList<BaseObject> datare) {
		ArrayList<BaseObject> da=new ArrayList<BaseObject>();
		
		if(datare!=null){
			if(datare.size()>0){
				da=datare;
			}
		}
		

		allAdapter.SetItems(da);
		allAdapter.notifyDataSetChanged();
		lvContent.setAdapter(allAdapter);
		

	}
	
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		closeProgressDialog();
		switch (taskType) {
		case TASK_SEARCH:
			ArrayList<BaseObject> sOj = (ArrayList<BaseObject>) list;
			updateLvContent( sOj);
			
			break;
		case TASK_UPDATE_DATA:
			updateUI();
			break;
		default:
			break;
		}
	}

}
