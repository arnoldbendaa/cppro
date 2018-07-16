package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.reset.UserResetLink;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.reset.UserResetLinkImpl;
import com.cedar.cp.dto.reset.UserResetLinkPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;

public class UserResetLinkAdapter
  implements UserResetLink
{
  private UserResetLinkImpl mEditorData;
  private UserResetLinkEditorSessionImpl mEditorSessionImpl;

  public UserResetLinkAdapter(UserResetLinkEditorSessionImpl e, UserResetLinkImpl editorData)
  {
    mEditorData = editorData;
    mEditorSessionImpl = e;
  }

  public void setPrimaryKey(Object key)
  {
    mEditorData.setPrimaryKey(key);
  }

  protected UserResetLinkEditorSessionImpl getEditorSessionImpl()
  {
    return mEditorSessionImpl;
  }

  protected UserResetLinkImpl getEditorData()
  {
    return mEditorData;
  }

  public Object getPrimaryKey()
  {
    return mEditorData.getPrimaryKey();
  }

  void setPrimaryKey(UserResetLinkPK paramKey)
  {
    mEditorData.setPrimaryKey(paramKey);
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
}