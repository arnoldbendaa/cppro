package com.cedar.cp.dto.budgetlocation;

import java.io.Serializable;
import java.util.List;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.budgetlocation.UserModelSecurity;

public class UserModelSecurityImpl implements UserModelSecurity, Serializable, Cloneable {

	private Object mPrimaryKey;
	private EntityRef mUserRef;
	private String mUserDescription;
	private boolean mDeployForms;
	private boolean mReplace;
	private List<UserModelElementAssignment> mUserModelElementAccess;
	private EntityList mModelsAndRAHierarchies;

	public UserModelSecurityImpl(Object paramKey) {
		mPrimaryKey = paramKey;
	}

	public Object getPrimaryKey() {
		return mPrimaryKey;
	}

	public void setPrimaryKey(Object paramKey) {
		mPrimaryKey = paramKey;
	}

	public EntityRef getUserRef() {
		return mUserRef;
	}

	public void setUserRef(EntityRef er) {
		mUserRef = er;
	}

	public String getUserDescription() {
		return mUserDescription;
	}

	public void setUserDescription(String s) {
		mUserDescription = s;
	}

	public boolean isDeployForms() {
		return mDeployForms;
	}

	public void setDeployForms(boolean deployForms) {
		mDeployForms = deployForms;
	}

	public List<UserModelElementAssignment> getUserModelElementAccess() {
		return mUserModelElementAccess;
	}

	public void setUserModelElementAccess(List<UserModelElementAssignment> l) {
		mUserModelElementAccess = l;
	}

	public EntityList getModelsAndRAHierarchies() {
		return mModelsAndRAHierarchies;
	}

	public void setModelsAndRAHierarchies(EntityList el) {
		mModelsAndRAHierarchies = el;
	}

	public void setReplace(boolean b) {
		mReplace = b;
	}

	public boolean isReplace() {
		return mReplace;
	}
}