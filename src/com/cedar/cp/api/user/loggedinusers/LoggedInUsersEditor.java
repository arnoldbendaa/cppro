package com.cedar.cp.api.user.loggedinusers;

import com.cedar.cp.api.user.loggedinusers.LoggedInUsers;
import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;

public interface LoggedInUsersEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   LoggedInUsers getLoggedInUsers();
}
