// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftest;

import com.cedar.cp.dto.perftest.PerfTestPK;
import com.cedar.cp.ejb.impl.perftest.PerfTestEVO;
import com.cedar.cp.ejb.impl.perftest.PerfTestRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface PerfTestHome extends EJBHome {

   PerfTestRemote create(PerfTestEVO var1) throws EJBException, CreateException, RemoteException;

   PerfTestRemote findByPrimaryKey(PerfTestPK var1) throws EJBException, FinderException, RemoteException;
}
