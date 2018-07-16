// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.report.reportdefinition.dto;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.utc.struts.admin.report.reportdefinition.dto.ReportDefinitionDTO;

public class ReportDefFormDTO extends ReportDefinitionDTO {

   private EntityRef xmlFormEntityRef = null;
   private String reportDepth = null;
   private EntityRef[] selectionList = null;
   private String autoExpendDepth = null;
   private EntityRef reportTemplate = null;
   private String formVisId = null;
   private String selectionVisIds = null;
   private String reportTemplateVisId = null;


   public String getReportTemplateVisId() {
      return this.reportTemplate != null?this.reportTemplate.getDisplayText():this.reportTemplateVisId;
   }

   public void setReportTemplateVisId(String reportTemplateVisId) {
      this.reportTemplateVisId = reportTemplateVisId;
   }

   public String getFormVisId() {
      return this.xmlFormEntityRef != null?this.xmlFormEntityRef.getDisplayText():this.formVisId;
   }

   public void setFormVisId(String formVisId) {
      this.formVisId = formVisId;
   }

   public String getSelectionVisIds() {
      if(this.selectionList != null && this.selectionList.length > 0) {
         StringBuilder builder = new StringBuilder();

         for(int i = 0; i < this.selectionList.length; ++i) {
            EntityRef formRef = this.selectionList[i];
            builder.append(formRef.getDisplayText());
            if(i < this.selectionList.length - 1) {
               builder.append(",");
            }
         }

         return builder.toString();
      } else {
         return this.selectionVisIds;
      }
   }

   public void setSelectionVisIds(String selectionVisIds) {
      this.selectionVisIds = selectionVisIds;
   }

   public EntityRef getReportTemplate() {
      return this.reportTemplate;
   }

   public void setReportTemplate(EntityRef reportTemplate) {
      this.reportTemplate = reportTemplate;
   }

   public EntityRef getXmlFormEntityRef() {
      return this.xmlFormEntityRef;
   }

   public void setXmlFormEntityRef(EntityRef xmlFormEntityRef) {
      this.xmlFormEntityRef = xmlFormEntityRef;
   }

   public String getReportDepth() {
      return this.reportDepth;
   }

   public void setReportDepth(String reportDepth) {
      this.reportDepth = reportDepth;
   }

   public EntityRef[] getSelectionList() {
      return this.selectionList;
   }

   public void setSelectionList(EntityRef[] selectionList) {
      this.selectionList = selectionList;
   }

   public String getAutoExpendDepth() {
      return this.autoExpendDepth;
   }

   public void setAutoExpendDepth(String autoExpendDepth) {
      this.autoExpendDepth = autoExpendDepth;
   }
}
