package com.cedar.cp.dto.reset;

import java.io.Serializable;

public class UserResetLinkEditorSessionSSO
  implements Serializable
{
  private UserResetLinkImpl mEditorData;

  public UserResetLinkEditorSessionSSO()
  {
  }

  public UserResetLinkEditorSessionSSO(UserResetLinkImpl paramEditorData)
  {
    mEditorData = paramEditorData;
  }

  public void setEditorData(UserResetLinkImpl paramEditorData)
  {
    mEditorData = paramEditorData;
  }

  public UserResetLinkImpl getEditorData()
  {
    return mEditorData;
  }
}