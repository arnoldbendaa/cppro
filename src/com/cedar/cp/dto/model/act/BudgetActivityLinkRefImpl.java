// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.act;

import com.cedar.cp.api.model.act.BudgetActivityLinkRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.act.BudgetActivityLinkCK;
import com.cedar.cp.dto.model.act.BudgetActivityLinkPK;
import java.io.Serializable;

public class BudgetActivityLinkRefImpl extends EntityRefImpl implements BudgetActivityLinkRef, Serializable {

   public BudgetActivityLinkRefImpl(BudgetActivityLinkCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetActivityLinkRefImpl(BudgetActivityLinkPK key, String narrative) {
      super(key, narrative);
   }

   public BudgetActivityLinkPK getBudgetActivityLinkPK() {
      return this.mKey instanceof BudgetActivityLinkCK?((BudgetActivityLinkCK)this.mKey).getBudgetActivityLinkPK():(BudgetActivityLinkPK)this.mKey;
   }
}
