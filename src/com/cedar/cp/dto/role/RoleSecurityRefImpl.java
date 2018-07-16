// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.api.role.RoleSecurityRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.role.RoleSecurityPK;
import java.io.Serializable;

public class RoleSecurityRefImpl extends EntityRefImpl implements RoleSecurityRef, Serializable {

   public RoleSecurityRefImpl(RoleSecurityPK key, String narrative) {
      super(key, narrative);
   }

   public RoleSecurityPK getRoleSecurityPK() {
      return (RoleSecurityPK)this.mKey;
   }
}
