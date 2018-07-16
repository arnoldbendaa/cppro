package com.cedar.cp.dto.reset;

import com.cedar.cp.api.reset.ChallengeQuestion;
import com.cedar.cp.api.user.UserRef;
import java.io.Serializable;

public class ChallengeQuestionImpl
  implements ChallengeQuestion, Serializable, Cloneable
{
  private Object mPrimaryKey;
  private String mQuestionAnswer;
  private int mVersionNum;
  private UserRef mUserRef;

  public ChallengeQuestionImpl(Object paramKey)
  {
    mPrimaryKey = paramKey;
    mQuestionAnswer = "";
  }

  public Object getPrimaryKey()
  {
    return mPrimaryKey;
  }

  public void setPrimaryKey(Object paramKey)
  {
    mPrimaryKey = ((ChallengeQuestionPK)paramKey);
  }

  public void setPrimaryKey(ChallengeQuestionCK paramKey)
  {
    mPrimaryKey = paramKey;
  }

  public String getQuestionAnswer()
  {
    return mQuestionAnswer;
  }

  public UserRef getUserRef()
  {
    return mUserRef;
  }

  public void setUserRef(UserRef ref)
  {
    mUserRef = ref;
  }

  public void setVersionNum(int p)
  {
    mVersionNum = p;
  }

  public int getVersionNum()
  {
    return mVersionNum;
  }

  public void setQuestionAnswer(String paramQuestionAnswer)
  {
    mQuestionAnswer = paramQuestionAnswer;
  }
}