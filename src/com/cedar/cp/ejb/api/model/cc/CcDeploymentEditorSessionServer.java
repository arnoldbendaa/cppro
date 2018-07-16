// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.cc;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionCSO;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.cc.CcDeploymentEditorSessionHome;
import com.cedar.cp.ejb.api.model.cc.CcDeploymentEditorSessionLocal;
import com.cedar.cp.ejb.api.model.cc.CcDeploymentEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.cc.CcDeploymentEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class CcDeploymentEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/CcDeploymentEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/CcDeploymentEditorSessionLocalHome";
   protected CcDeploymentEditorSessionRemote mRemote;
   protected CcDeploymentEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public CcDeploymentEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public CcDeploymentEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private CcDeploymentEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            CcDeploymentEditorSessionHome e = (CcDeploymentEditorSessionHome)this.getHome(jndiName, CcDeploymentEditorSessionHome.class);
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

   private CcDeploymentEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            CcDeploymentEditorSessionLocalHome e = (CcDeploymentEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public CcDeploymentEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CcDeploymentEditorSessionSSO e = null;
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

   public CcDeploymentEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CcDeploymentEditorSessionSSO e = null;
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

   public CcDeploymentCK insert(CcDeploymentImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CcDeploymentCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new CcDeploymentEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new CcDeploymentEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public CcDeploymentCK copy(CcDeploymentImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CcDeploymentCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new CcDeploymentEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new CcDeploymentEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(CcDeploymentImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new CcDeploymentEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new CcDeploymentEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String testDeployment(int modelId, StructureElementRef[] origin, boolean[] cascade) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         String e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().testDeployment(modelId, origin, cascade);
         } else {
            e = this.getLocal().testDeployment(modelId, origin, cascade);
         }

         if(timer != null) {
            timer.logDebug("testDeployment");
         }

         return e;
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public int[] issueCellCalcRebuildTask(List<Object[]> rebuildList) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueCellCalcRebuildTask(this.getUserId(), rebuildList):this.getLocal().issueCellCalcRebuildTask(this.getUserId(), rebuildList);
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/CcDeploymentEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/CcDeploymentEditorSessionLocalHome";
   }
}
