package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public abstract interface MasterQuestionLocalHome extends EJBLocalHome
{
  public abstract MasterQuestionLocal create(MasterQuestionEVO paramMasterQuestionEVO)
    throws EJBException, CreateException;

  public abstract MasterQuestionLocal findByPrimaryKey(MasterQuestionPK paramMasterQuestionPK)
    throws FinderException;
}