// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignmentRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import java.io.Serializable;

public class BudgetInstructionAssignmentRefImpl extends EntityRefImpl implements BudgetInstructionAssignmentRef, Serializable {

   public BudgetInstructionAssignmentRefImpl(BudgetInstructionAssignmentCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetInstructionAssignmentRefImpl(BudgetInstructionAssignmentPK key, String narrative) {
      super(key, narrative);
   }

   public BudgetInstructionAssignmentPK getBudgetInstructionAssignmentPK() {
      return this.mKey instanceof BudgetInstructionAssignmentCK?((BudgetInstructionAssignmentCK)this.mKey).getBudgetInstructionAssignmentPK():(BudgetInstructionAssignmentPK)this.mKey;
   }
}
