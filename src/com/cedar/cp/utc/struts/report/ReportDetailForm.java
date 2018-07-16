// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.report.ReportDTO;

public class ReportDetailForm extends CPForm {

   private String mReportKey;
   private int mReportId;
   private ReportDTO mReportDetails;


   public String getReportKey() {
      return this.mReportKey;
   }

   public void setReportKey(String reportKey) {
      this.mReportKey = reportKey;
   }

   public int getReportId() {
      return this.mReportId;
   }

   public void setReportId(int reportId) {
      this.mReportId = reportId;
   }

   public ReportDTO getReportDetails() {
      return this.mReportDetails;
   }

   public void setReportDetails(ReportDTO reportDetails) {
      this.mReportDetails = reportDetails;
   }
}
