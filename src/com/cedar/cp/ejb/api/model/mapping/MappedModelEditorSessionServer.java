// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.mapping;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionCSO;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionSSO;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionHome;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionLocal;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionRemote;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class MappedModelEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/MappedModelEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/MappedModelEditorSessionLocalHome";
   protected MappedModelEditorSessionRemote mRemote;
   protected MappedModelEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   static MappedModelEditorSessionSEJB server = new MappedModelEditorSessionSEJB();


   public MappedModelEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public MappedModelEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private MappedModelEditorSessionSEJB getRemote() {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            MappedModelEditorSessionHome e = (MappedModelEditorSessionHome)this.getHome(jndiName, MappedModelEditorSessionHome.class);
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

   private MappedModelEditorSessionSEJB getLocal() {
//      if(this.mLocal == null) {
//         try {
//            MappedModelEditorSessionLocalHome e = (MappedModelEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public MappedModelEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         MappedModelEditorSessionSSO e = null;
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

   public MappedModelEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         MappedModelEditorSessionSSO e = null;
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

   public MappedModelPK insert(MappedModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         MappedModelPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new MappedModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new MappedModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public MappedModelPK copy(MappedModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         MappedModelPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new MappedModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new MappedModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(MappedModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new MappedModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new MappedModelEditorSessionCSO(this.getUserId(), editorData));
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
   public int multiIssueModelImportTask(boolean safeMode, int[] mappedModelIds) throws CPException, ValidationException {
      try {
         return this.getRemote().multiIssueModelImportTask(this.getUserId(), safeMode, mappedModelIds, 0);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueModelImportTask(int userId, int[] mappedModelIds, int issuingTaskId) throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueModelImportTask(userId, false, mappedModelIds, issuingTaskId):this.getLocal().issueModelImportTask(userId, false, mappedModelIds, issuingTaskId);
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public void refreshMappedModelCalendar(int userId, MappedModelPK paramKey) throws ValidationException {
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

   public void refreshMappedModelHierarchy(int userId, MappedModelPK paramKey) throws ValidationException {
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
      return "ejb/MappedModelEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/MappedModelEditorSessionLocalHome";
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
	public String getTaskStatus(String taskId){
	      try {
	          if(this.isRemoteConnection()) {
	             return this.getRemote().getTaskStatus(taskId);
	          } else {
	             return this.getLocal().getTaskStatus(taskId);
	          }

	       } catch (Exception var4) {
	          var4.printStackTrace();
	       }
	      return null;
	}
	public String getTaskTime(String taskId){
	      try {
	          if(this.isRemoteConnection()) {
	             return this.getRemote().getTaskTime(taskId);
	          } else {
	             return this.getLocal().getTaskTime(taskId);
	          }

	       } catch (Exception var4) {
	          var4.printStackTrace();
	       }
	      return null;
	}
}
