package com.cedar.cp.ejb.api.user.loggedinusers;

import com.cedar.cp.ejb.api.user.loggedinusers.LoggedInUsersEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LoggedInUsersEditorSessionHome extends EJBHome {

	LoggedInUsersEditorSessionRemote create() throws RemoteException, CreateException;
}
