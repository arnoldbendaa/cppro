// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.performance;

import com.cedar.cp.util.performance.PerformanceDatum;
import java.io.Serializable;
import java.util.List;

public interface PerformanceType extends Serializable {

   String getType();

   String getDescription();

   List getPerformanceDatumPointNames();

   List getPerformanceDatums();

   PerformanceDatum getPerformanceDatum(String var1);
}
