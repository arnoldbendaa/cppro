// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignmentRef;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllBudgetInstructionAssignmentsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetInstructionAssignment", "BudgetInstruction", "Model", "FinanceCube", "BudgetCycle", "StructureElement"};
   private transient BudgetInstructionAssignmentRef mBudgetInstructionAssignmentEntityRef;
   private transient BudgetInstructionRef mBudgetInstructionEntityRef;
   private transient int mModelId;
   private transient int mFinanceCubeId;
   private transient int mBudgetCycleId;
   private transient int mBudgetLocationElementId;


   public AllBudgetInstructionAssignmentsELO() {
      super(new String[]{"BudgetInstructionAssignment", "BudgetInstruction", "ModelId", "FinanceCubeId", "BudgetCycleId", "BudgetLocationElementId"});
   }

   public void add(BudgetInstructionAssignmentRef eRefBudgetInstructionAssignment, BudgetInstructionRef eRefBudgetInstruction, int col1, int col2, int col3, int col4) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetInstructionAssignment);
      l.add(eRefBudgetInstruction);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(new Integer(col4));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mBudgetInstructionAssignmentEntityRef = (BudgetInstructionAssignmentRef)l.get(index);
      this.mBudgetInstructionEntityRef = (BudgetInstructionRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
      this.mBudgetCycleId = ((Integer)l.get(var4++)).intValue();
      this.mBudgetLocationElementId = ((Integer)l.get(var4++)).intValue();
   }

   public BudgetInstructionAssignmentRef getBudgetInstructionAssignmentEntityRef() {
      return this.mBudgetInstructionAssignmentEntityRef;
   }

   public BudgetInstructionRef getBudgetInstructionEntityRef() {
      return this.mBudgetInstructionEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getBudgetLocationElementId() {
      return this.mBudgetLocationElementId;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
