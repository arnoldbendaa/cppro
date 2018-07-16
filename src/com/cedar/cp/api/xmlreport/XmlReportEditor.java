// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlreport;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreport.XmlReport;

public interface XmlReportEditor extends BusinessEditor {

   void setXmlReportFolderId(int var1) throws ValidationException;

   void setUserId(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDefinition(String var1) throws ValidationException;

   XmlReport getXmlReport();
}
