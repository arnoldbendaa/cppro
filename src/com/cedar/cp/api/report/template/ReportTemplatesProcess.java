// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.template;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.template.ReportTemplateEditorSession;

public interface ReportTemplatesProcess extends BusinessProcess {

   EntityList getAllReportTemplates();

   ReportTemplateEditorSession getReportTemplateEditorSession(Object var1) throws ValidationException;
}
