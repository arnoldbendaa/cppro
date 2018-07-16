package com.cedar.cp.impl.budgetlocation;

import com.cedar.cp.api.budgetlocation.BudgetLocation;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.model.ModelPK;

public class BudgetLocationAdapter implements BudgetLocation {

	private BudgetLocationImpl mEditorData;
	private BudgetLocationEditorSessionImpl mEditorSessionImpl;

	public BudgetLocationAdapter(BudgetLocationEditorSessionImpl e, BudgetLocationImpl editorData) {
		mEditorData = editorData;
		mEditorSessionImpl = e;
	}

	public void setPrimaryKey(Object key) {
		mEditorData.setPrimaryKey(key);
	}

	protected BudgetLocationEditorSessionImpl getEditorSessionImpl() {
		return mEditorSessionImpl;
	}

	protected BudgetLocationImpl getEditorData() {
		return mEditorData;
	}

	public Object getPrimaryKey() {
		return mEditorData.getPrimaryKey();
	}

	void setPrimaryKey(ModelPK paramKey) {
		mEditorData.setPrimaryKey(paramKey);
	}
}