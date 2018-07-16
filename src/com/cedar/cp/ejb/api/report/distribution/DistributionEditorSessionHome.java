// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.distribution;

import com.cedar.cp.ejb.api.report.distribution.DistributionEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DistributionEditorSessionHome extends EJBHome {

   DistributionEditorSessionRemote create() throws RemoteException, CreateException;
}
