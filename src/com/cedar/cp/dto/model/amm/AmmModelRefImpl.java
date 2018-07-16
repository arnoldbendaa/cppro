// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmModelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import java.io.Serializable;

public class AmmModelRefImpl extends EntityRefImpl implements AmmModelRef, Serializable {

   public AmmModelRefImpl(AmmModelPK key, String narrative) {
      super(key, narrative);
   }

   public AmmModelPK getAmmModelPK() {
      return (AmmModelPK)this.mKey;
   }
}
