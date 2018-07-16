package com.cedar.cp.dto.reset;

import com.cedar.cp.api.reset.UserResetLink;
import com.cedar.cp.api.user.UserRef;
import java.io.Serializable;

public class UserResetLinkImpl
  implements UserResetLink, Serializable, Cloneable
{
  private Object mPrimaryKey;
  private UserRef mUserRef;

  public UserResetLinkImpl(Object paramKey)
  {
    mPrimaryKey = paramKey;
  }

  public Object getPrimaryKey()
  {
    return mPrimaryKey;
  }

  public void setPrimaryKey(Object paramKey)
  {
    mPrimaryKey = ((UserResetLinkPK)paramKey);
  }

  public void setPrimaryKey(UserResetLinkCK paramKey)
  {
    mPrimaryKey = paramKey;
  }

  public UserRef getUserRef()
  {
    return mUserRef;
  }

  public void setUserRef(UserRef ref)
  {
    mUserRef = ref;
  }
}