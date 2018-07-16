package com.cedar.cp.impl.masterquestion;

import com.cedar.cp.api.masterquestion.MasterQuestion;
import com.cedar.cp.dto.masterquestion.MasterQuestionImpl;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;

public class MasterQuestionAdapter
  implements MasterQuestion
{
  private MasterQuestionImpl mEditorData;
  private MasterQuestionEditorSessionImpl mEditorSessionImpl;

  public MasterQuestionAdapter(MasterQuestionEditorSessionImpl e, MasterQuestionImpl editorData)
  {
    mEditorData = editorData;
    mEditorSessionImpl = e;
  }

  public void setPrimaryKey(Object key)
  {
    mEditorData.setPrimaryKey(key);
  }

  protected MasterQuestionEditorSessionImpl getEditorSessionImpl()
  {
    return mEditorSessionImpl;
  }

  protected MasterQuestionImpl getEditorData()
  {
    return mEditorData;
  }

  public Object getPrimaryKey()
  {
    return mEditorData.getPrimaryKey();
  }

  void setPrimaryKey(MasterQuestionPK paramKey)
  {
    mEditorData.setPrimaryKey(paramKey);
  }

  public String getQuestionText()
  {
    return mEditorData.getQuestionText();
  }

  public void setQuestionText(String p)
  {
    mEditorData.setQuestionText(p);
  }
}