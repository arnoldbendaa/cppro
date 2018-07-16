package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskRef;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.recalculate.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class RecalculateBatchTaskEVO implements Serializable {

	private transient RecalculateBatchTaskPK mPK;
	private int mRecalculateBatchTaskId;
	private int mModelId;
	private int mBudgetCycleId;
	private int mUserId;
	private List<DataEntryProfileRef> mRecalculateBatchTaskForms;
	private List<RecalculateBatchTaskAssignment> mRecalculateBatchTaskAssignments;
	private List<Integer> mRecalculateBatchTaskRespArea;
	private String mIdentifier;
	private String mDescription;
	private boolean mModified;

	public RecalculateBatchTaskEVO() {
	}

	public RecalculateBatchTaskEVO(int newRecalculateBatchTaskId, int newModelId, int newBudgetCycleId, int newUserId, String newIdentifier, String newDescription) {
		this.mRecalculateBatchTaskId = newRecalculateBatchTaskId;
		this.mModelId = newModelId;
		this.mBudgetCycleId = newBudgetCycleId;
		this.mUserId = newUserId;
		this.mIdentifier = newIdentifier;
		this.mDescription = newDescription;
	}

	public void setRecalculateBatchTaskForms(List<DataEntryProfileRef> forms) {
		mRecalculateBatchTaskForms = forms;
	}

	public void setRecalculateBatchTaskRespArea(List<Integer> theseItems) {
		this.mRecalculateBatchTaskRespArea = theseItems;
	}

	public void setRecalculateBatchTaskAssignments(List<RecalculateBatchTaskAssignment> theseItems) {
		this.mRecalculateBatchTaskAssignments = theseItems;
	}

	public RecalculateBatchTaskPK getPK() {
		if (this.mPK == null) {
			this.mPK = new RecalculateBatchTaskPK(this.mRecalculateBatchTaskId);
		}

		return this.mPK;
	}

	public boolean isModified() {
		return this.mModified;
	}

	public int getRecalculateBatchTaskId() {
		return this.mRecalculateBatchTaskId;
	}

	public int getModelId() {
		return this.mModelId;
	}

	public int getBudgetCycleId() {
		return this.mBudgetCycleId;
	}

	public int getUserId() {
		return this.mUserId;
	}

	public void setRecalculateBatchTaskId(int newRecalculateBatchTaskId) {
		if (this.mRecalculateBatchTaskId != newRecalculateBatchTaskId) {
			this.mModified = true;
			this.mRecalculateBatchTaskId = newRecalculateBatchTaskId;
			this.mPK = null;
		}
	}

	public void setBudgetCycleId(int newBudgetCycleId) {
		if (this.mBudgetCycleId != newBudgetCycleId) {
			this.mModified = true;
			this.mBudgetCycleId = newBudgetCycleId;
		}
	}

	public void setUserId(int newUserId) {
		if (this.mUserId != newUserId) {
			this.mModified = true;
			this.mUserId = newUserId;
		}
	}

	public void setModelId(int newModelId) {
		if (this.mModelId != newModelId) {
			this.mModified = true;
			this.mModelId = newModelId;
		}
	}

	public void setDetails(RecalculateBatchTaskEVO newDetails) {
		this.setRecalculateBatchTaskId(newDetails.getRecalculateBatchTaskId());
		this.setModelId(newDetails.getModelId());
		this.setBudgetCycleId(newDetails.getBudgetCycleId());
		this.setUserId(newDetails.getUserId());
		this.setRecalculateBatchTaskForms(newDetails.getRecalculateBatchTaskForms());
		this.setRecalculateBatchTaskRespArea(newDetails.getRecalculateBatchTaskRespArea());
		this.setUserId(newDetails.getUserId());
		this.setIdentifier(newDetails.getIdentifier());
		this.setDescription(newDetails.getDescription());
	}

	public RecalculateBatchTaskEVO deepClone() {
		RecalculateBatchTaskEVO cloned = null;

		try {
			ByteArrayOutputStream e = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(e);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			cloned = (RecalculateBatchTaskEVO) ois.readObject();
			ois.close();
			return cloned;
		} catch (Exception var6) {
			throw new RuntimeException(var6);
		}
	}

	public void prepareForInsert() {
		boolean newKey = false;
		if (this.mRecalculateBatchTaskId > 0) {
			newKey = true;
			this.mRecalculateBatchTaskId = 0;
		} else if (this.mRecalculateBatchTaskId < 1) {
			newKey = true;
		}
	}

	public int getInsertCount(int startCount) {
		int returnCount = startCount;
		if (this.mRecalculateBatchTaskId < 1) {
			returnCount = startCount + 1;
		}
		return mRecalculateBatchTaskForms.size();
	}

	public int assignNextKey(int startKey) {
		int nextKey = startKey;
		if (this.mRecalculateBatchTaskId < 1) {
			this.mRecalculateBatchTaskId = startKey;
			nextKey = startKey + 1;
		}
		return nextKey;
	}

	public List<DataEntryProfileRef> getRecalculateBatchTaskForms() {
		return this.mRecalculateBatchTaskForms != null ? this.mRecalculateBatchTaskForms : null;
	}

	public List<Integer> getRecalculateBatchTaskRespArea() {
		return this.mRecalculateBatchTaskRespArea != null ? this.mRecalculateBatchTaskRespArea : null;
	}

	public List<RecalculateBatchTaskAssignment> getRecalculateBatchTaskAssignments() {
		return this.mRecalculateBatchTaskAssignments != null ? this.mRecalculateBatchTaskAssignments : null;
	}

	protected void reset() {
		this.mModified = false;
	}

	public RecalculateBatchTaskRef getEntityRef() {
		return new RecalculateBatchTaskRefImpl(this.getPK(), "RecalculateBatchTask");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RecalculateBatchTaskId=");
		sb.append(String.valueOf(this.mRecalculateBatchTaskId));
		sb.append(' ');
		sb.append("ModelId=");
		sb.append(String.valueOf(this.mModelId));
		sb.append(' ');
		if (this.mModified) {
			sb.append("modified ");
		}

		return sb.toString();
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
