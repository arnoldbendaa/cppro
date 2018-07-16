package com.cedar.cp.ejb.api.masterquestion;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface MasterQuestionEditorSessionHome extends EJBHome
{
  public abstract MasterQuestionEditorSessionRemote create()
    throws RemoteException, CreateException;
}