// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionLinkRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.distribution.DistributionLinkCK;
import com.cedar.cp.dto.report.distribution.DistributionLinkPK;
import java.io.Serializable;

public class DistributionLinkRefImpl extends EntityRefImpl implements DistributionLinkRef, Serializable {

   public DistributionLinkRefImpl(DistributionLinkCK key, String narrative) {
      super(key, narrative);
   }

   public DistributionLinkRefImpl(DistributionLinkPK key, String narrative) {
      super(key, narrative);
   }

   public DistributionLinkPK getDistributionLinkPK() {
      return this.mKey instanceof DistributionLinkCK?((DistributionLinkCK)this.mKey).getDistributionLinkPK():(DistributionLinkPK)this.mKey;
   }
}
