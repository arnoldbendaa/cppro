// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.recharge;

import com.cedar.cp.ejb.api.model.recharge.RechargeGroupEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface RechargeGroupEditorSessionHome extends EJBHome {

   RechargeGroupEditorSessionRemote create() throws RemoteException, CreateException;
}
