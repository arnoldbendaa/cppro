// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report;

import com.cedar.cp.api.report.Report;
import com.cedar.cp.dto.report.ReportImpl;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.impl.report.ReportEditorSessionImpl;

public class ReportAdapter implements Report {

   private ReportImpl mEditorData;
   private ReportEditorSessionImpl mEditorSessionImpl;


   public ReportAdapter(ReportEditorSessionImpl e, ReportImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ReportEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ReportImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ReportPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getUserId() {
      return this.mEditorData.getUserId();
   }

   public int getReportType() {
      return this.mEditorData.getReportType();
   }

   public int getTaskId() {
      return this.mEditorData.getTaskId();
   }

   public boolean isComplete() {
      return this.mEditorData.isComplete();
   }

   public String getReportText() {
      return this.mEditorData.getReportText();
   }

   public boolean isHasUpdates() {
      return this.mEditorData.isHasUpdates();
   }

   public boolean isUpdatesApplied() {
      return this.mEditorData.isUpdatesApplied();
   }

   public Integer getUpdateTaskId() {
      return this.mEditorData.getUpdateTaskId();
   }

   public Integer getBudgetCycleId() {
      return this.mEditorData.getBudgetCycleId();
   }

   public Integer getActivityType() {
      return this.mEditorData.getActivityType();
   }

   public String getActivityDetail() {
      return this.mEditorData.getActivityDetail();
   }

   public void setUserId(int p) {
      this.mEditorData.setUserId(p);
   }

   public void setReportType(int p) {
      this.mEditorData.setReportType(p);
   }

   public void setTaskId(int p) {
      this.mEditorData.setTaskId(p);
   }

   public void setComplete(boolean p) {
      this.mEditorData.setComplete(p);
   }

   public void setReportText(String p) {
      this.mEditorData.setReportText(p);
   }

   public void setHasUpdates(boolean p) {
      this.mEditorData.setHasUpdates(p);
   }

   public void setUpdatesApplied(boolean p) {
      this.mEditorData.setUpdatesApplied(p);
   }

   public void setUpdateTaskId(Integer p) {
      this.mEditorData.setUpdateTaskId(p);
   }

   public void setBudgetCycleId(Integer p) {
      this.mEditorData.setBudgetCycleId(p);
   }

   public void setActivityType(Integer p) {
      this.mEditorData.setActivityType(p);
   }

   public void setActivityDetail(String p) {
      this.mEditorData.setActivityDetail(p);
   }
}
