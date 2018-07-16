// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.perftest;

import com.cedar.cp.utc.perftest.AbstractServerPerfTest;

public class JdbcConnectionPerfTest extends AbstractServerPerfTest {

   protected String getServerClassName() {
      return "com.cedar.cp.ejb.impl.perftestrun.tests.JdbcConnectionPerfTest";
   }
}
