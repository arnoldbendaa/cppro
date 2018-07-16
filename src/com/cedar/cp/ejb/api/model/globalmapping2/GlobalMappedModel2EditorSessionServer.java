package com.cedar.cp.ejb.api.model.globalmapping2;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionCSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2EditorSessionSSO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2Impl;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionHome;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionLocal;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class GlobalMappedModel2EditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/GlobalMappedModel2EditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/GlobalMappedModel2EditorSessionLocalHome";
   protected GlobalMappedModel2EditorSessionRemote mRemote;
   protected GlobalMappedModel2EditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public GlobalMappedModel2EditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public GlobalMappedModel2EditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private GlobalMappedModel2EditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            GlobalMappedModel2EditorSessionHome e = (GlobalMappedModel2EditorSessionHome)this.getHome(jndiName, GlobalMappedModel2EditorSessionHome.class);
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

   private GlobalMappedModel2EditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            GlobalMappedModel2EditorSessionLocalHome e = (GlobalMappedModel2EditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ModelRef[] getAvailableOwningModelRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ModelRef[] ret = (ModelRef[])((ModelRef[])this.getConnection().getListHelper().getAllModels().getValues("Model"));
      if(timer != null) {
         timer.logDebug("getAvailableOwningModelRefs", "");
      }

      return ret;
   }

   public ExternalSystemRef[] getAvailableExternalSystemRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ExternalSystemRef[] ret = (ExternalSystemRef[])((ExternalSystemRef[])this.getConnection().getListHelper().getAllExternalSystems().getValues("ExternalSystem"));
      if(timer != null) {
         timer.logDebug("getAvailableExternalSystemRefs", "");
      }

      return ret;
   }

   public GlobalMappedModel2EditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         GlobalMappedModel2EditorSessionSSO e = null;
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

   public GlobalMappedModel2EditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         GlobalMappedModel2EditorSessionSSO e = null;
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

   public GlobalMappedModel2PK insert(GlobalMappedModel2Impl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         GlobalMappedModel2PK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new GlobalMappedModel2EditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new GlobalMappedModel2EditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public GlobalMappedModel2PK copy(GlobalMappedModel2Impl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         GlobalMappedModel2PK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new GlobalMappedModel2EditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new GlobalMappedModel2EditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(GlobalMappedModel2Impl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new GlobalMappedModel2EditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new GlobalMappedModel2EditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueModelImportTask(boolean safeMode, int[] mappedModelIds) throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueModelImportTask(this.getUserId(), safeMode, mappedModelIds, 0):this.getLocal().issueModelImportTask(this.getUserId(), safeMode, mappedModelIds, 0);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }
   
   public boolean isGlobalMappedModel(int modelId) throws CPException, ValidationException {
       try {
           return this.isRemoteConnection()?this.getRemote().isGlobalMappedModel(modelId):this.getLocal().isGlobalMappedModel(modelId);
        } catch (Exception e) {
           throw this.unravelException(e);
        }
   }

   public int issueModelImportTask(int userId, int[] mappedModelIds, int issuingTaskId) throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueModelImportTask(userId, false, mappedModelIds, issuingTaskId):this.getLocal().issueModelImportTask(userId, false, mappedModelIds, issuingTaskId);
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public void refreshMappedModelCalendar(int userId, GlobalMappedModel2PK paramKey) throws ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().refreshMappedModelCalendar(userId, paramKey);
         } else {
            this.getLocal().refreshMappedModelCalendar(userId, paramKey);
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void refreshMappedModelHierarchy(int userId, GlobalMappedModel2PK paramKey) throws ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().refreshMappedModelHierarchy(userId, paramKey);
         } else {
            this.getLocal().refreshMappedModelHierarchy(userId, paramKey);
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueMappedModelExportTask(int mappedModelId, String mappedModelVisId, List<EntityRef> financeCubes) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueMappedModelExportTask(this.getUserId(), mappedModelId, mappedModelVisId, financeCubes):this.getLocal().issueMappedModelExportTask(this.getUserId(), mappedModelId, mappedModelVisId, financeCubes);
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/GlobalMappedModel2EditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/GlobalMappedModel2EditorSessionLocalHome";
   }

	public int getModelId(Object primaryKey) throws ValidationException {
		try {
			if(this.isRemoteConnection()) {
			   return this.getRemote().getModelId(primaryKey);
			} else {
				return this.getLocal().getModelId(primaryKey);
			}
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}
}
