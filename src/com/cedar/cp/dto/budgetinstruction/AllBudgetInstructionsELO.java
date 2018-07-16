// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllBudgetInstructionsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetInstruction", "BudgetInstructionAssignment"};
   private transient BudgetInstructionRef mBudgetInstructionEntityRef;
   private transient String mDocumentRef;


   public AllBudgetInstructionsELO() {
      super(new String[]{"BudgetInstruction", "DocumentRef"});
   }

   public void add(BudgetInstructionRef eRefBudgetInstruction, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetInstruction);
      l.add(col1);
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
      this.mBudgetInstructionEntityRef = (BudgetInstructionRef)l.get(index);
      this.mDocumentRef = (String)l.get(var4++);
   }

   public BudgetInstructionRef getBudgetInstructionEntityRef() {
      return this.mBudgetInstructionEntityRef;
   }

   public String getDocumentRef() {
      return this.mDocumentRef;
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
