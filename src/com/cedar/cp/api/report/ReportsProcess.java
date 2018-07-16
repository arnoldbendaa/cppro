// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.ReportEditorSession;

public interface ReportsProcess extends BusinessProcess {

   EntityList getAllReports();

   EntityList getAllReportsForUser(int var1);

   EntityList getAllReportsForAdmin();

   EntityList getWebReportDetails(int var1);

   ReportEditorSession getReportEditorSession(Object var1) throws ValidationException;

   int issueReportUpdateTask(Object var1, boolean var2) throws ValidationException;
}
