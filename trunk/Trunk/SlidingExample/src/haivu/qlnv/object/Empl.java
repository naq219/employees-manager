package haivu.qlnv.object;

import com.telpoo.frame.object.BaseObject;

public class Empl extends BaseObject implements Mcon.Group {

	public Empl() {

	}

	public static String[] keys = { "name", "start_date", "end_date", "content", "alert", "manyday", "group1", "session", "start_time", "end_time" };

	public static String NAME = keys[0];
	public static String START_DATE = keys[1];
	public static String END_DATE = keys[2];
	public static String CONTENT = keys[3];
	public static String ALERT = keys[4];
	public static String MANYDAY = keys[5];
	public static String GROUP = keys[6];
	public static String SESSION = keys[7];
	public static String START_TIME = keys[8];
	public static String END_TIME = keys[9];

}