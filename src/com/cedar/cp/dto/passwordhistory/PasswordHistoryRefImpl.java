// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.passwordhistory;

import com.cedar.cp.api.passwordhistory.PasswordHistoryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
import java.io.Serializable;

public class PasswordHistoryRefImpl extends EntityRefImpl implements PasswordHistoryRef, Serializable {

   public PasswordHistoryRefImpl(PasswordHistoryPK key, String narrative) {
      super(key, narrative);
   }

   public PasswordHistoryPK getPasswordHistoryPK() {
      return (PasswordHistoryPK)this.mKey;
   }
}
