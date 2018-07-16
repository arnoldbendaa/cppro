package com.cedar.cp.dto.budgetlocation;

import java.io.Serializable;

public class BudgetLocationEditorSessionCSO implements Serializable {

	BudgetLocationImpl mEditorData;
	private int mUserId;

	public BudgetLocationEditorSessionCSO(int userId, BudgetLocationImpl editorData) {
		mUserId = userId;
		mEditorData = editorData;
	}

	public BudgetLocationImpl getEditorData() {
		return mEditorData;
	}

	public int getUserId() {
		return mUserId;
	}
}