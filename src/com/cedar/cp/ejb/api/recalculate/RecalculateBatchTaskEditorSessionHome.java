package com.cedar.cp.ejb.api.recalculate;

import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionRemote;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface RecalculateBatchTaskEditorSessionHome extends EJBHome {

	RecalculateBatchTaskEditorSessionRemote create() throws RemoteException, CreateException;
}
