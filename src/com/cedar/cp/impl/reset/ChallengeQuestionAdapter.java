package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.reset.ChallengeQuestion;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.reset.ChallengeQuestionImpl;
import com.cedar.cp.dto.reset.ChallengeQuestionPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;

public class ChallengeQuestionAdapter
  implements ChallengeQuestion
{
  private ChallengeQuestionImpl mEditorData;
  private ChallengeQuestionEditorSessionImpl mEditorSessionImpl;

  public ChallengeQuestionAdapter(ChallengeQuestionEditorSessionImpl e, ChallengeQuestionImpl editorData)
  {
    mEditorData = editorData;
    mEditorSessionImpl = e;
  }

  public void setPrimaryKey(Object key)
  {
    mEditorData.setPrimaryKey(key);
  }

  protected ChallengeQuestionEditorSessionImpl getEditorSessionImpl()
  {
    return mEditorSessionImpl;
  }

  protected ChallengeQuestionImpl getEditorData()
  {
    return mEditorData;
  }

  public Object getPrimaryKey()
  {
    return mEditorData.getPrimaryKey();
  }

  void setPrimaryKey(ChallengeQuestionPK paramKey)
  {
    mEditorData.setPrimaryKey(paramKey);
  }

  public String getQuestionAnswer()
  {
    return mEditorData.getQuestionAnswer();
  }

  public UserRef getUserRef()
  {
    if (mEditorData.getUserRef() != null)
    {
      if ((mEditorData.getUserRef().getNarrative() != null) && (mEditorData.getUserRef().getNarrative().length() > 0))
      {
        return mEditorData.getUserRef();
      }
      try
      {
        UserRef ref = ((BusinessProcessImpl)getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getUserEntityRef(mEditorData.getUserRef());

        mEditorData.setUserRef(ref);
        return ref;
      }
      catch (Exception e)
      {
        throw new RuntimeException(e.getMessage());
      }
    }

    return null;
  }

  public void setUserRef(UserRef ref)
  {
    mEditorData.setUserRef(ref);
  }

  public void setQuestionAnswer(String p)
  {
    mEditorData.setQuestionAnswer(p);
  }
}