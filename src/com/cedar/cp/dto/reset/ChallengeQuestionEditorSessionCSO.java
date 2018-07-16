package com.cedar.cp.dto.reset;

import java.io.Serializable;

public class ChallengeQuestionEditorSessionCSO
  implements Serializable
{
  private int mUserId;
  private ChallengeQuestionImpl mEditorData;

  public ChallengeQuestionEditorSessionCSO(int userId, ChallengeQuestionImpl editorData)
  {
    mUserId = userId;
    mEditorData = editorData;
  }

  public ChallengeQuestionImpl getEditorData()
  {
    return mEditorData;
  }

  public int getUserId()
  {
    return mUserId;
  }
}