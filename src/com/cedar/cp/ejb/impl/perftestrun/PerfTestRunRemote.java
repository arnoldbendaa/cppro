// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.perftestrun.PerfTestRunCK;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunEVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PerfTestRunRemote extends EJBObject {

   PerfTestRunEVO getDetails(String var1) throws ValidationException, RemoteException;

   PerfTestRunEVO getDetails(PerfTestRunCK var1, String var2) throws ValidationException, RemoteException;

   PerfTestRunPK generateKeys();

   void setDetails(PerfTestRunEVO var1) throws RemoteException;

   PerfTestRunEVO setAndGetDetails(PerfTestRunEVO var1, String var2) throws RemoteException;
}
