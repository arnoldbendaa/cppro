// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.workbench;

import com.cedar.cp.utc.common.CPForm;

public class BaseWorkBenchForm extends CPForm {

   private int mModelId;
   private int mBudgetCycleId;
   private int mStructureId;
   private int mStructureElementId;
   private boolean mController;


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

   public int getStructureId() {
      return this.mStructureId;
   }

   public void setStructureId(int structureId) {
      this.mStructureId = structureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public void setStructureElementId(int structureElementId) {
      this.mStructureElementId = structureElementId;
   }

   public boolean isController() {
      return this.mController;
   }

   public void setController(boolean controller) {
      this.mController = controller;
   }
}
