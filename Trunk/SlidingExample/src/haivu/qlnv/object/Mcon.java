package haivu.qlnv.object;

import android.os.Environment;

public class Mcon {
	public static String dbPath = Environment.getExternalStorageDirectory() + "/naq";
	public interface Group{
		
		public static final String NHOM_HANH_CHINH = "0";
		public static final String NHOM_LAP_DAT = "1";
		public static final String NHOM_THUC_TAP ="2";
		public static final String NHOM_KINH_DOANH = "3";
		public static final String NHOM_KE_TOAN = "4";
		public static final String NHOM_HOP_DONG = "5";
		
	}
	
}