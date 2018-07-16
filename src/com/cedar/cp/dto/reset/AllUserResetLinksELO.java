package com.cedar.cp.dto.reset;

import com.cedar.cp.api.reset.UserResetLinkRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllUserResetLinksELO extends AbstractELO
  implements Serializable
{
  private static final String[] mEntity = { "UserResetLink", "User" };
  private transient UserResetLinkRef mUserResetLinkEntityRef;
  private transient UserRef mUserEntityRef;
  private transient int mUserId;
  private transient String mPwdLink;

  public AllUserResetLinksELO()
  {
    super(new String[] { "UserResetLink", "User", "UserId", "PwdLink" });
  }

  public void add(UserResetLinkRef eRefUserResetLink, UserRef eRefUser, int col1, String col2)
  {
    List l = new ArrayList();
    l.add(eRefUserResetLink);
    l.add(eRefUser);
    l.add(new Integer(col1));
    l.add(col2);
    mCollection.add(l);
  }

  public void next()
  {
    if (mIterator == null) {
      reset();
    }
    mCurrRowIndex += 1;
    List l = (List)mIterator.next();
    int index = 0;
    mUserResetLinkEntityRef = ((UserResetLinkRef)l.get(index++));
    mUserEntityRef = ((UserRef)l.get(index++));
    mUserId = ((Integer)l.get(index++)).intValue();
    mPwdLink = ((String)l.get(index++));
  }

  public UserResetLinkRef getUserResetLinkEntityRef()
  {
    return mUserResetLinkEntityRef;
  }

  public UserRef getUserEntityRef()
  {
    return mUserEntityRef;
  }

  public int getUserId()
  {
    return mUserId;
  }

  public String getPwdLink()
  {
    return mPwdLink;
  }

  public boolean includesEntity(String name)
  {
    for (int i = 0; i < mEntity.length; i++)
      if (name.equals(mEntity[i]))
        return true;
    return false;
  }
}