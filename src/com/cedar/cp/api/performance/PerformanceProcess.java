// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.performance;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.util.performance.PerformanceData;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;

public interface PerformanceProcess {

   PerformanceData getPerformanceData() throws ValidationException, CPException;

   PerformanceType getPerformanceType(String var1) throws ValidationException, CPException;

   PerformanceDatum getPerformanceDatum(String var1) throws ValidationException, CPException;
}
