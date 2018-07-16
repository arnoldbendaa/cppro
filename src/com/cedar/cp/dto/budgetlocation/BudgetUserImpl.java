package com.cedar.cp.dto.budgetlocation;

import java.io.Serializable;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetlocation.BudgetUser;
import com.cedar.cp.api.user.UserRef;

public class BudgetUserImpl implements BudgetUser, Serializable {

	private int mStructureElementId;
	private UserRef mUserRef;
	private boolean mIsReadOnly;
	private boolean mInsertPending;
	private boolean mDeletePending;
	private boolean mModified;

	public int getStructureElementId() {
		return mStructureElementId;
	}

	public EntityRef getUserRef() {
		return mUserRef;
	}

	public boolean isReadOnly() {
		return mIsReadOnly;
	}

	public void setStructureElementId(int key) {
		mStructureElementId = key;
	}

	public void setReadOnly(boolean b) {
		if (mIsReadOnly != b) {
			mIsReadOnly = b;
			mModified = true;
		}
	}

	public void setUserRef(UserRef userRef) {
		mUserRef = userRef;
	}

	public void setInsertPending() {
		mInsertPending = true;
	}

	public boolean isInsertPending() {
		return mInsertPending;
	}

	public void setDeletePending(boolean b) {
		mDeletePending = b;
	}

	public boolean isDeletePending() {
		return mDeletePending;
	}

	public boolean isModified() {
		return mModified;
	}
}