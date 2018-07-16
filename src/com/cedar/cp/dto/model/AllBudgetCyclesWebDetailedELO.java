// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllBudgetCyclesWebDetailedELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetCycle", "Model", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetInstructionAssignment"};
   private transient BudgetCycleRef mBudgetCycleEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mModelId;
   private transient int mBudgetCycleId;
   private transient String mDescription;
   private transient int mStatus;


   public AllBudgetCyclesWebDetailedELO() {
      super(new String[]{"BudgetCycle", "Model", "ModelId", "BudgetCycleId", "Description", "Status"});
   }

   public void add(BudgetCycleRef eRefBudgetCycle, ModelRef eRefModel, int col1, int col2, String col3, int col4) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetCycle);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
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
      this.mBudgetCycleEntityRef = (BudgetCycleRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mBudgetCycleId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mStatus = ((Integer)l.get(var4++)).intValue();
   }

   public BudgetCycleRef getBudgetCycleEntityRef() {
      return this.mBudgetCycleEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getStatus() {
      return this.mStatus;
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
