// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.pack;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.pack.ReportPackEditorSession;
import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.report.pack.ReportPackProjection;

public interface ReportPacksProcess extends BusinessProcess {

   EntityList getAllReportPacks();

   EntityList getReportDefDistList(String var1);

   ReportPackEditorSession getReportPackEditorSession(Object var1) throws ValidationException;

   int issueReport(int var1, EntityRef var2, ReportPackOption var3) throws ValidationException, CPException;

   ReportPackProjection getReportPackProjection(int var1, Object var2) throws ValidationException, CPException;
}
