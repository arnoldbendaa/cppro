// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.budgetinstruction;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;

public class BudgetInstructionForm extends CPForm {

   private int mModelId;
   private int mBudgetCycleId;
   private int mStructureId;
   private int mStructureElementId;
   private List mModelInstructions;
   private List mCycleInstructions;
   private List mStructureElementInstructions;


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

   public List getModelInstructions() {
      return this.mModelInstructions;
   }

   public void setModelInstructions(List modelInstructions) {
      this.mModelInstructions = modelInstructions;
   }

   public List getCycleInstructions() {
      return this.mCycleInstructions;
   }

   public void setCycleInstructions(List cycleInstructions) {
      this.mCycleInstructions = cycleInstructions;
   }

   public List getStructureElementInstructions() {
      return this.mStructureElementInstructions;
   }

   public void setStructureElementInstructions(List structureElementInstructions) {
      this.mStructureElementInstructions = structureElementInstructions;
   }
}
