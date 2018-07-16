package com.cedar.cp.dto.recalculate;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class RecalculateBatchTaskRequest extends AbstractTaskRequest implements TaskRequest {

	private static final long serialVersionUID = 2738631961506041770L;
	private String mIdentifier;
	private List<String> mMessages;
	
	private int mUserId;
	private int mModelId;
	private int mBudgetCycleId;
	private List<DataEntryProfileRef> mDataEntryProfiles;
	private List<Integer> mBudgetRespAreas;


	public RecalculateBatchTaskRequest(String identifier, int userId, int modelId, int budgetCycleId, List<DataEntryProfileRef> dataEntryProfiles, List<Integer> budgetRespAreas) {
		this.mIdentifier = identifier;
		this.mMessages = new ArrayList<String>();
		this.mMessages.add("Processing RecalculateBatchTask...");

		this.mUserId = userId;
		this.mModelId = modelId;
		this.mBudgetCycleId = budgetCycleId;
		this.mDataEntryProfiles = dataEntryProfiles;
		this.mBudgetRespAreas = budgetRespAreas;
	}

	public String getIdentifier() {
		return this.mIdentifier;
	}

	public List<String> toDisplay() {
		return this.mMessages;
	}

	public String getService() {
		return "com.cedar.cp.ejb.base.async.recalculate.RecalculateBatchTask";
	}

	public int getModelId() {
		return mModelId;
	}

	public int getBudgetCycleId() {
		return mBudgetCycleId;
	}

	public int getUserId() {
		return mUserId;
	}

	public List<DataEntryProfileRef> getDataEntryProfiles() {
		return mDataEntryProfiles;
	}

	public List<Integer> getBudgetRespAreas() {
		return mBudgetRespAreas;
	}

}
