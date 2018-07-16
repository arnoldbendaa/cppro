// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlform;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.api.xmlform.XmlFormWizardParameters;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionCSO;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface XmlFormEditorSessionLocal extends EJBLocalObject {

	XmlFormEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

	XmlFormEditorSessionSSO getNewItemData(int var1) throws EJBException;

	XmlFormPK insert(XmlFormEditorSessionCSO var1) throws ValidationException, EJBException;

	XmlFormPK copy(XmlFormEditorSessionCSO var1) throws ValidationException, EJBException;

	void update(XmlFormEditorSessionCSO var1) throws ValidationException, EJBException;

	void delete(int var1, Object var2) throws ValidationException, EJBException;

	Map invokeOnServer(List var1) throws EJBException;

	XmlFormWizardParameters getFinanceXMLFormWizardData(int var1, int var2) throws EJBException;

	void deleteFormAndProfiles(int var1, Object var2) throws ValidationException, EJBException;

	void deleteFormProfiles(int paramInt, Object paramObject, String paramString1, String paramString2) throws ValidationException, EJBException;

	void deleteFormProfiles(int paramInt, Object paramObject, String paramString1, String paramString2, Boolean mobile) throws ValidationException, EJBException;

	int processFormDeployment(int var1, FormDeploymentData var2) throws ValidationException, EJBException;

	int[] issueCellCalcRebuildTask(int var1, List<EntityRef> var2) throws ValidationException, EJBException;

	Object[] getExcelFile(Object pk) throws EJBException;

	boolean saveJsonForm(Object pk, String json, int versionNumber, int userId) throws EJBException;
}
