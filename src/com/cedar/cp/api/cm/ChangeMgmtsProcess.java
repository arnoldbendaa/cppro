// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cm;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cm.ChangeManagementSession;
import com.cedar.cp.api.cm.ChangeMgmtEditorSession;

public interface ChangeMgmtsProcess extends BusinessProcess {

   EntityList getAllChangeMgmts();

   EntityList getAllChangeMgmtsForModel(int var1);

   EntityList getAllChangeMgmtsForModelWithXML(int var1);

   ChangeMgmtEditorSession getChangeMgmtEditorSession(Object var1) throws ValidationException;

   ChangeManagementSession getChangeManagementSession();

   void tidyBudgetState(int var1) throws ValidationException;
}
