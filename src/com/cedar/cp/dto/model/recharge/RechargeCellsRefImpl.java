// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.api.model.recharge.RechargeCellsRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.recharge.RechargeCellsCK;
import com.cedar.cp.dto.model.recharge.RechargeCellsPK;
import java.io.Serializable;

public class RechargeCellsRefImpl extends EntityRefImpl implements RechargeCellsRef, Serializable {

   public RechargeCellsRefImpl(RechargeCellsCK key, String narrative) {
      super(key, narrative);
   }

   public RechargeCellsRefImpl(RechargeCellsPK key, String narrative) {
      super(key, narrative);
   }

   public RechargeCellsPK getRechargeCellsPK() {
      return this.mKey instanceof RechargeCellsCK?((RechargeCellsCK)this.mKey).getRechargeCellsPK():(RechargeCellsPK)this.mKey;
   }
}
