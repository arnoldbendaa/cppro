// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllBudgetInstructionsForCycleELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetInstruction", "BudgetInstructionAssignment", "BudgetInstructionAssignment"};
   private transient int mBudgetInstructionId;
   private transient String mVisId;


   public AllBudgetInstructionsForCycleELO() {
      super(new String[]{"BudgetInstructionId", "VisId"});
   }

   public void add(int col1, String col2) {
      ArrayList l = new ArrayList();
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
      this.mBudgetInstructionId = ((Integer)l.get(index)).intValue();
      this.mVisId = (String)l.get(var4++);
   }

   public int getBudgetInstructionId() {
      return this.mBudgetInstructionId;
   }

   public String getVisId() {
      return this.mVisId;
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