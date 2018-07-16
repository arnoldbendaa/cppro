package com.cedar.cp.api.reset;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;

public abstract interface UserResetLinkEditorSession extends BusinessSession
{
  public abstract UserResetLinkEditor getUserResetLinkEditor();

  public abstract EntityList getAvailableUsers();

  public abstract EntityList getOwnershipRefs();
}