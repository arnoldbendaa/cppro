// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report;

import com.cedar.cp.dto.report.ReportImpl;
import java.io.Serializable;

public class ReportEditorSessionSSO implements Serializable {

   private ReportImpl mEditorData;


   public ReportEditorSessionSSO() {}

   public ReportEditorSessionSSO(ReportImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ReportImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ReportImpl getEditorData() {
      return this.mEditorData;
   }
}
