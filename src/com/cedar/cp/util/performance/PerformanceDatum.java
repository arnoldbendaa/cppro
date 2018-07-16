// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.performance;

import com.cedar.cp.util.performance.PerformanceType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PerformanceDatum extends Serializable {

   PerformanceType getType();

   String getId();

   Date getWhenInitiated();

   String getWhoInitiated();

   double getElapsedSeconds();

   List getAllDatumPoints();

   Map getAdditionalProperties();
}
