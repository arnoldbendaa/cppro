// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.mapping;

import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MappedModelEditorSessionHome extends EJBHome {

   MappedModelEditorSessionRemote create() throws RemoteException, CreateException;
}
