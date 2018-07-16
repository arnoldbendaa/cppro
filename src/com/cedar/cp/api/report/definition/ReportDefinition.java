// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.definition;

import com.cedar.cp.api.base.EntityList;
import java.util.List;

public interface ReportDefinition {

   int REPORT_DEF_FORM_TYPE_ID = 1;
   int REPORT_DEF_MAPPED_EXCEL_TYPE_ID = 2;
   int REPORT_DEF_CALCULATOR_TYPE_ID = 3;
   int REPORT_DEF_SUMMARY_CALC_TYPE_ID = 4;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   int getReportTypeId();

   boolean isIsPublic();

   List getReportParams();

   EntityList getReportTypes();

   String getReportTypeVisId();
}
