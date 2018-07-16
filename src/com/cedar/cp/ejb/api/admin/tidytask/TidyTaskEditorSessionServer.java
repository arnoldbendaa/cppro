// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.admin.tidytask;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionCSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionSSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionHome;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionLocal;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionLocalHome;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionRemote;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class TidyTaskEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/TidyTaskEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/TidyTaskEditorSessionLocalHome";
   protected TidyTaskEditorSessionRemote mRemote;
   protected TidyTaskEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static TidyTaskEditorSessionSEJB server = new TidyTaskEditorSessionSEJB(); 

   public TidyTaskEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public TidyTaskEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private TidyTaskEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            TidyTaskEditorSessionHome e = (TidyTaskEditorSessionHome)this.getHome(jndiName, TidyTaskEditorSessionHome.class);
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
//      }
//
//      return this.mRemote;
	   return this.server;
   }

   private TidyTaskEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            TidyTaskEditorSessionLocalHome e = (TidyTaskEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return this.server;
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

   public TidyTaskEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TidyTaskEditorSessionSSO e = null;
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

   public TidyTaskEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TidyTaskEditorSessionSSO e = null;
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

   public TidyTaskPK insert(TidyTaskImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TidyTaskPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new TidyTaskEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new TidyTaskEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public TidyTaskPK copy(TidyTaskImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         TidyTaskPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new TidyTaskEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new TidyTaskEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(TidyTaskImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new TidyTaskEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new TidyTaskEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueTidyTask(EntityRef ref, int userId) throws CPException, ValidationException {
      return this.issueTidyTask(ref, userId, 0);
   }

   public int issueTidyTask(EntityRef ref, int userId, int issuingTaskId) throws CPException, ValidationException {
      boolean id = false;
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int id1;
         if(this.isRemoteConnection()) {
            id1 = this.getRemote().issueTidyTask(ref, userId, issuingTaskId);
         } else {
            id1 = this.getLocal().issueTidyTask(ref, userId, issuingTaskId);
         }

         if(timer != null) {
            timer.logDebug("issueTidyTask");
         }

         return id1;
      } catch (Exception var7) {
         throw this.unravelException(var7);
      }
   }

   public int issueTestTask(Integer testid, int userId) throws CPException, ValidationException {
      boolean id = false;
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int id1;
         if(this.isRemoteConnection()) {
            id1 = this.getRemote().issueTestTask(testid, userId);
         } else {
            id1 = this.getLocal().issueTestTask(testid, userId);
         }

         if(timer != null) {
            timer.logDebug("issueTestTask");
         }

         return id1;
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public int issueTestRollbackTask() throws CPException, ValidationException {
      boolean id = false;
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int id1;
         if(this.isRemoteConnection()) {
            id1 = this.getRemote().issueTestRollbackTask(this.getUserId());
         } else {
            id1 = this.getLocal().issueTestRollbackTask(this.getUserId());
         }

         if(timer != null) {
            timer.logDebug("issueTestRollbackTask");
         }

         return id1;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/TidyTaskEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/TidyTaskEditorSessionLocalHome";
   }
}
