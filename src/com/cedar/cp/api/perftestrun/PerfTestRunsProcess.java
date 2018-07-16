// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.perftestrun;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunEditorSession;

public interface PerfTestRunsProcess extends BusinessProcess {

   EntityList getAllPerfTestRuns();

   PerfTestRunEditorSession getPerfTestRunEditorSession(Object var1) throws ValidationException;

   long executePerfTest(String var1) throws ValidationException;
}