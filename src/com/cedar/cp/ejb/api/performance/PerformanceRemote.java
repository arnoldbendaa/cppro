// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.performance;

import com.cedar.cp.util.performance.PerformanceData;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PerformanceRemote extends EJBObject {

   PerformanceData getPerformanceData() throws RemoteException;

   PerformanceType getPerformanceType(String var1) throws RemoteException;

   PerformanceDatum getPerformanceDatum(String var1) throws RemoteException;
}
