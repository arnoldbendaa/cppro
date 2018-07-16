// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmFinanceCubeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.amm.AmmFinanceCubeCK;
import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
import java.io.Serializable;

public class AmmFinanceCubeRefImpl extends EntityRefImpl implements AmmFinanceCubeRef, Serializable {

   public AmmFinanceCubeRefImpl(AmmFinanceCubeCK key, String narrative) {
      super(key, narrative);
   }

   public AmmFinanceCubeRefImpl(AmmFinanceCubePK key, String narrative) {
      super(key, narrative);
   }

   public AmmFinanceCubePK getAmmFinanceCubePK() {
      return this.mKey instanceof AmmFinanceCubeCK?((AmmFinanceCubeCK)this.mKey).getAmmFinanceCubePK():(AmmFinanceCubePK)this.mKey;
   }
}
