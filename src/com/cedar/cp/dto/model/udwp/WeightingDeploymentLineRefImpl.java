// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingDeploymentLineRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK;
import java.io.Serializable;

public class WeightingDeploymentLineRefImpl extends EntityRefImpl implements WeightingDeploymentLineRef, Serializable {

   public WeightingDeploymentLineRefImpl(WeightingDeploymentLineCK key, String narrative) {
      super(key, narrative);
   }

   public WeightingDeploymentLineRefImpl(WeightingDeploymentLinePK key, String narrative) {
      super(key, narrative);
   }

   public WeightingDeploymentLinePK getWeightingDeploymentLinePK() {
      return this.mKey instanceof WeightingDeploymentLineCK?((WeightingDeploymentLineCK)this.mKey).getWeightingDeploymentLinePK():(WeightingDeploymentLinePK)this.mKey;
   }
}
