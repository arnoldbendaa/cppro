// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.amm;

import com.cedar.cp.ejb.api.model.amm.AmmModelEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AmmModelEditorSessionHome extends EJBHome {

   AmmModelEditorSessionRemote create() throws RemoteException, CreateException;
}
