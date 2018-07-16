// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:04:21
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.Destroyable;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.xmlform.CellPickerInfo;
import com.cedar.cp.api.xmlform.FormDeploymentEditor;
import com.cedar.cp.api.xmlform.XmlForm;

public interface XmlFormEditor extends BusinessEditor, Destroyable {
	public static enum SeQueryType
	{
		IMMEDIATE_CHILDREN, LEAVES, CASCADE;
	}

	void setType(int var1) throws ValidationException;

	void setDesignMode(boolean var1) throws ValidationException;

	void setFinanceCubeId(int var1) throws ValidationException;

	void setSecurityAccess(boolean var1) throws ValidationException;

	void setVisId(String var1) throws ValidationException;

	void setDescription(String var1) throws ValidationException;

	void setDefinition(String var1) throws ValidationException;

	void setExcelFile(byte[] blob) throws ValidationException;

	byte[] getExcelFile();
	
	String getJsonForm();
	
	void setJsonForm(String jsonForm);

	XmlForm getXmlForm();

	FormDeploymentEditor getFormDeploymentEditor() throws ValidationException;

	CellPickerInfo getCellPickerInfo() throws ValidationException;

	CellPickerInfo getCellPickerInfo(ModelRef var1, FinanceCubeRef var2);

	EntityList lookupStructureElement(Object var1, Object var2, String var3, boolean var4);

	EntityList queryStructureElements(Object var1, Object var2, SeQueryType var3);

	UdefLookup getUdefLookup(String var1) throws ValidationException;

	DimensionRef[] getDimensions();
}
