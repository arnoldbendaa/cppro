// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.template;

import com.cedar.cp.dto.report.template.ReportTemplateImpl;
import java.io.Serializable;

public class ReportTemplateEditorSessionSSO implements Serializable {

   private ReportTemplateImpl mEditorData;


   public ReportTemplateEditorSessionSSO() {}

   public ReportTemplateEditorSessionSSO(ReportTemplateImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ReportTemplateImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ReportTemplateImpl getEditorData() {
      return this.mEditorData;
   }
}
