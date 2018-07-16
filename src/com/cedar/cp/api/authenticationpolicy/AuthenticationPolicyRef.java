// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.authenticationpolicy;

import com.cedar.cp.api.base.EntityRef;

public interface AuthenticationPolicyRef extends EntityRef {

   int AUTHENTICATE_VIA_INTERNAL = 1;
   int AUTHENTICATE_VIA_LOGON_MODULE = 2;
   int AUTHENTICATE_VIA_COSIGN = 3;
   int AUTHENTICATE_VIA_NTLM = 4;
   int AUTHENTICATE_VIA_SSO = 5;
   int NONE = 1;
   int FAILED = 2;
   int FAILED_AND_SUCCESSFUL = 3;
   int ALL = 4;

}
