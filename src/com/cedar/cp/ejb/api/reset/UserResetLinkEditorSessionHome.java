package com.cedar.cp.ejb.api.reset;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface UserResetLinkEditorSessionHome extends EJBHome
{
  public abstract UserResetLinkEditorSessionRemote create()
    throws RemoteException, CreateException;
}