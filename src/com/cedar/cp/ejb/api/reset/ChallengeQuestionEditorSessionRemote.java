package com.cedar.cp.ejb.api.reset;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionCSO;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface ChallengeQuestionEditorSessionRemote extends EJBObject
{
  public abstract ChallengeQuestionEditorSessionSSO getItemData(int paramInt, Object paramObject)
    throws ValidationException, RemoteException;

  public abstract ChallengeQuestionEditorSessionSSO getNewItemData(int paramInt)
    throws RemoteException;

  public abstract ChallengeQuestionCK insert(ChallengeQuestionEditorSessionCSO paramChallengeQuestionEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract EntityList getOwnershipData(int paramInt, Object paramObject)
    throws RemoteException;

  public abstract ChallengeQuestionCK copy(ChallengeQuestionEditorSessionCSO paramChallengeQuestionEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract void update(ChallengeQuestionEditorSessionCSO paramChallengeQuestionEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract void delete(int paramInt, Object paramObject)
    throws ValidationException, RemoteException;
}