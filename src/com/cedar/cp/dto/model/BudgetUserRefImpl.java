// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetUserRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.BudgetUserCK;
import com.cedar.cp.dto.model.BudgetUserPK;
import java.io.Serializable;

public class BudgetUserRefImpl extends EntityRefImpl implements BudgetUserRef, Serializable {

   public BudgetUserRefImpl(BudgetUserCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetUserRefImpl(BudgetUserPK key, String narrative) {
      super(key, narrative);
   }

   public BudgetUserPK getBudgetUserPK() {
      return this.mKey instanceof BudgetUserCK?((BudgetUserCK)this.mKey).getBudgetUserPK():(BudgetUserPK)this.mKey;
   }
}
