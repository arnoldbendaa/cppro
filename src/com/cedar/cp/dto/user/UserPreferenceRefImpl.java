// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserPreferenceRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.user.UserPreferenceCK;
import com.cedar.cp.dto.user.UserPreferencePK;
import java.io.Serializable;

public class UserPreferenceRefImpl extends EntityRefImpl implements UserPreferenceRef, Serializable {

   public UserPreferenceRefImpl(UserPreferenceCK key, String narrative) {
      super(key, narrative);
   }

   public UserPreferenceRefImpl(UserPreferencePK key, String narrative) {
      super(key, narrative);
   }

   public UserPreferencePK getUserPreferencePK() {
      return this.mKey instanceof UserPreferenceCK?((UserPreferenceCK)this.mKey).getUserPreferencePK():(UserPreferencePK)this.mKey;
   }
}
