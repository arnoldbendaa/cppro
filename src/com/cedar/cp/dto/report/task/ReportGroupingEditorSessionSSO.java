// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.task;

import com.cedar.cp.dto.report.task.ReportGroupingImpl;
import java.io.Serializable;

public class ReportGroupingEditorSessionSSO implements Serializable {

   private ReportGroupingImpl mEditorData;


   public ReportGroupingEditorSessionSSO() {}

   public ReportGroupingEditorSessionSSO(ReportGroupingImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ReportGroupingImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ReportGroupingImpl getEditorData() {
      return this.mEditorData;
   }
}
