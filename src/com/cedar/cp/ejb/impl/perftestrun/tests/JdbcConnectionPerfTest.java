// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun.tests;

import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunDAO;
import com.cedar.cp.util.perfTest.PerfTest;

public class JdbcConnectionPerfTest implements PerfTest {

   public long runTest() {
      long start = System.currentTimeMillis();
      PerfTestRunDAO dao = new PerfTestRunDAO();
      dao.justConnect();
      long end = System.currentTimeMillis();
      return end - start;
   }
}
