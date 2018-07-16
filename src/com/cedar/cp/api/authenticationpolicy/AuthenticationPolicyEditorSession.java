// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyEditor;
import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;

public interface AuthenticationPolicyEditorSession extends BusinessSession {

   AuthenticationPolicyEditor getAuthenticationPolicyEditor();

   EntityList getSecurityAdminList();
}
