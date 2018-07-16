// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.destination.internal;

import com.cedar.cp.ejb.api.report.destination.internal.InternalDestinationEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface InternalDestinationEditorSessionHome extends EJBHome {

   InternalDestinationEditorSessionRemote create() throws RemoteException, CreateException;
}
