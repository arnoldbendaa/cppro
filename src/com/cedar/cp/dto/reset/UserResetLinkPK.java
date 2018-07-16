package com.cedar.cp.dto.reset;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class UserResetLinkPK extends PrimaryKey
  implements Serializable
{
  private int mHashCode = -2147483648;
  int mUserId;
  String mPwdLink;

  public UserResetLinkPK(int newUserId, String newPwdLink)
  {
    mUserId = newUserId;
    mPwdLink = newPwdLink;
  }

  public int getUserId()
  {
    return mUserId;
  }

  public String getPwdLink()
  {
    return mPwdLink;
  }

  public int hashCode()
  {
    if (mHashCode == -2147483648)
    {
      mHashCode += String.valueOf(mUserId).hashCode();
      mHashCode += mPwdLink.hashCode();
    }

    return mHashCode;
  }

  public boolean equals(Object obj)
  {
    UserResetLinkPK other = null;

    if ((obj instanceof UserResetLinkCK)) {
      other = ((UserResetLinkCK)obj).getUserResetLinkPK();
    }
    else if ((obj instanceof UserResetLinkPK))
      other = (UserResetLinkPK)obj;
    else {
      return false;
    }
    boolean eq = true;

    eq = (eq) && (mUserId == other.mUserId);
    eq = (eq) && (mPwdLink.equals(other.mPwdLink));

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" UserId=");
    sb.append(mUserId);
    sb.append(",PwdLink=");
    sb.append(mPwdLink);
    return sb.toString().substring(1);
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" ");
    sb.append(mUserId);
    sb.append(",");
    sb.append(mPwdLink);
    return "UserResetLinkPK|" + sb.toString().substring(1);
  }

  public static UserResetLinkPK getKeyFromTokens(String extKey)
  {
    String[] extValues = extKey.split("[|]");

    if (extValues.length != 2) {
      throw new IllegalStateException(extKey + ": format incorrect");
    }
    if (!extValues[0].equals("UserResetLinkPK")) {
      throw new IllegalStateException(extKey + ": format incorrect - must start with 'UserResetLinkPK|'");
    }
    extValues = extValues[1].split(",");

    int i = 0;
    int pUserId = new Integer(extValues[(i++)]).intValue();
    String pPwdLink = new String(extValues[(i++)]);
    return new UserResetLinkPK(pUserId, pPwdLink);
  }
}