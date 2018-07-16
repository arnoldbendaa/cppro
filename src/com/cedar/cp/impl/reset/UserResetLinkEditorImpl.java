package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.reset.UserResetLink;
import com.cedar.cp.api.reset.UserResetLinkEditor;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionSSO;
import com.cedar.cp.dto.reset.UserResetLinkImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;

public class UserResetLinkEditorImpl extends BusinessEditorImpl
  implements UserResetLinkEditor
{
  private UserResetLinkEditorSessionSSO mServerSessionData;
  private UserResetLinkImpl mEditorData;
  private UserResetLinkAdapter mEditorDataAdapter;

  public UserResetLinkEditorImpl(UserResetLinkEditorSessionImpl session, UserResetLinkEditorSessionSSO serverSessionData, UserResetLinkImpl editorData)
  {
    super(session);

    mServerSessionData = serverSessionData;

    mEditorData = editorData;
  }

  public void updateEditorData(UserResetLinkEditorSessionSSO serverSessionData, UserResetLinkImpl editorData)
  {
    mServerSessionData = serverSessionData;
    mEditorData = editorData;
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
    return ((UserResetLinkEditorSessionImpl)getBusinessSession()).getOwnershipRefs();
  }

  public UserResetLink getUserResetLink()
  {
    if (mEditorDataAdapter == null)
    {
      mEditorDataAdapter = new UserResetLinkAdapter((UserResetLinkEditorSessionImpl)getBusinessSession(), mEditorData);
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