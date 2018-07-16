// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.gadget.apps;

import com.cedar.cp.utc.common.CPForm;

public class GadgetForm extends CPForm {

   private int mTopNodeId;
   private int mModelId;
   private int mBudgetCycleId;
   private int mSelectedStructureElementId;


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

   public int getSelectedStructureElementId() {
      return this.mSelectedStructureElementId;
   }

   public void setSelectedStructureElementId(int selectedStructureElementId) {
      this.mSelectedStructureElementId = selectedStructureElementId;
   }
}
