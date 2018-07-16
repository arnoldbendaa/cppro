package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersImpl;

import java.io.Serializable;

public class LoggedInUsersEditorSessionSSO implements Serializable {

	private LoggedInUsersImpl mEditorData;

	public LoggedInUsersEditorSessionSSO() {
	}

	public LoggedInUsersEditorSessionSSO(LoggedInUsersImpl paramEditorData) {
		this.mEditorData = paramEditorData;
	}

	public void setEditorData(LoggedInUsersImpl paramEditorData) {
		this.mEditorData = paramEditorData;
	}

	public LoggedInUsersImpl getEditorData() {
		return this.mEditorData;
	}
}
