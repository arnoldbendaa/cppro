package com.cedar.cp.api.reset;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public abstract interface UserResetLinksProcess extends BusinessProcess
{
  public abstract EntityList getAllUserResetLinks();

  public abstract EntityList getLinkByUserID(int paramInt);

  public abstract UserResetLinkEditorSession getUserResetLinkEditorSession(Object paramObject)
    throws ValidationException;
}