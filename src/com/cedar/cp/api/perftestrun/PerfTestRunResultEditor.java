// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.perftestrun;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import com.cedar.cp.api.perftestrun.PerfTestRunResult;

public interface PerfTestRunResultEditor extends BusinessEditor {

   void setPerfTestRunId(int var1) throws ValidationException;

   void setPerfTestId(int var1) throws ValidationException;

   void setExecutionTime(long var1) throws ValidationException;

   void setPerfTestRunRef(PerfTestRunRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   PerfTestRunResult getPerfTestRunResult();
}
