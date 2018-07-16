// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftest;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftest.PerfTestEditorSession;
import com.cedar.cp.api.perftest.PerfTestsProcess;
import com.cedar.cp.ejb.api.perftest.PerfTestEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.perftest.PerfTestEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class PerfTestsProcessImpl extends BusinessProcessImpl implements PerfTestsProcess {

   private Log mLog = new Log(this.getClass());


   public PerfTestsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      PerfTestEditorSessionServer es = new PerfTestEditorSessionServer(this.getConnection());

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

   public PerfTestEditorSession getPerfTestEditorSession(Object key) throws ValidationException {
      PerfTestEditorSessionImpl sess = new PerfTestEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllPerfTests() {
      try {
         return this.getConnection().getListHelper().getAllPerfTests();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllPerfTests", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing PerfTest";
      return ret;
   }

   protected int getProcessID() {
      return 62;
   }
}
