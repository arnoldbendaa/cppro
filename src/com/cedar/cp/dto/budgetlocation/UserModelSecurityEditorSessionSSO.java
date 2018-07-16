package com.cedar.cp.dto.budgetlocation;

import com.cedar.cp.api.dimension.Hierarchy;
import java.io.Serializable;

public class UserModelSecurityEditorSessionSSO implements Serializable {

	private Hierarchy mHierarchy;
	private UserModelSecurityImpl mEditorData;

	public void setEditorData(UserModelSecurityImpl bl) {
		mEditorData = bl;
	}

	public UserModelSecurityImpl getEditorData() {
		return mEditorData;
	}

	public void setHierarchy(Hierarchy hierarchy) {
		mHierarchy = hierarchy;
	}

	public Hierarchy getHierarchy() {
		return mHierarchy;
	}
}