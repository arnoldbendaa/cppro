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

public class AllBudgetCyclesWebELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetCycle", "Model", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetInstructionAssignment"};
   private transient BudgetCycleRef mBudgetCycleEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mBudgetCycleId;
   private transient String mDescription;


   public AllBudgetCyclesWebELO() {
      super(new String[]{"BudgetCycle", "Model", "BudgetCycleId", "Description"});
   }

   public void add(BudgetCycleRef eRefBudgetCycle, ModelRef eRefModel, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetCycle);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(col2);
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
      this.mBudgetCycleId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public BudgetCycleRef getBudgetCycleEntityRef() {
      return this.mBudgetCycleEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public String getDescription() {
      return this.mDescription;
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
