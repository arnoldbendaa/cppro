package com.cedar.cp.ejb.api.reset;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionCSO;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public abstract interface UserResetLinkEditorSessionLocal extends EJBLocalObject
{
  public abstract UserResetLinkEditorSessionSSO getItemData(int paramInt, Object paramObject)
    throws ValidationException, EJBException;

  public abstract UserResetLinkEditorSessionSSO getNewItemData(int paramInt)
    throws EJBException;

  public abstract UserResetLinkCK insert(UserResetLinkEditorSessionCSO paramUserResetLinkEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract EntityList getOwnershipData(int paramInt, Object paramObject)
    throws EJBException;

  public abstract UserResetLinkCK copy(UserResetLinkEditorSessionCSO paramUserResetLinkEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract void update(UserResetLinkEditorSessionCSO paramUserResetLinkEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract void delete(int paramInt, Object paramObject)
    throws ValidationException, EJBException;
}