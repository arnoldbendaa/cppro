package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskCK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface RecalculateBatchTaskRemote extends EJBObject {

	RecalculateBatchTaskEVO getDetails(String var1) throws ValidationException, RemoteException;

	RecalculateBatchTaskEVO getDetails(RecalculateBatchTaskCK var1, String var2) throws ValidationException, RemoteException;

   void setDetails(RecalculateBatchTaskEVO var1) throws RemoteException;

   RecalculateBatchTaskEVO setAndGetDetails(RecalculateBatchTaskEVO var1, String var2) throws RemoteException;
}
