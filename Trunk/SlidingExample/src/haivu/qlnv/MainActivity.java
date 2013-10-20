package haivu.qlnv;

import haivu.qlnv.object.DbSupport;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.slidingmenu.lib.SlidingMenu;
import com.telpoo.frame.database.BaseDBSupport;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;

public class MainActivity extends SherlockFragmentActivity implements ModelListener {
	public static BaseModel model = null;
	public static BaseDBSupport db = null;
	
	SlidingMenu menu;
	ImageView btnMenu;
	AdView ads;
	TextView tvTitle;
	TextView tvNumber_title;
	ImageView btnSearch, btnAdd_menu, btnAddActionbar;
	EditText edtSearch;
	boolean stt_search = false;
	RadioButton radHC, radLD, radTT, radKD, radKT, radHD;
	
	ListView lvContent;
	public Context mct;
	ProgressDialog loadingProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mct=MainActivity.this;
		db= DbSupport.getInstance(mct);
		setTheme(R.style.Theme_Sherlock);
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

		btnAdd_menu = (ImageView) menu.findViewById(R.id.btnAdd_menu);

		btnMenu = (ImageView) findViewById(R.id.btnMenu);

		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvNumber_title = (TextView) findViewById(R.id.tvNumber_title);
		edtSearch = (EditText) findViewById(R.id.edtSearch);
		btnSearch = (ImageView) findViewById(R.id.btnSearch);

		lvContent = (ListView) findViewById(R.id.lvContent);
		

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
	public Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProgress(int taskType, int progress) {
		// TODO Auto-generated method stub

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
	
}
