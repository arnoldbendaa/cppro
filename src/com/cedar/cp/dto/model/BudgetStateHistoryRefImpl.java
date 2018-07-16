// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetStateHistoryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.BudgetStateHistoryCK;
import com.cedar.cp.dto.model.BudgetStateHistoryPK;
import java.io.Serializable;

public class BudgetStateHistoryRefImpl extends EntityRefImpl implements BudgetStateHistoryRef, Serializable {

   public BudgetStateHistoryRefImpl(BudgetStateHistoryCK key, String narrative) {
      super(key, narrative);
   }

   public BudgetStateHistoryRefImpl(BudgetStateHistoryPK key, String narrative) {
      super(key, narrative);
   }

   public BudgetStateHistoryPK getBudgetStateHistoryPK() {
      return this.mKey instanceof BudgetStateHistoryCK?((BudgetStateHistoryCK)this.mKey).getBudgetStateHistoryPK():(BudgetStateHistoryPK)this.mKey;
   }
}
