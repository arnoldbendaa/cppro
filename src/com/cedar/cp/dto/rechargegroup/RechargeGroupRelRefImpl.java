// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.rechargegroup;

import com.cedar.cp.api.rechargegroup.RechargeGroupRelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelCK;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK;
import java.io.Serializable;

public class RechargeGroupRelRefImpl extends EntityRefImpl implements RechargeGroupRelRef, Serializable {

   public RechargeGroupRelRefImpl(RechargeGroupRelCK key, String narrative) {
      super(key, narrative);
   }

   public RechargeGroupRelRefImpl(RechargeGroupRelPK key, String narrative) {
      super(key, narrative);
   }

   public RechargeGroupRelPK getRechargeGroupRelPK() {
      return this.mKey instanceof RechargeGroupRelCK?((RechargeGroupRelCK)this.mKey).getRechargeGroupRelPK():(RechargeGroupRelPK)this.mKey;
   }
}
