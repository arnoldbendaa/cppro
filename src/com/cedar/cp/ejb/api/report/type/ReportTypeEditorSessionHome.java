// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.type;

import com.cedar.cp.ejb.api.report.type.ReportTypeEditorSessionRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ReportTypeEditorSessionHome extends EJBHome {

   ReportTypeEditorSessionRemote create() throws RemoteException, CreateException;
}
