package com.cedar.cp.ejb.api.reset;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface ChallengeQuestionEditorSessionHome extends EJBHome
{
  public abstract ChallengeQuestionEditorSessionRemote create()
    throws RemoteException, CreateException;
}