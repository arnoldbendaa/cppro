// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunEVO;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface PerfTestRunHome extends EJBHome {

   PerfTestRunRemote create(PerfTestRunEVO var1) throws EJBException, CreateException, RemoteException;

   PerfTestRunRemote findByPrimaryKey(PerfTestRunPK var1) throws EJBException, FinderException, RemoteException;
}
