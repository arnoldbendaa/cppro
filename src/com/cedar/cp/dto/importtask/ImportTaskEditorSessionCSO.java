package com.cedar.cp.dto.importtask;

import java.io.Serializable;

public class ImportTaskEditorSessionCSO implements Serializable {

	private int mUserId;
	private ImportTaskImpl mEditorData;

	public ImportTaskEditorSessionCSO(int userId, ImportTaskImpl editorData) {
		this.mUserId = userId;
		this.mEditorData = editorData;
	}

	public ImportTaskImpl getEditorData() {
		return this.mEditorData;
	}

	public int getUserId() {
		return this.mUserId;
	}
}
