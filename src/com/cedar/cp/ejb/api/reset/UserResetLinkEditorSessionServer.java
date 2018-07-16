package com.cedar.cp.ejb.api.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionCSO;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionSSO;
import com.cedar.cp.dto.reset.UserResetLinkImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class UserResetLinkEditorSessionServer extends AbstractSession
{
  private static final String REMOTE_JNDI_NAME = "ejb/UserResetLinkEditorSessionRemoteHome";
  private static final String LOCAL_JNDI_NAME = "ejb/UserResetLinkEditorSessionLocalHome";
  protected UserResetLinkEditorSessionRemote mRemote;
  protected UserResetLinkEditorSessionLocal mLocal;
  private Log mLog = new Log(getClass());

  public UserResetLinkEditorSessionServer(CPConnection conn_)
  {
    super(conn_);
  }

  public UserResetLinkEditorSessionServer(Context context_, boolean remote)
  {
    super(context_, remote);
  }

  private UserResetLinkEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException
  {
    if (mRemote == null)
    {
      String jndiName = getRemoteJNDIName();
      try
      {
        UserResetLinkEditorSessionHome home = (UserResetLinkEditorSessionHome)getHome(jndiName, UserResetLinkEditorSessionHome.class);
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

  private UserResetLinkEditorSessionLocal getLocal() throws CPException
  {
    if (mLocal == null)
    {
      try
      {
        UserResetLinkEditorSessionLocalHome home = (UserResetLinkEditorSessionLocalHome)getLocalHome(getLocalJNDIName());
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

  public UserResetLinkEditorSessionSSO getNewItemData()
    throws ValidationException, CPException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      UserResetLinkEditorSessionSSO ret = null;
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

  public UserResetLinkEditorSessionSSO getItemData(Object key) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      UserResetLinkEditorSessionSSO ret = null;
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

  public UserResetLinkCK insert(UserResetLinkImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      UserResetLinkCK ret = null;
      if (isRemoteConnection())
        ret = getRemote().insert(new UserResetLinkEditorSessionCSO(getUserId(), editorData));
      else
        ret = getLocal().insert(new UserResetLinkEditorSessionCSO(getUserId(), editorData));
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

  public UserResetLinkCK copy(UserResetLinkImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      UserResetLinkCK ret = null;
      if (isRemoteConnection())
        ret = getRemote().copy(new UserResetLinkEditorSessionCSO(getUserId(), editorData));
      else {
        ret = getLocal().copy(new UserResetLinkEditorSessionCSO(getUserId(), editorData));
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

  public void update(UserResetLinkImpl editorData) throws CPException, ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      if (isRemoteConnection())
        getRemote().update(new UserResetLinkEditorSessionCSO(getUserId(), editorData));
      else {
        getLocal().update(new UserResetLinkEditorSessionCSO(getUserId(), editorData));
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
    return "ejb/UserResetLinkEditorSessionRemoteHome";
  }

  public String getLocalJNDIName()
  {
    return "ejb/UserResetLinkEditorSessionLocalHome";
  }
}