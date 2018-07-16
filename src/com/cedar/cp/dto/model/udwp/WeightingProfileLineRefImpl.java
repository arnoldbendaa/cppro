// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingProfileLineRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfileLineCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
import java.io.Serializable;

public class WeightingProfileLineRefImpl extends EntityRefImpl implements WeightingProfileLineRef, Serializable {

   public WeightingProfileLineRefImpl(WeightingProfileLineCK key, String narrative) {
      super(key, narrative);
   }

   public WeightingProfileLineRefImpl(WeightingProfileLinePK key, String narrative) {
      super(key, narrative);
   }

   public WeightingProfileLinePK getWeightingProfileLinePK() {
      return this.mKey instanceof WeightingProfileLineCK?((WeightingProfileLineCK)this.mKey).getWeightingProfileLinePK():(WeightingProfileLinePK)this.mKey;
   }
}
