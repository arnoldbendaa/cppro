// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import com.cedar.cp.utc.common.CPForm;

public class ReportUpdateForm extends CPForm {

   public String mReportId;
   public String mMessage;


   public String getReportId() {
      return this.mReportId;
   }

   public void setReportId(String reportId) {
      this.mReportId = reportId;
   }

   public String getMessage() {
      return this.mMessage;
   }

   public void setMessage(String message) {
      this.mMessage = message;
   }
}
