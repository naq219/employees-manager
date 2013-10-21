package haivu.qlnv.utils;

import haivu.qlnv.object.AdapterOj;
import haivu.qlnv.object.Empl;
import haivu.qlnv.object.HcOj;
import haivu.qlnv.object.NvOj;

import java.util.ArrayList;
import java.util.HashMap;

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

}
