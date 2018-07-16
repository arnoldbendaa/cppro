// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.logonhistory;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionCSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionSSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryImpl;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.logonhistory.LogonHistoryEditorSessionHome;
import com.cedar.cp.ejb.api.logonhistory.LogonHistoryEditorSessionLocal;
import com.cedar.cp.ejb.api.logonhistory.LogonHistoryEditorSessionLocalHome;
import com.cedar.cp.ejb.api.logonhistory.LogonHistoryEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class LogonHistoryEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/LogonHistoryEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/LogonHistoryEditorSessionLocalHome";
   protected LogonHistoryEditorSessionRemote mRemote;
   protected LogonHistoryEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public LogonHistoryEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public LogonHistoryEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private LogonHistoryEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            LogonHistoryEditorSessionHome e = (LogonHistoryEditorSessionHome)this.getHome(jndiName, LogonHistoryEditorSessionHome.class);
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

   private LogonHistoryEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            LogonHistoryEditorSessionLocalHome e = (LogonHistoryEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public LogonHistoryEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         LogonHistoryEditorSessionSSO e = null;
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

   public LogonHistoryEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         LogonHistoryEditorSessionSSO e = null;
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

   public LogonHistoryPK insert(LogonHistoryImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         LogonHistoryPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new LogonHistoryEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new LogonHistoryEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public LogonHistoryPK copy(LogonHistoryImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         LogonHistoryPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new LogonHistoryEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new LogonHistoryEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(LogonHistoryImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new LogonHistoryEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new LogonHistoryEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/LogonHistoryEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/LogonHistoryEditorSessionLocalHome";
   }
}
