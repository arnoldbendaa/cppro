// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.budgetlimit;

import com.cedar.cp.api.model.budgetlimit.BudgetLimitRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
import java.io.Serializable;

public class BudgetLimitRefImpl extends EntityRefImpl implements BudgetLimitRef, Serializable {

   public BudgetLimitRefImpl(BudgetLimitCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetLimitRefImpl(BudgetLimitPK key, String narrative) {
      super(key, narrative);
   }

   public BudgetLimitPK getBudgetLimitPK() {
      return this.mKey instanceof BudgetLimitCK?((BudgetLimitCK)this.mKey).getBudgetLimitPK():(BudgetLimitPK)this.mKey;
   }
}
