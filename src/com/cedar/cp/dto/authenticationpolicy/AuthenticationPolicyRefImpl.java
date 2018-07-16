// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyRef;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class AuthenticationPolicyRefImpl extends EntityRefImpl implements AuthenticationPolicyRef, Serializable {

   public AuthenticationPolicyRefImpl(AuthenticationPolicyPK key, String narrative) {
      super(key, narrative);
   }

   public AuthenticationPolicyPK getAuthenticationPolicyPK() {
      return (AuthenticationPolicyPK)this.mKey;
   }
}
