package haivu.qlnv.database;

import java.util.ArrayList;

import haivu.qlnv.database.Group;
import haivu.qlnv.database.Memebership;
import haivu.qlnv.object.HanhChinhObject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

	private static final String TBGroup = "tbgroup";
	private static final String TBMemberShip = "tbmembership";
	private static final String TBWork = "tbwork";

	private final Context contex;
	private Connection connect;
	private SQLiteDatabase sqldb;

	public Database(Context ct) {
		contex = ct;
		connect = new Connection(contex);
		sqldb = connect.getWritableDatabase();
	}

	private static class Connection extends SQLiteOpenHelper {

		public Connection(Context context) {
			super(context, "db", null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				String crearte = "CREATE TABLE "
						+ TBGroup
						+ "(groupid integer PRIMARY KEY autoincrement,groupname text)";
				db.execSQL(crearte);

				crearte = "CREATE TABLE "
						+ TBMemberShip
						+ "(membershipid integer PRIMARY KEY autoincrement,membername text,groupid integer)";
				db.execSQL(crearte);

				crearte = "CREATE TABLE "
						+ TBWork
						+ "(workid integer PRIMARY KEY autoincrement,parentid integer,membershipid integer,content text,startdate datetime,enddate datetime,morning integer,starttime text,endtime text,remindful interger,repeat text)";
				db.execSQL(crearte);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i("DBAdapter", "Updating database...");
			db.execSQL("DROP TABLE IF EXISTS tbgroup");
			onCreate(db);

			db.execSQL("DROP TABLE IF EXISTS tbmembership");
			onCreate(db);

			db.execSQL("DROP TABLE IF EXISTS tbwork");
			onCreate(db);
		}

	}

	public void closeConnect() {
		connect.close();
	}

	public long insert(String table, String[] column, String[] value) {
		ContentValues cvl = new ContentValues();
		int count = column.length;

		for (int i = 0; i < count; i++) {
			String str = column[i];
			int counts = column[i].length();

			if (str.length() >= 4) {
				str = str.substring(0, 4);

				if (str.equals("int:")) {
					String cl = column[i].substring(4, counts);
					cvl.put(cl, Integer.parseInt(value[i]));
				} else
					cvl.put(column[i], value[i].toString());

			} else
				cvl.put(column[i], value[i].toString());
		}

		return sqldb.insert(table, null, cvl);
	}

	public boolean update(String table, String[] column, String[] value,
			String parameter) {
		ContentValues cvl = new ContentValues();
		int count = column.length;
		for (int i = 0; i < count; i++) {
			String str = column[i];
			int counts = column[i].length();

			if (str.length() >= 4) {
				str = str.substring(0, 4);

				if (str.equals("int:")) {
					String cl = column[i].substring(4, counts);
					cvl.put(cl, Integer.parseInt(value[i]));
				} else
					cvl.put(column[i], value[i].toString());
			} else
				cvl.put(column[i], value[i].toString());
		}

		return sqldb.update(table, cvl, parameter, null) > 0;
	}

	public boolean delete(String table, String parameter) {
		return sqldb.delete(table, parameter, null) > 0;
	}

	public Cursor selectAll(String table, String[] columns) {
		return sqldb.query(table, columns, null, null, null, null, null);
	}

	// ###
	// ### insert table
	// ###

	public long insertgroup(String groupname) {
		String[] column = { "groupname" };
		String[] value = { groupname };
		return insert(TBGroup, column, value);
	}

	public int insertmembership(String membername, int groupid) {
		String[] column = { "membername", "int:groupid" };
		String[] value = { membername, "" + groupid };
		insert(TBMemberShip, column, value);

		Cursor mcursor = sqldb.rawQuery("select membershipid from "
				+ TBMemberShip + " order by membershipid DESC", null);
		mcursor.moveToNext();
		int membershipid = mcursor.getInt(0);
		return membershipid;
	}

	public int insertwork(int parentid, int membershipid, String content,
			String startdate, String enddate, int morning, String starttime,
			String endtime, int remindful, String repeat) {
		String[] column = { "int:parentid", "int:membershipid", "content",
				"startdate", "enddate", "int:morning", "starttime", "endtime",
				"int:remindful", "repeat" };

		String[] value = { "" + parentid, "" + membershipid, content,
				startdate, enddate, "" + morning, starttime, endtime,
				"" + remindful, "" + repeat };
		insert(TBWork, column, value);

		Cursor mcursor = sqldb.rawQuery("select workid from " + TBWork
				+ " order by workid DESC", null);
		mcursor.moveToNext();
		int workid = mcursor.getInt(0);
		mcursor.close();
		return workid;
	}

	//
	// #### update table
	//
	public boolean updategroup(String groupname, int groupid) {
		String[] column = { "groupname" };
		String[] value = { groupname };
		return update(TBGroup, column, value, "groupid=" + groupid);
	}

	public boolean updatemembership(String membername, int groupid,
			int membershipid) {
		String[] column = { "membername", "int:groupid" };
		String[] value = { membername, "" + groupid };
		return update(TBMemberShip, column, value, "membershipid="
				+ membershipid);
	}

	// public boolean updatework(int parentid, int membershipid, String content,
	// String startdate, String enddate, int morning, String starttime,
	// String endtime, int remindful, int repeat, int workid) {
	//
	// String[] column = { "int:parentid", "int:membershipid", "content",
	// "startdate", "enddate", "int:morning", "starttime", "endtime",
	// "int:remindful", "int:repeat" };
	//
	// String[] value = { "" + parentid, "" + membershipid, content,
	// startdate, enddate, "" + morning, starttime, endtime,
	// "" + remindful, "" + repeat };
	//
	// return update(TBWork, column, value, "workid=" + workid);
	// }
	public boolean updatework(String[] column, String[] value, int workid) {
		return update(TBWork, column, value, "workid=" + workid);
	}

	//
	// #### delete table
	//
	public boolean deletegroup(int groupid) {
		return delete(TBGroup, "groupid=" + groupid);
	}

	public boolean deletemembership(int membershipid) {
		return delete(TBMemberShip, "membershipid=" + membershipid);
	}

	public boolean deletework(int workid) {
		return delete(TBWork, "workid=" + workid);
	}

	//
	// Các câu query trên bảng GiaoTrinh
	//
	public ArrayList<Group> listgroup() {
		ArrayList<Group> groups = new ArrayList<Group>();

		int i = 0;
		Cursor cursorgroup = sqldb.query(TBGroup, null, null, null, null, null,
				null);
		if (cursorgroup.moveToFirst()) {
			do {
				Cursor cs = sqldb.query(TBMemberShip, null, "groupid="
						+ cursorgroup.getInt(0), null, null, null, null);

				Group group = new Group(cursorgroup.getInt(0),
						cursorgroup.getString(1));

				group.setNumberMember(cs.getCount());
				groups.add(i, group);
				i++;
				cs.close();
			} while (cursorgroup.moveToNext());
		}
		cursorgroup.close();
		return groups;
	}

	public int Ismembership(String membername) {
		Cursor mcursor = sqldb.query(TBMemberShip, null, "membername='"
				+ membername + "'", null, null, null, null);
		int membershipid = 0;
		if (mcursor.getCount() > 0) {
			mcursor.moveToNext();
			membershipid = mcursor.getInt(0);

		}
		return membershipid;
	}

	public int Iswork(String membername,  String content) {
		String sql = "select workid from " + TBMemberShip + "," + TBWork
				+ " where " + TBMemberShip + ".membershipid=" + TBWork
				+ ".membershipid and membername='" + membername
				+ "' and content='" + content + "'";
		Log.i("", "" + sql);
		Cursor mcursor = sqldb.rawQuery(sql, null);
		int workid = 0;
		if (mcursor.getCount() > 0) {
			mcursor.moveToNext();
			workid = mcursor.getInt(0);
			mcursor.close();
		}
		return workid;
	}
	
	// public int getNumberNV(int id_group){
	// int count;
	// return count;
	// }
	public ArrayList<HanhChinhObject> listhanhchinh(int groupid) {
		ArrayList<HanhChinhObject> hcobj = new ArrayList<HanhChinhObject>();
		String column = "membername,startdate,morning,starttime,endtime,content,remindful,repeat";
		String sql = "select " + column + " from " + TBMemberShip + ","
				+ TBWork + " where " + TBMemberShip + ".membershipid=" + TBWork
				+ ".membershipid and groupid=" + groupid;
		Cursor mcursor = sqldb.rawQuery(sql, null);

		int i = 0;
		if (mcursor.moveToFirst()) {
			do {
				Log.i("djgfsdgfidsigfidig","HCNAME:"+mcursor.getString(0));
				HanhChinhObject hc = new HanhChinhObject(mcursor.getString(0),
						mcursor.getString(1), mcursor.getString(2),
						mcursor.getString(3), mcursor.getString(4),
						mcursor.getString(5), mcursor.getString(6),
						mcursor.getString(7));
				hcobj.add(i, hc);
				i++;
			} while (mcursor.moveToNext());
		}
		mcursor.close();
		return hcobj;
	}

	public ArrayList<Memebership> listmember() {
		ArrayList<Memebership> memberships = new ArrayList<Memebership>();
		Cursor cursor = sqldb.query(TBMemberShip, null, null, null, null, null,
				null);

		return memberships;
	}

}
