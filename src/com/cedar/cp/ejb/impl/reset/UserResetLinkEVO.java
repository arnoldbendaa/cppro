package com.cedar.cp.ejb.impl.reset;

import com.cedar.cp.api.reset.UserResetLinkRef;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkPK;
import com.cedar.cp.dto.reset.UserResetLinkRefImpl;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.io.Serializable;

public class UserResetLinkEVO
  implements Serializable
{
  private transient UserResetLinkPK mPK;
  private int mUserId;
  private String mPwdLink;
  private boolean mInsertPending;
  private boolean mDeletePending;
  private boolean mModified;

  public UserResetLinkEVO()
  {
  }

  public UserResetLinkEVO(int newUserId, String newPwdLink)
  {
    mUserId = newUserId;
    mPwdLink = newPwdLink;
  }

  public UserResetLinkPK getPK()
  {
    if (mPK == null)
    {
      mPK = new UserResetLinkPK(mUserId, mPwdLink);
    }

    return mPK;
  }

  public boolean isModified()
  {
    return mModified;
  }

  public int getUserId()
  {
    return mUserId;
  }

  public String getPwdLink()
  {
    return mPwdLink;
  }

  public void setUserId(int newUserId)
  {
    if (mUserId == newUserId)
      return;
    mModified = true;
    mUserId = newUserId;
    mPK = null;
  }

  public void setPwdLink(String newPwdLink)
  {
    if (((mPwdLink != null) && (newPwdLink == null)) || ((mPwdLink == null) && (newPwdLink != null)) || ((mPwdLink != null) && (newPwdLink != null) && (!mPwdLink.equals(newPwdLink))))
    {
      mPwdLink = newPwdLink;
      mModified = true;
    }
  }

  public void setDetails(UserResetLinkEVO newDetails)
  {
    setUserId(newDetails.getUserId());
    setPwdLink(newDetails.getPwdLink());
  }

  public UserResetLinkEVO deepClone()
  {
    UserResetLinkEVO cloned = new UserResetLinkEVO();

    cloned.mModified = mModified;
    cloned.mInsertPending = mInsertPending;
    cloned.mDeletePending = mDeletePending;

    cloned.mUserId = mUserId;
    if (mPwdLink != null)
      cloned.mPwdLink = mPwdLink;
    return cloned;
  }

  public void prepareForInsert(UserEVO parent)
  {
    boolean newKey = insertPending();
  }

  public int getInsertCount(int startCount)
  {
    int returnCount = startCount;

    return returnCount;
  }

  public int assignNextKey(UserEVO parent, int startKey)
  {
    int nextKey = startKey;

    return nextKey;
  }

  public void setInsertPending()
  {
    mInsertPending = true;
  }

  public boolean insertPending()
  {
    return mInsertPending;
  }

  public void setDeletePending()
  {
    mDeletePending = true;
  }

  public boolean deletePending()
  {
    return mDeletePending;
  }

  protected void reset()
  {
    mModified = false;
    mInsertPending = false;
  }

  public UserResetLinkRef getEntityRef(UserEVO evoUser)
  {
    return new UserResetLinkRefImpl(new UserResetLinkCK(evoUser.getPK(), getPK()), mPwdLink);
  }

  public UserResetLinkCK getCK(UserEVO evoUser)
  {
    return new UserResetLinkCK(evoUser.getPK(), getPK());
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("UserId=");
    sb.append(String.valueOf(mUserId));
    sb.append(' ');
    sb.append("PwdLink=");
    sb.append(String.valueOf(mPwdLink));
    sb.append(' ');
    if (mModified)
      sb.append("modified ");
    if (mInsertPending)
      sb.append("insertPending ");
    if (mDeletePending)
      sb.append("deletePending ");
    return sb.toString();
  }

  public String print()
  {
    return print(0);
  }

  public String print(int indent)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < indent; i++)
      sb.append(' ');
    sb.append("UserResetLink: ");
    sb.append(toString());
    return sb.toString();
  }
}