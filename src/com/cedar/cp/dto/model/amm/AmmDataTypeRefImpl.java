// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmDataTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.amm.AmmDataTypeCK;
import com.cedar.cp.dto.model.amm.AmmDataTypePK;
import java.io.Serializable;

public class AmmDataTypeRefImpl extends EntityRefImpl implements AmmDataTypeRef, Serializable {

   public AmmDataTypeRefImpl(AmmDataTypeCK key, String narrative) {
      super(key, narrative);
   }

   public AmmDataTypeRefImpl(AmmDataTypePK key, String narrative) {
      super(key, narrative);
   }

   public AmmDataTypePK getAmmDataTypePK() {
      return this.mKey instanceof AmmDataTypeCK?((AmmDataTypeCK)this.mKey).getAmmDataTypePK():(AmmDataTypePK)this.mKey;
   }
}
