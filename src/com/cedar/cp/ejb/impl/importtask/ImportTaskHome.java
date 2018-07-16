package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEVO;
import com.cedar.cp.ejb.impl.importtask.ImportTaskRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface ImportTaskHome extends EJBHome {

   ImportTaskRemote create(ImportTaskEVO var1) throws EJBException, CreateException, RemoteException;

   ImportTaskRemote findByPrimaryKey(ImportTaskPK var1) throws EJBException, FinderException, RemoteException;
}
