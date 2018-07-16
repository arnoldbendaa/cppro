package com.cedar.cp.dto.importtask;

import com.cedar.cp.dto.importtask.ImportTaskImpl;
import java.io.Serializable;

public class ImportTaskEditorSessionSSO implements Serializable {

	private ImportTaskImpl mEditorData;

	public ImportTaskEditorSessionSSO() {
	}

	public ImportTaskEditorSessionSSO(ImportTaskImpl paramEditorData) {
		this.mEditorData = paramEditorData;
	}

	public void setEditorData(ImportTaskImpl paramEditorData) {
		this.mEditorData = paramEditorData;
	}

	public ImportTaskImpl getEditorData() {
		return this.mEditorData;
	}
}
