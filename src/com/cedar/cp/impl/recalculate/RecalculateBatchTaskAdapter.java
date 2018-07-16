package com.cedar.cp.impl.recalculate;

import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.recalculate.RecalculateBatchTask;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.impl.recalculate.RecalculateBatchTaskEditorSessionImpl;

import java.util.List;

public class RecalculateBatchTaskAdapter implements RecalculateBatchTask {

	private RecalculateBatchTaskImpl mEditorData;
	private RecalculateBatchTaskEditorSessionImpl mEditorSessionImpl;

	public RecalculateBatchTaskAdapter(RecalculateBatchTaskEditorSessionImpl e, RecalculateBatchTaskImpl editorData) {
		this.mEditorData = editorData;
		this.mEditorSessionImpl = e;
	}

	public void setPrimaryKey(Object key) {
		this.mEditorData.setPrimaryKey(key);
	}

	protected RecalculateBatchTaskEditorSessionImpl getEditorSessionImpl() {
		return this.mEditorSessionImpl;
	}

	protected RecalculateBatchTaskImpl getEditorData() {
		return this.mEditorData;
	}

	public Object getPrimaryKey() {
		return this.mEditorData.getPrimaryKey();
	}

	public List<DataEntryProfileRef> getRecalculateBatchTaskForms() {
		return this.mEditorData.getRecalculateBatchTaskForms();
	}
	
	public List<DataEntryProfileRef> getSelectedRecalculateBatchTaskForms() {
		return this.mEditorData.getSelectedRecalculateBatchTaskForms();
	}
	
	public void setRecalculateBatchTaskForms(List<DataEntryProfileRef> list) {
		this.mEditorData.setRecalculateBatchTaskForms(list);
	}
	
	public void setSelectedRecalculateBatchTaskForms(List<DataEntryProfileRef> list) {
		this.mEditorData.setSelectedRecalculateBatchTaskForms(list);
	}

	public int getBudgetCycleId() {
		return this.mEditorData.getBudgetCycleId();
	}
	
	public void setBudgetCycleId(int budgetCycleId) {
		this.mEditorData.setBudgetCycleId(budgetCycleId);
	}

	public int getModelId() {
		return this.mEditorData.getModelId();
	}

	public List<RecalculateBatchTaskAssignment> getRecalculateBatchTaskAssignments() {
		return this.mEditorData.getRecalculateBatchTaskAssignments();
	}
	
	public List<Integer> getRecalculateBatchTaskRespArea() {
		return this.mEditorData.getRecalculateBatchTaskRespArea();
	}
	
	public int getUserId() {
		return this.mEditorData.getUserId();
	}
	
	public void setUserId(int userId) {
		this.mEditorData.setUserId(userId);
	}
	
	public String getIdentifier() {
		return mEditorData.getIdentifier();
	}
	
	public String getDescription() {
		return mEditorData.getDescription();
	}
	
	public void setIdentifier(String identifier) {
		mEditorData.setIdentifier(identifier);
	}
	
	public void setDescription(String description) {
		mEditorData.setDescription(description);
	}
}
