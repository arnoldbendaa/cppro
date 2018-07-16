// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.sqlmon;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.sqlmon.SqlDetails;
import com.cedar.cp.api.sqlmon.SqlMonitorSession;

public interface SqlMonitorProcess extends BusinessProcess {

   EntityList getAllOracleSessions();

   void refresh();

   SqlMonitorSession getSqlMonitorSession(Object var1);

   SqlDetails getSqlDetails(Object var1);
}
