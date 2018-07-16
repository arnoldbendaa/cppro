package com.cedar.cp.ejb.api.masterquestion;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionCSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionSSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface MasterQuestionEditorSessionRemote extends EJBObject
{
  public abstract MasterQuestionEditorSessionSSO getItemData(int paramInt, Object paramObject)
    throws ValidationException, RemoteException;

  public abstract MasterQuestionEditorSessionSSO getNewItemData(int paramInt)
    throws RemoteException;

  public abstract MasterQuestionPK insert(MasterQuestionEditorSessionCSO paramMasterQuestionEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract MasterQuestionPK copy(MasterQuestionEditorSessionCSO paramMasterQuestionEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract void update(MasterQuestionEditorSessionCSO paramMasterQuestionEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract void delete(int paramInt, Object paramObject)
    throws ValidationException, RemoteException;
}