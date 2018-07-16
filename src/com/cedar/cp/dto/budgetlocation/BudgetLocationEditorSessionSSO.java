package com.cedar.cp.dto.budgetlocation;

import java.io.Serializable;

import com.cedar.cp.api.dimension.Hierarchy;

public class BudgetLocationEditorSessionSSO implements Serializable {

	private Hierarchy mHierarchy;
	private BudgetLocationImpl mEditorData;

	public void setEditorData(BudgetLocationImpl bl) {
		mEditorData = bl;
	}

	public BudgetLocationImpl getEditorData() {
		return mEditorData;
	}

	public void setHierarchy(Hierarchy hierarchy) {
		mHierarchy = hierarchy;
	}

	public Hierarchy getHierarchy() {
		return mHierarchy;
	}
}