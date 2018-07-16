package com.cedar.cp.dto.masterquestion;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class MasterQuestionPK extends PrimaryKey
  implements Serializable
{
  private int mHashCode = -2147483648;
  int mQuestionId;

  public MasterQuestionPK(int newQuestionId)
  {
    mQuestionId = newQuestionId;
  }

  public int getQuestionId()
  {
    return mQuestionId;
  }

  public int hashCode()
  {
    if (mHashCode == -2147483648)
    {
      mHashCode += String.valueOf(mQuestionId).hashCode();
    }

    return mHashCode;
  }

  public boolean equals(Object obj)
  {
    MasterQuestionPK other = null;

    if ((obj instanceof MasterQuestionCK)) {
      other = ((MasterQuestionCK)obj).getMasterQuestionPK();
    }
    else if ((obj instanceof MasterQuestionPK))
      other = (MasterQuestionPK)obj;
    else {
      return false;
    }
    boolean eq = true;

    eq = (eq) && (mQuestionId == other.mQuestionId);

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" QuestionId=");
    sb.append(mQuestionId);
    return sb.toString().substring(1);
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" ");
    sb.append(mQuestionId);
    return "MasterQuestionPK|" + sb.toString().substring(1);
  }

  public static MasterQuestionPK getKeyFromTokens(String extKey)
  {
    String[] extValues = extKey.split("[|]");

    if (extValues.length != 2) {
      throw new IllegalStateException(extKey + ": format incorrect");
    }
    if (!extValues[0].equals("MasterQuestionPK")) {
      throw new IllegalStateException(extKey + ": format incorrect - must start with 'MasterQuestionPK|'");
    }
    extValues = extValues[1].split(",");

    int i = 0;
    int pQuestionId = new Integer(extValues[(i++)]).intValue();
    return new MasterQuestionPK(pQuestionId);
  }
}