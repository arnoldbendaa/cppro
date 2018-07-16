// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.perftestrun;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PerfTestRunRemote extends EJBObject {

   long executePerfTest(String var1) throws RemoteException;
}
