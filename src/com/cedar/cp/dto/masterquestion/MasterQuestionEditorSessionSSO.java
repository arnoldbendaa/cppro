package com.cedar.cp.dto.masterquestion;

import java.io.Serializable;

public class MasterQuestionEditorSessionSSO
  implements Serializable
{
  private MasterQuestionImpl mEditorData;

  public MasterQuestionEditorSessionSSO()
  {
  }

  public MasterQuestionEditorSessionSSO(MasterQuestionImpl paramEditorData)
  {
    mEditorData = paramEditorData;
  }

  public void setEditorData(MasterQuestionImpl paramEditorData)
  {
    mEditorData = paramEditorData;
  }

  public MasterQuestionImpl getEditorData()
  {
    return mEditorData;
  }
}