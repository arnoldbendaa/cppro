package com.cedar.cp.ejb.api.user.loggedinusers;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionSSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionCSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionSSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface LoggedInUsersEditorSessionRemote extends EJBObject {

	LoggedInUsersEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

	LoggedInUsersEditorSessionSSO getNewItemData(int var1) throws RemoteException;

	LoggedInUsersPK insert(LoggedInUsersEditorSessionCSO var1) throws ValidationException, RemoteException;

	LoggedInUsersPK copy(LoggedInUsersEditorSessionCSO var1) throws ValidationException, RemoteException;

	void update(LoggedInUsersEditorSessionCSO var1) throws ValidationException, RemoteException;

	void delete(int var1, Object var2) throws ValidationException, RemoteException;

}
