package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class LoggedInUsersLinkCK extends LoggedInUsersCK
  implements Serializable
{
  protected LoggedInUsersLinkPK mLoggedInUsersLinkPK;

  public LoggedInUsersLinkCK(LoggedInUsersPK paramLoggedInUsersPK, LoggedInUsersLinkPK paramLoggedInUsersLinkPK)
  {
    super(paramLoggedInUsersPK);

    mLoggedInUsersLinkPK = paramLoggedInUsersLinkPK;
  }

  public LoggedInUsersLinkPK getLoggedInUsersLinkPK()
  {
    return mLoggedInUsersLinkPK;
  }

  public PrimaryKey getPK()
  {
    return mLoggedInUsersLinkPK;
  }

  public int hashCode()
  {
    return mLoggedInUsersLinkPK.hashCode();
  }

  public boolean equals(Object obj)
  {
    if ((obj instanceof LoggedInUsersLinkPK)) {
      return obj.equals(this);
    }
    if (!(obj instanceof LoggedInUsersLinkCK)) {
      return false;
    }
    LoggedInUsersLinkCK other = (LoggedInUsersLinkCK)obj;
    boolean eq = true;

    eq = (eq) && (mLoggedInUsersPK.equals(other.mLoggedInUsersPK));
    eq = (eq) && (mLoggedInUsersLinkPK.equals(other.mLoggedInUsersLinkPK));

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString());
    sb.append("[");
    sb.append(mLoggedInUsersLinkPK);
    sb.append("]");
    return sb.toString();
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("LoggedInUsersLinkCK|");
    sb.append(super.getPK().toTokens());
    sb.append('|');
    sb.append(mLoggedInUsersLinkPK.toTokens());
    return sb.toString();
  }

  public static LoggedInUsersCK getKeyFromTokens(String extKey)
  {
    String[] token = extKey.split("[|]");
    int i = 0;
    checkExpected("LoggedInUsersLinkCK", token[(i++)]);
    checkExpected("LoggedInUsersPK", token[(i++)]);
    i++;
    checkExpected("LoggedInUsersLinkPK", token[(i++)]);
    i = 1;
    return new LoggedInUsersLinkCK(LoggedInUsersPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), LoggedInUsersLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
  }

  private static void checkExpected(String expected, String found)
  {
    if (!expected.equals(found))
      throw new IllegalArgumentException("expected=" + expected + " found=" + found);
  }
}