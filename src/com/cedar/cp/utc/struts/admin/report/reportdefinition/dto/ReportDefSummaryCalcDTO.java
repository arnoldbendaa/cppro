// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.report.reportdefinition.dto;

import com.cedar.cp.utc.struts.admin.report.reportdefinition.dto.ReportDefinitionDTO;

public class ReportDefSummaryCalcDTO extends ReportDefinitionDTO {

   private String reportDepth = null;
   private String ccDeploymentVisId = null;
   private String templateVisId = null;
   private String columnMapping = null;


   public String getCcDeploymentVisId() {
      return this.ccDeploymentVisId;
   }

   public void setCcDeploymentVisId(String ccDeploymentVisId) {
      this.ccDeploymentVisId = ccDeploymentVisId;
   }

   public String getTemplateVisId() {
      return this.templateVisId;
   }

   public void setTemplateVisId(String templateVisId) {
      this.templateVisId = templateVisId;
   }

   public String getColumnMapping() {
      return this.columnMapping;
   }

   public void setColumnMapping(String columnMapping) {
      this.columnMapping = columnMapping;
   }

   public String getReportDepth() {
      return this.reportDepth;
   }

   public void setReportDepth(String reportDepth) {
      this.reportDepth = reportDepth;
   }
}
