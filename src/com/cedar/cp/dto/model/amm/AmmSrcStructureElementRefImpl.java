// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmSrcStructureElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementCK;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK;
import java.io.Serializable;

public class AmmSrcStructureElementRefImpl extends EntityRefImpl implements AmmSrcStructureElementRef, Serializable {

   public AmmSrcStructureElementRefImpl(AmmSrcStructureElementCK key, String narrative) {
      super(key, narrative);
   }

   public AmmSrcStructureElementRefImpl(AmmSrcStructureElementPK key, String narrative) {
      super(key, narrative);
   }

   public AmmSrcStructureElementPK getAmmSrcStructureElementPK() {
      return this.mKey instanceof AmmSrcStructureElementCK?((AmmSrcStructureElementCK)this.mKey).getAmmSrcStructureElementPK():(AmmSrcStructureElementPK)this.mKey;
   }
}
