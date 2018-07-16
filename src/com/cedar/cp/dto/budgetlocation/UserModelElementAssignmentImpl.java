package com.cedar.cp.dto.budgetlocation;

import java.io.Serializable;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;

public class UserModelElementAssignmentImpl implements UserModelElementAssignment, Serializable {

	EntityRef mModel;
	Boolean mReadOnly;
	EntityRef mStructureElement;
	EntityRef mUser;
	String mDescription;

	public UserModelElementAssignmentImpl(EntityRef model, EntityRef user, EntityRef se, String sedesc, Boolean readOnly) {
		mModel = model;
		mUser = user;
		mStructureElement = se;
		mDescription = sedesc;
		mReadOnly = readOnly;
	}

	public String toString() {
		return mStructureElement.getNarrative() + " - " + getDescription();
	}

	public String getVisId() {
		return mStructureElement.getNarrative();
	}

	public String getDescription() {
		return mDescription;
	}

	public Boolean getReadOnly() {
		return mReadOnly;
	}

	public void setReadOnly(Boolean b) {
		mReadOnly = b;
	}

	public Object getStructureElementPK() {
		return mStructureElement.getPrimaryKey();
	}

	public EntityRef getModel() {
		return mModel;
	}

	public EntityRef getStructureElementRef() {
		return mStructureElement;
	}

	public EntityRef getUser() {
		return mUser;
	}
}