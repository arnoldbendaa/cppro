package com.cedar.cp.impl.masterquestion;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.masterquestion.MasterQuestion;
import com.cedar.cp.api.masterquestion.MasterQuestionEditor;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionSSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.util.StringUtils;

public class MasterQuestionEditorImpl extends BusinessEditorImpl
  implements MasterQuestionEditor
{
  private MasterQuestionEditorSessionSSO mServerSessionData;
  private MasterQuestionImpl mEditorData;
  private MasterQuestionAdapter mEditorDataAdapter;

  public MasterQuestionEditorImpl(MasterQuestionEditorSessionImpl session, MasterQuestionEditorSessionSSO serverSessionData, MasterQuestionImpl editorData)
  {
    super(session);

    mServerSessionData = serverSessionData;

    mEditorData = editorData;
  }

  public void updateEditorData(MasterQuestionEditorSessionSSO serverSessionData, MasterQuestionImpl editorData)
  {
    mServerSessionData = serverSessionData;
    mEditorData = editorData;
  }

  public void setQuestionText(String newQuestionText)
    throws ValidationException
  {
    if (newQuestionText != null)
      newQuestionText = StringUtils.rtrim(newQuestionText);
    validateQuestionText(newQuestionText);
    if ((mEditorData.getQuestionText() != null) && 
      (mEditorData.getQuestionText().equals(newQuestionText))) {
      return;
    }

    setContentModified();
    mEditorData.setQuestionText(newQuestionText);
  }

  public void validateQuestionText(String newQuestionText)
    throws ValidationException
  {
    if ((newQuestionText != null) && (newQuestionText.length() > 255))
      throw new ValidationException("length (" + newQuestionText.length() + ") of QuestionText must not exceed 255 on a MasterQuestion");
  }

  public MasterQuestion getMasterQuestion()
  {
    if (mEditorDataAdapter == null)
    {
      mEditorDataAdapter = new MasterQuestionAdapter((MasterQuestionEditorSessionImpl)getBusinessSession(), mEditorData);
    }

    return mEditorDataAdapter;
  }

  public void saveModifications()
    throws ValidationException
  {
    saveValidation();
  }

  private void saveValidation()
    throws ValidationException
  {
  }
}