// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.role.RolePK;
import java.io.Serializable;

public class RoleRefImpl extends EntityRefImpl implements RoleRef, Serializable {

   public RoleRefImpl(RolePK key, String narrative) {
      super(key, narrative);
   }

   public RolePK getRolePK() {
      return (RolePK)this.mKey;
   }
}
