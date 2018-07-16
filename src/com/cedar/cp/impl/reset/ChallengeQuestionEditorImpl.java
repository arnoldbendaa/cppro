package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.reset.ChallengeQuestion;
import com.cedar.cp.api.reset.ChallengeQuestionEditor;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionSSO;
import com.cedar.cp.dto.reset.ChallengeQuestionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.util.StringUtils;

public class ChallengeQuestionEditorImpl extends BusinessEditorImpl
  implements ChallengeQuestionEditor
{
  private ChallengeQuestionEditorSessionSSO mServerSessionData;
  private ChallengeQuestionImpl mEditorData;
  private ChallengeQuestionAdapter mEditorDataAdapter;

  public ChallengeQuestionEditorImpl(ChallengeQuestionEditorSessionImpl session, ChallengeQuestionEditorSessionSSO serverSessionData, ChallengeQuestionImpl editorData)
  {
    super(session);

    mServerSessionData = serverSessionData;

    mEditorData = editorData;
  }

  public void updateEditorData(ChallengeQuestionEditorSessionSSO serverSessionData, ChallengeQuestionImpl editorData)
  {
    mServerSessionData = serverSessionData;
    mEditorData = editorData;
  }

  public void setQuestionAnswer(String newQuestionAnswer)
    throws ValidationException
  {
    if (newQuestionAnswer != null)
      newQuestionAnswer = StringUtils.rtrim(newQuestionAnswer);
    validateQuestionAnswer(newQuestionAnswer);
    if ((mEditorData.getQuestionAnswer() != null) && 
      (mEditorData.getQuestionAnswer().equals(newQuestionAnswer))) {
      return;
    }

    setContentModified();
    mEditorData.setQuestionAnswer(newQuestionAnswer);
  }

  public void validateQuestionAnswer(String newQuestionAnswer)
    throws ValidationException
  {
    if ((newQuestionAnswer != null) && (newQuestionAnswer.length() > 255))
      throw new ValidationException("length (" + newQuestionAnswer.length() + ") of QuestionAnswer must not exceed 255 on a ChallengeQuestion");
  }

  public void setUserRef(UserRef ref)
    throws ValidationException
  {
    UserRef actualRef = ref;
    if (actualRef != null)
    {
      try
      {
        actualRef = getConnection().getListHelper().getUserEntityRef(ref);
      }
      catch (Exception e)
      {
        throw new ValidationException(e.getMessage());
      }
    }

    if (mEditorData.getUserRef() == null)
    {
      if (actualRef != null);
    }
    else if ((actualRef != null) && 
      (mEditorData.getUserRef().getPrimaryKey().equals(actualRef.getPrimaryKey()))) {
      return;
    }

    mEditorData.setUserRef(actualRef);
    setContentModified();
  }

  public EntityList getOwnershipRefs()
  {
    return ((ChallengeQuestionEditorSessionImpl)getBusinessSession()).getOwnershipRefs();
  }

  public ChallengeQuestion getChallengeQuestion()
  {
    if (mEditorDataAdapter == null)
    {
      mEditorDataAdapter = new ChallengeQuestionAdapter((ChallengeQuestionEditorSessionImpl)getBusinessSession(), mEditorData);
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