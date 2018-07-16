package com.cedar.cp.api.masterquestion;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;

public abstract interface MasterQuestionEditor extends BusinessEditor
{
  public abstract void setQuestionText(String paramString)
    throws ValidationException;

  public abstract MasterQuestion getMasterQuestion();
}