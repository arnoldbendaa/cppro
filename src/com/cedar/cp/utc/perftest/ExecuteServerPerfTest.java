// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.perftest;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunsProcess;
import com.cedar.cp.utc.perftest.AbstractWebPerfTest;

public class ExecuteServerPerfTest extends AbstractWebPerfTest {

   public void runWebTest(CPConnection cnx) {
      PerfTestRunsProcess testRunsProcess = cnx.getPerfTestRunsProcess();

      try {
         testRunsProcess.executePerfTest("com.cedar.cp.ejb.impl.perftestrun.tests.ExecuteServerPerfTest");
      } catch (ValidationException var4) {
         throw new RuntimeException(var4.getMessage(), var4);
      }
   }
}
