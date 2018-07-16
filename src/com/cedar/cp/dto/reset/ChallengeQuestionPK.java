package com.cedar.cp.dto.reset;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class ChallengeQuestionPK extends PrimaryKey
  implements Serializable
{
  private int mHashCode = -2147483648;
  int mUserId;
  String mQuestionText;

  public ChallengeQuestionPK(int newUserId, String newQuestionText)
  {
    mUserId = newUserId;
    mQuestionText = newQuestionText;
  }

  public int getUserId()
  {
    return mUserId;
  }

  public String getQuestionText()
  {
    return mQuestionText;
  }

  public int hashCode()
  {
    if (mHashCode == -2147483648)
    {
      mHashCode += String.valueOf(mUserId).hashCode();
      mHashCode += mQuestionText.hashCode();
    }

    return mHashCode;
  }

  public boolean equals(Object obj)
  {
    ChallengeQuestionPK other = null;

    if ((obj instanceof ChallengeQuestionCK)) {
      other = ((ChallengeQuestionCK)obj).getChallengeQuestionPK();
    }
    else if ((obj instanceof ChallengeQuestionPK))
      other = (ChallengeQuestionPK)obj;
    else {
      return false;
    }
    boolean eq = true;

    eq = (eq) && (mUserId == other.mUserId);
    eq = (eq) && (mQuestionText.equals(other.mQuestionText));

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" UserId=");
    sb.append(mUserId);
    sb.append(",QuestionText=");
    sb.append(mQuestionText);
    return sb.toString().substring(1);
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" ");
    sb.append(mUserId);
    sb.append(",");
    sb.append(mQuestionText);
    return "ChallengeQuestionPK|" + sb.toString().substring(1);
  }

  public static ChallengeQuestionPK getKeyFromTokens(String extKey)
  {
    String[] extValues = extKey.split("[|]");

    if (extValues.length != 2) {
      throw new IllegalStateException(extKey + ": format incorrect");
    }
    if (!extValues[0].equals("ChallengeQuestionPK")) {
      throw new IllegalStateException(extKey + ": format incorrect - must start with 'ChallengeQuestionPK|'");
    }
    extValues = extValues[1].split(",");

    int i = 0;
    int pUserId = new Integer(extValues[(i++)]).intValue();
    String pQuestionText = new String(extValues[(i++)]);
    return new ChallengeQuestionPK(pUserId, pQuestionText);
  }
}