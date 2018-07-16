// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report;

import com.cedar.cp.dto.report.ReportImpl;
import java.io.Serializable;

public class ReportEditorSessionCSO implements Serializable {

   private int mUserId;
   private ReportImpl mEditorData;


   public ReportEditorSessionCSO(int userId, ReportImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ReportImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
