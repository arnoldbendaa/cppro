// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionCSO;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementCategoryImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.virement.VirementCategoryEditorSessionHome;
import com.cedar.cp.ejb.api.model.virement.VirementCategoryEditorSessionLocal;
import com.cedar.cp.ejb.api.model.virement.VirementCategoryEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.virement.VirementCategoryEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class VirementCategoryEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/VirementCategoryEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/VirementCategoryEditorSessionLocalHome";
   protected VirementCategoryEditorSessionRemote mRemote;
   protected VirementCategoryEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public VirementCategoryEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public VirementCategoryEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private VirementCategoryEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            VirementCategoryEditorSessionHome e = (VirementCategoryEditorSessionHome)this.getHome(jndiName, VirementCategoryEditorSessionHome.class);
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

   private VirementCategoryEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            VirementCategoryEditorSessionLocalHome e = (VirementCategoryEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public VirementCategoryEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementCategoryEditorSessionSSO e = null;
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

   public VirementCategoryEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementCategoryEditorSessionSSO e = null;
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

   public VirementCategoryCK insert(VirementCategoryImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementCategoryCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new VirementCategoryEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new VirementCategoryEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public VirementCategoryCK copy(VirementCategoryImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementCategoryCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new VirementCategoryEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new VirementCategoryEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(VirementCategoryImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new VirementCategoryEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new VirementCategoryEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void validateVirementPosting(boolean to, ModelRef modelRef, FinanceCubeRef financeCubeRef, String respobsibilityAreaVisId, String accountElementVisId, double temporaryVirement, double permanentVirement) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().validateVirementPosting(to, modelRef, financeCubeRef, respobsibilityAreaVisId, accountElementVisId, temporaryVirement, permanentVirement);
         } else {
            this.getLocal().validateVirementPosting(to, modelRef, financeCubeRef, respobsibilityAreaVisId, accountElementVisId, temporaryVirement, permanentVirement);
         }

         if(timer != null) {
            timer.logDebug("validateVirementPosting");
         }

      } catch (Exception var12) {
         throw this.unravelException(var12);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/VirementCategoryEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/VirementCategoryEditorSessionLocalHome";
   }
}
