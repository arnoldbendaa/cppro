// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.defaultuserpref;

import com.cedar.cp.api.defaultuserpref.DefaultUserPrefRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
import java.io.Serializable;

public class DefaultUserPrefRefImpl extends EntityRefImpl implements DefaultUserPrefRef, Serializable {

   public DefaultUserPrefRefImpl(DefaultUserPrefPK key, String narrative) {
      super(key, narrative);
   }

   public DefaultUserPrefPK getDefaultUserPrefPK() {
      return (DefaultUserPrefPK)this.mKey;
   }
}
