package com.cedar.cp.api.reset;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.UserRef;

public abstract interface UserResetLinkEditor extends BusinessEditor
{
  public abstract void setUserRef(UserRef paramUserRef)
    throws ValidationException;

  public abstract EntityList getOwnershipRefs();

  public abstract UserResetLink getUserResetLink();
}