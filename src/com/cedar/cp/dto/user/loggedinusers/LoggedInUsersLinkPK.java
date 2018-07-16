package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class LoggedInUsersLinkPK extends PrimaryKey
  implements Serializable
{
  private int mHashCode = -2147483648;
  int mLoggedInUsersId;
  int mLoggedInUsersLinkId;

  public LoggedInUsersLinkPK(int newLoggedInUsersId, int newLoggedInUsersLinkId)
  {
    mLoggedInUsersId = newLoggedInUsersId;
    mLoggedInUsersLinkId = newLoggedInUsersLinkId;
  }

  public int getLoggedInUsersId()
  {
    return mLoggedInUsersId;
  }

  public int getLoggedInUsersLinkId()
  {
    return mLoggedInUsersLinkId;
  }

  public int hashCode()
  {
    if (mHashCode == -2147483648)
    {
      mHashCode += String.valueOf(mLoggedInUsersId).hashCode();
      mHashCode += String.valueOf(mLoggedInUsersLinkId).hashCode();
    }

    return mHashCode;
  }

  public boolean equals(Object obj)
  {
    LoggedInUsersLinkPK other = null;

    if ((obj instanceof LoggedInUsersLinkCK)) {
      other = ((LoggedInUsersLinkCK)obj).getLoggedInUsersLinkPK();
    }
    else if ((obj instanceof LoggedInUsersLinkPK))
      other = (LoggedInUsersLinkPK)obj;
    else {
      return false;
    }
    boolean eq = true;

    eq = (eq) && (mLoggedInUsersId == other.mLoggedInUsersId);
    eq = (eq) && (mLoggedInUsersLinkId == other.mLoggedInUsersLinkId);

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" LoggedInUsersId=");
    sb.append(mLoggedInUsersId);
    sb.append(",LoggedInUsersLinkId=");
    sb.append(mLoggedInUsersLinkId);
    return sb.toString().substring(1);
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" ");
    sb.append(mLoggedInUsersId);
    sb.append(",");
    sb.append(mLoggedInUsersLinkId);
    return "LoggedInUsersLinkPK|" + sb.toString().substring(1);
  }

  public static LoggedInUsersLinkPK getKeyFromTokens(String extKey)
  {
    String[] extValues = extKey.split("[|]");

    if (extValues.length != 2) {
      throw new IllegalStateException(extKey + ": format incorrect");
    }
    if (!extValues[0].equals("LoggedInUsersLinkPK")) {
      throw new IllegalStateException(extKey + ": format incorrect - must start with 'LoggedInUsersLinkPK|'");
    }
    extValues = extValues[1].split(",");

    int i = 0;
    int pLoggedInUsersId = new Integer(extValues[(i++)]).intValue();
    int pLoggedInUsersLinkId = new Integer(extValues[(i++)]).intValue();
    return new LoggedInUsersLinkPK(pLoggedInUsersId, pLoggedInUsersLinkId);
  }
}