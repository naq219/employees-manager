/**
 * 
 */
package haivu.qlnv.task;

import haivu.qlnv.HomeActivity;
import haivu.qlnv.utils.Mutils;
import haivu.qlnv.utils.Sdata;

import java.util.ArrayList;

import android.content.Context;

import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.net.BaseNetSupport;

/**
 * @author NAQ219
 * @version 1.0
 */
public class TaskUser1 extends BaseTask implements TaskType {

	public TaskUser1(TaskListener taskListener, int taskType, ArrayList<?> list, Context context) {
		super(taskListener, taskType, list, context);
	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {
		if (taskListener == null)
			return TASK_FAILED;

		switch (taskType) {

		case TASK_UPDATE_DATA:
			Mutils.updateData();
			return TASK_DONE;
			
		case TASK_SEARCH:
			dataReturn = Mutils.search(Sdata.hmData.get(HomeActivity.curentGroup), Sdata.key_search);
			return TASK_DONE;

		default:

			break;
		}

		if ((dataReturn != null && dataReturn.size() > 0)) {
			return TASK_DONE;
		}
		return TASK_FAILED;

	}



}
