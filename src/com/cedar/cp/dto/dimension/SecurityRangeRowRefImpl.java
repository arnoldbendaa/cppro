// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.SecurityRangeRowRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.SecurityRangeRowCK;
import com.cedar.cp.dto.dimension.SecurityRangeRowPK;
import java.io.Serializable;

public class SecurityRangeRowRefImpl extends EntityRefImpl implements SecurityRangeRowRef, Serializable {

   public SecurityRangeRowRefImpl(SecurityRangeRowCK key, String narrative) {
      super(key, narrative);
   }

   public SecurityRangeRowRefImpl(SecurityRangeRowPK key, String narrative) {
      super(key, narrative);
   }

   public SecurityRangeRowPK getSecurityRangeRowPK() {
      return this.mKey instanceof SecurityRangeRowCK?((SecurityRangeRowCK)this.mKey).getSecurityRangeRowPK():(SecurityRangeRowPK)this.mKey;
   }
}
