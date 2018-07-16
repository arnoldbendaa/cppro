// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.model.virement.VirementQueryParams;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionCSO;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.virement.VirementRequestEditorSessionHome;
import com.cedar.cp.ejb.api.model.virement.VirementRequestEditorSessionLocal;
import com.cedar.cp.ejb.api.model.virement.VirementRequestEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.virement.VirementRequestEditorSessionRemote;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class VirementRequestEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/VirementRequestEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/VirementRequestEditorSessionLocalHome";
   protected VirementRequestEditorSessionRemote mRemote;
   protected VirementRequestEditorSessionLocal mLocal;
   VirementRequestEditorSessionSEJB server = new VirementRequestEditorSessionSEJB();
   private Log mLog = new Log(this.getClass());


   public VirementRequestEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public VirementRequestEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private VirementRequestEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            VirementRequestEditorSessionHome e = (VirementRequestEditorSessionHome)this.getHome(jndiName, VirementRequestEditorSessionHome.class);
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

   private VirementRequestEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            VirementRequestEditorSessionLocalHome e = (VirementRequestEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public UserRef[] getAvailableOwningUserRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      UserRef[] ret = (UserRef[])((UserRef[])this.getConnection().getListHelper().getAllUsers().getValues("User"));
      if(timer != null) {
         timer.logDebug("getAvailableOwningUserRefs", "");
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

   public VirementRequestEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementRequestEditorSessionSSO e = null;
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

   public VirementRequestEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementRequestEditorSessionSSO e = null;
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

   public VirementRequestCK insert(VirementRequestImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementRequestCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public VirementRequestCK copy(VirementRequestImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementRequestCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(VirementRequestImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int saveAndSubmit(VirementRequestImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().saveAndSubmit(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().saveAndSubmit(new VirementRequestEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("saveAndSubmit", editorData);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int submitVirementRequest(Object primaryKey) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().submitVirementRequest(this.getUserId(), primaryKey);
         } else {
            e = this.getLocal().submitVirementRequest(this.getUserId(), primaryKey);
         }

         if(timer != null) {
            timer.logDebug("submitVirementRequest", primaryKey);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public boolean haveVirementsWhichRequireAuthorisation() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         boolean e;
//         if(this.isRemoteConnection()) {
//            e = this.getRemote().haveVirementsWhichRequireAuthorisation(this.getUserId());
//         } else {
//            e = this.getLocal().haveVirementsWhichRequireAuthorisation(this.getUserId());
//         }
         e = server.haveVirementsWhichRequireAuthorisation(this.getUserId());

         if(timer != null) {
            timer.logDebug("haveVirementsWhichRequireAuthorisation");
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public EntityList queryVirementRequests(boolean includeInherited) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryVirementRequests(this.getUserId(), includeInherited);
         } else {
            e = this.getLocal().queryVirementRequests(this.getUserId(), includeInherited);
         }

         if(timer != null) {
            timer.logDebug("queryVirementRequests");
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public List<DataTypeRef> queryTransferDataTypes(int financeCubeId) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         List e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryTransferDataTypes(this.getUserId(), financeCubeId);
         } else {
            e = this.getLocal().queryTransferDataTypes(this.getUserId(), financeCubeId);
         }

         if(timer != null) {
            timer.logDebug("queryTransferDataTypes");
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public VirementQueryParams getQueryParams(int financeCubeId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         VirementQueryParams e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getQueryParams(this.getUserId(), financeCubeId);
         } else {
            e = this.getLocal().getQueryParams(this.getUserId(), financeCubeId);
         }

         if(timer != null) {
            timer.logDebug("getQueryParams");
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelFatalException(var4);
      }
   }

   public List<String> queryVirementRequests(int modelId, int numDims, Integer creator, Integer authoriser, Integer virementId, Integer virementStatus, List<StructureElementKey> structureElements, Double minimumValue, Double maximumValue, Date fromDate, Date toDate) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         List e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryVirementRequests(this.getUserId(), modelId, numDims, creator, authoriser, virementId, virementStatus, structureElements, minimumValue, maximumValue, fromDate, toDate);
         } else {
            e = this.getLocal().queryVirementRequests(this.getUserId(), modelId, numDims, creator, authoriser, virementId, virementStatus, structureElements, minimumValue, maximumValue, fromDate, toDate);
         }

         if(timer != null) {
            timer.logDebug("queryVirementRequests");
         }

         return e;
      } catch (Exception var14) {
         throw this.unravelFatalException(var14);
      }
   }

   public String queryVirementRequest(int requesrId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      String e;
      try {
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryVirementRequest(this.getUserId(), requesrId);
            return e;
         }

         e = this.getLocal().queryVirementRequest(this.getUserId(), requesrId);
      } catch (Exception var7) {
         throw this.unravelFatalException(var7);
      } finally {
         if(timer != null) {
            timer.logDebug("queryVirementRequest");
         }

      }

      return e;
   }

   public String getRemoteJNDIName() {
      return "ejb/VirementRequestEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/VirementRequestEditorSessionLocalHome";
   }
}
