package com.cedar.cp.dto.reset;

import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserPK;
import java.io.Serializable;

public class ChallengeQuestionCK extends UserCK
  implements Serializable
{
  protected ChallengeQuestionPK mChallengeQuestionPK;

  public ChallengeQuestionCK(UserPK paramUserPK, ChallengeQuestionPK paramChallengeQuestionPK)
  {
    super(paramUserPK);

    mChallengeQuestionPK = paramChallengeQuestionPK;
  }

  public ChallengeQuestionPK getChallengeQuestionPK()
  {
    return mChallengeQuestionPK;
  }

  public PrimaryKey getPK()
  {
    return mChallengeQuestionPK;
  }

  public int hashCode()
  {
    return mChallengeQuestionPK.hashCode();
  }

  public boolean equals(Object obj)
  {
    if ((obj instanceof ChallengeQuestionPK)) {
      return obj.equals(this);
    }
    if (!(obj instanceof ChallengeQuestionCK)) {
      return false;
    }
    ChallengeQuestionCK other = (ChallengeQuestionCK)obj;
    boolean eq = true;

    eq = (eq) && (mUserPK.equals(other.mUserPK));
    eq = (eq) && (mChallengeQuestionPK.equals(other.mChallengeQuestionPK));

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString());
    sb.append("[");
    sb.append(mChallengeQuestionPK);
    sb.append("]");
    return sb.toString();
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("ChallengeQuestionCK|");
    sb.append(super.getPK().toTokens());
    sb.append('|');
    sb.append(mChallengeQuestionPK.toTokens());
    return sb.toString();
  }

  public static UserCK getKeyFromTokens(String extKey)
  {
    String[] token = extKey.split("[|]");
    int i = 0;
    checkExpected("ChallengeQuestionCK", token[(i++)]);
    checkExpected("UserPK", token[(i++)]);
    i++;
    checkExpected("ChallengeQuestionPK", token[(i++)]);
    i = 1;
    return new ChallengeQuestionCK(UserPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ChallengeQuestionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
  }

  private static void checkExpected(String expected, String found)
  {
    if (!expected.equals(found))
      throw new IllegalArgumentException("expected=" + expected + " found=" + found);
  }
}