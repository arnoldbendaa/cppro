// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.mappingtemplate;

import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateImpl;
import java.io.Serializable;

public class ReportMappingTemplateEditorSessionSSO implements Serializable {

   private ReportMappingTemplateImpl mEditorData;


   public ReportMappingTemplateEditorSessionSSO() {}

   public ReportMappingTemplateEditorSessionSSO(ReportMappingTemplateImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ReportMappingTemplateImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ReportMappingTemplateImpl getEditorData() {
      return this.mEditorData;
   }
}
