// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import java.io.Serializable;

public class DistributionRefImpl extends EntityRefImpl implements DistributionRef, Serializable {

   public DistributionRefImpl(DistributionPK key, String narrative) {
      super(key, narrative);
   }

   public DistributionPK getDistributionPK() {
      return (DistributionPK)this.mKey;
   }
}
