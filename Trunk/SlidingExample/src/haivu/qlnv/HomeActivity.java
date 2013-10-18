package haivu.qlnv;

import com.telpoo.frame.utils.Mlog;

import haivu.qlnv.object.Mcon;
import haivu.qlnv.object.DbSupport;
import haivu.qlnv.utils.DbTable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends MainActivity implements OnItemClickListener {
	OnClickListener clickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		// init database
		boolean init = DbSupport.init(DbTable.tables, DbTable.keys, getBaseContext(), Mcon.dbPath, 3);
		if(!init) Mlog.E("init database fail!!");
	
		initView();
		IniOnclick();
		// lvMenu.setOnItemClickListener(clickListener);
		btnAdd_menu.setOnClickListener(clickListener);
		btnMenu.setOnClickListener(clickListener);
		btnSearch.setOnClickListener(clickListener);
		btnOk_dialog.setOnClickListener(clickListener);
		btnCancel_dialog.setOnClickListener(clickListener);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	void IniOnclick() {
		clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btnAdd_menu:
					showDialogChoose();
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
				case R.id.btnOK_dialog:
					/*
					 * if (radHC.isChecked()) { startActivity(new
					 * Intent(getApplicationContext(), InsertHCActivity.class));
					 * } else { startActivity(new
					 * Intent(getApplicationContext(), InsertNVActivity.class));
					 * } dl.dismiss(); finish();
					 */
					break;
				case R.id.btnCancel_dialog:
					dl.dismiss();
				}

			}
		};
	}

}
