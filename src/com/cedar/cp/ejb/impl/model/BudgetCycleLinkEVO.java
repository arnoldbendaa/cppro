package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.BudgetCycleLinkRef;
import com.cedar.cp.dto.model.BudgetCycleLinkCK;
import com.cedar.cp.dto.model.BudgetCycleLinkPK;
import com.cedar.cp.dto.model.BudgetCycleLinkRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class BudgetCycleLinkEVO implements Serializable {

	private transient BudgetCycleLinkPK mPK;
	private int mBudgetCycleId;
	private int mXmlFormId;
	private String mXmlFormDataType;
	private boolean mInsertPending;
	private boolean mDeletePending;
	private boolean mModified;

	public BudgetCycleLinkEVO() {
	}

	public BudgetCycleLinkEVO(int newBudgetCycleId, int newXmlFormId, String newXmlFormDataType) {
		mBudgetCycleId = newBudgetCycleId;
		mXmlFormId = newXmlFormId;
		mXmlFormDataType = newXmlFormDataType;
	}

	public BudgetCycleLinkPK getPK() {
		if (mPK == null) {
			mPK = new BudgetCycleLinkPK(mBudgetCycleId, mXmlFormId);
		}

		return mPK;
	}

	public boolean isModified() {
		return mModified;
	}

	public int getBudgetCycleId() {
		return mBudgetCycleId;
	}

	public int getXmlFormId() {
		return mXmlFormId;
	}
	
	public String getXmlFormDataType() {
	    return mXmlFormDataType;
	  }
	
	public void setBudgetCycleId(int newBudgetCycleId) {
		if (mBudgetCycleId == newBudgetCycleId)
			return;
		mModified = true;
		mBudgetCycleId = newBudgetCycleId;
		mPK = null;
	}

	public void setXmlFormId(int newXmlFormId) {
		if (mXmlFormId == newXmlFormId)
			return;
		mModified = true;
		mXmlFormId = newXmlFormId;
		mPK = null;
	}
	
	  public void setXmlFormDataType(String newXmlFormDataType) {
	    if (((mXmlFormDataType != null) && (newXmlFormDataType == null)) || ((mXmlFormDataType == null) && (newXmlFormDataType != null)) || ((mXmlFormDataType != null) && (newXmlFormDataType != null) && (!mXmlFormDataType.equals(newXmlFormDataType))))
	    {
	      mXmlFormDataType = newXmlFormDataType;
	      mModified = true;
	    }
	  }

	public void setDetails(BudgetCycleLinkEVO newDetails) {
		setBudgetCycleId(newDetails.getBudgetCycleId());
		setXmlFormId(newDetails.getXmlFormId());
	}

	public BudgetCycleLinkEVO deepClone() {
		BudgetCycleLinkEVO cloned = new BudgetCycleLinkEVO();

		cloned.mModified = mModified;
		cloned.mInsertPending = mInsertPending;
		cloned.mDeletePending = mDeletePending;

		cloned.mBudgetCycleId = mBudgetCycleId;
		cloned.mXmlFormId = mXmlFormId;
		return cloned;
	}

	public void prepareForInsert(BudgetCycleEVO parent) {
		boolean newKey = insertPending();
	}

	public int getInsertCount(int startCount) {
		int returnCount = startCount;

		return returnCount;
	}

	public int assignNextKey(BudgetCycleEVO parent, int startKey) {
		int nextKey = startKey;

		return nextKey;
	}

	public void setInsertPending() {
		mInsertPending = true;
	}

	public boolean insertPending() {
		return mInsertPending;
	}

	public void setDeletePending() {
		mDeletePending = true;
	}

	public boolean deletePending() {
		return mDeletePending;
	}

	protected void reset() {
		mModified = false;
		mInsertPending = false;
	}

	public BudgetCycleLinkRef getEntityRef(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle, String entityText) {
		return new BudgetCycleLinkRefImpl(new BudgetCycleLinkCK(evoModel.getPK(), evoBudgetCycle.getPK(), getPK()), entityText);
	}

	public BudgetCycleLinkCK getCK(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle) {
		return new BudgetCycleLinkCK(evoModel.getPK(), evoBudgetCycle.getPK(), getPK());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BudgetCycleId=");
		sb.append(String.valueOf(mBudgetCycleId));
		sb.append(' ');
		sb.append("XmlFormId=");
		sb.append(String.valueOf(mXmlFormId));
		sb.append(' ');
		sb.append("XmlFormDataType=");
	    sb.append(String.valueOf(mXmlFormDataType));
	    sb.append(' ');
		if (mModified)
			sb.append("modified ");
		if (mInsertPending)
			sb.append("insertPending ");
		if (mDeletePending)
			sb.append("deletePending ");
		return sb.toString();
	}

	public String print() {
		return print(0);
	}

	public String print(int indent) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < indent; i++)
			sb.append(' ');
		sb.append("BudgetCycleLink: ");
		sb.append(toString());
		return sb.toString();
	}
}