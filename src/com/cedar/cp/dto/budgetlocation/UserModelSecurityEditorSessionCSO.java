package com.cedar.cp.dto.budgetlocation;

import java.io.Serializable;

public class UserModelSecurityEditorSessionCSO implements Serializable {

	UserModelSecurityImpl mEditorData;
	private int mUserId;

	public UserModelSecurityEditorSessionCSO(int userId, UserModelSecurityImpl editorData) {
		mUserId = userId;
		mEditorData = editorData;
	}

	public UserModelSecurityImpl getEditorData() {
		return mEditorData;
	}

	public int getUserId() {
		return mUserId;
	}
}