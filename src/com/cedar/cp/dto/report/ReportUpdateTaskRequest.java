// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report;

import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.Arrays;
import java.util.List;

public class ReportUpdateTaskRequest extends AbstractTaskRequest {

   private int mReportId;
   private int mFinanceCubeId;


   public ReportUpdateTaskRequest(int reportId, int financeCubeId) {
      this.mReportId = reportId;
      this.mFinanceCubeId = financeCubeId;
   }

   public int getReportId() {
      return this.mReportId;
   }

   public void setReportId(int reportId) {
      this.mReportId = reportId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.ReportUpdateTask";
   }

   public List toDisplay() {
      return Arrays.asList(new Object[]{"ReportId: " + this.mReportId + " FinanceCubeId: " + this.mFinanceCubeId});
   }
}
