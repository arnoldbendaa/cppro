// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.ra;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionCSO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionSSO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.ra.ResponsibilityAreaEditorSessionHome;
import com.cedar.cp.ejb.api.model.ra.ResponsibilityAreaEditorSessionLocal;
import com.cedar.cp.ejb.api.model.ra.ResponsibilityAreaEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.ra.ResponsibilityAreaEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ResponsibilityAreaEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ResponsibilityAreaEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ResponsibilityAreaEditorSessionLocalHome";
   protected ResponsibilityAreaEditorSessionRemote mRemote;
   protected ResponsibilityAreaEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ResponsibilityAreaEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ResponsibilityAreaEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ResponsibilityAreaEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            ResponsibilityAreaEditorSessionHome e = (ResponsibilityAreaEditorSessionHome)this.getHome(jndiName, ResponsibilityAreaEditorSessionHome.class);
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

   private ResponsibilityAreaEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            ResponsibilityAreaEditorSessionLocalHome e = (ResponsibilityAreaEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ResponsibilityAreaEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ResponsibilityAreaEditorSessionSSO e = null;
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

   public ResponsibilityAreaEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ResponsibilityAreaEditorSessionSSO e = null;
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

   public ResponsibilityAreaCK insert(ResponsibilityAreaImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ResponsibilityAreaCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ResponsibilityAreaEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ResponsibilityAreaEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ResponsibilityAreaCK copy(ResponsibilityAreaImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ResponsibilityAreaCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ResponsibilityAreaEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ResponsibilityAreaEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ResponsibilityAreaImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ResponsibilityAreaEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ResponsibilityAreaEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ResponsibilityAreaEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ResponsibilityAreaEditorSessionLocalHome";
   }
}
