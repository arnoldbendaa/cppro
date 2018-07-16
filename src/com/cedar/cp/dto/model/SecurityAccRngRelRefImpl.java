// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.SecurityAccRngRelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.SecurityAccRngRelCK;
import com.cedar.cp.dto.model.SecurityAccRngRelPK;
import java.io.Serializable;

public class SecurityAccRngRelRefImpl extends EntityRefImpl implements SecurityAccRngRelRef, Serializable {

   public SecurityAccRngRelRefImpl(SecurityAccRngRelCK key, String narrative) {
      super(key, narrative);
   }

   public SecurityAccRngRelRefImpl(SecurityAccRngRelPK key, String narrative) {
      super(key, narrative);
   }

   public SecurityAccRngRelPK getSecurityAccRngRelPK() {
      return this.mKey instanceof SecurityAccRngRelCK?((SecurityAccRngRelCK)this.mKey).getSecurityAccRngRelPK():(SecurityAccRngRelPK)this.mKey;
   }
}
