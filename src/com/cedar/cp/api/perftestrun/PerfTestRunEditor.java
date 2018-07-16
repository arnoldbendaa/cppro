// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.perftestrun;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRun;
import com.cedar.cp.api.perftestrun.PerfTestRunResult;
import com.cedar.cp.api.perftestrun.PerfTestRunResultEditor;
import java.sql.Timestamp;

public interface PerfTestRunEditor extends BusinessEditor {

   void setShipped(boolean var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setWhenRan(Timestamp var1) throws ValidationException;

   PerfTestRun getPerfTestRun();

   PerfTestRunResultEditor getEditor(PerfTestRunResult var1) throws ValidationException;
}
