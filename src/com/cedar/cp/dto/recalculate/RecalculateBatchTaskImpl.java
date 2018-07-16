package com.cedar.cp.dto.recalculate;

import com.cedar.cp.api.recalculate.RecalculateBatchTask;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecalculateBatchTaskImpl implements RecalculateBatchTask, Serializable, Cloneable {

	private static final long serialVersionUID = 4745265978306465526L;
	private Object mPrimaryKey;
	private int mModelId;
	private int mBudgetCycleId;
	private int mUserId;
	private String mIdentifier;
	private String mDescription;
	private List<RecalculateBatchTaskAssignment> mRecalculateBatchTaskAssignments;
	private List<Integer> mRecalculateBatchTaskRespArea;
	private List<DataEntryProfileRef> mRecalculateBatchTaskForms;
	private List<DataEntryProfileRef> selectedRecalculateBatchTaskForms;


	public RecalculateBatchTaskImpl(Object paramKey) {
		this.mPrimaryKey = paramKey;
	}

	public Object getPrimaryKey() {
		return this.mPrimaryKey;
	}

	public void setPrimaryKey(Object paramKey) {
		this.mPrimaryKey = (RecalculateBatchTaskPK) paramKey;
	}

	public void setRecalculateBatchTaskAssignments(List<RecalculateBatchTaskAssignment> recalculateBatchTaskAssignments) {
		this.mRecalculateBatchTaskAssignments = recalculateBatchTaskAssignments;
	}

	public List<RecalculateBatchTaskAssignment> getRecalculateBatchTaskAssignments() {
		if (mRecalculateBatchTaskAssignments == null) {
			mRecalculateBatchTaskAssignments = new ArrayList<RecalculateBatchTaskAssignment>();
		}
		return this.mRecalculateBatchTaskAssignments;
	}

	public void addRecalculateBatchTaskAssignmentItem(RecalculateBatchTaskAssignment recalculateBatchTaskAssignment) {
		this.mRecalculateBatchTaskAssignments.add(recalculateBatchTaskAssignment);
	}

	public RecalculateBatchTaskAssignment getRecalculateBatchTaskAssignmentItem(Object key) {
		if (key != null) {
			Iterator<RecalculateBatchTaskAssignment> iter = this.mRecalculateBatchTaskAssignments.iterator();

			while (iter.hasNext()) {
				RecalculateBatchTaskAssignment item = (RecalculateBatchTaskAssignment) iter.next();
				if (key.equals(item.getPrimaryKey())) {
					return item;
				}
			}
		}

		return new RecalculateBatchTaskAssignmentImpl((Object) null);
	}

	public List<DataEntryProfileRef> getRecalculateBatchTaskForms() {
		return mRecalculateBatchTaskForms;
	}
	
	public List<DataEntryProfileRef> getSelectedRecalculateBatchTaskForms() {
		if (selectedRecalculateBatchTaskForms == null) {
			selectedRecalculateBatchTaskForms = new ArrayList<DataEntryProfileRef>();
		}
		return selectedRecalculateBatchTaskForms;
	}

	public void setRecalculateBatchTaskForms(List<DataEntryProfileRef> recalculateBatchTaskForms) {
		this.mRecalculateBatchTaskForms = recalculateBatchTaskForms;
	}
	
	public void setSelectedRecalculateBatchTaskForms(List<DataEntryProfileRef> recalculateBatchTaskForms) {
		this.selectedRecalculateBatchTaskForms = recalculateBatchTaskForms;
	}

	public void addRecalculateBatchTaskFormItem(DataEntryProfileRef xmlRef) {
		if (selectedRecalculateBatchTaskForms == null) {
			selectedRecalculateBatchTaskForms = new ArrayList<DataEntryProfileRef>();
		}
		this.selectedRecalculateBatchTaskForms.add(xmlRef);		
	}

	public void removeRecalculateBatchTaskFormItem(DataEntryProfileRef xmlRef) {
		if (selectedRecalculateBatchTaskForms == null) {
			selectedRecalculateBatchTaskForms = new ArrayList<DataEntryProfileRef>();
		}
		this.selectedRecalculateBatchTaskForms.remove(xmlRef);
	}

	public void clearRecalculateBatchTaskForm() {
		if (selectedRecalculateBatchTaskForms == null) {
			selectedRecalculateBatchTaskForms = new ArrayList<DataEntryProfileRef>();
		} else {
			this.selectedRecalculateBatchTaskForms.clear();
		}
	}

	public void clearRecalculateBatchTaskAssignments() {
		if (mRecalculateBatchTaskAssignments == null) {
			mRecalculateBatchTaskAssignments = new ArrayList<RecalculateBatchTaskAssignment>();
		} else {
			this.mRecalculateBatchTaskAssignments.clear();
		}
	}

	public boolean containsRecalculateBatchTaskFormItem(DataEntryProfileRef xmlRef) {
		if (mRecalculateBatchTaskForms == null) {
			mRecalculateBatchTaskForms = new ArrayList<DataEntryProfileRef>();
		}
		return this.mRecalculateBatchTaskForms.contains(xmlRef);
	}

	public int getModelId() {
		return mModelId;
	}

	public void setModelId(int modelId) {
		this.mModelId = modelId;
	}

	public int getBudgetCycleId() {
		return mBudgetCycleId;
	}

	public void setBudgetCycleId(int budgetCycleId) {
		this.mBudgetCycleId = budgetCycleId;
	}

	public List<Integer> getRecalculateBatchTaskRespArea() {
		if (mRecalculateBatchTaskRespArea == null) {
			mRecalculateBatchTaskRespArea = new ArrayList<Integer>();
		}
		return mRecalculateBatchTaskRespArea;
	}

	public void setRecalculateBatchTaskRespArea(List<Integer> mRecalculateBatchTaskRespArea) {
		this.mRecalculateBatchTaskRespArea = mRecalculateBatchTaskRespArea;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int mUserId) {
		this.mUserId = mUserId;
	}
	
	public String getIdentifier() {
		return mIdentifier;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public void setIdentifier(String identifier) {
		mIdentifier = identifier;
	}
	
	public void setDescription(String description) {
		mDescription = description;
	}
}
