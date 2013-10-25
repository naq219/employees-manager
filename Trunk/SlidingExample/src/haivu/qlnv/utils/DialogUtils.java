package haivu.qlnv.utils;

import haivu.qlnv.InsertHCActivity;
import haivu.qlnv.InsertNVActivity;
import haivu.qlnv.R;
import haivu.qlnv.database.DbSupport;
import haivu.qlnv.object.Empl;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

public class DialogUtils implements Mcon.Group {

	public static void showDialogChoose(final Context ct) {
		final Dialog dl;
		dl = new Dialog(ct);
		dl.setTitle("Chọn loại nhân viên: ");
		dl.setContentView(R.layout.dialog_select_loainv);
		final RadioButton radHC = (RadioButton) dl.findViewById(R.id.radHC);
		final RadioButton radHD = (RadioButton) dl.findViewById(R.id.radHD);
		final RadioButton radKD = (RadioButton) dl.findViewById(R.id.radKD);
		final RadioButton radKT = (RadioButton) dl.findViewById(R.id.radKT);
		final RadioButton radLD = (RadioButton) dl.findViewById(R.id.radLD);
		final RadioButton radTT = (RadioButton) dl.findViewById(R.id.radTT);
		Button btnOk_dialog = (Button) dl.findViewById(R.id.btnOK_dialog);
		Button btnCancel_dialog = (Button) dl.findViewById(R.id.btnCancel_dialog);
		btnOk_dialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dl.dismiss();
				if (radHC.isChecked()) {
					// choose.onChoose(Mcon.Group.NHOM_HANH_CHINH);
					// Intent it=;
					((Activity) ct).startActivity(new Intent(ct, InsertHCActivity.class));
				}
				if (radHD.isChecked()) {
					Intent it = new Intent(ct, InsertNVActivity.class);
					it.putExtra("group", NHOM_HOP_DONG);
					ct.startActivity(it);
				}
				if (radKD.isChecked()) {
					Intent it = new Intent(ct, InsertNVActivity.class);
					it.putExtra("group", NHOM_KINH_DOANH);
					ct.startActivity(it);
				}
				if (radKT.isChecked()) {
					Intent it = new Intent(ct, InsertNVActivity.class);
					it.putExtra("group", NHOM_KE_TOAN);
					ct.startActivity(it);
				}
				if (radLD.isChecked()) {
					Intent it = new Intent(ct, InsertNVActivity.class);
					it.putExtra("group", NHOM_LAP_DAT);
					ct.startActivity(it);
				}

				if (radTT.isChecked()) {
					Intent it = new Intent(ct, InsertNVActivity.class);
					it.putExtra("group", NHOM_THUC_TAP);
					ct.startActivity(it);
				}

			}
		});
		btnCancel_dialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dl.dismiss();
			}
		});

		dl.show();
	}

	public static OnClickListener datePickerListener(final IdialogDate listener, final Context context) {

		OnClickListener ls = new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Calendar calendar = Calendar.getInstance();
				DatePickerDialog datePickerDialog;

				datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
						String text_tv, text_value;

						Calendar c = Calendar.getInstance();
						c.set(year, monthOfYear, dayOfMonth);

						if (c.get(Calendar.DAY_OF_WEEK) == 1) {
							text_tv = "Chủ nhật: " + Mutils.addZero(dayOfMonth) + "/" + Mutils.addZero(monthOfYear + 1) + "/" + year;
						} else {
							text_tv = "Thứ " + c.get(Calendar.DAY_OF_WEEK) + ": " + Mutils.addZero(dayOfMonth) + "/" + Mutils.addZero(monthOfYear + 1) + "/" + year;
						}

						text_value = year + "-" + Mutils.addZero(monthOfYear + 1) + "-" + Mutils.addZero(dayOfMonth);
						listener.onSet(text_tv, text_value);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.show();

			}
		};
		return ls;
	}

	public static void confirmEditDelete(final Context context,  BaseObject oj) {
		final String row_id=oj.get(Empl.ROW_ID);
		Mlog.T("oj.get(Empl.ROW_ID)="+oj.get(Empl.ROW_ID));
		final BaseObject oj1=oj;
		final Dialog dl = new Dialog(context, android.R.style.Theme_Dialog);
		dl.getWindow();
		dl.setCanceledOnTouchOutside(false);
		dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dl.setCanceledOnTouchOutside(true);
		dl.setContentView(R.layout.dialog_edit_delete);
		// dlConfig.getWindow().setBackgroundDrawable(
		// new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// dlConfig.getWindow().setGravity(Gravity.CENTER);

		dl.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Mutils.startActivity(context, InsertHCActivity.class, oj1, 1);

				dl.dismiss();

			}
		});

		dl.findViewById(R.id.delete).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (DbSupport.deleteRowInTable(DbTable.EMPL, Empl.ROW_ID, row_id)){
					Mutils.updateData();
					Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
				}
				else Toast.makeText(context, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
				dl.dismiss();

			}
		});

		dl.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dl.dismiss();
			}
		});

		dl.show();
	}

}
