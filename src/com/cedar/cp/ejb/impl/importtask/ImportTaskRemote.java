package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.importtask.ImportTaskCK;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ImportTaskRemote extends EJBObject {

   ImportTaskEVO getDetails(String var1) throws ValidationException, RemoteException;

   ImportTaskEVO getDetails(ImportTaskCK var1, String var2) throws ValidationException, RemoteException;

   ImportTaskPK generateKeys();

   void setDetails(ImportTaskEVO var1) throws RemoteException;

   ImportTaskEVO setAndGetDetails(ImportTaskEVO var1, String var2) throws RemoteException;
}
