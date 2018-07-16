package com.cedar.cp.ejb.api.reset;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionCSO;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface UserResetLinkEditorSessionRemote extends EJBObject
{
  public abstract UserResetLinkEditorSessionSSO getItemData(int paramInt, Object paramObject)
    throws ValidationException, RemoteException;

  public abstract UserResetLinkEditorSessionSSO getNewItemData(int paramInt)
    throws RemoteException;

  public abstract UserResetLinkCK insert(UserResetLinkEditorSessionCSO paramUserResetLinkEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract EntityList getOwnershipData(int paramInt, Object paramObject)
    throws RemoteException;

  public abstract UserResetLinkCK copy(UserResetLinkEditorSessionCSO paramUserResetLinkEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract void update(UserResetLinkEditorSessionCSO paramUserResetLinkEditorSessionCSO)
    throws ValidationException, RemoteException;

  public abstract void delete(int paramInt, Object paramObject)
    throws ValidationException, RemoteException;
}