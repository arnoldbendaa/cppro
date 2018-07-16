package com.cedar.cp.impl.masterquestion;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.masterquestion.MasterQuestionEditorSession;
import com.cedar.cp.api.masterquestion.MasterQuestionsProcess;
import com.cedar.cp.ejb.api.masterquestion.MasterQuestionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.Set;

public class MasterQuestionsProcessImpl extends BusinessProcessImpl
  implements MasterQuestionsProcess
{
  private Log mLog = new Log(getClass());

  public MasterQuestionsProcessImpl(CPConnection connection)
  {
    super(connection);
  }

  public void deleteObject(Object primaryKey) throws ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;

    MasterQuestionEditorSessionServer es = new MasterQuestionEditorSessionServer(getConnection());
    try
    {
      es.delete(primaryKey);
    }
    catch (ValidationException e)
    {
      throw e;
    }
    catch (CPException e)
    {
      throw new RuntimeException("can't delete " + primaryKey, e);
    }

    if (timer != null)
      timer.logDebug("deleteObject", primaryKey);
  }

  public MasterQuestionEditorSession getMasterQuestionEditorSession(Object key) throws ValidationException
  {
    MasterQuestionEditorSessionImpl sess = new MasterQuestionEditorSessionImpl(this, key);
    mActiveSessions.add(sess);
    return sess;
  }

  public EntityList getAllMasterQuestions()
  {
    try
    {
      return getConnection().getListHelper().getAllMasterQuestions();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("can't get AllMasterQuestions", e);
    }
  }

  public EntityList getQuestionByID(int param1)
  {
    try
    {
      return getConnection().getListHelper().getQuestionByID(param1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("can't get QuestionByID", e);
    }
  }

  public String getProcessName()
  {
    String ret = "Processing MasterQuestion";

    return ret;
  }

  protected int getProcessID()
  {
    return 102;
  }
}