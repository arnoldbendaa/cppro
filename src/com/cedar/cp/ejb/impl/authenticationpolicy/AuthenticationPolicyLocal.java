// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import javax.ejb.EJBLocalObject;

public interface AuthenticationPolicyLocal extends EJBLocalObject {

   AuthenticationPolicyEVO getDetails(String var1) throws ValidationException;

   AuthenticationPolicyPK generateKeys();

   void setDetails(AuthenticationPolicyEVO var1);

   AuthenticationPolicyEVO setAndGetDetails(AuthenticationPolicyEVO var1, String var2);
}
