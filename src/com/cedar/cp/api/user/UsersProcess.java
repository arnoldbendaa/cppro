package com.cedar.cp.api.user;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.UserEditorSession;
import com.cedar.cp.api.user.UserRef;

public interface UsersProcess extends BusinessProcess {

   EntityList getSecurityStringsForUser(int var1);

   EntityList getAllUsersExport();

   EntityList getAllUserAttributes();

   EntityList getAllNonDisabledUsers();

   EntityList getUserMessageAttributes();

   EntityList getUserMessageAttributesForId(int var1);

   EntityList getUserMessageAttributesForName(String var1);

   EntityList getFinanceSystemUserName(int var1);

   EntityList getUsersWithSecurityString(String var1);

   UserEditorSession getUserEditorSession(Object var1) throws ValidationException;

   EntityList getUserPreferencesForUser(int var1) throws CPException;

   EntityList getExportResponsibilityAreaDetailsForUser(UserRef var1) throws CPException;

   UserEditorSession getUserEditorSession(int var1) throws ValidationException;

   EntityList getAllUserAssignments(String var1, String var2, String var3, String var4) throws CPException;

   EntityList getAllUserDetailsReport(String var1, String var2, String var3, boolean var4);
   
   EntityList getNodeAndUpUserAssignments(int paramInt1, int paramInt2) throws CPException;
   
   EntityList getAllUsers();
}
