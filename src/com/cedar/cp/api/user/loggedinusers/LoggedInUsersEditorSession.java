package com.cedar.cp.api.user.loggedinusers;

import com.cedar.cp.api.user.loggedinusers.LoggedInUsersEditor;
import com.cedar.cp.api.base.BusinessSession;

public interface LoggedInUsersEditorSession extends BusinessSession {

   LoggedInUsersEditor getLoggedInUsersEditor();
}
