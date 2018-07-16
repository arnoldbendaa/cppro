// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.ModelEditorSessionCSO;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.ModelEditorSessionHome;
import com.cedar.cp.ejb.api.model.ModelEditorSessionLocal;
import com.cedar.cp.ejb.api.model.ModelEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.ModelEditorSessionRemote;
import com.cedar.cp.ejb.impl.model.ModelEditorSessionSEJB;
import com.cedar.cp.impl.model.ModelEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.Context;

public class ModelEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ModelEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ModelEditorSessionLocalHome";
   protected ModelEditorSessionRemote mRemote;
   protected ModelEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ModelEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ModelEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ModelEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            ModelEditorSessionHome e = (ModelEditorSessionHome)this.getHome(jndiName, ModelEditorSessionHome.class);
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
	   return ModelEditorSessionImpl.server;
   }

   private ModelEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            ModelEditorSessionLocalHome e = (ModelEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return ModelEditorSessionImpl.server;
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

   public CurrencyRef[] getAvailableCurrencyRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CurrencyRef[] ret = (CurrencyRef[])((CurrencyRef[])this.getConnection().getListHelper().getAllCurrencys().getValues("Currency"));
      if(timer != null) {
         timer.logDebug("getAvailableCurrencyRefs", "");
      }

      return ret;
   }

   public DimensionRef[] getAvailableAccountRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      DimensionRef[] ret = (DimensionRef[])((DimensionRef[])this.getConnection().getListHelper().getAllDimensions().getValues("Dimension"));
      if(timer != null) {
         timer.logDebug("getAvailableAccountRefs", "");
      }

      return ret;
   }

   public DimensionRef[] getAvailableCalendarRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      DimensionRef[] ret = (DimensionRef[])((DimensionRef[])this.getConnection().getListHelper().getAllDimensions().getValues("Dimension"));
      if(timer != null) {
         timer.logDebug("getAvailableCalendarRefs", "");
      }

      return ret;
   }

   public HierarchyRef[] getAvailableBudgetHierarchyRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      HierarchyRef[] ret = (HierarchyRef[])((HierarchyRef[])this.getConnection().getListHelper().getAllHierarchys().getValues("Hierarchy"));
      if(timer != null) {
         timer.logDebug("getAvailableBudgetHierarchyRefs", "");
      }

      return ret;
   }

   public ModelEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ModelEditorSessionSSO e = null;
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

   public ModelEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ModelEditorSessionSSO e = null;
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

   public ModelPK insert(ModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ModelPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ModelPK copy(ModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ModelPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ModelEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ModelEditorSessionLocalHome";
   }
   
	public Object[][] checkForms(int modelId) throws RemoteException, ValidationException {
      try {
			if(this.isRemoteConnection()) {
			   return this.getRemote().checkForms(modelId);
			} else {
	           return this.getLocal().checkForms(modelId);
			}
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

    public int issueImportDataModelTask(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId) throws CPException, ValidationException {
        return this.issueImportDataModelTask(models, dataTypes, globalModelId, userId, 0);
    }

    private int issueImportDataModelTask(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId, int issuingTaskId) throws CPException, ValidationException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
        
        try {
            int taskId;
            if (this.isRemoteConnection()) {
                taskId = this.getRemote().issueImportDataModelTask(models, dataTypes, globalModelId, userId, issuingTaskId);
            } else {
                taskId = this.getLocal().issueImportDataModelTask(models, dataTypes, globalModelId, userId, issuingTaskId);
            }

            if (timer != null) {
                timer.logDebug("issueImportDataModelTask");
            }

            return taskId;
        } catch (Exception e) {
            throw this.unravelException(e);
        }
    }
}
