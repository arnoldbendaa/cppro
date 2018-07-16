package com.cedar.cp.ejb.api.masterquestion;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionCSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionSSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionImpl;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class MasterQuestionEditorSessionServer extends AbstractSession
{
  private static final String REMOTE_JNDI_NAME = "ejb/MasterQuestionEditorSessionRemoteHome";
  private static final String LOCAL_JNDI_NAME = "ejb/MasterQuestionEditorSessionLocalHome";
  protected MasterQuestionEditorSessionRemote mRemote;
  protected MasterQuestionEditorSessionLocal mLocal;
  private Log mLog = new Log(getClass());

  public MasterQuestionEditorSessionServer(CPConnection conn_)
  {
    super(conn_);
  }

  public MasterQuestionEditorSessionServer(Context context_, boolean remote)
  {
    super(context_, remote);
  }

  private MasterQuestionEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException
  {
    if (mRemote == null)
    {
      String jndiName = getRemoteJNDIName();
      try
      {
        MasterQuestionEditorSessionHome home = (MasterQuestionEditorSessionHome)getHome(jndiName, MasterQuestionEditorSessionHome.class);
        mRemote = home.create();
      }
      catch (CreateException e)
      {
        removeFromCache(jndiName);
        e.printStackTrace();
        throw new CPException("getRemote " + jndiName + " CreateException", e);
      }
      catch (RemoteException e)
      {
        removeFromCache(jndiName);
        e.printStackTrace();
        throw new CPException("getRemote " + jndiName + " RemoteException", e);
      }

    }

    return mRemote;
  }

  private MasterQuestionEditorSessionLocal getLocal() throws CPException
  {
    if (mLocal == null)
    {
      try
      {
        MasterQuestionEditorSessionLocalHome home = (MasterQuestionEditorSessionLocalHome)getLocalHome(getLocalJNDIName());
        mLocal = home.create();
      }
      catch (CreateException e)
      {
        throw new CPException("can't create local session for " + getLocalJNDIName(), e);
      }
    }

    return mLocal;
  }

  public void removeSession()
    throws CPException
  {
  }

  public void delete(Object primaryKey_) throws ValidationException, CPException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      if (isRemoteConnection())
        getRemote().delete(getUserId(), primaryKey_);
      else {
        getLocal().delete(getUserId(), primaryKey_);
      }
      if (timer != null)
        timer.logDebug("delete", primaryKey_.toString());
    }
    catch (Exception e)
    {
      throw unravelException(e);
    }
  }

  public MasterQuestionEditorSessionSSO getNewItemData()
    throws ValidationException, CPException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      MasterQuestionEditorSessionSSO ret = null;
      if (isRemoteConnection())
        ret = getRemote().getNewItemData(getUserId());
      else {
        ret = getLocal().getNewItemData(getUserId());
      }
      if (timer != null) {
        timer.logDebug("getNewItemData", "");
      }
      return ret;
    }
    catch (Exception e)
    {
      throw unravelException(e);
    }
  }

  public MasterQuestionEditorSessionSSO getItemData(Object key) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      MasterQuestionEditorSessionSSO ret = null;
      if (isRemoteConnection())
        ret = getRemote().getItemData(getUserId(), key);
      else {
        ret = getLocal().getItemData(getUserId(), key);
      }
      if (timer != null)
        timer.logDebug("getItemData", key.toString());
      return ret;
    }
    catch (Exception e)
    {
      throw unravelException(e);
    }
  }

  public MasterQuestionPK insert(MasterQuestionImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      MasterQuestionPK ret = null;
      if (isRemoteConnection())
        ret = getRemote().insert(new MasterQuestionEditorSessionCSO(getUserId(), editorData));
      else
        ret = getLocal().insert(new MasterQuestionEditorSessionCSO(getUserId(), editorData));
      if (timer != null) {
        timer.logDebug("insert", ret);
      }
      return ret;
    }
    catch (Exception e)
    {
      throw unravelException(e);
    }
  }

  public MasterQuestionPK copy(MasterQuestionImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      MasterQuestionPK ret = null;
      if (isRemoteConnection())
        ret = getRemote().copy(new MasterQuestionEditorSessionCSO(getUserId(), editorData));
      else {
        ret = getLocal().copy(new MasterQuestionEditorSessionCSO(getUserId(), editorData));
      }
      if (timer != null) {
        timer.logDebug("insert", ret);
      }
      return ret;
    }
    catch (Exception e)
    {
      throw unravelException(e);
    }
  }

  public void update(MasterQuestionImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      if (isRemoteConnection())
        getRemote().update(new MasterQuestionEditorSessionCSO(getUserId(), editorData));
      else {
        getLocal().update(new MasterQuestionEditorSessionCSO(getUserId(), editorData));
      }
      if (timer != null)
        timer.logDebug("update", editorData.getPrimaryKey());
    }
    catch (Exception e)
    {
      throw unravelException(e);
    }
  }

  public String getRemoteJNDIName()
  {
    return "ejb/MasterQuestionEditorSessionRemoteHome";
  }

  public String getLocalJNDIName()
  {
    return "ejb/MasterQuestionEditorSessionLocalHome";
  }
}