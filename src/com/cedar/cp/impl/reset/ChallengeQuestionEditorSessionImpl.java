package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.reset.ChallengeQuestionEditor;
import com.cedar.cp.api.reset.ChallengeQuestionEditorSession;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionSSO;
import com.cedar.cp.dto.reset.ChallengeQuestionImpl;
import com.cedar.cp.ejb.api.reset.ChallengeQuestionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;
import java.util.Set;

public class ChallengeQuestionEditorSessionImpl extends BusinessSessionImpl
  implements ChallengeQuestionEditorSession
{
  protected ChallengeQuestionEditorSessionSSO mServerSessionData;
  protected ChallengeQuestionImpl mEditorData;
  protected ChallengeQuestionEditorImpl mClientEditor;
  private Log mLog = new Log(getClass());

  public ChallengeQuestionEditorSessionImpl(ChallengeQuestionsProcessImpl process, Object key)
    throws ValidationException
  {
    super(process);
    try
    {
      if (key == null)
        mServerSessionData = getSessionServer().getNewItemData();
      else
        mServerSessionData = getSessionServer().getItemData(key);
    }
    catch (ValidationException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException("Can't get ChallengeQuestion", e);
    }

    mEditorData = mServerSessionData.getEditorData();
  }

  protected ChallengeQuestionEditorSessionServer getSessionServer() throws CPException
  {
    return new ChallengeQuestionEditorSessionServer(getConnection());
  }

  public ChallengeQuestionEditor getChallengeQuestionEditor()
  {
    if (mClientEditor == null)
    {
      mClientEditor = new ChallengeQuestionEditorImpl(this, mServerSessionData, mEditorData);
      mActiveEditors.add(mClientEditor);
    }

    return mClientEditor;
  }

  public Object getPrimaryKey()
  {
    return mEditorData.getPrimaryKey();
  }

  public EntityList getAvailableUsers()
  {
    try
    {
      return getSessionServer().getAvailableUsers();
    }
    catch (Exception e)
    {
      throw new RuntimeException("unexpected exceptio", e);
    }
  }

  public EntityList getOwnershipRefs()
  {
    try
    {
      return getSessionServer().getOwnershipRefs(mEditorData.getPrimaryKey());
    }
    catch (Exception e)
    {
      throw new RuntimeException("unexpected exceptio", e);
    }
  }

  public Object persistModifications(boolean cloneOnSave)
    throws CPException, ValidationException
  {
    if (mClientEditor != null) {
      mClientEditor.saveModifications();
    }

    if (mEditorData.getPrimaryKey() == null)
      mEditorData.setPrimaryKey(getSessionServer().insert(mEditorData));
    else if (cloneOnSave)
      mEditorData.setPrimaryKey(getSessionServer().copy(mEditorData));
    else {
      getSessionServer().update(mEditorData);
    }
    return mEditorData.getPrimaryKey();
  }

  public void terminate()
  {
  }
}