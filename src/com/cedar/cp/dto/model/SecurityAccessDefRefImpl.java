// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.SecurityAccessDefCK;
import com.cedar.cp.dto.model.SecurityAccessDefPK;
import java.io.Serializable;

public class SecurityAccessDefRefImpl extends EntityRefImpl implements SecurityAccessDefRef, Serializable {

   public SecurityAccessDefRefImpl(SecurityAccessDefCK key, String narrative) {
      super(key, narrative);
   }

   public SecurityAccessDefRefImpl(SecurityAccessDefPK key, String narrative) {
      super(key, narrative);
   }

   public SecurityAccessDefPK getSecurityAccessDefPK() {
      return this.mKey instanceof SecurityAccessDefCK?((SecurityAccessDefCK)this.mKey).getSecurityAccessDefPK():(SecurityAccessDefPK)this.mKey;
   }
}
