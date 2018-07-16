// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.SecurityGroupEditor;

public interface SecurityGroupEditorSession extends BusinessSession {

   SecurityGroupEditor getSecurityGroupEditor();

   EntityList getAvailableModels();

   EntityList getOwnershipRefs();
}
