// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.task.group;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.group.TaskGroupEditorSession;
import com.cedar.cp.api.task.group.TaskGroupsProcess;
import com.cedar.cp.ejb.api.task.group.TaskGroupEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.task.group.TaskGroupEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class TaskGroupsProcessImpl extends BusinessProcessImpl implements TaskGroupsProcess {

   private Log mLog = new Log(this.getClass());


   public TaskGroupsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      TaskGroupEditorSessionServer es = new TaskGroupEditorSessionServer(this.getConnection());

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

   public TaskGroupEditorSession getTaskGroupEditorSession(Object key) throws ValidationException {
      TaskGroupEditorSessionImpl sess = new TaskGroupEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllTaskGroups() {
      try {
         return this.getConnection().getListHelper().getAllTaskGroups();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllTaskGroups", var2);
      }
   }

   public EntityList getTaskGroupRICheck(int param1) {
      try {
         return this.getConnection().getListHelper().getTaskGroupRICheck(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get TaskGroupRICheck", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing TaskGroup";
      return ret;
   }

   protected int getProcessID() {
      return 95;
   }

   public int submitGroup(EntityRef ref, int userId) throws ValidationException {
      TaskGroupEditorSessionServer server = new TaskGroupEditorSessionServer(this.getConnection());
      return server.submitGroup(ref, userId);
   }
}
