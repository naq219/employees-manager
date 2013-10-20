/**
 * 
 */
package haivu.qlnv.task;

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

		case TASK_GET_DATA:
			
			

		default:

			break;
		}

		if ((dataReturn != null && dataReturn.size() > 0)) {
			return TASK_DONE;
		}
		return TASK_FAILED;

	}



}
