// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionCSO;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionSSO;
import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.cm.BudgetStateChanges;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtAccessor;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ChangeMgmtEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ChangeMgmtAccessor mChangeMgmtAccessor;
   private transient ModelAccessor mModelAccessor;
   private ChangeMgmtEditorSessionSSO mSSO;
   private ChangeMgmtPK mThisTableKey;
   private ChangeMgmtEVO mChangeMgmtEVO;


   public ChangeMgmtEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ChangeMgmtPK)paramKey;

      ChangeMgmtEditorSessionSSO e;
      try {
         this.mChangeMgmtEVO = this.getChangeMgmtAccessor().getDetails(this.mThisTableKey, "");
         this.makeItemData();
         e = this.mSSO;
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         if(var11.getCause() instanceof ValidationException) {
            throw (ValidationException)var11.getCause();
         }

         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getItemData", this.mThisTableKey);
         }

      }

      return e;
   }

   private void makeItemData() throws Exception {
      this.mSSO = new ChangeMgmtEditorSessionSSO();
      ChangeMgmtImpl editorData = this.buildChangeMgmtEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ChangeMgmtImpl editorData) throws Exception {}

   private ChangeMgmtImpl buildChangeMgmtEditData(Object thisKey) throws Exception {
      ChangeMgmtImpl editorData = new ChangeMgmtImpl(thisKey);
      editorData.setModelId(this.mChangeMgmtEVO.getModelId());
      editorData.setCreatedTime(this.mChangeMgmtEVO.getCreatedTime());
      editorData.setTaskId(this.mChangeMgmtEVO.getTaskId());
      editorData.setSourceSystem(this.mChangeMgmtEVO.getSourceSystem());
      editorData.setXmlText(this.mChangeMgmtEVO.getXmlText());
      editorData.setVersionNum(this.mChangeMgmtEVO.getVersionNum());
      ModelPK key = null;
      if(this.mChangeMgmtEVO.getModelId() != 0) {
         key = new ModelPK(this.mChangeMgmtEVO.getModelId());
      }

      if(key != null) {
         ModelEVO evoModel = this.getModelAccessor().getDetails(key, "");
         editorData.setRelatedModelRef(new ModelRefImpl(evoModel.getPK(), evoModel.getVisId()));
      }

      this.completeChangeMgmtEditData(editorData);
      return editorData;
   }

   private void completeChangeMgmtEditData(ChangeMgmtImpl editorData) throws Exception {}

   public ChangeMgmtEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ChangeMgmtEditorSessionSSO var4;
      try {
         this.mSSO = new ChangeMgmtEditorSessionSSO();
         ChangeMgmtImpl e = new ChangeMgmtImpl((Object)null);
         this.completeGetNewItemData(e);
         this.mSSO.setEditorData(e);
         var4 = this.mSSO;
      } catch (EJBException var9) {
         throw var9;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage(), var10);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getNewItemData", "");
         }

      }

      return var4;
   }

   private void completeGetNewItemData(ChangeMgmtImpl editorData) throws Exception {}

   public ChangeMgmtPK insert(ChangeMgmtEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ChangeMgmtImpl editorData = cso.getEditorData();

      ChangeMgmtPK e;
      try {
         this.mChangeMgmtEVO = new ChangeMgmtEVO();
         this.mChangeMgmtEVO.setModelId(editorData.getModelId());
         this.mChangeMgmtEVO.setCreatedTime(editorData.getCreatedTime());
         this.mChangeMgmtEVO.setTaskId(editorData.getTaskId());
         this.mChangeMgmtEVO.setSourceSystem(editorData.getSourceSystem());
         this.mChangeMgmtEVO.setXmlText(editorData.getXmlText());
         this.updateChangeMgmtRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mChangeMgmtEVO = this.getChangeMgmtAccessor().create(this.mChangeMgmtEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ChangeMgmt", this.mChangeMgmtEVO.getPK(), 1);
         e = this.mChangeMgmtEVO.getPK();
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }

      return e;
   }

   private void updateChangeMgmtRelationships(ChangeMgmtImpl editorData) throws ValidationException {
      if(editorData.getRelatedModelRef() != null) {
         Object key = editorData.getRelatedModelRef().getPrimaryKey();
         if(key instanceof ModelPK) {
            this.mChangeMgmtEVO.setModelId(((ModelPK)key).getModelId());
         } else {
            this.mChangeMgmtEVO.setModelId(((ModelCK)key).getModelPK().getModelId());
         }

         try {
            this.getModelAccessor().getDetails(key, "");
         } catch (Exception var4) {
            var4.printStackTrace();
            throw new ValidationException(editorData.getRelatedModelRef() + " no longer exists");
         }
      } else {
         this.mChangeMgmtEVO.setModelId(0);
      }

   }

   private void completeInsertSetup(ChangeMgmtImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ChangeMgmtImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ChangeMgmtPK copy(ChangeMgmtEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ChangeMgmtImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ChangeMgmtPK)editorData.getPrimaryKey();

      ChangeMgmtPK var5;
      try {
         ChangeMgmtEVO e = this.getChangeMgmtAccessor().getDetails(this.mThisTableKey, "");
         this.mChangeMgmtEVO = e.deepClone();
         this.mChangeMgmtEVO.setModelId(editorData.getModelId());
         this.mChangeMgmtEVO.setCreatedTime(editorData.getCreatedTime());
         this.mChangeMgmtEVO.setTaskId(editorData.getTaskId());
         this.mChangeMgmtEVO.setSourceSystem(editorData.getSourceSystem());
         this.mChangeMgmtEVO.setXmlText(editorData.getXmlText());
         this.mChangeMgmtEVO.setVersionNum(0);
         this.updateChangeMgmtRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mChangeMgmtEVO.prepareForInsert();
         this.mChangeMgmtEVO = this.getChangeMgmtAccessor().create(this.mChangeMgmtEVO);
         this.mThisTableKey = this.mChangeMgmtEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ChangeMgmt", this.mChangeMgmtEVO.getPK(), 1);
         var5 = this.mThisTableKey;
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }

      return var5;
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(ChangeMgmtImpl editorData) throws Exception {}

   public void update(ChangeMgmtEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ChangeMgmtImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ChangeMgmtPK)editorData.getPrimaryKey();

      try {
         this.mChangeMgmtEVO = this.getChangeMgmtAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mChangeMgmtEVO.setModelId(editorData.getModelId());
         this.mChangeMgmtEVO.setCreatedTime(editorData.getCreatedTime());
         this.mChangeMgmtEVO.setTaskId(editorData.getTaskId());
         this.mChangeMgmtEVO.setSourceSystem(editorData.getSourceSystem());
         this.mChangeMgmtEVO.setXmlText(editorData.getXmlText());
         if(editorData.getVersionNum() != this.mChangeMgmtEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mChangeMgmtEVO.getVersionNum());
         }

         this.updateChangeMgmtRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getChangeMgmtAccessor().setDetails(this.mChangeMgmtEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ChangeMgmt", this.mChangeMgmtEVO.getPK(), 3);
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("update", this.mThisTableKey);
         }

      }

   }

   private void preValidateUpdate(ChangeMgmtImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ChangeMgmtImpl editorData) throws Exception {}

   private void updateAdditionalTables(ChangeMgmtImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ChangeMgmtPK)paramKey;

      try {
         this.mChangeMgmtEVO = this.getChangeMgmtAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mChangeMgmtAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ChangeMgmt", this.mThisTableKey, 2);
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("delete", this.mThisTableKey);
         }

      }

   }

   private void deleteDataFromOtherTables() throws Exception {}

   private void validateDelete() throws ValidationException, Exception {}

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ChangeMgmtAccessor getChangeMgmtAccessor() throws Exception {
      if(this.mChangeMgmtAccessor == null) {
         this.mChangeMgmtAccessor = new ChangeMgmtAccessor(this.getInitialContext());
      }

      return this.mChangeMgmtAccessor;
   }

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public void tidyBudgetState(int modelId) {
      BudgetStateChanges state = new BudgetStateChanges();
      state.tidyBudgetState(modelId);
   }
}
