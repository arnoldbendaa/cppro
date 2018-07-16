// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.api.xmlform.XmlFormEditorSession;
import java.util.List;

public interface XmlFormsProcess extends BusinessProcess {

	EntityList getAllXmlForms();

	EntityList getAllFinXmlForms();

	EntityList getAllFFXmlForms();

	EntityList getAllFFXmlFormsForLoggedUser();

	EntityList getAllXcellXmlForms();

	EntityList getAllXcellXmlFormsForLoggedUser();

	EntityList getXcellXmlFormsForUser(int userId);

	EntityList getAllMassVirementXmlForms();

	EntityList getAllFinanceXmlForms();

	EntityList getAllFinanceAndFlatForms();

	EntityList getAllFinanceXmlFormsForModel(int var1);

	EntityList getAllFinanceAndFlatFormsForModel(int var1);

	EntityList getAllFinanceXmlFormsAndDataTypesForModel(int var1);

	EntityList getAllXmlFormsForModel(int modelId);

	EntityList getAllFixedXMLForms();

	EntityList getAllDynamicXMLForms();

	EntityList getAllFlatXMLForms();

	EntityList getXMLFormDefinition(int var1);

	EntityList getXMLFormCellPickerInfo(int var1);

	XmlFormEditorSession getXmlFormEditorSession(Object var1) throws ValidationException;

	EntityList getXMLFormDefinition(Object var1);

	EntityList getAllFinanceXmlFormsForModelAndUser(int var1, int budgetCycleId, int var2, boolean var3);

	void deleteFormAndProfiles(Object var1) throws ValidationException;

	void deleteFormProfiles(EntityRef er, String subject, String messageText) throws ValidationException;
	
	void deleteFormProfiles(EntityRef er, String subject, String messageText, Boolean mobile) throws ValidationException;

	int processFormDeployment(FormDeploymentData var1) throws ValidationException;

	int[] issueCellCalcRebuildTask(List<EntityRef> var1) throws ValidationException;

	EntityList getAllXmlFormsAndProfiles(String var1, String var2, String var3);

	UdefLookup getUdefLookup(String var1) throws ValidationException;

	EntityList getMassVirementFormsForFinanceCube(FinanceCubeRef var1);

	EntityList getAllXmlFormsForLoggedUser();

	EntityList getAllMassVirementXmlFormsForLoggedUser();

	/**
	 * 
	 * @param primaryKey
	 * @return excelFile and version number
	 * @throws ValidationException
	 */
	Object[] getExcelFile(Object primaryKey) throws ValidationException;

	/**
	 * 
	 * @param primaryKey
	 * @param json converted excel to json
	 * @param versionNumber version of form used to conversion
	 * @param userId 
	 * @return true if saved
	 * @throws ValidationException
	 */
	boolean saveJsonForm(Object primaryKey, String json, int versionNumber, int userId) throws ValidationException;

}
