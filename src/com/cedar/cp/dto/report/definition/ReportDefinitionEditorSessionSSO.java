// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.dto.report.definition.ReportDefinitionImpl;
import java.io.Serializable;

public class ReportDefinitionEditorSessionSSO implements Serializable {

   private ReportDefinitionImpl mEditorData;


   public ReportDefinitionEditorSessionSSO() {}

   public ReportDefinitionEditorSessionSSO(ReportDefinitionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ReportDefinitionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ReportDefinitionImpl getEditorData() {
      return this.mEditorData;
   }
}
