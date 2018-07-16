package com.cedar.cp.ejb.api.budgetlocation;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface UserModelSecurityEditorSessionHome extends EJBHome {

	UserModelSecurityEditorSessionRemote create() throws RemoteException, CreateException;
}