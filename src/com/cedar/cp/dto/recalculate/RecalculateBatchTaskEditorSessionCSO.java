package com.cedar.cp.dto.recalculate;

import java.io.Serializable;

public class RecalculateBatchTaskEditorSessionCSO implements Serializable {

	private int mUserId;
	private RecalculateBatchTaskImpl mEditorData;

	public RecalculateBatchTaskEditorSessionCSO(int userId, RecalculateBatchTaskImpl editorData) {
		this.mUserId = userId;
		this.mEditorData = editorData;
	}

	public RecalculateBatchTaskImpl getEditorData() {
		return this.mEditorData;
	}

	public int getUserId() {
		return this.mUserId;
	}
}
