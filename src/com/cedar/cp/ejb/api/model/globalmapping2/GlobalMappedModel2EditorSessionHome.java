package com.cedar.cp.ejb.api.model.globalmapping2;

import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface GlobalMappedModel2EditorSessionHome extends EJBHome {

   GlobalMappedModel2EditorSessionRemote create() throws RemoteException, CreateException;
}
