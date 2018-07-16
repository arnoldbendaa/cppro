// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.xmlform.rebuild;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildEditorSessionCSO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildEditorSessionSSO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.xmlform.rebuild.FormRebuildEditorSessionHome;
import com.cedar.cp.ejb.api.xmlform.rebuild.FormRebuildEditorSessionLocal;
import com.cedar.cp.ejb.api.xmlform.rebuild.FormRebuildEditorSessionLocalHome;
import com.cedar.cp.ejb.api.xmlform.rebuild.FormRebuildEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class FormRebuildEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/FormRebuildEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/FormRebuildEditorSessionLocalHome";
   protected FormRebuildEditorSessionRemote mRemote;
   protected FormRebuildEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public FormRebuildEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public FormRebuildEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private FormRebuildEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            FormRebuildEditorSessionHome e = (FormRebuildEditorSessionHome)this.getHome(jndiName, FormRebuildEditorSessionHome.class);
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

   private FormRebuildEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            FormRebuildEditorSessionLocalHome e = (FormRebuildEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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
      EntityList ret = this.getConnection().getListHelper().getAllModelsForLoggedUser();
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

   public FormRebuildEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FormRebuildEditorSessionSSO e = null;
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

   public FormRebuildEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FormRebuildEditorSessionSSO e = null;
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

   public FormRebuildCK insert(FormRebuildImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FormRebuildCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new FormRebuildEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new FormRebuildEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public FormRebuildCK copy(FormRebuildImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FormRebuildCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new FormRebuildEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new FormRebuildEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(FormRebuildImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new FormRebuildEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new FormRebuildEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public List submit(EntityRef ref, int userId) throws CPException, ValidationException {
      return this.submit(ref, userId, 0);
   }

   public List submit(EntityRef ref, int userId, int issuingTaskId) throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().submit(ref, userId, issuingTaskId):this.getLocal().submit(ref, userId, issuingTaskId);
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/FormRebuildEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/FormRebuildEditorSessionLocalHome";
   }
}
