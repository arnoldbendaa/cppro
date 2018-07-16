// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.role;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.role.RoleEditorSession;

public interface RolesProcess extends BusinessProcess {

   EntityList getAllRoles();

   EntityList getAllRolesForUser(int var1);

   RoleEditorSession getRoleEditorSession(Object var1) throws ValidationException;
}
