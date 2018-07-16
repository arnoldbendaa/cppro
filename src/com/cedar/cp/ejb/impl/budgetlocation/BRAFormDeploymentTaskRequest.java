package com.cedar.cp.ejb.impl.budgetlocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;

public class BRAFormDeploymentTaskRequest extends AbstractTaskRequest implements TaskRequest {

	private Map<Integer, List<Integer>> mNewUsers;
	private EntityRef mModelRef;

	public List toDisplay() {
		List l = new ArrayList();
		l.add("Structure Ids to process " + getNewUsers().size());

		return l;
	}

	public String getService() {
		return "com.cedar.cp.ejb.base.async.formdeployment.BRADeploymentTask";
	}

	public Map<Integer, List<Integer>> getNewUsers() {
		return mNewUsers;
	}

	public void setNewUsers(Map<Integer, List<Integer>> newUsers) {
		mNewUsers = newUsers;
	}

	public EntityRef getModelRef() {
		return mModelRef;
	}

	public void setModelRef(EntityRef modelRef) {
		mModelRef = modelRef;
	}
}