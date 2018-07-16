// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmDimensionElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.amm.AmmDimensionElementCK;
import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
import java.io.Serializable;

public class AmmDimensionElementRefImpl extends EntityRefImpl implements AmmDimensionElementRef, Serializable {

   public AmmDimensionElementRefImpl(AmmDimensionElementCK key, String narrative) {
      super(key, narrative);
   }

   public AmmDimensionElementRefImpl(AmmDimensionElementPK key, String narrative) {
      super(key, narrative);
   }

   public AmmDimensionElementPK getAmmDimensionElementPK() {
      return this.mKey instanceof AmmDimensionElementCK?((AmmDimensionElementCK)this.mKey).getAmmDimensionElementPK():(AmmDimensionElementPK)this.mKey;
   }
}
