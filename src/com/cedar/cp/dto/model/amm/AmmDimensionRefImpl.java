// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmDimensionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.amm.AmmDimensionCK;
import com.cedar.cp.dto.model.amm.AmmDimensionPK;
import java.io.Serializable;

public class AmmDimensionRefImpl extends EntityRefImpl implements AmmDimensionRef, Serializable {

   public AmmDimensionRefImpl(AmmDimensionCK key, String narrative) {
      super(key, narrative);
   }

   public AmmDimensionRefImpl(AmmDimensionPK key, String narrative) {
      super(key, narrative);
   }

   public AmmDimensionPK getAmmDimensionPK() {
      return this.mKey instanceof AmmDimensionCK?((AmmDimensionCK)this.mKey).getAmmDimensionPK():(AmmDimensionPK)this.mKey;
   }
}
