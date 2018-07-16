// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.AllDimensionElementsForDimensionELO;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.CellCalcCK;
import com.cedar.cp.dto.model.CellCalcEditorSessionCSO;
import com.cedar.cp.dto.model.CellCalcEditorSessionSSO;
import com.cedar.cp.dto.model.CellCalcImpl;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAO;
import com.cedar.cp.ejb.impl.model.CellCalcAssocEVO;
import com.cedar.cp.ejb.impl.model.CellCalcEVO;
import com.cedar.cp.ejb.impl.model.CellCalculationDataDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class CellCalcEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<21>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<21>";
   private static final String DEPENDANTS_FOR_UPDATE = "<21>";
   private static final String DEPENDANTS_FOR_DELETE = "<21>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private CellCalcEditorSessionSSO mSSO;
   private CellCalcCK mThisTableKey;
   private ModelEVO mModelEVO;
   private CellCalcEVO mCellCalcEVO;


   public CellCalcEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (CellCalcCK)paramKey;

      CellCalcEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<21>");
         this.mCellCalcEVO = this.mModelEVO.getCellCalculationsItem(this.mThisTableKey.getCellCalcPK());
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
      this.mSSO = new CellCalcEditorSessionSSO();
      CellCalcImpl editorData = this.buildCellCalcEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(CellCalcImpl editorData) throws Exception {
      this.buildAccountElementList(editorData);
   }

   private CellCalcImpl buildCellCalcEditData(Object thisKey) throws Exception {
      CellCalcImpl editorData = new CellCalcImpl(thisKey);
      editorData.setModelId(this.mCellCalcEVO.getModelId());
      editorData.setVisId(this.mCellCalcEVO.getVisId());
      editorData.setDescription(this.mCellCalcEVO.getDescription());
      editorData.setXmlformId(this.mCellCalcEVO.getXmlformId());
      editorData.setAccessDefinitionId(this.mCellCalcEVO.getAccessDefinitionId());
      editorData.setDataTypeId(this.mCellCalcEVO.getDataTypeId());
      editorData.setSummaryPeriodAssociation(this.mCellCalcEVO.getSummaryPeriodAssociation());
      editorData.setVersionNum(this.mCellCalcEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeCellCalcEditData(editorData);
      return editorData;
   }

   private void completeCellCalcEditData(CellCalcImpl editorData) throws Exception {
      AllDimensionElementsForDimensionELO list = (AllDimensionElementsForDimensionELO)this.getAllDimensionElementsforModel();
      boolean evoid = false;
      boolean listid = false;
      Iterator iter = this.mCellCalcEVO.getCellCalculationAccounts().iterator();

      while(iter.hasNext()) {
         CellCalcAssocEVO evo = (CellCalcAssocEVO)iter.next();
         int evoid1 = evo.getAccountElementId();
         list.reset();

         while(list.hasNext()) {
            list.next();
            DimensionElementRef ref = list.getDimensionElementEntityRef();
            int listid1 = ((DimensionElementCK)ref.getPrimaryKey()).getDimensionElementPK().getDimensionElementId();
            if(listid1 == evoid1) {
               editorData.addTableData(ref, evo.getFormField());
               break;
            }
         }
      }

   }

   public CellCalcEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      CellCalcEditorSessionSSO var4;
      try {
         this.mSSO = new CellCalcEditorSessionSSO();
         CellCalcImpl e = new CellCalcImpl((Object)null);
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

   private void completeGetNewItemData(CellCalcImpl editorData) throws Exception {}

   public CellCalcCK insert(CellCalcEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CellCalcImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mCellCalcEVO = new CellCalcEVO();
         this.mCellCalcEVO.setModelId(editorData.getModelId());
         this.mCellCalcEVO.setVisId(editorData.getVisId());
         this.mCellCalcEVO.setDescription(editorData.getDescription());
         this.mCellCalcEVO.setXmlformId(editorData.getXmlformId());
         this.mCellCalcEVO.setAccessDefinitionId(editorData.getAccessDefinitionId());
         this.mCellCalcEVO.setDataTypeId(editorData.getDataTypeId());
         this.mCellCalcEVO.setSummaryPeriodAssociation(editorData.isSummaryPeriodAssociation());
         this.updateCellCalcRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addCellCalculationsItem(this.mCellCalcEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<20>");
         Iterator e = this.mModelEVO.getCellCalculations().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mCellCalcEVO = (CellCalcEVO)e.next();
               if(!this.mCellCalcEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("CellCalc", this.mCellCalcEVO.getPK(), 1);
            CellCalcCK var5 = new CellCalcCK(this.mModelEVO.getPK(), this.mCellCalcEVO.getPK());
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

   private void updateCellCalcRelationships(CellCalcImpl editorData) throws ValidationException {}

   private void completeInsertSetup(CellCalcImpl editorData) throws Exception {
      CellCalcAssocEVO evo = null;
      Map items = editorData.getTableData();
      Iterator iter = items.keySet().iterator();
      int fakeId = 0;

      while(iter.hasNext()) {
         EntityRef ref = (EntityRef)iter.next();
         DimensionElementCK ck = (DimensionElementCK)ref.getPrimaryKey();
         DimensionElementPK pk = ck.getDimensionElementPK();
         String id = (String)items.get(ref);
         evo = new CellCalcAssocEVO();
         evo.setCellCalcId(this.mCellCalcEVO.getCellCalcId());
         evo.setCellCalcAssocId(fakeId--);
         evo.setAccountElementId(pk.getDimensionElementId());
         evo.setFormField(id);
         this.mCellCalcEVO.addCellCalculationAccountsItem(evo);
      }

   }

   private void insertIntoAdditionalTables(CellCalcImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {
      if(this.mCellCalcEVO.getXmlformId() == 0) {
         throw new ValidationException("A XMLForm must be set");
      } else if(this.mCellCalcEVO.getVisId() != null && this.mCellCalcEVO.getVisId().length() >= 1) {
         if(this.mCellCalcEVO.getAccessDefinitionId() == 0) {
            throw new ValidationException("A Access Definition must be selected");
         } else if(this.mCellCalcEVO.getDataTypeId() == 0) {
            throw new ValidationException("A Data type must be selected");
         } else if(this.mCellCalcEVO.getCellCalculationAccounts() == null || this.mCellCalcEVO.getCellCalculationAccounts().size() < 1) {
            throw new ValidationException("An account association element must be set");
         }
      } else {
         throw new ValidationException("Identifier can not be null");
      }
   }

   public CellCalcCK copy(CellCalcEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CellCalcImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CellCalcCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<21>");
         CellCalcEVO e = this.mModelEVO.getCellCalculationsItem(this.mThisTableKey.getCellCalcPK());
         this.mCellCalcEVO = e.deepClone();
         this.mCellCalcEVO.setModelId(editorData.getModelId());
         this.mCellCalcEVO.setVisId(editorData.getVisId());
         this.mCellCalcEVO.setDescription(editorData.getDescription());
         this.mCellCalcEVO.setXmlformId(editorData.getXmlformId());
         this.mCellCalcEVO.setAccessDefinitionId(editorData.getAccessDefinitionId());
         this.mCellCalcEVO.setDataTypeId(editorData.getDataTypeId());
         this.mCellCalcEVO.setSummaryPeriodAssociation(editorData.isSummaryPeriodAssociation());
         this.mCellCalcEVO.setVersionNum(0);
         this.updateCellCalcRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mCellCalcEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addCellCalculationsItem(this.mCellCalcEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<20><21>");
         Iterator iter = this.mModelEVO.getCellCalculations().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mCellCalcEVO = (CellCalcEVO)iter.next();
               if(!this.mCellCalcEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new CellCalcCK(this.mModelEVO.getPK(), this.mCellCalcEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("CellCalc", this.mCellCalcEVO.getPK(), 1);
            CellCalcCK var7 = this.mThisTableKey;
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

   private void validateCopy() throws ValidationException {
      throw new ValidationException("copy not allowed");
   }

   private void completeCopySetup(CellCalcImpl editorData) throws Exception {}

   public void update(CellCalcEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CellCalcImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CellCalcCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<21>");
         this.mCellCalcEVO = this.mModelEVO.getCellCalculationsItem(this.mThisTableKey.getCellCalcPK());
         this.preValidateUpdate(editorData);
         this.mCellCalcEVO.setModelId(editorData.getModelId());
         this.mCellCalcEVO.setVisId(editorData.getVisId());
         this.mCellCalcEVO.setDescription(editorData.getDescription());
         this.mCellCalcEVO.setXmlformId(editorData.getXmlformId());
         this.mCellCalcEVO.setAccessDefinitionId(editorData.getAccessDefinitionId());
         this.mCellCalcEVO.setDataTypeId(editorData.getDataTypeId());
         this.mCellCalcEVO.setSummaryPeriodAssociation(editorData.isSummaryPeriodAssociation());
         if(editorData.getVersionNum() != this.mCellCalcEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mCellCalcEVO.getVersionNum());
         }

         this.updateCellCalcRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("CellCalc", this.mCellCalcEVO.getPK(), 3);
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

   private void preValidateUpdate(CellCalcImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(CellCalcImpl editorData) throws Exception {
      CellCalcAssocEVO evo = null;
      Iterator iter = this.mCellCalcEVO.getCellCalculationAccounts().iterator();

      while(iter.hasNext()) {
         evo = (CellCalcAssocEVO)iter.next();
         this.mCellCalcEVO.deleteCellCalculationAccountsItem(evo.getPK());
      }

      this.completeInsertSetup(editorData);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (CellCalcCK)paramKey;

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

   private void updateAdditionalTables(CellCalcImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (CellCalcCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<21>");
         this.mCellCalcEVO = this.mModelEVO.getCellCalculationsItem(this.mThisTableKey.getCellCalcPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteCellCalculationsItem(this.mThisTableKey.getCellCalcPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("CellCalc", this.mThisTableKey.getCellCalcPK(), 2);
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

   private void deleteDataFromOtherTables() throws Exception {
      CellCalculationDataDAO dao = new CellCalculationDataDAO();
      dao.deleteAllCellCalcValues(this.mModelEVO.getModelId(), this.mCellCalcEVO.getCellCalcId());
   }

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

   private void buildAccountElementList(CellCalcImpl editordata) {
      if(editordata.getModelId() == 0) {
         this.mLog.debug("Model not set cant build list");
      } else {
         editordata.setAccountDimensionElements(this.getAllDimensionElementsforModel());
      }
   }

   private EntityList getAllDimensionElementsforModel() {
      ModelDAO dao = new ModelDAO();
      ModelDimensionsELO dimension = dao.getModelDimensions(this.mCellCalcEVO.getModelId());
      int size = dimension.getNumRows();
      DimensionRef dim = null;
      boolean accountDimensionId = false;
      boolean type = false;

      for(int elemDao = 0; elemDao < size; ++elemDao) {
         int var9 = ((Integer)dimension.getValueAt(elemDao, "DimensionType")).intValue();
         if(var9 == 1) {
            dim = (DimensionRef)dimension.getValueAt(elemDao, "Dimension");
            break;
         }
      }

      int var8 = ((DimensionPK)dim.getPrimaryKey()).getDimensionId();
      DimensionElementDAO var10 = new DimensionElementDAO();
      return var10.getAllDimensionElementsForDimension(var8);
   }
}
