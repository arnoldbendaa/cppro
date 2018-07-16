package com.cedar.cp.api.reset;

import com.cedar.cp.api.user.UserRef;

public abstract interface UserResetLink
{
  public abstract Object getPrimaryKey();

  public abstract UserRef getUserRef();
}