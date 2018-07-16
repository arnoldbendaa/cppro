// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReportDefinitionFormRunDetailsImpl implements ReportDefinitionFormRunDetails, Serializable {

   private List mBudgetLocations;
   private String mModelId;
   private Map mSelection;
   private String mDataType;
   private int mXMLFormId;
   private int mDepth;
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

   public Map getSelection() {
      return this.mSelection;
   }

   public void setSelection(Map selection) {
      this.mSelection = selection;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public int getXMLFormId() {
      return this.mXMLFormId;
   }

   public void setXMLFormId(int XMLFormId) {
      this.mXMLFormId = XMLFormId;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public void setDepth(int depth) {
      this.mDepth = depth;
   }

   public byte[] getTemplate() {
      return this.mTemplate;
   }

   public void setTemplate(byte[] template) {
      this.mTemplate = template;
   }
}
