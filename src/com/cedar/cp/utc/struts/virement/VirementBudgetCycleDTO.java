// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import java.io.Serializable;

public class VirementBudgetCycleDTO implements Serializable {

   private int mModelId;
   private String mModelVisId;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;
   private int mBudgetCycleId;
   private String mBudgetCycleVisId;
   private String mBudgetCycleDescription;


   public VirementBudgetCycleDTO(int modelId, String modelVisId, int financeCubeId, String financeCubeVisId, int budgetCycleId, String budgetCycleVisId, String budgetCycleDescription) {
      this.mModelId = modelId;
      this.mModelVisId = modelVisId;
      this.mFinanceCubeId = financeCubeId;
      this.mFinanceCubeVisId = financeCubeVisId;
      this.mBudgetCycleId = budgetCycleId;
      this.mBudgetCycleVisId = budgetCycleVisId;
      this.mBudgetCycleDescription = budgetCycleDescription;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public String getBudgetCycleVisId() {
      return this.mBudgetCycleVisId;
   }

   public String getBudgetCycleDescription() {
      return this.mBudgetCycleDescription;
   }
}
