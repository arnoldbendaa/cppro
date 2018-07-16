package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface MasterQuestionRemote extends EJBObject
{
  public abstract MasterQuestionEVO getDetails(String paramString)
    throws ValidationException, RemoteException;

  public abstract MasterQuestionPK generateKeys();

  public abstract void setDetails(MasterQuestionEVO paramMasterQuestionEVO)
    throws RemoteException;

  public abstract MasterQuestionEVO setAndGetDetails(MasterQuestionEVO paramMasterQuestionEVO, String paramString)
    throws RemoteException;
}