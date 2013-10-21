package haivu.qlnv.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import haivu.qlnv.R;
import haivu.qlnv.utils.Mcon;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Utils;

public class MenuListviewAdapter extends ArrayAdapter<String> {

	private final LayoutInflater mInflater;
	private final int mLayoutRes;
	private Context context;
	HashMap<Integer, ArrayList<BaseObject>> hmData;

	public MenuListviewAdapter(Context context, int textViewResourceId, String[] list, HashMap<Integer, ArrayList<BaseObject>> hmData) {
		super(context, textViewResourceId, list);
		this.context = context;
		this.hmData = hmData;
		this.mLayoutRes = textViewResourceId;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	static class ViewHolder {
		LinearLayout bg;
		ImageView icon;
		TextView txtName;
		int position;
		TextView tv_count;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(mLayoutRes, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.txtName = (TextView) convertView.findViewById(R.id.menu_tv);
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.menu_icon);
			viewHolder.bg = (LinearLayout) convertView.findViewById(R.id.menu_item_bg);
			viewHolder.tv_count = (TextView) convertView.findViewById(R.id.menu_count);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String item = getItem(position);
		if (item != null) {
			viewHolder.txtName.setText("" + item);
			ViewTreeObserver vto = viewHolder.txtName.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					ViewTreeObserver obs = viewHolder.txtName.getViewTreeObserver();
					obs.removeGlobalOnLayoutListener(this);
					if (viewHolder.txtName.getLineCount() > 2) {
						int lineEndIndex = viewHolder.txtName.getLayout().getLineEnd(1);
						String text = viewHolder.txtName.getText().subSequence(0, lineEndIndex - 2) + "...";
						viewHolder.txtName.setText(text);
					}

				}
			});
			int icon = 0;

			int getGroup=0;
				switch (position) {
				case 0: //hanh chinh
					getGroup=Mcon.Group.NHOM_HANH_CHINH;
					break;
				case 1:
					getGroup=Mcon.Group.NHOM_THUC_TAP;
					break;
				case 2:
					getGroup=Mcon.Group.NHOM_LAP_DAT;
					break;
				case 3:
					getGroup=Mcon.Group.NHOM_KINH_DOANH;
					break;
				case 4:
					getGroup=Mcon.Group.NHOM_KE_TOAN;
					break;
				case 5:
					getGroup=Mcon.Group.NHOM_HOP_DONG;
					break;
				default:
					 getGroup=Mcon.Group.NHOM_HANH_CHINH;
					break;
				}
			if(hmData!=null){
				int size=hmData.get(getGroup).size();
				viewHolder.tv_count.setText(size+"");
			}
				

			viewHolder.icon.setBackgroundResource(icon);
			int lastPositon = 100;
			try {
				lastPositon = Integer.parseInt(Utils.getStringSPR(Mcon.spr.GROUP, this.context));
			} catch (Exception e) {
				// TODO: handle exception
				lastPositon = 100;
			}
			if (lastPositon != position)
				viewHolder.bg.setBackgroundResource(R.drawable.menu_item_background);
			else
				viewHolder.bg.setBackgroundColor(this.context.getResources().getColor(R.color.blue));
		}
		return convertView;
	}

}