// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.report.reportdefinition.dto;

import com.cedar.cp.utc.struts.admin.report.reportdefinition.dto.ReportDefinitionDTO;

public class ReportDefMappedExcelDTO extends ReportDefinitionDTO {

   private String reportDepth = null;
   private String mappingTemplateVisId = null;
   private String mDefaultParam;


   public String getReportDepth() {
      return this.reportDepth;
   }

   public void setReportDepth(String reportDepth) {
      this.reportDepth = reportDepth;
   }

   public String getMappingTemplateVisId() {
      return this.mappingTemplateVisId;
   }

   public void setMappingTemplateVisId(String mappingTemplateVisId) {
      this.mappingTemplateVisId = mappingTemplateVisId;
   }

   public String getDefaultParam() {
      return this.mDefaultParam == null?"":this.mDefaultParam;
   }

   public void setDefaultParam(String defaultParam) {
      this.mDefaultParam = defaultParam;
   }
}
