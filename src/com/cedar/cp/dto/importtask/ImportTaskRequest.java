package com.cedar.cp.dto.importtask;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ImportTaskRequest extends AbstractTaskRequest implements TaskRequest {

	private String mTaskName;
	private List mMessages;
	private String mTableName;
	 
    public ImportTaskRequest(String taskName, String externalSystemVisId) {
		this.mTaskName = taskName;
		this.mMessages = new ArrayList();
		this.mMessages.add("Processing " + taskName + "...");
		this.mTableName = externalSystemVisId;
	}

	public String getIdentifier() {
		return this.mTaskName;
	}

	public List toDisplay() {
		return this.mMessages;
	}

	public String getService() {
		return "com.cedar.cp.ejb.base.async.importtask.ImportTask";
	}

    public String getmTableName() {
        return mTableName;
    }

    public void setmTableName(String mTableName) {
        this.mTableName = mTableName;
    }
	
	
}
