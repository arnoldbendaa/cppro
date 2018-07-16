// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskEditorSession;
import com.cedar.cp.api.admin.tidytask.TidyTasksProcess;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionServer;
import com.cedar.cp.impl.admin.tidytask.TidyTaskEditorSessionImpl;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class TidyTasksProcessImpl extends BusinessProcessImpl implements TidyTasksProcess {

   private Log mLog = new Log(this.getClass());


   public TidyTasksProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      TidyTaskEditorSessionServer es = new TidyTaskEditorSessionServer(this.getConnection());

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

   public TidyTaskEditorSession getTidyTaskEditorSession(Object key) throws ValidationException {
      TidyTaskEditorSessionImpl sess = new TidyTaskEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllTidyTasks() {
      try {
         return this.getConnection().getListHelper().getAllTidyTasks();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllTidyTasks", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing TidyTask";
      return ret;
   }

   protected int getProcessID() {
      return 83;
   }

   public int issueTidyTask(EntityRef ref, int userId) throws ValidationException {
      TidyTaskEditorSessionServer server = new TidyTaskEditorSessionServer(this.getConnection());
      return server.issueTidyTask(ref, userId);
   }

   public int issueTestTask(Integer testId, int userId) throws ValidationException {
      TidyTaskEditorSessionServer server = new TidyTaskEditorSessionServer(this.getConnection());
      return server.issueTestTask(testId, userId);
   }

   public int issueTestRollbackTask() throws ValidationException {
      TidyTaskEditorSessionServer server = new TidyTaskEditorSessionServer(this.getConnection());
      return server.issueTestRollbackTask();
   }
}
