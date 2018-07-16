package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public abstract interface MasterQuestionHome extends EJBHome
{
  public abstract MasterQuestionRemote create(MasterQuestionEVO paramMasterQuestionEVO)
    throws EJBException, CreateException, RemoteException;

  public abstract MasterQuestionRemote findByPrimaryKey(MasterQuestionPK paramMasterQuestionPK)
    throws EJBException, FinderException, RemoteException;
}