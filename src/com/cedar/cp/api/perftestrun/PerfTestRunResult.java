// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import java.io.Serializable;

public interface PerfTestRunResult extends Serializable {

   Object getPrimaryKey();

   void setPrimaryKey(Object var1);

   int getPerfTestRunId();

   int getPerfTestId();

   long getExecutionTime();

   PerfTestRunRef getPerfTestRunRef();
}
