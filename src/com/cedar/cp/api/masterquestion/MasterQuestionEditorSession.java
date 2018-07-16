package com.cedar.cp.api.masterquestion;

import com.cedar.cp.api.base.BusinessSession;

public abstract interface MasterQuestionEditorSession extends BusinessSession
{
  public abstract MasterQuestionEditor getMasterQuestionEditor();
}