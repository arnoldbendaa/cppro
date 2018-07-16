package com.cedar.cp.impl.budgetlocation;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.BudgetUserEditor;
import com.cedar.cp.dto.budgetlocation.BudgetUserImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;

public class BudgetUserEditorImpl extends SubBusinessEditorImpl implements BudgetUserEditor {

	private BudgetLocationEditorImpl mEditor;
	private BudgetUserImpl mData;

	public BudgetUserEditorImpl(BusinessSession session, BudgetLocationEditorImpl editor, BudgetUserImpl bu) {
		super(session, editor);
		mEditor = editor;
		mData = bu;
	}

	public void setReadOnly(boolean b) throws ValidationException {
		if (b != mData.isReadOnly()) {
			mData.setReadOnly(b);
			setContentModified();
		}
	}

	protected void undoModifications() throws CPException {
		throw new UnsupportedOperationException("can't undo");
	}

	protected void saveModifications() throws ValidationException {
		if (isContentModified())
			mEditor.setContentModified();
	}
}