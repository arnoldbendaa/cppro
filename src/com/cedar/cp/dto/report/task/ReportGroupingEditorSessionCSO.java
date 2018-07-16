// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.task;

import com.cedar.cp.dto.report.task.ReportGroupingImpl;
import java.io.Serializable;

public class ReportGroupingEditorSessionCSO implements Serializable {

   private int mUserId;
   private ReportGroupingImpl mEditorData;


   public ReportGroupingEditorSessionCSO(int userId, ReportGroupingImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ReportGroupingImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
