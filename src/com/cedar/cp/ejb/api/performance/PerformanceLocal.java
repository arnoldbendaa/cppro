// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.performance;

import com.cedar.cp.util.performance.PerformanceData;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface PerformanceLocal extends EJBLocalObject {

   PerformanceData getPerformanceData() throws EJBException;

   PerformanceType getPerformanceType(String var1) throws EJBException;

   PerformanceDatum getPerformanceDatum(String var1) throws EJBException;
}
