// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftestrun;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunEditorSession;
import com.cedar.cp.api.perftestrun.PerfTestRunsProcess;
import com.cedar.cp.ejb.api.perftestrun.PerfTestRunEditorSessionServer;
import com.cedar.cp.ejb.api.perftestrun.PerfTestRunServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.perftestrun.PerfTestRunEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class PerfTestRunsProcessImpl extends BusinessProcessImpl implements PerfTestRunsProcess {

   private Log mLog = new Log(this.getClass());


   public PerfTestRunsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      PerfTestRunEditorSessionServer es = new PerfTestRunEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public PerfTestRunEditorSession getPerfTestRunEditorSession(Object key) throws ValidationException {
      PerfTestRunEditorSessionImpl sess = new PerfTestRunEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllPerfTestRuns() {
      try {
         return this.getConnection().getListHelper().getAllPerfTestRuns();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllPerfTestRuns", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing PerfTestRun";
      return ret;
   }

   protected int getProcessID() {
      return 63;
   }

   public long executePerfTest(String className) throws ValidationException {
      PerfTestRunServer server = new PerfTestRunServer(this.getConnection());
      return server.executePerfTest(className);
   }
}
