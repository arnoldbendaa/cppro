// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.ra;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.StructureElementCK;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionCSO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionSSO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.StructureElementAccessor;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.ra.ResponsibilityAreaEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ResponsibilityAreaEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private transient StructureElementAccessor mStructureElementAccessor;
   private ResponsibilityAreaEditorSessionSSO mSSO;
   private ResponsibilityAreaCK mThisTableKey;
   private ModelEVO mModelEVO;
   private ResponsibilityAreaEVO mResponsibilityAreaEVO;


   public ResponsibilityAreaEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ResponsibilityAreaCK)paramKey;

      ResponsibilityAreaEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mResponsibilityAreaEVO = this.mModelEVO.getResponsibilityAreasItem(this.mThisTableKey.getResponsibilityAreaPK());
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
      this.mSSO = new ResponsibilityAreaEditorSessionSSO();
      ResponsibilityAreaImpl editorData = this.buildResponsibilityAreaEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ResponsibilityAreaImpl editorData) throws Exception {}

   private ResponsibilityAreaImpl buildResponsibilityAreaEditData(Object thisKey) throws Exception {
      ResponsibilityAreaImpl editorData = new ResponsibilityAreaImpl(thisKey);
      editorData.setModelId(this.mResponsibilityAreaEVO.getModelId());
      editorData.setStructureId(this.mResponsibilityAreaEVO.getStructureId());
      editorData.setStructureElementId(this.mResponsibilityAreaEVO.getStructureElementId());
      editorData.setVirementAuthStatus(this.mResponsibilityAreaEVO.getVirementAuthStatus());
      editorData.setVersionNum(this.mResponsibilityAreaEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      StructureElementPK key = null;
      if(this.mResponsibilityAreaEVO.getStructureId() != 0 && this.mResponsibilityAreaEVO.getStructureElementId() != 0) {
         key = new StructureElementPK(this.mResponsibilityAreaEVO.getStructureId(), this.mResponsibilityAreaEVO.getStructureElementId());
      }

      if(key != null) {
         StructureElementEVO evoStructureElement = this.getStructureElementAccessor().getDetails(key, "");
         editorData.setOwningStructureElementRef(new StructureElementRefImpl(evoStructureElement.getPK(), evoStructureElement.getVisId()));
      }

      this.completeResponsibilityAreaEditData(editorData);
      return editorData;
   }

   private void completeResponsibilityAreaEditData(ResponsibilityAreaImpl editorData) throws Exception {}

   public ResponsibilityAreaEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ResponsibilityAreaEditorSessionSSO var4;
      try {
         this.mSSO = new ResponsibilityAreaEditorSessionSSO();
         ResponsibilityAreaImpl e = new ResponsibilityAreaImpl((Object)null);
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

   private void completeGetNewItemData(ResponsibilityAreaImpl editorData) throws Exception {}

   public ResponsibilityAreaCK insert(ResponsibilityAreaEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ResponsibilityAreaImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mResponsibilityAreaEVO = new ResponsibilityAreaEVO();
         this.mResponsibilityAreaEVO.setModelId(editorData.getModelId());
         this.mResponsibilityAreaEVO.setStructureId(editorData.getStructureId());
         this.mResponsibilityAreaEVO.setStructureElementId(editorData.getStructureElementId());
         this.mResponsibilityAreaEVO.setVirementAuthStatus(editorData.getVirementAuthStatus());
         this.updateResponsibilityAreaRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addResponsibilityAreasItem(this.mResponsibilityAreaEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<36>");
         Iterator e = this.mModelEVO.getResponsibilityAreas().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mResponsibilityAreaEVO = (ResponsibilityAreaEVO)e.next();
               if(!this.mResponsibilityAreaEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("ResponsibilityArea", this.mResponsibilityAreaEVO.getPK(), 1);
            ResponsibilityAreaCK var5 = new ResponsibilityAreaCK(this.mModelEVO.getPK(), this.mResponsibilityAreaEVO.getPK());
            return var5;
         }
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13.getMessage(), var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }
   }

   private void updateResponsibilityAreaRelationships(ResponsibilityAreaImpl editorData) throws ValidationException {
      if(editorData.getOwningStructureElementRef() != null) {
         Object key = editorData.getOwningStructureElementRef().getPrimaryKey();
         if(key instanceof StructureElementPK) {
            this.mResponsibilityAreaEVO.setStructureId(((StructureElementPK)key).getStructureId());
            this.mResponsibilityAreaEVO.setStructureElementId(((StructureElementPK)key).getStructureElementId());
         } else {
            this.mResponsibilityAreaEVO.setStructureId(((StructureElementCK)key).getStructureElementPK().getStructureId());
            this.mResponsibilityAreaEVO.setStructureElementId(((StructureElementCK)key).getStructureElementPK().getStructureElementId());
         }

         try {
            this.getStructureElementAccessor().getDetails(key, "");
         } catch (Exception var4) {
            var4.printStackTrace();
            throw new ValidationException(editorData.getOwningStructureElementRef() + " no longer exists");
         }
      } else {
         this.mResponsibilityAreaEVO.setStructureId(0);
         this.mResponsibilityAreaEVO.setStructureElementId(0);
      }

   }

   private void completeInsertSetup(ResponsibilityAreaImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ResponsibilityAreaImpl editorData, boolean isInsert) throws Exception {
      this.performMassResponsibilityAreaUpdates(editorData);
   }

   private void validateInsert() throws ValidationException {}

   public ResponsibilityAreaCK copy(ResponsibilityAreaEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ResponsibilityAreaImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ResponsibilityAreaCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         ResponsibilityAreaEVO e = this.mModelEVO.getResponsibilityAreasItem(this.mThisTableKey.getResponsibilityAreaPK());
         this.mResponsibilityAreaEVO = e.deepClone();
         this.mResponsibilityAreaEVO.setModelId(editorData.getModelId());
         this.mResponsibilityAreaEVO.setStructureId(editorData.getStructureId());
         this.mResponsibilityAreaEVO.setStructureElementId(editorData.getStructureElementId());
         this.mResponsibilityAreaEVO.setVirementAuthStatus(editorData.getVirementAuthStatus());
         this.mResponsibilityAreaEVO.setVersionNum(0);
         this.updateResponsibilityAreaRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mResponsibilityAreaEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addResponsibilityAreasItem(this.mResponsibilityAreaEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<36>");
         Iterator iter = this.mModelEVO.getResponsibilityAreas().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mResponsibilityAreaEVO = (ResponsibilityAreaEVO)iter.next();
               if(!this.mResponsibilityAreaEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new ResponsibilityAreaCK(this.mModelEVO.getPK(), this.mResponsibilityAreaEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("ResponsibilityArea", this.mResponsibilityAreaEVO.getPK(), 1);
            ResponsibilityAreaCK var7 = this.mThisTableKey;
            return var7;
         }
      } catch (ValidationException var13) {
         throw new EJBException(var13);
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(ResponsibilityAreaImpl editorData) throws Exception {}

   public void update(ResponsibilityAreaEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ResponsibilityAreaImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ResponsibilityAreaCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mResponsibilityAreaEVO = this.mModelEVO.getResponsibilityAreasItem(this.mThisTableKey.getResponsibilityAreaPK());
         this.preValidateUpdate(editorData);
         this.mResponsibilityAreaEVO.setModelId(editorData.getModelId());
         this.mResponsibilityAreaEVO.setStructureId(editorData.getStructureId());
         this.mResponsibilityAreaEVO.setStructureElementId(editorData.getStructureElementId());
         this.mResponsibilityAreaEVO.setVirementAuthStatus(editorData.getVirementAuthStatus());
         if(editorData.getVersionNum() != this.mResponsibilityAreaEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mResponsibilityAreaEVO.getVersionNum());
         }

         this.updateResponsibilityAreaRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ResponsibilityArea", this.mResponsibilityAreaEVO.getPK(), 3);
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

   private void preValidateUpdate(ResponsibilityAreaImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ResponsibilityAreaImpl editorData) throws Exception {}

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ResponsibilityAreaCK)paramKey;

      AllModelsELO e;
      try {
         e = this.getModelAccessor().getAllModels();
      } catch (Exception var8) {
         var8.printStackTrace();
         throw new EJBException(var8.getMessage(), var8);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getOwnershipData", "");
         }

      }

      return e;
   }

   private void updateAdditionalTables(ResponsibilityAreaImpl editorData) throws Exception {
      this.performMassResponsibilityAreaUpdates(editorData);
   }

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ResponsibilityAreaCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mResponsibilityAreaEVO = this.mModelEVO.getResponsibilityAreasItem(this.mThisTableKey.getResponsibilityAreaPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteResponsibilityAreasItem(this.mThisTableKey.getResponsibilityAreaPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("ResponsibilityArea", this.mThisTableKey.getResponsibilityAreaPK(), 2);
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

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private StructureElementAccessor getStructureElementAccessor() throws Exception {
      if(this.mStructureElementAccessor == null) {
         this.mStructureElementAccessor = new StructureElementAccessor(this.getInitialContext());
      }

      return this.mStructureElementAccessor;
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

   			/*     */   private void performMassResponsibilityAreaUpdates(ResponsibilityAreaImpl editorData)
		   /*     */     throws Exception
		   /*     */   {
		   /* 866 */     int newKeyId = 0;
		   /* 867 */     ModelEVO modelEVO = this.mModelAccessor.getDetails(editorData.getModelRef(), "<36>");
		   /*     */ 
		   /* 870 */     for (Iterator nIter = editorData.getUpdatedNodes().entrySet().iterator(); nIter.hasNext(); )
		   /*     */     {
		   /* 872 */       Map.Entry entry = (Map.Entry)nIter.next();
		   /* 873 */       Integer seId = (Integer)entry.getKey();
		   /* 874 */       ResponsibilityAreaImpl ra = (ResponsibilityAreaImpl)entry.getValue();
		   /* 875 */       ResponsibilityAreaPK respAreaPK = (ResponsibilityAreaPK)ra.getPrimaryKey();
		   /*     */       ResponsibilityAreaEVO raEVO;
		   /* 877 */       if ((respAreaPK != null) && ((raEVO = modelEVO.getResponsibilityAreasItem(respAreaPK)) != null))
		   /*     */       {
		   /* 879 */         if (ra.getVirementAuthStatus() == 0) {
		   /* 880 */           raEVO.setDeletePending();
		   /*     */         }
		   /*     */         else {
		   /* 883 */           raEVO.setVirementAuthStatus(ra.getVirementAuthStatus());
		   /* 884 */           raEVO.setVersionNum(ra.getVersionNum());
		   /*     */         }
		   /*     */       }
		   /* 887 */       else if (ra.getVirementAuthStatus() != 0)
		   /*     */       {
		   /* 889 */         newKeyId--; ResponsibilityAreaEVO raEVO1 = new ResponsibilityAreaEVO(newKeyId, ra.getModelId(), ra.getStructureId(), ra.getStructureElementId(), ra.getVirementAuthStatus(), 0);
		   /*     */ 
		   /* 892 */         modelEVO.addResponsibilityAreasItem(raEVO1);
		   /*     */       }
		   /*     */     }
		   /*     */ 
		   /* 896 */     this.mModelAccessor.setDetails(modelEVO);
		   /*     */   }
}
