package haivu.qlnv;

import haivu.qlnv.adapter.HanhChinhAdapter;
import haivu.qlnv.adapter.MenuAdapter;
import haivu.qlnv.database.Database;
import haivu.qlnv.database.Group;
import haivu.qlnv.object.Cons;
import haivu.qlnv.object.HanhChinhObject;
import haivu.qlnv.object.MenuObject;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends SherlockFragmentActivity implements
		OnItemClickListener, OnClickListener {
	SlidingMenu menu;
	ImageView btnMenu;
	ArrayList<MenuObject> listMenu = new ArrayList<MenuObject>();
	ArrayList<HanhChinhObject> listHanhChinh = new ArrayList<HanhChinhObject>();
	MenuAdapter menuAdapter;
	HanhChinhAdapter hanhChinhAdapter;
	AdView ads;
	TextView tvTitle;
	TextView tvNumber_title;
	ImageView btnSearch, btnAdd_menu, btnAddActionbar;
	EditText edtSearch;
	boolean stt_search = false;
	RadioButton radHC, radLD, radTT, radKD, radKT, radHD;
	Database db;
	Button btnOk_dialog, btnCancel_dialog;
	Dialog dl;
	ListView lvContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		db = new Database(this);
		createGroup();

		initSlidingMenu();
		initView();
	}

	public void createGroup() {
		if (db.listgroup().size() <= 1) {
			db.insertgroup(Cons.NHOM_HANH_CHINH.toUpperCase());
			db.insertgroup(Cons.NHOM_LAP_DAT.toUpperCase());
			db.insertgroup(Cons.NHOM_THUC_TAP.toUpperCase());
			db.insertgroup(Cons.NHOM_KINH_DOANH.toUpperCase());
			db.insertgroup(Cons.NHOM_KE_TOAN.toUpperCase());
			db.insertgroup(Cons.NHOM_HOP_DONG.toUpperCase());
		}
	}

	public void initView() {
		// TODO Auto-generated method stub
		ads = (AdView) findViewById(R.id.ad);
		// Look up the AdView as a resource and load a request.
		ads.loadAd(new AdRequest());

		btnAdd_menu = (ImageView) menu.findViewById(R.id.btnAdd_menu);
		btnAdd_menu.setOnClickListener(this);

		btnMenu = (ImageView) findViewById(R.id.btnMenu);
		btnMenu.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvNumber_title = (TextView) findViewById(R.id.tvNumber_title);
		edtSearch = (EditText) findViewById(R.id.edtSearch);
		btnSearch = (ImageView) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);

		ListView lvMenu = (ListView) menu.findViewById(R.id.lvMenu);
		ArrayList<Group> group_property = db.listgroup();
		for (int i = 0; i < db.listgroup().size(); i++) {
			listMenu.add(new MenuObject(group_property.get(i).getGroupname(),
					"" + group_property.get(i).getNumberMember()));
		}
		menuAdapter = new MenuAdapter(this, listMenu);
		lvMenu.setAdapter(menuAdapter);
		lvMenu.setOnItemClickListener(this);

		tvTitle.setText(Cons.NHOM_HANH_CHINH);
		tvNumber_title.setText(String.valueOf(group_property.get(0)
				.getNumberMember()));

		lvContent = (ListView) findViewById(R.id.lvContent);
		ArrayList<HanhChinhObject> hanhChinh_property = db.listhanhchinh(1);

		for (int i = 0; i < hanhChinh_property.size(); i++) {
			listHanhChinh.add(new HanhChinhObject(hanhChinh_property.get(i)
					.getName(), hanhChinh_property.get(i).getDay(),
					hanhChinh_property.get(i).getMorning(), hanhChinh_property
							.get(i).getTime_begin(), hanhChinh_property.get(i)
							.getTime_end(), hanhChinh_property.get(i)
							.getContent_work(), hanhChinh_property.get(i)
							.getReminder(), hanhChinh_property.get(i)
							.getRepeat()));
		}
		hanhChinhAdapter = new HanhChinhAdapter(this, listHanhChinh);
		tvNumber_title.setText(listMenu.get(0).getSo());
		lvContent.setAdapter(hanhChinhAdapter);
	}

	// khởi tạo menu
	public void initSlidingMenu() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.activity_menu);
		menu.setSlidingEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		menu.toggle();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.lvMenu:
			tvTitle.setText(listMenu.get(position).getLoai());
			tvNumber_title.setText(listMenu.get(position).getSo());
			menu.showContent();
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnAdd_menu:
			showDialog();
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
			if (radHC.isChecked()) {
				startActivity(new Intent(getApplicationContext(),
						InsertHCActivity.class));
			} else {
				startActivity(new Intent(getApplicationContext(),
						InsertNVActivity.class));
			}
			dl.dismiss();
			finish();
			break;
		case R.id.btnCancel_dialog:
			dl.dismiss();
		}
	}

	public void showDialog() {
		dl = new Dialog(this);
		dl.setTitle("Chọn loại nhân viên: ");
		dl.setContentView(R.layout.dialog_select_loainv);
		radHC = (RadioButton) dl.findViewById(R.id.radHC);
		radHD = (RadioButton) dl.findViewById(R.id.radHD);
		radKD = (RadioButton) dl.findViewById(R.id.radKD);
		radKT = (RadioButton) dl.findViewById(R.id.radKT);
		radLD = (RadioButton) dl.findViewById(R.id.radLD);
		radTT = (RadioButton) dl.findViewById(R.id.radTT);
		btnOk_dialog = (Button) dl.findViewById(R.id.btnOK_dialog);
		btnCancel_dialog = (Button) dl.findViewById(R.id.btnCancel_dialog);
		btnOk_dialog.setOnClickListener(this);
		btnCancel_dialog.setOnClickListener(this);
		dl.show();
	}
}
