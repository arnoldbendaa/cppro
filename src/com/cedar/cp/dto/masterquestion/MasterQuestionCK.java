package com.cedar.cp.dto.masterquestion;

import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class MasterQuestionCK extends CompositeKey
  implements Serializable
{
  protected MasterQuestionPK mMasterQuestionPK;

  public MasterQuestionCK(MasterQuestionPK paramMasterQuestionPK)
  {
    mMasterQuestionPK = paramMasterQuestionPK;
  }

  public MasterQuestionPK getMasterQuestionPK()
  {
    return mMasterQuestionPK;
  }

  public PrimaryKey getPK()
  {
    return mMasterQuestionPK;
  }

  public int hashCode()
  {
    return mMasterQuestionPK.hashCode();
  }

  public boolean equals(Object obj)
  {
    if ((obj instanceof MasterQuestionPK)) {
      return obj.equals(this);
    }
    if (!(obj instanceof MasterQuestionCK)) {
      return false;
    }
    MasterQuestionCK other = (MasterQuestionCK)obj;
    boolean eq = true;

    eq = (eq) && (mMasterQuestionPK.equals(other.mMasterQuestionPK));

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    sb.append(mMasterQuestionPK);
    sb.append("]");
    return sb.toString();
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("MasterQuestionCK|");
    sb.append(mMasterQuestionPK.toTokens());
    return sb.toString();
  }

  public static MasterQuestionCK getKeyFromTokens(String extKey)
  {
    String[] token = extKey.split("[|]");
    int i = 0;
    checkExpected("MasterQuestionCK", token[(i++)]);
    checkExpected("MasterQuestionPK", token[(i++)]);
    i = 1;
    return new MasterQuestionCK(MasterQuestionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
  }

  private static void checkExpected(String expected, String found)
  {
    if (!expected.equals(found))
      throw new IllegalArgumentException("expected=" + expected + " found=" + found);
  }
}