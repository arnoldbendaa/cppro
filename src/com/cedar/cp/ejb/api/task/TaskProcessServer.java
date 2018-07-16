// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.task;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.task.TaskDetails;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.task.TaskProcessHome;
import com.cedar.cp.ejb.api.task.TaskProcessLocal;
import com.cedar.cp.ejb.api.task.TaskProcessLocalHome;
import com.cedar.cp.ejb.api.task.TaskProcessRemote;
import com.cedar.cp.ejb.impl.task.TaskProcessSEJB;
import com.cedar.cp.util.Log;
import com.softproideas.app.admin.monitors.services.TaskViewerServiceImpl;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class TaskProcessServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/TaskProcessRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/TaskProcessLocalHome";
   protected TaskProcessRemote mRemote;
   protected TaskProcessLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public TaskProcessServer(CPConnection conn_) {
      super(conn_);
   }

   public TaskProcessServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private TaskProcessSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            TaskProcessHome e = (TaskProcessHome)this.getHome(jndiName, TaskProcessHome.class);
//            this.mRemote = e.create();
//         } catch (CreateException var3) {
//            this.removeFromCache(jndiName);
//            var3.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " CreateException", var3);
//         } catch (RemoteException var4) {
//            this.removeFromCache(jndiName);
//            var4.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//         }
	   return TaskViewerServiceImpl.server;
//      }

//      return this.mRemote;
   }

   private TaskProcessSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            TaskProcessLocalHome e = (TaskProcessLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return TaskViewerServiceImpl.server;
   }

   public void removeSession() throws CPException {}

   public TaskDetails getTask(TaskPK taskPK) throws ValidationException, CPException {
      try {
         TaskDetails e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getTask(taskPK);
         } else {
            e = this.getLocal().getTask(taskPK);
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public void delete(TaskPK taskPK) throws ValidationException, CPException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().delete(taskPK);
//            this.getRemote().ejbRemove();
         } else {
//            this.getLocal().delete(taskPK);
            this.getRemote().ejbRemove();
         }

      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public void failTask(TaskPK taskPK) throws ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().failTask(taskPK);
         } else {
            this.getLocal().failTask(taskPK);
         }

      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public void unsafeDeleteTask(int userId, TaskPK taskPK) throws ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().unsafeDeleteTask(userId, taskPK);
         } else {
            this.getLocal().unsafeDeleteTask(userId, taskPK);
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public EntityList getPageTasks(int page, int offset) throws ValidationException, CPException {
      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getPageTasks(page, offset);
         } else {
            e = this.getLocal().getPageTasks(page, offset);
         }

         return e;
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public EntityList getAllTasks() throws ValidationException, CPException {
      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getTasks();
         } else {
            e = this.getLocal().getTasks();
         }

         return e;
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public EntityList getTaskEvents(int taskId) throws ValidationException, CPException {
      try {
         return this.isRemoteConnection()?this.getRemote().getTaskEvents(taskId):this.getLocal().getTaskEvents(taskId);
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public int getNewTaskId() throws ValidationException {
      try {
         boolean e = true;
         int e1;
         if(this.isRemoteConnection()) {
            e1 = this.getRemote().getNewTaskId();
         } else {
            e1 = this.getLocal().getNewTaskId();
         }

         return e1;
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public void restartTask(int taskId) throws ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().restartTask(taskId);
         } else {
            this.getLocal().restartTask(taskId);
         }

      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public void wakeUpDespatcher() throws ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().wakeUpDespatcher();
         } else {
            this.getLocal().wakeUpDespatcher();
         }

      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public int newTask(int userId, int taskType, TaskRequest request, long systemTime, int issuingTaskId) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().newTask(userId, taskType, request, systemTime, issuingTaskId):this.getLocal().newTask(userId, taskType, request, systemTime, issuingTaskId);
      } catch (Exception var8) {
         throw this.unravelException(var8);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/TaskProcessRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/TaskProcessLocalHome";
   }
}
