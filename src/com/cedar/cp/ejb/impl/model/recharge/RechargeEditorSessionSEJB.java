// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.recharge.RechargeCellDataVO;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.dimension.StructureElementForIdsELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelDimensionseExcludeCallELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.dto.model.recharge.RechargeEditorSessionCSO;
import com.cedar.cp.dto.model.recharge.RechargeEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeCellsEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.math.BigDecimal;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class RechargeEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<26>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<26>";
   private static final String DEPENDANTS_FOR_UPDATE = "<26>";
   private static final String DEPENDANTS_FOR_DELETE = "<26>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private RechargeEditorSessionSSO mSSO;
   private RechargeCK mThisTableKey;
   private ModelEVO mModelEVO;
   private RechargeEVO mRechargeEVO;


   public RechargeEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (RechargeCK)paramKey;

      RechargeEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<26>");
         this.mRechargeEVO = this.mModelEVO.getRechargeItem(this.mThisTableKey.getRechargePK());
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
      this.mSSO = new RechargeEditorSessionSSO();
      RechargeImpl editorData = this.buildRechargeEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(RechargeImpl editorData) throws Exception {}

   private RechargeImpl buildRechargeEditData(Object thisKey) throws Exception {
      RechargeImpl editorData = new RechargeImpl(thisKey);
      editorData.setVisId(this.mRechargeEVO.getVisId());
      editorData.setDescription(this.mRechargeEVO.getDescription());
      editorData.setReason(this.mRechargeEVO.getReason());
      editorData.setReference(this.mRechargeEVO.getReference());
      editorData.setAllocationPercentage(this.mRechargeEVO.getAllocationPercentage());
      editorData.setManualRatios(this.mRechargeEVO.getManualRatios());
      editorData.setAllocationDataTypeId(this.mRechargeEVO.getAllocationDataTypeId());
      editorData.setDiffAccount(this.mRechargeEVO.getDiffAccount());
      editorData.setAccountStructureId(this.mRechargeEVO.getAccountStructureId());
      editorData.setAccountStructureElementId(this.mRechargeEVO.getAccountStructureElementId());
      editorData.setRatioType(this.mRechargeEVO.getRatioType());
      editorData.setVersionNum(this.mRechargeEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeRechargeEditData(editorData);
      return editorData;
   }

   private void completeRechargeEditData(RechargeImpl editorData) throws Exception {
      boolean size = false;
      EntityRef[] ref = null;
      Iterator iter = this.mRechargeEVO.getRechargeCells().iterator();

      while(iter.hasNext()) {
         RechargeCellsEVO cell = (RechargeCellsEVO)iter.next();
         int size1 = this.getRefCount(cell);
         ref = this.populateRefs(cell, size1);
         int type = cell.getCellType();
         ref[size1 - 1] = this.getDataTypeRef(cell);
         switch(type) {
         case 0:
            editorData.addSourceCell(ref);
            break;
         case 1:
            editorData.addTargetCell(ref, cell.getRatio());
            break;
         case 2:
            editorData.addOffsetCell(ref);
         }
      }

   }

   public RechargeEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      RechargeEditorSessionSSO var4;
      try {
         this.mSSO = new RechargeEditorSessionSSO();
         RechargeImpl e = new RechargeImpl((Object)null);
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

   private void completeGetNewItemData(RechargeImpl editorData) throws Exception {}

   public RechargeCK insert(RechargeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RechargeImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mRechargeEVO = new RechargeEVO();
         this.mRechargeEVO.setVisId(editorData.getVisId());
         this.mRechargeEVO.setDescription(editorData.getDescription());
         this.mRechargeEVO.setReason(editorData.getReason());
         this.mRechargeEVO.setReference(editorData.getReference());
         this.mRechargeEVO.setAllocationPercentage(editorData.getAllocationPercentage());
         this.mRechargeEVO.setManualRatios(editorData.isManualRatios());
         this.mRechargeEVO.setAllocationDataTypeId(editorData.getAllocationDataTypeId());
         this.mRechargeEVO.setDiffAccount(editorData.isDiffAccount());
         this.mRechargeEVO.setAccountStructureId(editorData.getAccountStructureId());
         this.mRechargeEVO.setAccountStructureElementId(editorData.getAccountStructureElementId());
         this.mRechargeEVO.setRatioType(editorData.getRatioType());
         this.updateRechargeRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addRechargeItem(this.mRechargeEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<25>");
         Iterator e = this.mModelEVO.getRecharge().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mRechargeEVO = (RechargeEVO)e.next();
               if(!this.mRechargeEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("Recharge", this.mRechargeEVO.getPK(), 1);
            RechargeCK var5 = new RechargeCK(this.mModelEVO.getPK(), this.mRechargeEVO.getPK());
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

   private void updateRechargeRelationships(RechargeImpl editorData) throws ValidationException {}

   private void completeInsertSetup(RechargeImpl editorData) throws Exception {
      int cell = 0;
      RechargeCellsEVO evo = null;
      Iterator i$ = editorData.getSelectedSourceCells().iterator();

      RechargeCellDataVO row;
      while(i$.hasNext()) {
         row = (RechargeCellDataVO)i$.next();
         evo = this.processSelected(row.getCellData(), cell--, 0);
         this.mRechargeEVO.addRechargeCellsItem(evo);
      }

      for(i$ = editorData.getSelectedTargetCells().iterator(); i$.hasNext(); this.mRechargeEVO.addRechargeCellsItem(evo)) {
         row = (RechargeCellDataVO)i$.next();
         evo = this.processSelected(row.getCellData(), cell--, 1);
         if(row.getRatio() != null) {
            evo.setRatio(new BigDecimal(row.getRatio().doubleValue()));
         }
      }

      i$ = editorData.getSelectedOffsetCells().iterator();

      while(i$.hasNext()) {
         row = (RechargeCellDataVO)i$.next();
         evo = this.processSelected(row.getCellData(), cell--, 2);
         this.mRechargeEVO.addRechargeCellsItem(evo);
      }

   }

   private void insertIntoAdditionalTables(RechargeImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public RechargeCK copy(RechargeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RechargeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (RechargeCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<26>");
         RechargeEVO e = this.mModelEVO.getRechargeItem(this.mThisTableKey.getRechargePK());
         this.mRechargeEVO = e.deepClone();
         this.mRechargeEVO.setVisId(editorData.getVisId());
         this.mRechargeEVO.setDescription(editorData.getDescription());
         this.mRechargeEVO.setReason(editorData.getReason());
         this.mRechargeEVO.setReference(editorData.getReference());
         this.mRechargeEVO.setAllocationPercentage(editorData.getAllocationPercentage());
         this.mRechargeEVO.setManualRatios(editorData.isManualRatios());
         this.mRechargeEVO.setAllocationDataTypeId(editorData.getAllocationDataTypeId());
         this.mRechargeEVO.setDiffAccount(editorData.isDiffAccount());
         this.mRechargeEVO.setAccountStructureId(editorData.getAccountStructureId());
         this.mRechargeEVO.setAccountStructureElementId(editorData.getAccountStructureElementId());
         this.mRechargeEVO.setRatioType(editorData.getRatioType());
         this.mRechargeEVO.setVersionNum(0);
         this.updateRechargeRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mRechargeEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addRechargeItem(this.mRechargeEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<25><26>");
         Iterator iter = this.mModelEVO.getRecharge().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mRechargeEVO = (RechargeEVO)iter.next();
               if(!this.mRechargeEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new RechargeCK(this.mModelEVO.getPK(), this.mRechargeEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("Recharge", this.mRechargeEVO.getPK(), 1);
            RechargeCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(RechargeImpl editorData) throws Exception {}

   public void update(RechargeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RechargeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (RechargeCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<26>");
         this.mRechargeEVO = this.mModelEVO.getRechargeItem(this.mThisTableKey.getRechargePK());
         this.preValidateUpdate(editorData);
         this.mRechargeEVO.setVisId(editorData.getVisId());
         this.mRechargeEVO.setDescription(editorData.getDescription());
         this.mRechargeEVO.setReason(editorData.getReason());
         this.mRechargeEVO.setReference(editorData.getReference());
         this.mRechargeEVO.setAllocationPercentage(editorData.getAllocationPercentage());
         this.mRechargeEVO.setManualRatios(editorData.isManualRatios());
         this.mRechargeEVO.setAllocationDataTypeId(editorData.getAllocationDataTypeId());
         this.mRechargeEVO.setDiffAccount(editorData.isDiffAccount());
         this.mRechargeEVO.setAccountStructureId(editorData.getAccountStructureId());
         this.mRechargeEVO.setAccountStructureElementId(editorData.getAccountStructureElementId());
         this.mRechargeEVO.setRatioType(editorData.getRatioType());
         if(editorData.getVersionNum() != this.mRechargeEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mRechargeEVO.getVersionNum());
         }

         this.updateRechargeRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Recharge", this.mRechargeEVO.getPK(), 3);
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

   private void preValidateUpdate(RechargeImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(RechargeImpl editorData) throws Exception {
      RechargeCellsEVO rc1EVO = null;
      Iterator iter = this.mRechargeEVO.getRechargeCells().iterator();

      while(iter.hasNext()) {
         rc1EVO = (RechargeCellsEVO)iter.next();
         this.mRechargeEVO.deleteRechargeCellsItem(rc1EVO.getPK());
      }

      this.completeInsertSetup(editorData);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (RechargeCK)paramKey;

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

   private void updateAdditionalTables(RechargeImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (RechargeCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<26>");
         this.mRechargeEVO = this.mModelEVO.getRechargeItem(this.mThisTableKey.getRechargePK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteRechargeItem(this.mThisTableKey.getRechargePK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("Recharge", this.mThisTableKey.getRechargePK(), 2);
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

   private int getRefCount(RechargeCellsEVO evo) {
      ModelDAO dao = new ModelDAO();
      ModelDimensionseExcludeCallELO list = dao.getModelDimensionseExcludeCall(this.mRechargeEVO.getModelId());
      int size = list.getNumRows();
      ++size;
      return size;
   }

   private EntityRef getDataTypeRef(RechargeCellsEVO cell) {
      EntityRef ref = null;
      if(cell.getDataType() == null) {
         return ref;
      } else {
         DataTypeDAO dao = new DataTypeDAO();
         AllDataTypesELO list = dao.getAllDataTypes();
         int size = list.getNumRows();

         for(int i = 0; i < size; ++i) {
            ref = (EntityRef)list.getValueAt(i, "DataType");
            if(ref.getNarrative().equals(cell.getDataType())) {
               break;
            }
         }

         return ref;
      }
   }

   private EntityRef[] populateRefs(RechargeCellsEVO cell, int size) {
      EntityRef[] ref = new EntityRef[size];
      StructureElementDAO dao = new StructureElementDAO();
      StructureElementForIdsELO list = null;
      int i = 0;

      for(int max = size - 1; i < max; ++i) {
         switch(i) {
         case 0:
            list = dao.getStructureElementForIds(cell.getDim0StructureId(), cell.getDim0StructureElementId());
            break;
         case 1:
            list = dao.getStructureElementForIds(cell.getDim1StructureId(), cell.getDim1StructureElementId());
            break;
         case 2:
            list = dao.getStructureElementForIds(cell.getDim2StructureId(), cell.getDim2StructureElementId());
            break;
         case 3:
            list = dao.getStructureElementForIds(cell.getDim3StructureId(), cell.getDim3StructureElementId());
            break;
         case 4:
            list = dao.getStructureElementForIds(cell.getDim4StructureId(), cell.getDim4StructureElementId());
            break;
         case 5:
            list = dao.getStructureElementForIds(cell.getDim5StructureId(), cell.getDim5StructureElementId());
            break;
         case 6:
            list = dao.getStructureElementForIds(cell.getDim6StructureId(), cell.getDim6StructureElementId());
            break;
         case 7:
            list = dao.getStructureElementForIds(cell.getDim7StructureId(), cell.getDim7StructureElementId());
            break;
         case 8:
            list = dao.getStructureElementForIds(cell.getDim8StructureId(), cell.getDim8StructureElementId());
            break;
         default:
            list = dao.getStructureElementForIds(cell.getDim9StructureId(), cell.getDim9StructureElementId());
         }

         if(list.getNumRows() > 0) {
            ref[i] = (EntityRef)list.getValueAt(0, "StructureElement");
         } else {
            ref[i] = null;
         }
      }

      return ref;
   }

   private RechargeCellsEVO processSelected(EntityRef[] ref, int id, int type) {
      RechargeCellsEVO evo = new RechargeCellsEVO();
      evo.setRechargeCellId(id);
      evo.setCellType(type);
      Object pk = null;
      int size = ref.length;
      --size;

      for(int dataTypeRef = 0; dataTypeRef < size; ++dataTypeRef) {
         if(ref[dataTypeRef] != null) {
            pk = ref[dataTypeRef].getPrimaryKey();
         } else {
            pk = null;
         }

         if(pk instanceof StructureElementPK) {
            StructureElementPK myPk = (StructureElementPK)pk;
            switch(dataTypeRef) {
            case 0:
               evo.setDim0StructureId(myPk.getStructureId());
               evo.setDim0StructureElementId(myPk.getStructureElementId());
               break;
            case 1:
               evo.setDim1StructureId(myPk.getStructureId());
               evo.setDim1StructureElementId(myPk.getStructureElementId());
               break;
            case 2:
               evo.setDim2StructureId(myPk.getStructureId());
               evo.setDim2StructureElementId(myPk.getStructureElementId());
               break;
            case 3:
               evo.setDim3StructureId(myPk.getStructureId());
               evo.setDim3StructureElementId(myPk.getStructureElementId());
               break;
            case 4:
               evo.setDim4StructureId(myPk.getStructureId());
               evo.setDim4StructureElementId(myPk.getStructureElementId());
               break;
            case 5:
               evo.setDim5StructureId(myPk.getStructureId());
               evo.setDim5StructureElementId(myPk.getStructureElementId());
               break;
            case 6:
               evo.setDim6StructureId(myPk.getStructureId());
               evo.setDim6StructureElementId(myPk.getStructureElementId());
               break;
            case 7:
               evo.setDim7StructureId(myPk.getStructureId());
               evo.setDim7StructureElementId(myPk.getStructureElementId());
               break;
            case 8:
               evo.setDim8StructureId(myPk.getStructureId());
               evo.setDim8StructureElementId(myPk.getStructureElementId());
               break;
            default:
               evo.setDim9StructureId(myPk.getStructureId());
               evo.setDim9StructureElementId(myPk.getStructureElementId());
            }
         }
      }

      EntityRef var9 = ref[size];
      if(var9 != null) {
         evo.setDataType(var9.toString());
      }

      return evo;
   }

   private void checkTargetCells(RechargeImpl editorData) throws ValidationException {
      int withoutRatio = 0;
      int withRatio = 0;
      Iterator dao = editorData.getSelectedTargetCells().iterator();

      while(dao.hasNext()) {
         RechargeCellDataVO i$ = (RechargeCellDataVO)dao.next();
         if(i$.getRatio() == null) {
            ++withoutRatio;
         } else {
            ++withRatio;
         }
      }

      if(withoutRatio > 0 && withRatio > 0) {
         throw new ValidationException("target cells must either all have no ratio or all have a ratio");
      } else if(withRatio != 0) {
         StructureElementDAO var13 = new StructureElementDAO();
         Iterator var14 = editorData.getSelectedTargetCells().iterator();

         while(var14.hasNext()) {
            RechargeCellDataVO row = (RechargeCellDataVO)var14.next();
            EntityRef[] arr$ = row.getCellData();
            int len$ = arr$.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
               EntityRef ref = arr$[i$1];
               System.out.println(ref);
               if(ref instanceof StructureElementRef) {
                  StructureElementPK pk = (StructureElementPK)ref.getPrimaryKey();
                  StructureElementForIdsELO el = var13.getStructureElementForIds(pk.getStructureId(), pk.getStructureElementId());
                  if(!((Boolean)el.getValueAt(0, "Leaf")).booleanValue()) {
                     throw new ValidationException("when a ratio is specified, all elements must be leaves. " + ref.getNarrative() + " is not a leaf element");
                  }
               }
            }
         }

      }
   }
}
