// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import java.io.Serializable;

public class BudgetInstructionRefImpl extends EntityRefImpl implements BudgetInstructionRef, Serializable {

   public BudgetInstructionRefImpl(BudgetInstructionPK key, String narrative) {
      super(key, narrative);
   }

   public BudgetInstructionPK getBudgetInstructionPK() {
      return (BudgetInstructionPK)this.mKey;
   }
}
