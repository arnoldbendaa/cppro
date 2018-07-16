// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.task.group;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionCSO;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionSSO;
import com.cedar.cp.dto.task.group.TaskGroupImpl;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.task.group.TaskGroupEditorSessionHome;
import com.cedar.cp.ejb.api.task.group.TaskGroupEditorSessionLocal;
import com.cedar.cp.ejb.api.task.group.TaskGroupEditorSessionLocalHome;
import com.cedar.cp.ejb.api.task.group.TaskGroupEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class TaskGroupEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/TaskGroupEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/TaskGroupEditorSessionLocalHome";
   protected TaskGroupEditorSessionRemote mRemote;
   protected TaskGroupEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public TaskGroupEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public TaskGroupEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private TaskGroupEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            TaskGroupEditorSessionHome e = (TaskGroupEditorSessionHome)this.getHome(jndiName, TaskGroupEditorSessionHome.class);
            this.mRemote = e.create();
         } catch (CreateException var3) {
            this.removeFromCache(jndiName);
            var3.printStackTrace();
            throw new CPException("getRemote " + jndiName + " CreateException", var3);
         } catch (RemoteException var4) {
            this.removeFromCache(jndiName);
            var4.printStackTrace();
            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
         }
      }

      return this.mRemote;
   }

   private TaskGroupEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            TaskGroupEditorSessionLocalHome e = (TaskGroupEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public void delete(Object primaryKey_) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().delete(this.getUserId(), primaryKey_);
         } else {
            this.getLocal().delete(this.getUserId(), primaryKey_);
         }

         if(timer != null) {
            timer.logDebug("delete", primaryKey_.toString());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public TaskGroupEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TaskGroupEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getNewItemData(this.getUserId());
         } else {
            e = this.getLocal().getNewItemData(this.getUserId());
         }

         if(timer != null) {
            timer.logDebug("getNewItemData", "");
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public TaskGroupEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TaskGroupEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getItemData(this.getUserId(), key);
         } else {
            e = this.getLocal().getItemData(this.getUserId(), key);
         }

         if(timer != null) {
            timer.logDebug("getItemData", key.toString());
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public TaskGroupPK insert(TaskGroupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TaskGroupPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new TaskGroupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new TaskGroupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public TaskGroupPK copy(TaskGroupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TaskGroupPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new TaskGroupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new TaskGroupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(TaskGroupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new TaskGroupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new TaskGroupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int submitGroup(EntityRef ref, int userId) throws CPException, ValidationException {
      return this.submitGroup(ref, userId, 0);
   }

   public int submitGroup(EntityRef ref, int userId, int issuingTaskId) throws CPException, ValidationException {
      boolean id = false;
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int id1;
         if(this.isRemoteConnection()) {
            id1 = this.getRemote().submitGroup(ref, userId, issuingTaskId);
         } else {
            id1 = this.getLocal().submitGroup(ref, userId, issuingTaskId);
         }

         if(timer != null) {
            timer.logDebug("submitGroup");
         }

         return id1;
      } catch (Exception var7) {
         throw this.unravelException(var7);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/TaskGroupEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/TaskGroupEditorSessionLocalHome";
   }
}
