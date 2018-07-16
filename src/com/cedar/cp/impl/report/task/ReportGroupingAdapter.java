// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.task;

import com.cedar.cp.api.report.task.ReportGrouping;
import com.cedar.cp.dto.report.task.ReportGroupingImpl;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import com.cedar.cp.impl.report.task.ReportGroupingEditorSessionImpl;

public class ReportGroupingAdapter implements ReportGrouping {

   private ReportGroupingImpl mEditorData;
   private ReportGroupingEditorSessionImpl mEditorSessionImpl;


   public ReportGroupingAdapter(ReportGroupingEditorSessionImpl e, ReportGroupingImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ReportGroupingEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ReportGroupingImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ReportGroupingPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getParentTaskId() {
      return this.mEditorData.getParentTaskId();
   }

   public int getTaskId() {
      return this.mEditorData.getTaskId();
   }

   public int getDistributionType() {
      return this.mEditorData.getDistributionType();
   }

   public int getMessageType() {
      return this.mEditorData.getMessageType();
   }

   public String getMessageId() {
      return this.mEditorData.getMessageId();
   }

   public void setParentTaskId(int p) {
      this.mEditorData.setParentTaskId(p);
   }

   public void setTaskId(int p) {
      this.mEditorData.setTaskId(p);
   }

   public void setDistributionType(int p) {
      this.mEditorData.setDistributionType(p);
   }

   public void setMessageType(int p) {
      this.mEditorData.setMessageType(p);
   }

   public void setMessageId(String p) {
      this.mEditorData.setMessageId(p);
   }
}
