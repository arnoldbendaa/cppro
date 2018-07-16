// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.perftest;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunsProcess;
import com.cedar.cp.utc.perftest.CPConnectionAware;
import com.cedar.cp.util.perfTest.PerfTest;

public abstract class AbstractServerPerfTest implements PerfTest, CPConnectionAware {

   private CPConnection mCPConnection;


   public long runTest() {
      PerfTestRunsProcess testRunsProcess = this.mCPConnection.getPerfTestRunsProcess();

      try {
         return testRunsProcess.executePerfTest(this.getServerClassName());
      } catch (ValidationException var3) {
         throw new RuntimeException(var3.getMessage(), var3);
      }
   }

   public void setCPConnection(CPConnection cnx) {
      this.mCPConnection = cnx;
   }

   protected abstract String getServerClassName();
}
