// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.dto.report.pack.ReportPackImpl;
import java.io.Serializable;

public class ReportPackEditorSessionSSO implements Serializable {

   private ReportPackImpl mEditorData;


   public ReportPackEditorSessionSSO() {}

   public ReportPackEditorSessionSSO(ReportPackImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ReportPackImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ReportPackImpl getEditorData() {
      return this.mEditorData;
   }
}
