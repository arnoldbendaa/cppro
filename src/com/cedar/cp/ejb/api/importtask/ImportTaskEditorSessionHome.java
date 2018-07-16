package com.cedar.cp.ejb.api.importtask;

import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ImportTaskEditorSessionHome extends EJBHome {

	ImportTaskEditorSessionRemote create() throws RemoteException, CreateException;
}
