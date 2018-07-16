// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.SecurityRangeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangePK;
import java.io.Serializable;

public class SecurityRangeRefImpl extends EntityRefImpl implements SecurityRangeRef, Serializable {

   public SecurityRangeRefImpl(SecurityRangeCK key, String narrative) {
      super(key, narrative);
   }

   public SecurityRangeRefImpl(SecurityRangePK key, String narrative) {
      super(key, narrative);
   }

   public SecurityRangePK getSecurityRangePK() {
      return this.mKey instanceof SecurityRangeCK?((SecurityRangeCK)this.mKey).getSecurityRangePK():(SecurityRangePK)this.mKey;
   }
}
