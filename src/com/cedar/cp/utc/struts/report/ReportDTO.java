// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import java.sql.Timestamp;

public class ReportDTO {

   private String mReportKey;
   private int mReportId;
   private String mReportName;
   private int mTaskId;
   private int mReportType;
   private Timestamp mCreatedDate;
   private boolean mHasUpdates;
   private boolean mUpdatesApplied;
   private String mReportText;
   private String mReportTypeText;
   public static String[] sTypes = new String[]{"", "Cost Allocation", "Top Down Budgeting", "Budget Transfer", "Import Dimensions", "Change Management", "Cube Import", "Cell Calculation Rebuild", "", "", "", ""};


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

   public String getReportName() {
      return this.mReportName;
   }

   public void setReportName(String reportName) {
      this.mReportName = reportName;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public void setTaskId(int taskId) {
      this.mTaskId = taskId;
   }

   public String getReportType() {
      return "cp.report.list.type" + this.mReportType;
   }

   public int getReportTypeInt() {
      return this.mReportType;
   }

   public void setReportType(int reportType) {
      this.mReportType = reportType;
   }

   public Timestamp getCreatedDate() {
      return this.mCreatedDate;
   }

   public void setCreatedDate(Timestamp createdDate) {
      this.mCreatedDate = createdDate;
   }

   public boolean isHasUpdates() {
      return this.mHasUpdates;
   }

   public void setHasUpdates(boolean hasUpdates) {
      this.mHasUpdates = hasUpdates;
   }

   public boolean isUpdatesApplied() {
      return this.mUpdatesApplied;
   }

   public void setUpdatesApplied(boolean updatesApplied) {
      this.mUpdatesApplied = updatesApplied;
   }

   public String getReportText() {
      return this.mReportText;
   }

   public void setReportText(String reportText) {
      this.mReportText = reportText;
   }

   public String getReportTypeText() {
      return this.mReportTypeText;
   }

   public void setReportTypeText(String reportTypeText) {
      this.mReportTypeText = reportTypeText;
   }

}
