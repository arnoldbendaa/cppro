package com.cedar.cp.dto.masterquestion;

import com.cedar.cp.api.masterquestion.MasterQuestion;
import java.io.Serializable;

public class MasterQuestionImpl
  implements MasterQuestion, Serializable, Cloneable
{
  private Object mPrimaryKey;
  private String mQuestionText;
  private int mVersionNum;

  public MasterQuestionImpl(Object paramKey)
  {
    mPrimaryKey = paramKey;
    mQuestionText = "";
  }

  public Object getPrimaryKey()
  {
    return mPrimaryKey;
  }

  public void setPrimaryKey(Object paramKey)
  {
    mPrimaryKey = ((MasterQuestionPK)paramKey);
  }

  public String getQuestionText()
  {
    return mQuestionText;
  }

  public void setVersionNum(int p)
  {
    mVersionNum = p;
  }

  public int getVersionNum()
  {
    return mVersionNum;
  }

  public void setQuestionText(String paramQuestionText)
  {
    mQuestionText = paramQuestionText;
  }
}