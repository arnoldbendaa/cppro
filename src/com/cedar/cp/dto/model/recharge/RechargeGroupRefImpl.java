// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.api.model.recharge.RechargeGroupRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import java.io.Serializable;

public class RechargeGroupRefImpl extends EntityRefImpl implements RechargeGroupRef, Serializable {

   public RechargeGroupRefImpl(RechargeGroupPK key, String narrative) {
      super(key, narrative);
   }

   public RechargeGroupPK getRechargeGroupPK() {
      return (RechargeGroupPK)this.mKey;
   }
}
