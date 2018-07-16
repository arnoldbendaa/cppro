package com.cedar.cp.api.masterquestion;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public abstract interface MasterQuestionsProcess extends BusinessProcess
{
  public abstract EntityList getAllMasterQuestions();

  public abstract EntityList getQuestionByID(int paramInt);

  public abstract MasterQuestionEditorSession getMasterQuestionEditorSession(Object paramObject)
    throws ValidationException;
}