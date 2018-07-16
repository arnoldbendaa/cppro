package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class LoggedInUsersRequest extends AbstractTaskRequest implements TaskRequest {

	private String mTaskName;
	private List mMessages;

	public LoggedInUsersRequest(String taskName) {
		this.mTaskName = taskName;
		this.mMessages = new ArrayList();
		this.mMessages.add("Processing " + taskName + "...");
	}

	public String getIdentifier() {
		return this.mTaskName;
	}

	public List toDisplay() {
		return this.mMessages;
	}

	public String getService() {
		return "com.cedar.cp.ejb.base.async.logoutusers.LoggedInUsers";
	}
}
