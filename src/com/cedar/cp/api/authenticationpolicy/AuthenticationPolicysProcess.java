// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyEditorSession;
import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public interface AuthenticationPolicysProcess extends BusinessProcess {

   EntityList getAllAuthenticationPolicys();

   EntityList getActiveAuthenticationPolicys();

   EntityList getActiveAuthenticationPolicyForLogon();

   AuthenticationPolicyEditorSession getAuthenticationPolicyEditorSession(Object var1) throws ValidationException;

   int getActiveAuthenticationTechnique();
}
