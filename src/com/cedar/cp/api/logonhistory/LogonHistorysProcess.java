// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.logonhistory;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logonhistory.LogonHistoryEditorSession;
import java.sql.Timestamp;

public interface LogonHistorysProcess extends BusinessProcess {

   EntityList getAllLogonHistorys();

   LogonHistoryEditorSession getLogonHistoryEditorSession(Object var1) throws ValidationException;

   EntityList getAllLogonHistorysReport(String var1, Timestamp var2, int var3);
}
