// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.act;

import com.cedar.cp.api.model.act.BudgetActivityRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.act.BudgetActivityCK;
import com.cedar.cp.dto.model.act.BudgetActivityPK;
import java.io.Serializable;

public class BudgetActivityRefImpl extends EntityRefImpl implements BudgetActivityRef, Serializable {

   public BudgetActivityRefImpl(BudgetActivityCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetActivityRefImpl(BudgetActivityPK key, String narrative) {
      super(key, narrative);
   }

   public BudgetActivityPK getBudgetActivityPK() {
      return this.mKey instanceof BudgetActivityCK?((BudgetActivityCK)this.mKey).getBudgetActivityPK():(BudgetActivityPK)this.mKey;
   }
}
