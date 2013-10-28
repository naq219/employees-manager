package haivu.qlnv.utils;

import android.os.Environment;

public class Mcon {
	public static String dbPath = Environment.getExternalStorageDirectory() + "/naq";
	public interface Group{
		
		public static final int NHOM_HANH_CHINH = 0;
		public static final int NHOM_THUC_TAP =1;
		public static final int NHOM_LAP_DAT = 2;
		public static final int NHOM_KINH_DOANH =3;
		public static final int NHOM_KE_TOAN = 4;
		public static final int NHOM_HOP_DONG = 5;
		public static final int[] KEYS = {0,1,2,3,4,5};
	}
	public static class spr{
		public static String GROUP="spr group";
	}
	
	public static String[] nameGroup={"NHÂN VIÊN HÀNH CHÍNH","NHÂN VIÊN THỰC TẬP","NHÂN VIÊN LẮP ĐẶT","NHÂN VIÊN KINH DOANH",
		"NHÂN VIÊN KẾ TOÁN","NHÂN VIÊN HỢP ĐỒNG"};
	public static String dateFormat="yyyy-MM-dd";
	public static String dateTimeFormat="yyy-MM-dd.HH:mm";
	
}