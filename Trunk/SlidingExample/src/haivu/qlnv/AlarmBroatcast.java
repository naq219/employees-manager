package haivu.qlnv;

import haivu.qlnv.object.Empl;
import haivu.qlnv.utils.Mutils;
import haivu.qlnv.utils.Sdata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;
import android.util.TimeUtils;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

public class AlarmBroatcast {

	// used for register alarm manager
	static PendingIntent pendingIntent;
	// used to store running alarmmanager instance
	static AlarmManager alarmManager;
	// Callback function for Alarmmanager event
	static BroadcastReceiver mReceiver;
	static Context ct;
	static Intent intent;

	public static void RegisterAlarmBroadcast(Context ct1) {
		ct = ct1;
		Mlog.D("Going to register Intent.RegisterAlramBroadcast");

		// This is the call back function(BroadcastReceiver) which will be call
		// when your
		// alarm time will reached.
		mReceiver = new BroadcastReceiver() {
			private static final String TAG = "Alarm Example Receiver";

			@Override
			public void onReceive(Context context, Intent intent) {

				PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
				// Acquire the lock
				wl.acquire();

				Intent i = new Intent(context, Alarm.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);

				Mutils.generateNotification(ct, "tong bao", "naq", R.drawable.icon);

				Log.i(TAG, "BroadcastReceiver::OnReceive() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

				Mutils.updateData();

				wl.release();
			}
		};

		// register the alarm broadcast here
		intent=new Intent("haivu.qlnv");
		ct.registerReceiver(mReceiver, new IntentFilter("haivu.qlnv"));
		pendingIntent = PendingIntent.getBroadcast(ct, 0, intent, 0);
		alarmManager = (AlarmManager) (ct.getSystemService(Context.ALARM_SERVICE));
	}

	public static void UnregisterAlarmBroadcast() {
		alarmManager.cancel(pendingIntent);
		ct.unregisterReceiver(mReceiver);
	}

	public static void setAlarm(ArrayList<BaseObject> ojs) {
		Mlog.D("setAlarm");
		Long value = null;
		BaseObject oj_alarm=new BaseObject();

		for (int i = 0; i < ojs.size(); i++) {
			// Long cur = getCalendar(ojs.get(i));
			BaseObject oj = ojs.get(i);
			String start_date = oj.get(Empl.START_DATE);
			String start_time = oj.get(Empl.START_TIME);
			String time_alert= oj.get(Empl.ALERT);
			int time_alertint=0;
			try {
				time_alertint= Integer.parseInt(time_alert);
			} catch (Exception e) {
				// TODO: handle exception
			}
			Mlog.D("start_date=" + start_date + "=start_time==" + start_time);

			try {

				// Calendar cal = Calendar.getInstance();
				// cal.setTimeInMillis(System.currentTimeMillis());
				// cal.set(Calendar.YEAR, 2013);
				// cal.set(Calendar.MONTH, 9);
				// cal.set(Calendar.DAY_OF_MONTH, 26);
				// cal.set(Calendar.HOUR_OF_DAY, 14);
				// cal.set(Calendar.MINUTE, 10);
				// cal.set(Calendar.SECOND, 0);
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());
				Long systemtime = cal.getTimeInMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm");
				cal.setTime(sdf.parse(start_date + "." + start_time));

				Long cur = cal.getTimeInMillis()-time_alertint*60000;
				Mlog.T("so sanh=" + (cur - System.currentTimeMillis()));
				if (System.currentTimeMillis() < cur) {
					oj_alarm=ojs.get(i);
					value = cur;
					Mlog.T("miaj value=" + value + "cur=" + cur);
				}
			} catch (Exception e) {
				Mlog.E("=3452=getCalendar" + e);
				e.printStackTrace();
			}

		}

		if (value != null) {
			//Mlog.T("so sanh tiep=" + (value - System.currentTimeMillis()));

			if (value > System.currentTimeMillis()){
//				intent.putExtra("larm_oj", "hehe");
//				pendingIntent = PendingIntent.getBroadcast(ct, 0, intent, 0);
				Sdata.alarm_oj=oj_alarm;
				alarmManager.set(AlarmManager.RTC_WAKEUP, value, pendingIntent);
				
				Mlog.T("set rui");
			}
		}

	}

//	public static Long getCalendar(BaseObject oj) {
//
//		String start_date = oj.get(Empl.START_DATE);
//		String start_time = oj.get(Empl.START_TIME);
//		Mlog.D("start_date=" + start_date + "=start_time==" + start_time);
//
//		try {
//
//			Calendar cal = Calendar.getInstance();
//			cal.setTimeInMillis(System.currentTimeMillis());
//			cal.set(Calendar.YEAR, 2013);
//			cal.set(Calendar.MONTH, 9);
//			cal.set(Calendar.DAY_OF_MONTH, 26);
//			cal.set(Calendar.HOUR_OF_DAY, 14);
//			cal.set(Calendar.MINUTE, 57);
//			cal.set(Calendar.SECOND, 0);
//			Mlog.T("so sanh=" + (cal.getTimeInMillis() - System.currentTimeMillis()));
//			if (System.currentTimeMillis() < cal.getTimeInMillis())
//				return cal.getTimeInMillis();
//		} catch (Exception e) {
//			Mlog.E("=3452=getCalendar" + e);
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}

}
