// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.SecurityAccessDefCK;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionCSO;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionSSO;
import com.cedar.cp.dto.model.SecurityAccessDefImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.SecurityAccessDefEditorSessionHome;
import com.cedar.cp.ejb.api.model.SecurityAccessDefEditorSessionLocal;
import com.cedar.cp.ejb.api.model.SecurityAccessDefEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.SecurityAccessDefEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class SecurityAccessDefEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/SecurityAccessDefEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/SecurityAccessDefEditorSessionLocalHome";
   protected SecurityAccessDefEditorSessionRemote mRemote;
   protected SecurityAccessDefEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public SecurityAccessDefEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public SecurityAccessDefEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private SecurityAccessDefEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            SecurityAccessDefEditorSessionHome e = (SecurityAccessDefEditorSessionHome)this.getHome(jndiName, SecurityAccessDefEditorSessionHome.class);
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

   private SecurityAccessDefEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            SecurityAccessDefEditorSessionLocalHome e = (SecurityAccessDefEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public EntityList getAvailableModels() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      EntityList ret = this.getConnection().getListHelper().getAllModels();
      if(timer != null) {
         timer.logDebug("getModelList", "");
      }

      return ret;
   }

   public EntityList getOwnershipRefs(Object pk_) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getOwnershipData(this.getUserId(), pk_);
         } else {
            e = this.getLocal().getOwnershipData(this.getUserId(), pk_);
         }

         if(timer != null) {
            timer.logDebug("getOwnershipRefs", pk_ != null?pk_.toString():"null");
         }

         return e;
      } catch (Exception var4) {
         throw new CPException("unable to getOwnershipRefs(" + pk_ + ") from server " + var4.getMessage(), var4);
      }
   }

   public SecurityAccessDefEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityAccessDefEditorSessionSSO e = null;
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

   public SecurityAccessDefEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityAccessDefEditorSessionSSO e = null;
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

   public SecurityAccessDefCK insert(SecurityAccessDefImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityAccessDefCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new SecurityAccessDefEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new SecurityAccessDefEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public SecurityAccessDefCK copy(SecurityAccessDefImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityAccessDefCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new SecurityAccessDefEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new SecurityAccessDefEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(SecurityAccessDefImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new SecurityAccessDefEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new SecurityAccessDefEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/SecurityAccessDefEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/SecurityAccessDefEditorSessionLocalHome";
   }
}
