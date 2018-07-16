// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.type;

import com.cedar.cp.dto.report.type.ReportTypeImpl;
import java.io.Serializable;

public class ReportTypeEditorSessionSSO implements Serializable {

   private ReportTypeImpl mEditorData;


   public ReportTypeEditorSessionSSO() {}

   public ReportTypeEditorSessionSSO(ReportTypeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ReportTypeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ReportTypeImpl getEditorData() {
      return this.mEditorData;
   }
}
