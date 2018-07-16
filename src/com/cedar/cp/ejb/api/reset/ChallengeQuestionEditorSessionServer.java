package com.cedar.cp.ejb.api.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionCSO;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionSSO;
import com.cedar.cp.dto.reset.ChallengeQuestionImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ChallengeQuestionEditorSessionServer extends AbstractSession
{
  private static final String REMOTE_JNDI_NAME = "ejb/ChallengeQuestionEditorSessionRemoteHome";
  private static final String LOCAL_JNDI_NAME = "ejb/ChallengeQuestionEditorSessionLocalHome";
  protected ChallengeQuestionEditorSessionRemote mRemote;
  protected ChallengeQuestionEditorSessionLocal mLocal;
  private Log mLog = new Log(getClass());

  public ChallengeQuestionEditorSessionServer(CPConnection conn_)
  {
    super(conn_);
  }

  public ChallengeQuestionEditorSessionServer(Context context_, boolean remote)
  {
    super(context_, remote);
  }

  private ChallengeQuestionEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException
  {
    if (mRemote == null)
    {
      String jndiName = getRemoteJNDIName();
      try
      {
        ChallengeQuestionEditorSessionHome home = (ChallengeQuestionEditorSessionHome)getHome(jndiName, ChallengeQuestionEditorSessionHome.class);
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

  private ChallengeQuestionEditorSessionLocal getLocal() throws CPException
  {
    if (mLocal == null)
    {
      try
      {
        ChallengeQuestionEditorSessionLocalHome home = (ChallengeQuestionEditorSessionLocalHome)getLocalHome(getLocalJNDIName());
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

  public EntityList getAvailableUsers()
    throws CPException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;

    EntityList ret = getConnection().getListHelper().getAllUsers();

    if (timer != null) {
      timer.logDebug("getUserList", "");
    }
    return ret;
  }

  public EntityList getOwnershipRefs(Object pk_)
    throws CPException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      EntityList ret = null;

      if (isRemoteConnection())
        ret = getRemote().getOwnershipData(getUserId(), pk_);
      else {
        ret = getLocal().getOwnershipData(getUserId(), pk_);
      }
      if (timer != null) {
        timer.logDebug("getOwnershipRefs", pk_ != null ? pk_.toString() : "null");
      }
      return ret;
    }
    catch (Exception e)
    {
      throw new CPException("unable to getOwnershipRefs(" + pk_ + ") from server " + e.getMessage(), e);
    }
  }

  public ChallengeQuestionEditorSessionSSO getNewItemData()
    throws ValidationException, CPException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      ChallengeQuestionEditorSessionSSO ret = null;
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

  public ChallengeQuestionEditorSessionSSO getItemData(Object key) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      ChallengeQuestionEditorSessionSSO ret = null;
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

  public ChallengeQuestionCK insert(ChallengeQuestionImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      ChallengeQuestionCK ret = null;
      if (isRemoteConnection())
        ret = getRemote().insert(new ChallengeQuestionEditorSessionCSO(getUserId(), editorData));
      else
        ret = getLocal().insert(new ChallengeQuestionEditorSessionCSO(getUserId(), editorData));
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

  public ChallengeQuestionCK copy(ChallengeQuestionImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      ChallengeQuestionCK ret = null;
      if (isRemoteConnection())
        ret = getRemote().copy(new ChallengeQuestionEditorSessionCSO(getUserId(), editorData));
      else {
        ret = getLocal().copy(new ChallengeQuestionEditorSessionCSO(getUserId(), editorData));
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

  public void update(ChallengeQuestionImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      if (isRemoteConnection())
        getRemote().update(new ChallengeQuestionEditorSessionCSO(getUserId(), editorData));
      else {
        getLocal().update(new ChallengeQuestionEditorSessionCSO(getUserId(), editorData));
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
    return "ejb/ChallengeQuestionEditorSessionRemoteHome";
  }

  public String getLocalJNDIName()
  {
    return "ejb/ChallengeQuestionEditorSessionLocalHome";
  }
}