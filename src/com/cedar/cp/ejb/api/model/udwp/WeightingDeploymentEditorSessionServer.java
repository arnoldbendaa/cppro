// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.udwp;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionCSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.udwp.WeightingDeploymentEditorSessionHome;
import com.cedar.cp.ejb.api.model.udwp.WeightingDeploymentEditorSessionLocal;
import com.cedar.cp.ejb.api.model.udwp.WeightingDeploymentEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.udwp.WeightingDeploymentEditorSessionRemote;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class WeightingDeploymentEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/WeightingDeploymentEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/WeightingDeploymentEditorSessionLocalHome";
   protected WeightingDeploymentEditorSessionRemote mRemote;
   protected WeightingDeploymentEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static WeightingDeploymentEditorSessionSEJB server = new WeightingDeploymentEditorSessionSEJB(); 

   public WeightingDeploymentEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public WeightingDeploymentEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private WeightingDeploymentEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            WeightingDeploymentEditorSessionHome e = (WeightingDeploymentEditorSessionHome)this.getHome(jndiName, WeightingDeploymentEditorSessionHome.class);
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

   private WeightingDeploymentEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            WeightingDeploymentEditorSessionLocalHome e = (WeightingDeploymentEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public EntityList getAvailableWeightingProfiles() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      EntityList ret = this.getConnection().getListHelper().getAllWeightingProfiles();
      if(timer != null) {
         timer.logDebug("getWeightingProfileList", "");
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

   public WeightingDeploymentEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingDeploymentEditorSessionSSO e = null;
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

   public WeightingDeploymentEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingDeploymentEditorSessionSSO e = null;
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

   public WeightingDeploymentCK insert(WeightingDeploymentImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingDeploymentCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new WeightingDeploymentEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new WeightingDeploymentEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public WeightingDeploymentCK copy(WeightingDeploymentImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingDeploymentCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new WeightingDeploymentEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new WeightingDeploymentEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(WeightingDeploymentImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new WeightingDeploymentEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new WeightingDeploymentEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public EntityList queryDeployments() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryDeployments();
         } else {
            e = this.getLocal().queryDeployments();
         }

         if(timer != null) {
            timer.logDebug("queryDeployments");
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelFatalException(var3);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/WeightingDeploymentEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/WeightingDeploymentEditorSessionLocalHome";
   }
}
