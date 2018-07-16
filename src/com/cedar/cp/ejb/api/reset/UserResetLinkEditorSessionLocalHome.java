package com.cedar.cp.ejb.api.reset;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public abstract interface UserResetLinkEditorSessionLocalHome extends EJBLocalHome
{
  public abstract UserResetLinkEditorSessionLocal create()
    throws CreateException;
}