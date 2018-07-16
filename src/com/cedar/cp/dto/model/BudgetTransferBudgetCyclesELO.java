// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetTransferBudgetCyclesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetCycle", "Model", "BudgetState", "BudgetStateHistory", "LevelDate", "FinanceCube", "BudgetInstructionAssignment"};
   private transient BudgetCycleRef mBudgetCycleEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient int mModelId;
   private transient int mFinanceCubeId;
   private transient int mBudgetCycleId;
   private transient String mDescription;
   private transient int mPeriodId;


   public BudgetTransferBudgetCyclesELO() {
      super(new String[]{"BudgetCycle", "Model", "FinanceCube", "ModelId", "FinanceCubeId", "BudgetCycleId", "Description", "PeriodId"});
   }

   public void add(BudgetCycleRef eRefBudgetCycle, ModelRef eRefModel, FinanceCubeRef eRefFinanceCube, int col1, int col2, int col3, String col4, int col5) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetCycle);
      l.add(eRefModel);
      l.add(eRefFinanceCube);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(col4);
      l.add(new Integer(col5));
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
      this.mBudgetCycleEntityRef = (BudgetCycleRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
      this.mBudgetCycleId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mPeriodId = ((Integer)l.get(var4++)).intValue();
   }

   public BudgetCycleRef getBudgetCycleEntityRef() {
      return this.mBudgetCycleEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
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

   public String getDescription() {
      return this.mDescription;
   }

   public int getPeriodId() {
      return this.mPeriodId;
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
