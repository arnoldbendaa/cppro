// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.performance;

import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;
import java.io.Serializable;
import java.util.Collection;

public interface PerformanceData extends Serializable {

   Collection getPerformanceTypes();

   PerformanceType getPerformanceType(String var1);

   PerformanceDatum getPerformanceDatum(String var1);
}
