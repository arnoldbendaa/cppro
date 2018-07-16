// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import java.io.Serializable;
import java.util.List;

public class ReportDefinitionMappingRunDetailsImpl implements ReportDefinitionMappingRunDetails, Serializable {

   private List mBudgetLocations;
   private String mModelId;
   private byte[] mTemplate;


   public List getBudgetLocations() {
      return this.mBudgetLocations;
   }

   public void setBudgetLocations(List budgetLocations) {
      this.mBudgetLocations = budgetLocations;
   }

   public byte[] getTemplate() {
      return this.mTemplate;
   }

   public void setTemplate(byte[] template) {
      this.mTemplate = template;
   }

   public String getModelId() {
      return this.mModelId;
   }

   public void setModelId(String modelId) {
      this.mModelId = modelId;
   }
}
