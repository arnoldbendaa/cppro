package com.cedar.cp.ejb.api.masterquestion;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public abstract interface MasterQuestionEditorSessionLocalHome extends EJBLocalHome
{
  public abstract MasterQuestionEditorSessionLocal create()
    throws CreateException;
}