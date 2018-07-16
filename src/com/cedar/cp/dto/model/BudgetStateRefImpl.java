// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetStateRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.BudgetStateCK;
import com.cedar.cp.dto.model.BudgetStatePK;
import java.io.Serializable;

public class BudgetStateRefImpl extends EntityRefImpl implements BudgetStateRef, Serializable {

   public BudgetStateRefImpl(BudgetStateCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetStateRefImpl(BudgetStatePK key, String narrative) {
      super(key, narrative);
   }

   public BudgetStatePK getBudgetStatePK() {
      return this.mKey instanceof BudgetStateCK?((BudgetStateCK)this.mKey).getBudgetStatePK():(BudgetStatePK)this.mKey;
   }
}
