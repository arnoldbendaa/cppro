// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.user.UserPK;
import java.io.Serializable;

public class UserRefImpl extends EntityRefImpl implements UserRef, Serializable {

   public UserRefImpl(UserPK key, String narrative) {
      super(key, narrative);
   }

   public UserPK getUserPK() {
      return (UserPK)this.mKey;
   }
}
