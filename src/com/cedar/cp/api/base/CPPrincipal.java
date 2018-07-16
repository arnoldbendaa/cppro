package com.cedar.cp.api.base;

import java.io.Serializable;

public class CPPrincipal
  implements Serializable
{
  private String mUserId;
  private String mPassword;

  public CPPrincipal(String userId, String password)
  {
    mUserId = userId;
    mPassword = password;
  }

  public String getUserId()
  {
    return mUserId;
  }

  public void setUserId(String userId)
  {
    mUserId = userId;
  }

  public String getPassword()
  {
    return mPassword;
  }

  public void setPassword(String password)
  {
    mPassword = password;
  }
}