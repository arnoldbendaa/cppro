package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.reset.UserResetLinkEditorSession;
import com.cedar.cp.api.reset.UserResetLinksProcess;
import com.cedar.cp.ejb.api.reset.UserResetLinkEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.Set;

public class UserResetLinksProcessImpl extends BusinessProcessImpl
  implements UserResetLinksProcess
{
  private Log mLog = new Log(getClass());

  public UserResetLinksProcessImpl(CPConnection connection)
  {
    super(connection);
  }

  public void deleteObject(Object primaryKey) throws ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;

    UserResetLinkEditorSessionServer es = new UserResetLinkEditorSessionServer(getConnection());
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

  public UserResetLinkEditorSession getUserResetLinkEditorSession(Object key) throws ValidationException
  {
    UserResetLinkEditorSessionImpl sess = new UserResetLinkEditorSessionImpl(this, key);
    mActiveSessions.add(sess);
    return sess;
  }

  public EntityList getAllUserResetLinks()
  {
    try
    {
      return getConnection().getListHelper().getAllUserResetLinks();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("can't get AllUserResetLinks", e);
    }
  }

  public EntityList getLinkByUserID(int param1)
  {
    try
    {
      return getConnection().getListHelper().getLinkByUserID(param1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("can't get LinkByUserID", e);
    }
  }

  public String getProcessName()
  {
    String ret = "Processing UserResetLink";

    return ret;
  }

  protected int getProcessID()
  {
    return 104;
  }
}