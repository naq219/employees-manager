package haivu.qlnv;

import haivu.qlnv.adapter.AllAdapter;
import haivu.qlnv.detail.HcDayly;
import haivu.qlnv.object.DbSupport;
import haivu.qlnv.object.Empl;
import haivu.qlnv.utils.DbTable;
import haivu.qlnv.utils.DialogUtils;
import haivu.qlnv.utils.IDialogChoose;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.Mutils;
import haivu.qlnv.utils.Sdata;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.Utils;

public class HomeActivity extends MainActivity implements OnItemClickListener, Mcon.Group {
	OnClickListener clickListener;
	int curentGroup = NHOM_HANH_CHINH;
	int count_data = 0;
	ArrayList<BaseObject> data;

	ArrayList<BaseObject> dataHC;
	AllAdapter HcAdapter = null;
	ArrayList<BaseObject> curData;

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

		// init database
		boolean init = DbSupport.init(DbTable.tables, DbTable.keys, getBaseContext(), Mcon.dbPath, 3);
		if (!init)
			Mlog.E("init database fail!!");
		try {
			curentGroup = Integer.parseInt(Utils.getStringSPR(Mcon.spr.GROUP, mct));
		} catch (Exception e) {
			curentGroup = NHOM_HANH_CHINH;
		}
		initView();
		IniOnclick();
		btnAdd_menu.setOnClickListener(clickListener);
		btnMenu.setOnClickListener(clickListener);
		btnSearch.setOnClickListener(clickListener);
		HcAdapter = new AllAdapter(mct, R.layout.item_list_all, new ArrayList<BaseObject>(), curentGroup);
		//allAdapter=new  AllAdapter(mct, R.layout.item_list_hanhchinh, new ArrayList<BaseObject>(), curentGroup);
		
		lvContent.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (curentGroup) {
		case NHOM_HANH_CHINH:
			Sdata.hcDayly=curData;
			Sdata.hcDayly_dataline=curData.get(position);
			startActivity(new Intent(mct, HcDayly.class));
			break;

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
						stt_search = !stt_search;
					} else {
						edtSearch.setVisibility(View.VISIBLE);
						stt_search = !stt_search;
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

	// Dialog show choose create new Empl
	IDialogChoose actionDialog = new IDialogChoose() {

		@Override
		public void onChoose(int group) {
			if (NHOM_HANH_CHINH == group)
				startActivity(new Intent(getApplicationContext(), InsertHCActivity.class));

		}
	};

	void updateUI() {
		showProgressDialog(mct);

		data = DbSupport.getAllOfTable(DbTable.EMPL, Empl.keys);
		HashMap<Integer, ArrayList<BaseObject>> hmData = Mutils.filterData(data);

		 curData = hmData.get(curentGroup);

		count_data = curData.size();

		tvNumber_title.setText(count_data + "");
		
		
		HcAdapter.SetItems(curData);
		HcAdapter.notifyDataSetChanged();
		lvContent.setAdapter(HcAdapter);
		closeProgressDialog();
	}

}
