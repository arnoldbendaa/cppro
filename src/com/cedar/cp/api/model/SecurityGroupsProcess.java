// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.SecurityGroupEditorSession;

public interface SecurityGroupsProcess extends BusinessProcess {

   EntityList getAllSecurityGroups();

   EntityList getAllSecurityGroupsUsingAccessDef(int var1);

   EntityList getAllSecurityGroupsForUser(int var1);

   SecurityGroupEditorSession getSecurityGroupEditorSession(Object var1) throws ValidationException;
}
