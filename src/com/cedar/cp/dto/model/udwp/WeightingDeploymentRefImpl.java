// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingDeploymentRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
import java.io.Serializable;

public class WeightingDeploymentRefImpl extends EntityRefImpl implements WeightingDeploymentRef, Serializable {

   public WeightingDeploymentRefImpl(WeightingDeploymentCK key, String narrative) {
      super(key, narrative);
   }

   public WeightingDeploymentRefImpl(WeightingDeploymentPK key, String narrative) {
      super(key, narrative);
   }

   public WeightingDeploymentPK getWeightingDeploymentPK() {
      return this.mKey instanceof WeightingDeploymentCK?((WeightingDeploymentCK)this.mKey).getWeightingDeploymentPK():(WeightingDeploymentPK)this.mKey;
   }
}
