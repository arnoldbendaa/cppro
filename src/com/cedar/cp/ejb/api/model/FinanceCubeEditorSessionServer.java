// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionCSO;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionSSO;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionHome;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionLocal;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionRemote;
import com.cedar.cp.ejb.impl.model.FinanceCubeEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.Timer;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class FinanceCubeEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/FinanceCubeEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/FinanceCubeEditorSessionLocalHome";
   protected FinanceCubeEditorSessionRemote mRemote;
   protected FinanceCubeEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static FinanceCubeEditorSessionSEJB server = new FinanceCubeEditorSessionSEJB();
   

   public FinanceCubeEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public FinanceCubeEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private FinanceCubeEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            FinanceCubeEditorSessionHome e = (FinanceCubeEditorSessionHome)this.getHome(jndiName, FinanceCubeEditorSessionHome.class);
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

   private FinanceCubeEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            FinanceCubeEditorSessionLocalHome e = (FinanceCubeEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public EntityList getAvailableModels() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
//      EntityList ret = this.getConnection().getListHelper().getAllModels();
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

   public FinanceCubeEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FinanceCubeEditorSessionSSO e = null;
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

   public FinanceCubeEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FinanceCubeEditorSessionSSO e = null;
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

   public FinanceCubeCK insert(FinanceCubeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FinanceCubeCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new FinanceCubeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new FinanceCubeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public FinanceCubeCK copy(FinanceCubeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         FinanceCubeCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new FinanceCubeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new FinanceCubeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(FinanceCubeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new FinanceCubeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new FinanceCubeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueCubeRebuildTask(int userId, List<EntityRef> rebuildList) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueCubeRebuildtask(userId, rebuildList):this.getLocal().issueCubeRebuildtask(userId, rebuildList);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueCheckIntegrityTask(int userId, List<EntityRef> checkIntegrityList) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueCheckIntegrityTask(userId, checkIntegrityList):this.getLocal().issueCheckIntegrityTask(userId, checkIntegrityList);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueCellCalcImportTask(String clientSideURL, String serverSideURL, int issuingTaskId) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueCellCalcImportTask(this.getUserId(), clientSideURL, serverSideURL, issuingTaskId):this.getLocal().issueCellCalcImportTask(this.getUserId(), clientSideURL, serverSideURL, issuingTaskId);
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public int issueDynamicCellCalcImportTask(List<Pair<String, String>> clientAndServerURLs, int issuingTaskId) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueDynamicCellCalcImportTask(this.getUserId(), clientAndServerURLs, issuingTaskId):this.getLocal().issueDynamicCellCalcImportTask(this.getUserId(), clientAndServerURLs, issuingTaskId);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public URL initiateTransfer(byte[] data) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().initiateTransfer(data):this.getLocal().initiateTransfer(data);
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public void appendToFile(URL url, byte[] data) throws ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().appendToFile(url, data);
         } else {
            this.getLocal().appendToFile(url, data);
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/FinanceCubeEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/FinanceCubeEditorSessionLocalHome";
   }
}
