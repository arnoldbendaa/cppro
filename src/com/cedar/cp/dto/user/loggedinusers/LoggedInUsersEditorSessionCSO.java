package com.cedar.cp.dto.user.loggedinusers;

import java.io.Serializable;

public class LoggedInUsersEditorSessionCSO implements Serializable {

	private int mUserId;
	private LoggedInUsersImpl mEditorData;

	public LoggedInUsersEditorSessionCSO(int userId, LoggedInUsersImpl editorData) {
		this.mUserId = userId;
		this.mEditorData = editorData;
	}

	public LoggedInUsersImpl getEditorData() {
		return this.mEditorData;
	}

	public int getUserId() {
		return this.mUserId;
	}
}
