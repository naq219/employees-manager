package haivu.qlnv;

import haivu.qlnv.adapter.MenuListviewAdapter;
import haivu.qlnv.database.DbSupport;
import haivu.qlnv.utils.IListener;
import haivu.qlnv.utils.Mcon;
import haivu.qlnv.utils.myEdittext;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.slidingmenu.lib.SlidingMenu;
import com.telpoo.frame.database.BaseDBSupport;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.ui.BaseFragmentActivity;
import com.telpoo.frame.utils.BUtils;
import com.telpoo.frame.utils.Utils;

public class MainActivity extends BaseFragmentActivity implements ModelListener, Mcon.Group {
	public static BaseModel model = null;
	public static BaseDBSupport db = null;
	public static int curentGroup = NHOM_HANH_CHINH;
	SlidingMenu menu;
	LinearLayout btnMenu;
	AdView ads;
	TextView tvTitle;
	TextView tvNumber_title;
	ImageView btnSearch, btn_add;
	Button btnAdd_menu;
	myEdittext ed_search;
	boolean stt_search = false;
	RadioButton radHC, radLD, radTT, radKD, radKT, radHD;

	ListView lvContent;
	public Context mct;
	ProgressDialog loadingProgress;
	ListView lv_menu;
	String[] arMenu;
	RelativeLayout root;
	RelativeLayout help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

		mct = MainActivity.this;
		db = DbSupport.getInstance(mct);
		// setTheme(R.style.Theme_Sherlock_Light_NoActionBar);
		if (model == null) {
			model = new BaseModel();
			model.setModelListener1(this);
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initSlidingMenu();
		// initView();
	}

	public void initView() {
		// TODO Auto-generated method stub
		ads = (AdView) findViewById(R.id.ad);
		// Look up the AdView as a resource and load a request.
		ads.loadAd(new AdRequest());

		btnAdd_menu = (Button) menu.findViewById(R.id.btnAdd_menu);

		btnMenu = (LinearLayout) findViewById(R.id.btnMenu);

		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvNumber_title = (TextView) findViewById(R.id.tvNumber_title);
		ed_search = (myEdittext) findViewById(R.id.ed_search);
		

		btnSearch = (ImageView) findViewById(R.id.btnSearch);

		lvContent = (ListView) findViewById(R.id.lvContent);
		lv_menu = (ListView) menu.findViewById(R.id.lv_menu);
		arMenu = getResources().getStringArray(R.array.menu_list);
		MenuListviewAdapter adapter = new MenuListviewAdapter(mct, R.layout.itemlist_menu, arMenu, null);
		lv_menu.setAdapter(adapter);
		help = (RelativeLayout) findViewById(R.id.help);

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

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// menu.toggle();
	// return super.onOptionsItemSelected(item);
	// }

	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {

	}

	@Override
	public void onFail(int taskType, String msg) {

	}

	@Override
	public void onProgress(int taskType, int progress) {

	}

	@Override
	protected void onResume() {
		model.setModelListener1(this);
		super.onResume();
	}

	protected void showToast(int msg) {
		Toast.makeText(getBaseContext(), getString(msg), Toast.LENGTH_LONG).show();
	}

	protected void showToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
	}

	public void showProgressDialog(Context context, String message) {
		closeProgressDialog();

		loadingProgress = new ProgressDialog(context);
		loadingProgress.setMessage(message);
		loadingProgress.setCanceledOnTouchOutside(false);
		loadingProgress.show();
	}

	public void showProgressDialog(Context context) {
		closeProgressDialog();

		loadingProgress = new ProgressDialog(context);
		loadingProgress.setMessage("Đang tải...");
		loadingProgress.setCanceledOnTouchOutside(false);
		loadingProgress.show();
	}

	public void closeProgressDialog() {
		if (loadingProgress != null) {
			if (loadingProgress.isShowing())
				loadingProgress.dismiss();
			loadingProgress = null;
		}
	}

	public void onClickAdd(View v) {
		if (curentGroup == NHOM_HANH_CHINH)
			startActivity(new Intent(getBaseContext(), InsertHCActivity.class));
		else {
			Intent it = new Intent(getBaseContext(), InsertNVActivity.class);
			it.putExtra("group", curentGroup);
			startActivity(it);
		}
	}

	@Override
	public void onBackPressed() {
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		super.onBackPressed();
	}
	
	

}
