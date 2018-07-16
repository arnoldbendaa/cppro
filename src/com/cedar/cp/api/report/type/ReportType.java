// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.type;

import java.util.List;

public interface ReportType {

   int XML_FORM = 0;
   int EXCEL_TEMPLATE = 1;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   int getType();

   List getReportParams();
}
