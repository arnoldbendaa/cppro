// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.api.model.recharge.RechargeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.dto.model.recharge.RechargePK;
import java.io.Serializable;

public class RechargeRefImpl extends EntityRefImpl implements RechargeRef, Serializable {

   public RechargeRefImpl(RechargeCK key, String narrative) {
      super(key, narrative);
   }

   public RechargeRefImpl(RechargePK key, String narrative) {
      super(key, narrative);
   }

   public RechargePK getRechargePK() {
      return this.mKey instanceof RechargeCK?((RechargeCK)this.mKey).getRechargePK():(RechargePK)this.mKey;
   }
}
