package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.reset.UserResetLinkEditor;
import com.cedar.cp.api.reset.UserResetLinkEditorSession;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionSSO;
import com.cedar.cp.dto.reset.UserResetLinkImpl;
import com.cedar.cp.ejb.api.reset.UserResetLinkEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;
import java.util.Set;

public class UserResetLinkEditorSessionImpl extends BusinessSessionImpl
  implements UserResetLinkEditorSession
{
  protected UserResetLinkEditorSessionSSO mServerSessionData;
  protected UserResetLinkImpl mEditorData;
  protected UserResetLinkEditorImpl mClientEditor;
  private Log mLog = new Log(getClass());

  public UserResetLinkEditorSessionImpl(UserResetLinksProcessImpl process, Object key)
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
      throw new RuntimeException("Can't get UserResetLink", e);
    }

    mEditorData = mServerSessionData.getEditorData();
  }

  protected UserResetLinkEditorSessionServer getSessionServer() throws CPException
  {
    return new UserResetLinkEditorSessionServer(getConnection());
  }

  public UserResetLinkEditor getUserResetLinkEditor()
  {
    if (mClientEditor == null)
    {
      mClientEditor = new UserResetLinkEditorImpl(this, mServerSessionData, mEditorData);
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