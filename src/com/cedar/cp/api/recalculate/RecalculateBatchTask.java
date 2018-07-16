package com.cedar.cp.api.recalculate;

import java.util.List;

import com.cedar.cp.api.user.DataEntryProfileRef;

public interface RecalculateBatchTask {

	Object getPrimaryKey();

	int getModelId();

	int getBudgetCycleId();
	
	void setBudgetCycleId(int budgetCycleId);
	
	List<RecalculateBatchTaskAssignment> getRecalculateBatchTaskAssignments();
	
	List<Integer> getRecalculateBatchTaskRespArea();

	List<DataEntryProfileRef> getRecalculateBatchTaskForms();
	
	List<DataEntryProfileRef> getSelectedRecalculateBatchTaskForms();
	
	void setRecalculateBatchTaskForms(List<DataEntryProfileRef> list);
	
	void setSelectedRecalculateBatchTaskForms(List<DataEntryProfileRef> list);
	
	int getUserId();
	
	void setUserId(int userId);
	
	public String getIdentifier();
	
	public String getDescription();
	
	public void setIdentifier(String identifier);
	
	public void setDescription(String description);
}
