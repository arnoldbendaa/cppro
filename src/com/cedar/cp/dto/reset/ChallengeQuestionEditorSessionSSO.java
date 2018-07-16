package com.cedar.cp.dto.reset;

import java.io.Serializable;

public class ChallengeQuestionEditorSessionSSO
  implements Serializable
{
  private ChallengeQuestionImpl mEditorData;

  public ChallengeQuestionEditorSessionSSO()
  {
  }

  public ChallengeQuestionEditorSessionSSO(ChallengeQuestionImpl paramEditorData)
  {
    mEditorData = paramEditorData;
  }

  public void setEditorData(ChallengeQuestionImpl paramEditorData)
  {
    mEditorData = paramEditorData;
  }

  public ChallengeQuestionImpl getEditorData()
  {
    return mEditorData;
  }
}