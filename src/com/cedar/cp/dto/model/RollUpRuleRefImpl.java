// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.RollUpRuleRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.RollUpRuleCK;
import com.cedar.cp.dto.model.RollUpRulePK;
import java.io.Serializable;

public class RollUpRuleRefImpl extends EntityRefImpl implements RollUpRuleRef, Serializable {

   public RollUpRuleRefImpl(RollUpRuleCK key, String narrative) {
      super(key, narrative);
   }

   public RollUpRuleRefImpl(RollUpRulePK key, String narrative) {
      super(key, narrative);
   }

   public RollUpRulePK getRollUpRulePK() {
      return this.mKey instanceof RollUpRuleCK?((RollUpRuleCK)this.mKey).getRollUpRulePK():(RollUpRulePK)this.mKey;
   }
}
