package com.cedar.cp.dto.recalculate;

import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;

import java.io.Serializable;

public class RecalculateBatchTaskEditorSessionSSO implements Serializable {

	private RecalculateBatchTaskImpl mEditorData;

	public RecalculateBatchTaskEditorSessionSSO() {
	}

	public RecalculateBatchTaskEditorSessionSSO(RecalculateBatchTaskImpl paramEditorData) {
		this.mEditorData = paramEditorData;
	}

	public void setEditorData(RecalculateBatchTaskImpl paramEditorData) {
		this.mEditorData = paramEditorData;
	}

	public RecalculateBatchTaskImpl getEditorData() {
		return this.mEditorData;
	}
}
