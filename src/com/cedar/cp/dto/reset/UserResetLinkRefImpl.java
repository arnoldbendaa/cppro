package com.cedar.cp.dto.reset;

import com.cedar.cp.api.reset.UserResetLinkRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class UserResetLinkRefImpl extends EntityRefImpl
  implements UserResetLinkRef, Serializable
{
  public UserResetLinkRefImpl(UserResetLinkCK key, String narrative)
  {
    super(key, narrative);
  }

  public UserResetLinkRefImpl(UserResetLinkPK key, String narrative)
  {
    super(key, narrative);
  }

  public UserResetLinkPK getUserResetLinkPK()
  {
    if ((mKey instanceof UserResetLinkCK))
      return ((UserResetLinkCK)mKey).getUserResetLinkPK();
    return (UserResetLinkPK)mKey;
  }
}