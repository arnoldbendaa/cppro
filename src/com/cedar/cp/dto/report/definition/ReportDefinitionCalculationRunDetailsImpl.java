// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import java.io.Serializable;
import java.util.List;

public class ReportDefinitionCalculationRunDetailsImpl implements ReportDefinitionCalculationRunDetails, Serializable {

   private List mBudgetLocations;
   private String mModelId;
   private int mDeploymentId;
   private int mXMLFormId;
   private List<Integer> mContextDimensionIndexes;
   private byte[] mTemplate;


   public List getBudgetLocations() {
      return this.mBudgetLocations;
   }

   public void setBudgetLocations(List budgetLocations) {
      this.mBudgetLocations = budgetLocations;
   }

   public String getModelId() {
      return this.mModelId;
   }

   public void setModelId(String modelId) {
      this.mModelId = modelId;
   }

   public int getDeploymentId() {
      return this.mDeploymentId;
   }

   public void setDeploymentId(int deploymentId) {
      this.mDeploymentId = deploymentId;
   }

   public int getXMLFormId() {
      return this.mXMLFormId;
   }

   public void setXMLFormId(int XMLFormId) {
      this.mXMLFormId = XMLFormId;
   }

   public List<Integer> getContextDimenionIndexes() {
      return this.mContextDimensionIndexes;
   }

   public void setContextDimensionIndexes(List<Integer> contextDimensionIndexes) {
      this.mContextDimensionIndexes = contextDimensionIndexes;
   }

   public byte[] getTemplate() {
      return this.mTemplate;
   }

   public void setTemplate(byte[] template) {
      this.mTemplate = template;
   }
}
