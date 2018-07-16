package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskRemote;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface RecalculateBatchTaskHome extends EJBHome {

   RecalculateBatchTaskRemote create(RecalculateBatchTaskEVO var1) throws EJBException, CreateException, RemoteException;

   RecalculateBatchTaskRemote findByPrimaryKey(RecalculateBatchTaskPK var1) throws EJBException, FinderException, RemoteException;
}
