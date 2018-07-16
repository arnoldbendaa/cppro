package com.cedar.cp.impl.xmlform.convert;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.convert.ExcelIOEditor;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;

public class ExcelIOEditorImpl extends BusinessEditorImpl implements ExcelIOEditor {


	public ExcelIOEditorImpl(BusinessSessionImpl session) {
		super(session);
	}

	@Override
	public void saveModifications() throws ValidationException {
	}



}
