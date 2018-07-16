package com.cedar.cp.ejb.api.reset;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public abstract interface ChallengeQuestionEditorSessionLocalHome extends EJBLocalHome
{
  public abstract ChallengeQuestionEditorSessionLocal create()
    throws CreateException;
}