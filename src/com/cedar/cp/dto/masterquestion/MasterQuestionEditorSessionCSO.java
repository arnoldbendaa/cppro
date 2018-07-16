package com.cedar.cp.dto.masterquestion;

import java.io.Serializable;

public class MasterQuestionEditorSessionCSO
  implements Serializable
{
  private int mUserId;
  private MasterQuestionImpl mEditorData;

  public MasterQuestionEditorSessionCSO(int userId, MasterQuestionImpl editorData)
  {
    mUserId = userId;
    mEditorData = editorData;
  }

  public MasterQuestionImpl getEditorData()
  {
    return mEditorData;
  }

  public int getUserId()
  {
    return mUserId;
  }
}