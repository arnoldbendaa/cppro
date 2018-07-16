// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import java.io.Serializable;

public class BudgetCycleRefImpl extends EntityRefImpl implements BudgetCycleRef, Serializable {

   public BudgetCycleRefImpl(BudgetCycleCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetCycleRefImpl(BudgetCyclePK key, String narrative) {
      super(key, narrative);
   }

   public BudgetCyclePK getBudgetCyclePK() {
      return this.mKey instanceof BudgetCycleCK?((BudgetCycleCK)this.mKey).getBudgetCyclePK():(BudgetCyclePK)this.mKey;
   }
}
