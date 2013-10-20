package haivu.qlnv.utils;

import android.os.Environment;

public class Mcon {
	public static String dbPath = Environment.getExternalStorageDirectory() + "/naq";
	public interface Group{
		
		public static final int NHOM_HANH_CHINH = 0;
		public static final int NHOM_LAP_DAT = 1;
		public static final int NHOM_THUC_TAP =2;
		public static final int NHOM_KINH_DOANH =3;
		public static final int NHOM_KE_TOAN = 4;
		public static final int NHOM_HOP_DONG = 5;
		public static final int[] KEYS = {0,1,2,3,4,5};
	}
	public static class spr{
		public static String GROUP="spr group";
	}
	
	
	
}