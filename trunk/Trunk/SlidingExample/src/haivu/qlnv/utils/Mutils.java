package haivu.qlnv.utils;

import haivu.qlnv.AlarmBroatcast;
import haivu.qlnv.HomeActivity;
import haivu.qlnv.database.DbSupport;
import haivu.qlnv.object.AdapterOj;
import haivu.qlnv.object.Empl;
import haivu.qlnv.object.HcOj;
import haivu.qlnv.object.NvOj;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.telpoo.frame.object.BaseObject;

public class Mutils implements Mcon.Group {
	public static String pasreDayOfWeek(int id_day) {
		switch (id_day) {
		case 1:
			return "Chủ nhật";
		case 2:
			return "Thứ 2";
		case 3:
			return "Thứ 3";
		case 4:
			return "Thứ 4";
		case 5:
			return "Thứ 5";
		case 6:
			return "Thứ 6";
		case 7:
			return "Thứ 7";
		}
		return null;
	}

	public static HashMap<Integer, ArrayList<BaseObject>> filterData(ArrayList<BaseObject> oj) {
		ArrayList<BaseObject> hc = new ArrayList<BaseObject>();
		ArrayList<BaseObject> ld = new ArrayList<BaseObject>();
		ArrayList<BaseObject> tt = new ArrayList<BaseObject>();
		ArrayList<BaseObject> kd = new ArrayList<BaseObject>();
		ArrayList<BaseObject> kt = new ArrayList<BaseObject>();
		ArrayList<BaseObject> hd = new ArrayList<BaseObject>();

		for (BaseObject baseObject : oj) {
			int mgr = Integer.parseInt(baseObject.get(Empl.GROUP));
			if (NHOM_HANH_CHINH == mgr)
				hc.add(baseObject);
			if (NHOM_HOP_DONG == mgr)
				hd.add(baseObject);
			if (NHOM_KE_TOAN == mgr)
				kt.add(baseObject);
			if (NHOM_KINH_DOANH == mgr)
				kd.add(baseObject);
			if (NHOM_LAP_DAT == mgr)
				ld.add(baseObject);
			if (NHOM_THUC_TAP == mgr)
				tt.add(baseObject);

		}

		HashMap<Integer, ArrayList<BaseObject>> hm = new HashMap<Integer, ArrayList<BaseObject>>();
		hm.put(NHOM_HANH_CHINH, hc);
		hm.put(NHOM_HOP_DONG, hd);
		hm.put(NHOM_KE_TOAN, kt);
		hm.put(NHOM_KINH_DOANH, kd);
		hm.put(NHOM_LAP_DAT, ld);
		hm.put(NHOM_THUC_TAP, tt);

		return hm;

	}

	public static ArrayList<BaseObject> convertOj2AdapterOj(ArrayList<BaseObject> oj) {
		ArrayList<BaseObject> oj1 = new ArrayList<BaseObject>();
		if (oj == null) {
			return oj1;
		}
		if (oj.size() == 0)
			return oj1;

		int group;
		try {
			group = Integer.parseInt(oj.get(0).get(HcOj.GROUP));
		} catch (Exception e) {
			group = 0;
		}

		switch (group) {
		case Mcon.Group.NHOM_HANH_CHINH:
			for (BaseObject base : oj) {
				BaseObject tem = new BaseObject();
				tem.set(AdapterOj.ALERT, base.get(HcOj.ALERT));
				tem.set(AdapterOj.CONTENT, base.get(HcOj.CONTENT));
				tem.set(AdapterOj.COUNT, "0");
				tem.set(AdapterOj.TIME, base.get(HcOj.START_DATE));
				oj1.add(tem);

			}

			break;
		case Mcon.Group.NHOM_HOP_DONG:
		case Mcon.Group.NHOM_KE_TOAN:
		case Mcon.Group.NHOM_KINH_DOANH:
		case Mcon.Group.NHOM_THUC_TAP:
		case Mcon.Group.NHOM_LAP_DAT:
			for (BaseObject base : oj) {
				BaseObject tem = new BaseObject();
				tem.set(AdapterOj.ALERT, base.get(NvOj.ALERT));
				tem.set(AdapterOj.TIME, base.get(NvOj.NAME));
				tem.set(AdapterOj.COUNT, "0");
				tem.set(AdapterOj.CONTENT, base.get(NvOj.START_DATE) + " - " + base.get(NvOj.END_DATE));
				oj1.add(tem);

			}

		default:
			break;
		}

		return oj1;

	}

	public static String addZero(int value) {

		if (value < 10) {
			return "0" + value;
		}
		return "" + value;

	}
	
	public static void startActivity(Context ct,Class<?> cl, BaseObject oj){

		Intent it=new Intent(ct, cl);
		it.putExtra("boj", oj);
		ct.startActivity(it);
	}

	public static void startActivity(Context ct,Class<?> cl, BaseObject oj,int key){

		Intent it=new Intent(ct, cl);
		it.putExtra("boj", oj);
		it.putExtra("key", key);
		ct.startActivity(it);
	}
	
	public static OnItemClickListener onClickListView(final Context context,final ArrayList<BaseObject> dataLv){
		OnItemClickListener listener=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				DialogUtils.confirmEditDelete(context, dataLv.get(arg2));
				
			}
		};
		
		return listener;
	}
	
	public static void updateData(){
		ArrayList<BaseObject> data = DbSupport.getAllOfTable(DbTable.EMPL, Empl.keys_include_rowId);
		HashMap<Integer, ArrayList<BaseObject>> hmData = Mutils.filterData(data);
		Sdata.hmData = hmData;
		ArrayList<BaseObject> curData = hmData.get(HomeActivity.curentGroup);
		Sdata.hcDayly = curData;
		Sdata.hanhchinh = hmData.get(Mcon.Group.NHOM_HANH_CHINH);
		AlarmBroatcast.setAlarm(Sdata.hanhchinh);
	}
	
	  public static void setListViewHeightBasedOnChildren(ListView listView) {
   /*       ListAdapter listAdapter = listView.getAdapter(); 
          if (listAdapter == null) {
              // pre-condition
              return;
          }

          int totalHeight = 0;
          for (int i = 0; i < listAdapter.getCount(); i++) {
              View listItem = listAdapter.getView(i, null, listView);
              listItem.measure(0, 0);
              totalHeight += listItem.getMeasuredHeight();
          }

          ViewGroup.LayoutParams params = listView.getLayoutParams();
          params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
          listView.setLayoutParams(params);*/
      }
	  
	  public static void generateNotification(Context context, String message, String title,int drawableIcon) {
	        long when = System.currentTimeMillis();
	        NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);
	        Notification notification = new Notification(drawableIcon, message, when);
	        
	        
	        Intent notificationIntent = new Intent(context, HomeActivity.class);
	      //  notificationIntent.setAction(HandyTrailGCM.OPEN_FRAGMENT_ACTION_INCOMMING_MESSAGE);
	        // set intent so it does not start a new activity
//	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//	                Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent intent =
	                PendingIntent.getActivity(context, 0, notificationIntent, 0);
	        notification.setLatestEventInfo(context, title, message, intent);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	        
	        // Play default notification sound
	        notification.defaults |= Notification.DEFAULT_SOUND;
	        
	        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
	        
	        // Vibrate if vibrate is enabled
	        notification.defaults |= Notification.DEFAULT_VIBRATE;
	        notificationManager.notify(0, notification);      

	    }

}
