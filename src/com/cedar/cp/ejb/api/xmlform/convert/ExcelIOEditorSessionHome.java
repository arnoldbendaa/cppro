package com.cedar.cp.ejb.api.xmlform.convert;


import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ExcelIOEditorSessionHome extends EJBHome {

	ExcelIOEditorSessionRemote create() throws RemoteException, CreateException;
}
