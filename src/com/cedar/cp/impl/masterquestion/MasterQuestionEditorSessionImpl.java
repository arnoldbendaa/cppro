package com.cedar.cp.impl.masterquestion;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.masterquestion.MasterQuestionEditor;
import com.cedar.cp.api.masterquestion.MasterQuestionEditorSession;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionSSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionImpl;
import com.cedar.cp.ejb.api.masterquestion.MasterQuestionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;
import java.util.Set;

public class MasterQuestionEditorSessionImpl extends BusinessSessionImpl
  implements MasterQuestionEditorSession
{
  protected MasterQuestionEditorSessionSSO mServerSessionData;
  protected MasterQuestionImpl mEditorData;
  protected MasterQuestionEditorImpl mClientEditor;
  private Log mLog = new Log(getClass());

  public MasterQuestionEditorSessionImpl(MasterQuestionsProcessImpl process, Object key)
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
      throw new RuntimeException("Can't get MasterQuestion", e);
    }

    mEditorData = mServerSessionData.getEditorData();
  }

  protected MasterQuestionEditorSessionServer getSessionServer() throws CPException
  {
    return new MasterQuestionEditorSessionServer(getConnection());
  }

  public MasterQuestionEditor getMasterQuestionEditor()
  {
    if (mClientEditor == null)
    {
      mClientEditor = new MasterQuestionEditorImpl(this, mServerSessionData, mEditorData);
      mActiveEditors.add(mClientEditor);
    }

    return mClientEditor;
  }

  public Object getPrimaryKey()
  {
    return mEditorData.getPrimaryKey();
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