// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftestrun;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.perftestrun.PerfTestRunResultsProcess;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class PerfTestRunResultsProcessImpl extends BusinessProcessImpl implements PerfTestRunResultsProcess {

   private Log mLog = new Log(this.getClass());


   public PerfTestRunResultsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public EntityList getAllPerfTestRunResults() {
      try {
         return this.getConnection().getListHelper().getAllPerfTestRunResults();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllPerfTestRunResults", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing PerfTestRunResult";
      return ret;
   }

   protected int getProcessID() {
      return 64;
   }
}
