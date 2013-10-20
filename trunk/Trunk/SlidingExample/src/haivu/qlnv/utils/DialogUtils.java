package haivu.qlnv.utils;

import haivu.qlnv.InsertHCActivity;
import haivu.qlnv.InsertNVActivity;
import haivu.qlnv.R;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;

public class DialogUtils  {

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
					//choose.onChoose(Mcon.Group.NHOM_HANH_CHINH);
					//Intent it=;
					((Activity)ct).startActivity(new Intent(ct, InsertHCActivity.class));
				}
				if (radHD.isChecked()) {
					
					//choose.onChoose(Mcon.Group.NHOM_HOP_DONG);
				}
				if (radKD.isChecked()) {
					//choose.onChoose(Mcon.Group.NHOM_KINH_DOANH);
				}
				if (radKT.isChecked()) {
					//choose.onChoose(Mcon.Group.NHOM_KE_TOAN);
				}
				if (radLD.isChecked()) {
					((Activity)ct).startActivity(new Intent(ct, InsertNVActivity.class));
					//choose.onChoose(Mcon.Group.NHOM_LAP_DAT);
				}
				
				if (radTT.isChecked()) {
					//choose.onChoose(Mcon.Group.NHOM_THUC_TAP);
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
	
	public static OnClickListener datePickerListener(final IdialogDate listener,final Context context){
		 
		  
		OnClickListener ls=new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				 
				 Calendar calendar=Calendar.getInstance();
				 DatePickerDialog datePickerDialog;
				
				datePickerDialog=new DatePickerDialog(context, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
						 String text_tv,text_value;
						 
						Calendar c = Calendar.getInstance();
						c.set(year, monthOfYear, dayOfMonth);

						if (c.get(Calendar.DAY_OF_WEEK) == 1) {
							text_tv="Chủ nhật: " + Mutils.addZero(dayOfMonth) + "/" + Mutils.addZero(monthOfYear + 1) + "/" + year;
						} else {
							text_tv="Thứ " + c.get(Calendar.DAY_OF_WEEK) + ": " + Mutils.addZero(dayOfMonth) + "/" + Mutils.addZero(monthOfYear + 1) + "/" + year;
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

}
