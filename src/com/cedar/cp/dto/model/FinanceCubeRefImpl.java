// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import java.io.Serializable;

public class FinanceCubeRefImpl extends EntityRefImpl implements FinanceCubeRef, Serializable {

   public FinanceCubeRefImpl(FinanceCubeCK key, String narrative) {
      super(key, narrative);
   }

   public FinanceCubeRefImpl(FinanceCubePK key, String narrative) {
      super(key, narrative);
   }

   public FinanceCubePK getFinanceCubePK() {
      return this.mKey instanceof FinanceCubeCK?((FinanceCubeCK)this.mKey).getFinanceCubePK():(FinanceCubePK)this.mKey;
   }
}
