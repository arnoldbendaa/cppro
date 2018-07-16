// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.systemproperty;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionCSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionSSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.systemproperty.SystemPropertyEditorSessionHome;
import com.cedar.cp.ejb.api.systemproperty.SystemPropertyEditorSessionLocal;
import com.cedar.cp.ejb.api.systemproperty.SystemPropertyEditorSessionLocalHome;
import com.cedar.cp.ejb.api.systemproperty.SystemPropertyEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class SystemPropertyEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/SystemPropertyEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/SystemPropertyEditorSessionLocalHome";
   protected SystemPropertyEditorSessionRemote mRemote;
   protected SystemPropertyEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public SystemPropertyEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public SystemPropertyEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private SystemPropertyEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            SystemPropertyEditorSessionHome e = (SystemPropertyEditorSessionHome)this.getHome(jndiName, SystemPropertyEditorSessionHome.class);
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

   private SystemPropertyEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            SystemPropertyEditorSessionLocalHome e = (SystemPropertyEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public SystemPropertyEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SystemPropertyEditorSessionSSO e = null;
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

   public SystemPropertyEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SystemPropertyEditorSessionSSO e = null;
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

   public SystemPropertyPK insert(SystemPropertyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SystemPropertyPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new SystemPropertyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new SystemPropertyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public SystemPropertyPK copy(SystemPropertyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SystemPropertyPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new SystemPropertyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new SystemPropertyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(SystemPropertyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new SystemPropertyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new SystemPropertyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/SystemPropertyEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/SystemPropertyEditorSessionLocalHome";
   }
}
