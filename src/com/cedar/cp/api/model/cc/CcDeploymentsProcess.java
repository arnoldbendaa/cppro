// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.cc.CcDeploymentEditorSession;
import java.util.List;

public interface CcDeploymentsProcess extends BusinessProcess {

   EntityList getAllCcDeployments();

   EntityList getCcDeploymentsForLookupTable(String var1);

   EntityList getCcDeploymentsForXmlForm(int var1);

   EntityList getCcDeploymentsForModel(int var1);

   EntityList getCcDeploymentCellPickerInfo(int var1);

   EntityList getCcDeploymentXMLFormType(int var1);

   CcDeploymentEditorSession getCcDeploymentEditorSession(Object var1) throws ValidationException;

   int[] issueCellCalcRebuildTask(List<Object[]> var1) throws ValidationException;
}
