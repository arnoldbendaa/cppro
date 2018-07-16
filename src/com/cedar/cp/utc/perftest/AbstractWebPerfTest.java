// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.perftest;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.perftest.CPConnectionAware;
import com.cedar.cp.util.perfTest.PerfTest;

public abstract class AbstractWebPerfTest implements PerfTest, CPConnectionAware {

   private CPConnection mCPConnection;


   public long runTest() {
      long start = System.currentTimeMillis();
      this.runWebTest(this.mCPConnection);
      long end = System.currentTimeMillis();
      return end - start;
   }

   public void setCPConnection(CPConnection cnx) {
      this.mCPConnection = cnx;
   }

   protected abstract void runWebTest(CPConnection var1);
}
