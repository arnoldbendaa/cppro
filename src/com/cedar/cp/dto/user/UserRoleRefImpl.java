// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRoleRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.user.UserRoleCK;
import com.cedar.cp.dto.user.UserRolePK;
import java.io.Serializable;

public class UserRoleRefImpl extends EntityRefImpl implements UserRoleRef, Serializable {

   public UserRoleRefImpl(UserRoleCK key, String narrative) {
      super(key, narrative);
   }

   public UserRoleRefImpl(UserRolePK key, String narrative) {
      super(key, narrative);
   }

   public UserRolePK getUserRolePK() {
      return this.mKey instanceof UserRoleCK?((UserRoleCK)this.mKey).getUserRolePK():(UserRolePK)this.mKey;
   }
}
