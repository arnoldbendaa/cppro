// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlreport;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreport.XmlReportEditorSession;
import com.cedar.cp.util.xmlreport.ReportConfig;

public interface XmlReportsProcess extends BusinessProcess {

   EntityList getAllXmlReports();

   EntityList getAllPublicXmlReports();

   EntityList getAllXmlReportsForUser(int var1);

   EntityList getXmlReportsForFolder(int var1);

   EntityList getSingleXmlReport(int var1, String var2);

   XmlReportEditorSession getXmlReportEditorSession(Object var1) throws ValidationException;

   ReportConfig getXMLReportConfig(String var1) throws ValidationException, CPException;
}
