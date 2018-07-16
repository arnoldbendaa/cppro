// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extsys;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemEditorSession;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.extsys.TransferMonitor;
import java.net.URL;

public interface ExternalSystemsProcess extends BusinessProcess {

   EntityList getAllExternalSystems();

   EntityList getAllGenericExternalSystems();

   ExternalSystemEditorSession getExternalSystemEditorSession(Object var1) throws ValidationException;

   int issueExternalSystemImportTask(ExternalSystemRef var1, URL var2, TransferMonitor var3) throws ValidationException;
}
