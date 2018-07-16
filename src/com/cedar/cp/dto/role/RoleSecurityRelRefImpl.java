// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.role;

import com.cedar.cp.api.role.RoleSecurityRelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.role.RoleSecurityRelCK;
import com.cedar.cp.dto.role.RoleSecurityRelPK;
import java.io.Serializable;

public class RoleSecurityRelRefImpl extends EntityRefImpl implements RoleSecurityRelRef, Serializable {

   public RoleSecurityRelRefImpl(RoleSecurityRelCK key, String narrative) {
      super(key, narrative);
   }

   public RoleSecurityRelRefImpl(RoleSecurityRelPK key, String narrative) {
      super(key, narrative);
   }

   public RoleSecurityRelPK getRoleSecurityRelPK() {
      return this.mKey instanceof RoleSecurityRelCK?((RoleSecurityRelCK)this.mKey).getRoleSecurityRelPK():(RoleSecurityRelPK)this.mKey;
   }
}
