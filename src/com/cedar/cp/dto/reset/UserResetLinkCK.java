package com.cedar.cp.dto.reset;

import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserPK;
import java.io.Serializable;

public class UserResetLinkCK extends UserCK
  implements Serializable
{
  protected UserResetLinkPK mUserResetLinkPK;

  public UserResetLinkCK(UserPK paramUserPK, UserResetLinkPK paramUserResetLinkPK)
  {
    super(paramUserPK);

    mUserResetLinkPK = paramUserResetLinkPK;
  }

  public UserResetLinkPK getUserResetLinkPK()
  {
    return mUserResetLinkPK;
  }

  public PrimaryKey getPK()
  {
    return mUserResetLinkPK;
  }

  public int hashCode()
  {
    return mUserResetLinkPK.hashCode();
  }

  public boolean equals(Object obj)
  {
    if ((obj instanceof UserResetLinkPK)) {
      return obj.equals(this);
    }
    if (!(obj instanceof UserResetLinkCK)) {
      return false;
    }
    UserResetLinkCK other = (UserResetLinkCK)obj;
    boolean eq = true;

    eq = (eq) && (mUserPK.equals(other.mUserPK));
    eq = (eq) && (mUserResetLinkPK.equals(other.mUserResetLinkPK));

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString());
    sb.append("[");
    sb.append(mUserResetLinkPK);
    sb.append("]");
    return sb.toString();
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("UserResetLinkCK|");
    sb.append(super.getPK().toTokens());
    sb.append('|');
    sb.append(mUserResetLinkPK.toTokens());
    return sb.toString();
  }

  public static UserCK getKeyFromTokens(String extKey)
  {
    String[] token = extKey.split("[|]");
    int i = 0;
    checkExpected("UserResetLinkCK", token[(i++)]);
    checkExpected("UserPK", token[(i++)]);
    i++;
    checkExpected("UserResetLinkPK", token[(i++)]);
    i = 1;
    return new UserResetLinkCK(UserPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), UserResetLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
  }

  private static void checkExpected(String expected, String found)
  {
    if (!expected.equals(found))
      throw new IllegalArgumentException("expected=" + expected + " found=" + found);
  }
}