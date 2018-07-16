package com.cedar.cp.api.reset;

import com.cedar.cp.api.user.UserRef;

public abstract interface ChallengeQuestion
{
  public abstract Object getPrimaryKey();

  public abstract String getQuestionAnswer();

  public abstract UserRef getUserRef();
}