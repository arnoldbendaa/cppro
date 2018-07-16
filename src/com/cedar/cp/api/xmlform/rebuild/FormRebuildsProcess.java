// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform.rebuild;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildEditorSession;
import java.util.List;

public interface FormRebuildsProcess extends BusinessProcess {

   EntityList getAllFormRebuilds();
   
   EntityList getAllFormRebuildsForLoggedUser();

   EntityList getAllBudgetCyclesInRebuilds();

   FormRebuildEditorSession getFormRebuildEditorSession(Object var1) throws ValidationException;

   List submit(EntityRef var1, int var2) throws ValidationException;
}
