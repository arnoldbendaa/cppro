package com.cedar.cp.ejb.api.dataeditor;

import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DataEditorEditorSessionHome extends EJBHome {

   DataEditorEditorSessionRemote create() throws RemoteException, CreateException;
}
