// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.test;

import com.cedar.cp.ejb.base.async.AbstractTask;
import javax.naming.InitialContext;

public class TestTask extends AbstractTask {

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "TestTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {}
}
