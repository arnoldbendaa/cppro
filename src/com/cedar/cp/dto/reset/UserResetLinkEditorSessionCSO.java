package com.cedar.cp.dto.reset;

import java.io.Serializable;

public class UserResetLinkEditorSessionCSO
  implements Serializable
{
  private int mUserId;
  private UserResetLinkImpl mEditorData;

  public UserResetLinkEditorSessionCSO(int userId, UserResetLinkImpl editorData)
  {
    mUserId = userId;
    mEditorData = editorData;
  }

  public UserResetLinkImpl getEditorData()
  {
    return mEditorData;
  }

  public int getUserId()
  {
    return mUserId;
  }
}