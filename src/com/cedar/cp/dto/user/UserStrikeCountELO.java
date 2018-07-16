package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserStrikeCountELO extends AbstractELO
  implements Serializable
{
  private static final String[] mEntity = { "User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "VirementRequest" };
  private transient UserRef mUserEntityRef;
  private transient int mResetStrikes;

  public UserStrikeCountELO()
  {
    super(new String[] { "User", "ResetStrikes" });
  }

  public void add(UserRef eRefUser, int col1)
  {
    List l = new ArrayList();
    l.add(eRefUser);
    l.add(new Integer(col1));
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
    mUserEntityRef = ((UserRef)l.get(index++));
    mResetStrikes = ((Integer)l.get(index++)).intValue();
  }

  public UserRef getUserEntityRef()
  {
    return mUserEntityRef;
  }

  public int getResetStrikes()
  {
    return mResetStrikes;
  }

  public boolean includesEntity(String name)
  {
    for (int i = 0; i < mEntity.length; i++)
      if (name.equals(mEntity[i]))
        return true;
    return false;
  }
}