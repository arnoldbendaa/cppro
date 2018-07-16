package com.cedar.cp.ejb.api.user.loggedinusers;

import com.cedar.cp.ejb.api.user.loggedinusers.LoggedInUsersEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface LoggedInUsersEditorSessionLocalHome extends EJBLocalHome {

	LoggedInUsersEditorSessionLocal create() throws CreateException;
}
