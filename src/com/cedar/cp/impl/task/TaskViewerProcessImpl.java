// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.task;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskViewerProcess;
import com.cedar.cp.api.task.TaskViewerSession;
import com.cedar.cp.dto.task.TaskDetails;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.api.task.TaskProcessServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.task.TaskViewerSessionImpl;

public class TaskViewerProcessImpl extends BusinessProcessImpl implements TaskViewerProcess {

   private EntityList mTasks;


   public TaskViewerProcessImpl(CPConnectionImpl connection) {
      super(connection);
   }

   public EntityList getTasks(int userId) {
      this.refresh();
      this.ensureListsBuilt(userId);
      return this.mTasks;
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      TaskProcessServer messageRemote = new TaskProcessServer(this.getConnection());
      messageRemote.delete((TaskPK)primaryKey);
   }

   public void restartTask(int row) throws ValidationException {
      try {
         TaskProcessServer e = new TaskProcessServer(this.getConnection());
         int id = ((Integer)this.mTasks.getValueAt(row, "TaskId")).intValue();
         e.restartTask(id);
      } catch (ValidationException var4) {
         throw var4;
      } catch (CPException var5) {
         throw var5;
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new RuntimeException("Failed to restart a task", var6);
      }
   }

   public void failTask(int row) throws ValidationException {
      try {
         TaskProcessServer e = new TaskProcessServer(this.getConnection());
         int id = ((Integer)this.mTasks.getValueAt(row, "TaskId")).intValue();
         e.failTask(new TaskPK(id));
      } catch (ValidationException var4) {
         throw var4;
      } catch (CPException var5) {
         throw var5;
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new RuntimeException("Failed to fail a task", var6);
      }
   }

   public void unsafeDeleteTask(int userId, int row) throws ValidationException {
      try {
         TaskProcessServer e = new TaskProcessServer(this.getConnection());
         int id = ((Integer)this.mTasks.getValueAt(row, "TaskId")).intValue();
         e.unsafeDeleteTask(userId, new TaskPK(id));
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw var6;
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new RuntimeException("Failed to delete a task", var7);
      }
   }

   public void wakeUpDespatcher() {
      try {
         TaskProcessServer e = new TaskProcessServer(this.getConnection());
         e.wakeUpDespatcher();
      } catch (CPException var2) {
         throw var2;
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public boolean canRestartTask(int row) {
      try {
         TaskProcessServer e = new TaskProcessServer(this.getConnection());
         int id = ((Integer)this.mTasks.getValueAt(row, "TaskId")).intValue();
         TaskDetails taskDetails = e.getTask(new TaskPK(id));
         return taskDetails.getStatus() == 4;
      } catch (Exception var5) {
         throw new RuntimeException("Failed during task restart validation", var5);
      }
   }

   public void refresh() {
      this.mTasks = null;
   }

   public TaskViewerSession getTaskViewerSession(Object key) {
      try {
         return new TaskViewerSessionImpl(this, key);
      } catch (CPException var3) {
         var3.printStackTrace();
         throw new RuntimeException("getTaskViewrSession", var3);
      }
   }

   public TaskViewerSession getTaskViewerSession(int taskId) {
      return this.getTaskViewerSession(new TaskPK(taskId));
   }

   private void ensureListsBuilt(int userId) {
      try {
         if(this.mTasks == null) {
            TaskProcessServer e = new TaskProcessServer(this.getConnection());
            this.mTasks = e.getAllTasks();
         }

      } catch (Exception var3) {
         throw new RuntimeException("exception in ensureListsBuilt", var3);
      }
   }

   protected int getProcessID() {
      return 21;
   }
}
