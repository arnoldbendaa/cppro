// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;

public class LoadtestForm extends CPForm {

   private int mUserId;
   private int mTopNodeId;
   private int mModelId;
   private int mBudgetCycleId;
   private String mSelectionString;
   private String mDataType;
   private int mOrganisationId;
   private int mFinanceCubeId;
   private List mFormData;


   public List getFormData() {
      return this.mFormData;
   }

   public void setFormData(List formData) {
      this.mFormData = formData;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public int getTopNodeId() {
      return this.mTopNodeId;
   }

   public void setTopNodeId(int topNodeId) {
      this.mTopNodeId = topNodeId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(int budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }

   public String getSelectionString() {
      return this.mSelectionString;
   }

   public void setSelectionString(String selectionString) {
      this.mSelectionString = selectionString;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public int getOrganisationId() {
      return this.mOrganisationId;
   }

   public void setOrganisationId(int organisationId) {
      this.mOrganisationId = organisationId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }
}
