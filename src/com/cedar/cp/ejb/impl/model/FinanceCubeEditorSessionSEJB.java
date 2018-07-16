// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.RollUpRule;
import com.cedar.cp.api.model.RollUpRuleLine;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionAssignmentsELO;
import com.cedar.cp.dto.cm.AllChangeMgmtsForModelWithXMLELO;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeDependenciesELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.CheckCubeIntegrityTaskRequest;
import com.cedar.cp.dto.model.CubeAdminTaskRequest;
import com.cedar.cp.dto.model.CubeRebuildTaskRequest;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionCSO;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionSSO;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.dto.model.GlobalCubeAdminTaskRequest;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.RollUpRuleImpl;
import com.cedar.cp.dto.model.RollUpRuleLineImpl;
import com.cedar.cp.dto.model.cc.CellCalcImportTaskRequest;
import com.cedar.cp.dto.model.cc.DynamicCellCalcImportTaskRequest;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsELO;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentDAO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtDAO;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.cm.xml.CubeEvent;
import com.cedar.cp.ejb.impl.datatype.DataTypeAccessor;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.EventFactory;
import com.cedar.cp.ejb.impl.model.FinanceCubeDataTypeEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.RollUpRuleEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmFinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.mapping.MappedFinanceCubeDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class FinanceCubeEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<1><2><3><4><5><6><7><8>";
   private static final String DEPENDANTS_FOR_INSERT = "<1>";
   private static final String DEPENDANTS_FOR_COPY = "<1><2><3><4><5><6><7><8>";
   private static final String DEPENDANTS_FOR_UPDATE = "<1><2><3><4><5><6><7><8>";
   private static final String DEPENDANTS_FOR_DELETE = "<1><2><3><4><5><6><7>";
   private int mNextId = -1;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private FinanceCubeEditorSessionSSO mSSO;
   private FinanceCubeCK mThisTableKey;
   private ModelEVO mModelEVO;
   private FinanceCubeEVO mFinanceCubeEVO;


   public FinanceCubeEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (FinanceCubeCK)paramKey;

      FinanceCubeEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<1><2><3><4><5><6><7><8>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
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
      this.mSSO = new FinanceCubeEditorSessionSSO();
      FinanceCubeImpl editorData = this.buildFinanceCubeEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(FinanceCubeImpl editorData) throws Exception {
      if(!editorData.isInsideChangeManagement()) {
         editorData.setChangeManagementOutstanding(changeManagementRequestsOutstanding(this.mFinanceCubeEVO.getModelId(), this.mFinanceCubeEVO.getVisId()));
      }

      this.loadRollUpRules(this.mFinanceCubeEVO, editorData);
      editorData.setUpdatedTime(this.mFinanceCubeEVO.getUpdatedTime());
   }

   private FinanceCubeImpl buildFinanceCubeEditData(Object thisKey) throws Exception {
      FinanceCubeImpl editorData = new FinanceCubeImpl(thisKey);
      editorData.setVisId(this.mFinanceCubeEVO.getVisId());
      editorData.setDescription(this.mFinanceCubeEVO.getDescription());
      editorData.setLockedByTaskId(this.mFinanceCubeEVO.getLockedByTaskId());
      editorData.setHasData(this.mFinanceCubeEVO.getHasData());
      editorData.setAudited(this.mFinanceCubeEVO.getAudited());
      editorData.setCubeFormulaEnabled(this.mFinanceCubeEVO.getCubeFormulaEnabled());
      editorData.setVersionNum(this.mFinanceCubeEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeFinanceCubeEditData(editorData);
      return editorData;
   }

   private void completeFinanceCubeEditData(FinanceCubeImpl editorData) throws Exception {
      DataTypeAccessor datatypeAccess = new DataTypeAccessor(this.getInitialContext());
      AllDataTypesELO dataTypeELO = datatypeAccess.getAllDataTypes();
      Iterator iter = this.mFinanceCubeEVO.getFinanceCubeDataTypes().iterator();

      while(iter.hasNext()) {
         FinanceCubeDataTypeEVO fcdtEVO = (FinanceCubeDataTypeEVO)iter.next();
         DataTypePK pk = new DataTypePK(fcdtEVO.getDataTypeId());
         dataTypeELO.reset();

         while(dataTypeELO.hasNext()) {
            dataTypeELO.next();
            DataTypePK listPK = (DataTypePK)dataTypeELO.getDataTypeEntityRef().getPrimaryKey();
            if(listPK.equals(pk)) {
               editorData.addSelectedDataTypeRef(dataTypeELO.getDataTypeEntityRef(), fcdtEVO.getCubeLastUpdatedTime());
            }
         }
      }

      editorData.getMappedDataTypes().addAll(this.loadMappedDataTypes(this.mFinanceCubeEVO.getModelId(), this.mFinanceCubeEVO.getFinanceCubeId()));
      editorData.getAggregatedDataTypes().addAll(this.loadAggregatedDataTypes(this.mFinanceCubeEVO.getFinanceCubeId()));
   }

   public FinanceCubeEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      FinanceCubeEditorSessionSSO var4;
      try {
         this.mSSO = new FinanceCubeEditorSessionSSO();
         FinanceCubeImpl e = new FinanceCubeImpl((Object)null);
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

   private void completeGetNewItemData(FinanceCubeImpl editorData) throws Exception {}

   public FinanceCubeCK insert(FinanceCubeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      FinanceCubeImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "<1>");
         this.mFinanceCubeEVO = new FinanceCubeEVO();
         this.mFinanceCubeEVO.setVisId(editorData.getVisId());
         this.mFinanceCubeEVO.setDescription(editorData.getDescription());
         this.mFinanceCubeEVO.setLockedByTaskId(editorData.getLockedByTaskId());
         this.mFinanceCubeEVO.setHasData(editorData.isHasData());
         this.mFinanceCubeEVO.setAudited(editorData.isAudited());
         this.mFinanceCubeEVO.setCubeFormulaEnabled(editorData.isCubeFormulaEnabled());
         this.updateFinanceCubeRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addFinanceCubesItem(this.mFinanceCubeEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<0>");
         Iterator iter = this.mModelEVO.getFinanceCubes().iterator();
         this.mFinanceCubeEVO = ((FinanceCubeEVO)iter.next());
         while (iter.hasNext()) {
        	 FinanceCubeEVO tempFinanceCubeEvo = ((FinanceCubeEVO)iter.next());
        	 if (tempFinanceCubeEvo.getFinanceCubeId() >= this.mFinanceCubeEVO.getFinanceCubeId()) {
        		 this.mFinanceCubeEVO = tempFinanceCubeEvo;
        	 }
//        	 if (this.mFinanceCubeEVO.insertPending()) {
//        		 break;
//        	 }
         }
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("FinanceCube", this.mFinanceCubeEVO.getPK(), 1);

         return new FinanceCubeCK(this.mModelEVO.getPK(), this.mFinanceCubeEVO.getPK());
      } catch (ValidationException e) {
         throw new EJBException(e);
      } catch (EJBException e) {
         throw e;
      } catch (Exception e) {
         e.printStackTrace();
         throw new EJBException(e.getMessage(), e);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }
   }

   private void updateFinanceCubeRelationships(FinanceCubeImpl editorData) throws ValidationException {}

   private void completeInsertSetup(FinanceCubeImpl editorData) throws Exception {
      this.validateVirtualDataTypeReferences(editorData);
      FinanceCubeDataTypeEVO fcdyEVO = null;
      Iterator iter = editorData.getSelectedDataTypeRefs().keySet().iterator();

      while(iter.hasNext()) {
         EntityRef entity = (EntityRef)iter.next();
         DataTypePK pk = (DataTypePK)entity.getPrimaryKey();
         fcdyEVO = new FinanceCubeDataTypeEVO(this.mFinanceCubeEVO.getFinanceCubeId(), pk.getDataTypeId(), (Timestamp)null);
         this.mFinanceCubeEVO.addFinanceCubeDataTypesItem(fcdyEVO);
      }

      this.updateRollUpRules(editorData, this.mFinanceCubeEVO);
   }

   private void insertIntoAdditionalTables(FinanceCubeImpl editorData, boolean isInsert) throws Exception {
      int financeCubeId = this.mFinanceCubeEVO.getFinanceCubeId();
      this.issueFinanceCubeAdminTask(financeCubeId, editorData.getVisId(), editorData.getDescription(), 0, editorData.isGlobal() );
      ModelDimensionsELO modelDimensions = this.getModelAccessor().getModelDimensions(this.mModelEVO.getModelId());
//      this.mModelAccessor.setDetails(this.mModelEVO);
   }

   private void validateInsert() throws ValidationException {}

   public FinanceCubeCK copy(FinanceCubeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      FinanceCubeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (FinanceCubeCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<1><2><3><4><5><6><7><8>");
         FinanceCubeEVO e = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.mFinanceCubeEVO = e.deepClone();
         this.mFinanceCubeEVO.setVisId(editorData.getVisId());
         this.mFinanceCubeEVO.setDescription(editorData.getDescription());
         this.mFinanceCubeEVO.setLockedByTaskId(editorData.getLockedByTaskId());
         this.mFinanceCubeEVO.setHasData(editorData.isHasData());
         this.mFinanceCubeEVO.setAudited(editorData.isAudited());
         this.mFinanceCubeEVO.setCubeFormulaEnabled(editorData.isCubeFormulaEnabled());
         this.mFinanceCubeEVO.setVersionNum(0);
         this.updateFinanceCubeRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mFinanceCubeEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addFinanceCubesItem(this.mFinanceCubeEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<0><1><2><3><4><5><6><7><8>");
         Iterator iter = this.mModelEVO.getFinanceCubes().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mFinanceCubeEVO = (FinanceCubeEVO)iter.next();
               if(!this.mFinanceCubeEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new FinanceCubeCK(this.mModelEVO.getPK(), this.mFinanceCubeEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("FinanceCube", this.mFinanceCubeEVO.getPK(), 1);
            FinanceCubeCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(FinanceCubeImpl editorData) throws Exception {}

   public void update(FinanceCubeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      FinanceCubeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (FinanceCubeCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<1><2><3><4><5><6><7><8>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.preValidateUpdate(editorData);
         this.mFinanceCubeEVO.setVisId(editorData.getVisId());
         this.mFinanceCubeEVO.setDescription(editorData.getDescription());
         this.mFinanceCubeEVO.setLockedByTaskId(editorData.getLockedByTaskId());
         this.mFinanceCubeEVO.setHasData(editorData.isHasData());
         this.mFinanceCubeEVO.setAudited(editorData.isAudited());
         this.mFinanceCubeEVO.setCubeFormulaEnabled(editorData.isCubeFormulaEnabled());
         if(editorData.getVersionNum() != this.mFinanceCubeEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mFinanceCubeEVO.getVersionNum());
         }

         this.updateFinanceCubeRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("FinanceCube", this.mFinanceCubeEVO.getPK(), 3);
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

   private void preValidateUpdate(FinanceCubeImpl editorData) throws ValidationException {
      this.validateVirtualDataTypeReferences(editorData);
      if(!editorData.isInsideChangeManagement()) {
         if(editorData.isCubeFormulaEnabled() && !this.mFinanceCubeEVO.getCubeFormulaEnabled()) {
            editorData.setCreateCubeFomrulaRuntime(true);
         } else if(!editorData.isCubeFormulaEnabled() && this.mFinanceCubeEVO.getCubeFormulaEnabled()) {
            editorData.setDropCubeFormulaRuntime(true);
         }
      }

   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(FinanceCubeImpl editorData) throws Exception {
      if(editorData.isInsideChangeManagement()) {
         boolean eventFactory = false;
         this.updateDataTypes(editorData, this.mFinanceCubeEVO, this.queryDataTypes(), (EventFactory)null);
         this.updateRollUpRules(editorData, this.mFinanceCubeEVO);
         if(eventFactory) {
            this.issueFinanceCubeAdminTask(this.mFinanceCubeEVO.getFinanceCubeId(), this.mFinanceCubeEVO.getVisId(), this.mFinanceCubeEVO.getDescription(), 2);
         }
      } else {
         EventFactory eventFactory1 = new EventFactory();
         List cubeEvents = this.updateDataTypes(editorData, this.mFinanceCubeEVO, this.queryDataTypes(), eventFactory1);
         if(!cubeEvents.isEmpty()) {
            ModelRef modelRef = (new ModelDAO()).getRef(new ModelPK(this.mFinanceCubeEVO.getModelId()));
            ChangeManagementType cm = eventFactory1.createFinanceCubeCMRequest(modelRef.getNarrative(), "amend", editorData.getVisId(), editorData.getDescription(), cubeEvents);
            ChangeMgmtEngine engine = new ChangeMgmtEngine(this.getInitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
            engine.registerUpdateRequest(cm);
            if(editorData.isSubmitChangeManagement()) {
               engine.issueUpdateTask(new UserPK(this.getUserId()), editorData.getModelRef());
            }
         }

         if(editorData.isCreateCubeFomrulaRuntime()) {
            this.issueFinanceCubeAdminTask(this.mFinanceCubeEVO.getFinanceCubeId(), this.mFinanceCubeEVO.getVisId(), this.mFinanceCubeEVO.getDescription(), 3);
         } else if(editorData.isDropCubeFormulaRuntime()) {
            this.issueFinanceCubeAdminTask(this.mFinanceCubeEVO.getFinanceCubeId(), this.mFinanceCubeEVO.getVisId(), this.mFinanceCubeEVO.getDescription(), 4);
         }
      }

   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (FinanceCubeCK)paramKey;

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

   private void updateAdditionalTables(FinanceCubeImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (FinanceCubeCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<1><2><3><4><5><6><7>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("FinanceCube", this.mThisTableKey.getFinanceCubePK(), 2);
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
      this.issueFinanceCubeAdminTask(this.mFinanceCubeEVO.getFinanceCubeId(), this.mFinanceCubeEVO.getVisId(), this.mFinanceCubeEVO.getDescription(), 1);
   }

   private void validateDelete() throws ValidationException, Exception {
      if(this.mFinanceCubeEVO.getHasData()) {
         throw new ValidationException("Finance Cube has cells");
      } else {
         AllBudgetInstructionAssignmentsELO list = null;
         BudgetInstructionAssignmentDAO biaDao = new BudgetInstructionAssignmentDAO();
         list = biaDao.getAllBudgetInstructionAssignments();
         this.checkIfInUse(list, "FinanceCubeId", "Budget Instruction");
         XmlFormDAO xmlDao = new XmlFormDAO();
         AllFinanceXmlFormsELO list1 = xmlDao.getAllFinanceXmlForms();
         this.checkIfInUse(list1, "FinanceCubeId", "XML Form");
         if((new MappedFinanceCubeDAO()).isFinanceCubeMapped(this.mFinanceCubeEVO.getFinanceCubeId())) {
            throw new ValidationException("Finance Cube is involved in an external system mapping. Use mapping wizard.");
         } else if((new AmmFinanceCubeDAO()).isFinanceCubeMapped(this.mFinanceCubeEVO.getFinanceCubeId())) {
            throw new ValidationException("Finance Cube is used in an Aggregated Model Mapping. Use mapping wizard.");
         }
      }
   }

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

   private void checkIfInUse(EntityList list, String key, String check) throws ValidationException {
      String message = "Finance Cube is in use by " + check;
      int numRows = list.getNumRows();

      for(int i = 0; i < numRows; ++i) {
         Object o = list.getValueAt(i, key);
         if(o instanceof Integer) {
            Integer id = (Integer)o;
            if(this.mFinanceCubeEVO.getPK().getFinanceCubeId() == id.intValue()) {
               throw new ValidationException(message);
            }
         } else if(o instanceof FinanceCubeRef) {
            FinanceCubeRef var9 = (FinanceCubeRef)o;
            if(this.mFinanceCubeEVO.getPK() == (FinanceCubeRef)var9.getPrimaryKey()) {
               throw new ValidationException(message);
            }
         }
      }

   }

   private int issueFinanceCubeAdminTask(int financeCubeId, String visId, String description, int action) throws Exception {
	   return this.issueFinanceCubeAdminTask( financeCubeId, visId, description, action, false );
   }
   private int issueFinanceCubeAdminTask(int financeCubeId, String visId, String description, int action, boolean isGlobal) throws Exception {
	   TaskRequest e = null; 
      ModelDimensionsELO modelDimensions = this.getModelAccessor().getModelDimensions(this.mModelEVO.getModelId());
      
      try {
    	  	if( isGlobal )
    	  		e = new GlobalCubeAdminTaskRequest(financeCubeId, visId, description, modelDimensions.getNumRows(), action);
    	  	else
    	  		e = new CubeAdminTaskRequest(financeCubeId, visId, description, modelDimensions.getNumRows(), action);
    	  	
         int taskNum = TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, this.getUserId());
         this.mLog.info("issueFinanceCubeAdminTask", "taskId=" + taskNum);
         return taskNum;
      } catch (Exception var8) {
         var8.printStackTrace();
         this.mLog.warn("issueFinanceCubeAdminTask", "unable to issue task");
         throw new EJBException(var8);
      }
   }

   private void validateVirtualDataTypeReferences(FinanceCubeImpl editorData) throws ValidationException {
      Set dtRefs = editorData.getSelectedDataTypeRefs().keySet();
      DataTypeDAO dtDAO = new DataTypeDAO();
      if(dtRefs != null) {
         Iterator dtIter = dtRefs.iterator();

         while(dtIter.hasNext()) {
            DataTypeRef dataTypeRef = (DataTypeRef)dtIter.next();
            DataTypePK pk = (DataTypePK)dataTypeRef.getPrimaryKey();
            if(dataTypeRef.getSubType() == 3) {
               DataTypeDependenciesELO deps = dtDAO.getDataTypeDependencies(pk.getDataTypeId());

               for(int i = 0; i < deps.getNumRows(); ++i) {
                  DataTypeRef depDT = (DataTypeRef)deps.getValueAt(i, "DataType");
                  if(!editorData.hasDataType(depDT)) {
                     throw new ValidationException("Data type " + depDT.getNarrative() + " must also be deployed " + "due to reference from virtual data type " + dataTypeRef.getNarrative() + ".");
                  }
               }
            }
         }
      }

   }

   private Set<DataTypeRef> loadMappedDataTypes(int modelId, int financeCubeId) {
      return (new MappedFinanceCubeDAO()).queryMappedDataTypes(modelId, financeCubeId);
   }

   private Set<DataTypeRef> loadAggregatedDataTypes(int financeCubeId) {
      return (new AmmFinanceCubeDAO()).queryMappedDataTypes(financeCubeId);
   }

   private List<CubeEvent> updateDataTypes(FinanceCubeImpl impl, FinanceCubeEVO evo, Map<DataTypePK, DataTypeRef> dataTypes, EventFactory eventFactory) throws Exception {
      ArrayList cubeEvents = new ArrayList();
      ArrayList exsitingDataTypes = evo.getFinanceCubeDataTypes() != null?new ArrayList(evo.getFinanceCubeDataTypes()):new ArrayList();
      ArrayList newDataTypes = new ArrayList(impl.getSelectedDataTypeRefs().keySet());
      Iterator i$ = exsitingDataTypes.iterator();

      while(i$.hasNext()) {
         FinanceCubeDataTypeEVO dtRef = (FinanceCubeDataTypeEVO)i$.next();
         DataTypeRef fcdtEVO = (DataTypeRef)dataTypes.get(new DataTypePK(dtRef.getDataTypeId()));
         if(newDataTypes.contains(fcdtEVO)) {
            if(eventFactory != null && fcdtEVO.allowsConfigrableRollUp()) {
               RollUpRuleLineImpl rollUpRules = (RollUpRuleLineImpl)impl.getRollUpRuleLine(fcdtEVO);
               Object rollUpRules1 = null;
               if(rollUpRules != null && this.rollUpRulesChanged(evo, dtRef.getDataTypeId(), impl.getDimensions(), rollUpRules.getRollUpRulesAsBooleans())) {
                  cubeEvents.add(eventFactory.createCubeEvent("amend", "data-type", fcdtEVO.getNarrative(), rollUpRules.getRollUpRulesAsBooleans()));
               }
            }

            newDataTypes.remove(fcdtEVO);
         } else if(eventFactory != null) {
            cubeEvents.add(eventFactory.createCubeEvent("delete", "data-type", fcdtEVO.getNarrative(), (boolean[])null));
         } else {
            evo.deleteFinanceCubeDataTypesItem(dtRef.getPK());
         }
      }

      i$ = newDataTypes.iterator();

      while(i$.hasNext()) {
         DataTypeRef dtRef1 = (DataTypeRef)i$.next();
         if(eventFactory != null) {
            RollUpRuleLineImpl fcdtEVO1 = (RollUpRuleLineImpl)impl.getRollUpRuleLine(dtRef1);
            boolean[] rollUpRules2 = null;
            if(fcdtEVO1 != null) {
               rollUpRules2 = fcdtEVO1.getRollUpRulesAsBooleans();
            }

            cubeEvents.add(eventFactory.createCubeEvent("insert", "data-type", dtRef1.getNarrative(), rollUpRules2));
         } else {
            FinanceCubeDataTypeEVO fcdtEVO2 = new FinanceCubeDataTypeEVO(evo.getFinanceCubeId(), ((DataTypeRefImpl)dtRef1).getDataTypePK().getDataTypeId(), (Timestamp)null);
            evo.addFinanceCubeDataTypesItem(fcdtEVO2);
         }
      }

      return cubeEvents;
   }

   private boolean rollUpRulesChanged(FinanceCubeEVO evo, int dataTypeId, DimensionRef[] dimensions, boolean[] rollUpRules) {
      for(int i = 0; i < dimensions.length; ++i) {
         int dimensionId = ((DimensionRefImpl)dimensions[i]).getDimensionPK().getDimensionId();
         RollUpRuleEVO rurEVO = evo.getRollUpRule(dataTypeId, dimensionId);
         if(rurEVO == null) {
            throw new IllegalStateException("Unable to locate roll up rule for dataTypeId:" + dataTypeId + " dimensionId:" + dimensionId);
         }

         if(rurEVO.getRollUp() != rollUpRules[i]) {
            return true;
         }
      }

      return false;
   }

   private void updateRollUpRules(FinanceCubeImpl impl, FinanceCubeEVO evo) {
      ArrayList exsitingRollUpRules = evo.getRollUpRules() != null?new ArrayList(evo.getRollUpRules()):new ArrayList();
      List newRollUpRules = impl.getRollUpRuleLines();
      HashSet updatedRollUpRules = new HashSet();
      Iterator i$ = exsitingRollUpRules.iterator();

      while(i$.hasNext()) {
         RollUpRuleEVO rurLineImpl = (RollUpRuleEVO)i$.next();
         RollUpRuleLineImpl i$1 = (RollUpRuleLineImpl)impl.getRollUpRuleLine(rurLineImpl.getDataTypeId());
         if(i$1 != null) {
            if(rurLineImpl.getRollUp() != i$1.rollsUp(rurLineImpl.getDimensionId())) {
               rurLineImpl.setRollUp(i$1.rollsUp(rurLineImpl.getDimensionId()));
            }

            updatedRollUpRules.add(i$1);
         } else {
            evo.deleteRollUpRulesItem(rurLineImpl.getPK());
         }
      }

      i$ = newRollUpRules.iterator();

      while(i$.hasNext()) {
         RollUpRuleLine rurLineImpl1 = (RollUpRuleLine)i$.next();
         if(!updatedRollUpRules.contains(rurLineImpl1)) {
            Iterator i$2 = rurLineImpl1.getRollUpRules().iterator();

            while(i$2.hasNext()) {
               RollUpRule rurImpl = (RollUpRule)i$2.next();
               RollUpRuleEVO rurEVO = new RollUpRuleEVO(this.getNextId(), evo.getFinanceCubeId(), ((DataTypeRefImpl)rurImpl.getDataType()).getDataTypePK().getDataTypeId(), ((DimensionRefImpl)rurImpl.getDimension()).getDimensionPK().getDimensionId(), rurImpl.isRollUp());
               evo.addRollUpRulesItem(rurEVO);
            }
         }
      }

   }

   private void loadRollUpRules(FinanceCubeEVO evo, FinanceCubeImpl impl) {
      HashMap dataTypes = new HashMap();
      Iterator dimensions = impl.getSelectedDataTypeRefs().keySet().iterator();

      while(dimensions.hasNext()) {
         DataTypeRef i$ = (DataTypeRef)dimensions.next();
         dataTypes.put(Integer.valueOf(((DataTypeRefImpl)i$).getDataTypePK().getDataTypeId()), i$);
      }

      DimensionRef[] var13 = this.loadDimensionRefs(evo.getModelId());
      impl.setDimensions(var13);
      if(evo.getRollUpRules() != null) {
         Iterator var14 = evo.getRollUpRules().iterator();

         while(var14.hasNext()) {
            RollUpRuleEVO rurEVO = (RollUpRuleEVO)var14.next();
            RollUpRuleLineImpl rurLineImpl = (RollUpRuleLineImpl)impl.getRollUpRuleLine(rurEVO.getDataTypeId());
            if(rurLineImpl == null) {
               ArrayList rurImpl = new ArrayList();
               rurLineImpl = new RollUpRuleLineImpl(impl, (DataTypeRefImpl)dataTypes.get(Integer.valueOf(rurEVO.getDataTypeId())), rurImpl);
               DimensionRef[] arr$ = var13;
               int len$ = var13.length;

               for(int i$1 = 0; i$1 < len$; ++i$1) {
                  DimensionRef dimRef = arr$[i$1];
                  rurImpl.add(new RollUpRuleImpl(rurLineImpl, (DimensionRefImpl)dimRef, false));
               }

               impl.getRollUpRuleLines().add(rurLineImpl);
            }

            RollUpRuleImpl var15 = (RollUpRuleImpl)rurLineImpl.getRollUpRule(rurEVO.getDimensionId());
            var15.setRollUp(rurEVO.getRollUp());
         }
      }

   }

   private DimensionRef[] loadDimensionRefs(int modelId) {
      ModelDimensionsELO dimensions = (new ModelDAO()).getModelDimensions(modelId);
      DimensionRef[] result = new DimensionRef[dimensions.getNumRows()];

      for(int row = 0; row < dimensions.getNumRows(); ++row) {
         result[row] = (DimensionRef)dimensions.getValueAt(row, "Dimension");
      }

      return result;
   }

   private Map<DataTypePK, DataTypeRef> queryDataTypes() {
      AllDataTypesELO allDataTypes = (new DataTypeDAO()).getAllDataTypes();
      HashMap dataTypeMap = new HashMap();

      for(int i = 0; i < allDataTypes.getNumRows(); ++i) {
         DataTypeRefImpl dtRefImpl = (DataTypeRefImpl)allDataTypes.getValueAt(i, "DataType");
         dataTypeMap.put(dtRefImpl.getDataTypePK(), dtRefImpl);
      }

      return dataTypeMap;
   }

   public static boolean changeManagementRequestsOutstanding(int modelId, String cubeVisId) throws Exception {
      ChangeMgmtDAO cmDAO = new ChangeMgmtDAO();
      AllChangeMgmtsForModelWithXMLELO allCMCs = cmDAO.getAllChangeMgmtsForModelWithXML(modelId);
      JAXBContext jc = JAXBContext.newInstance("com.cedar.cp.ejb.impl.cm.xml");
      Unmarshaller u = jc.createUnmarshaller();
      int row = 0;

      label27:
      while(row < allCMCs.getNumRows()) {
         String xml = (String)allCMCs.getValueAt(row, "XmlText");
         StreamSource ss = new StreamSource(new StringReader(xml));
         ChangeManagementType cm = (ChangeManagementType)u.unmarshal(ss);
         Iterator i = cm.getEvent().iterator();

         ChangeManagementEvent cmEvent;
         do {
            if(!i.hasNext()) {
               ++row;
               continue label27;
            }

            cmEvent = (ChangeManagementEvent)i.next();
         } while(cmEvent.getType() == null || !cmEvent.getType().equals("finance-cube") || cmEvent.getVisId() == null || !cmEvent.getVisId().equals(cubeVisId));

         return true;
      }

      return false;
   }

   public int issueCubeRebuildtask(int userId, List<EntityRef> rebuildList) throws ValidationException, EJBException {
      try {
         CubeRebuildTaskRequest e = new CubeRebuildTaskRequest(rebuildList);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, userId);
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new EJBException(var5);
      }
   }

   public int issueCheckIntegrityTask(int userId, List<EntityRef> checkIntegrityList) throws ValidationException, EJBException {
      try {
         CheckCubeIntegrityTaskRequest e = new CheckCubeIntegrityTaskRequest(checkIntegrityList);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, userId);
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new EJBException(var5);
      }
   }

   public URL initiateTransfer(byte[] data) throws ValidationException, EJBException {
      try {
         File e = File.createTempFile("cpTmp", "ccXML");
         FileOutputStream fos = new FileOutputStream(e);
         fos.write(data);
         fos.close();
         return e.toURL();
      } catch (MalformedURLException var4) {
         throw new ValidationException("Failed to create server file:" + var4.getMessage());
      } catch (FileNotFoundException var5) {
         throw new ValidationException("Failed to create server file:" + var5.getMessage());
      } catch (IOException var6) {
         throw new ValidationException("Failed to write to server file:" + var6.getMessage());
      }
   }

   public void appendToFile(URL url, byte[] data) throws ValidationException, EJBException {
      RandomAccessFile raf = null;

      try {
         File e = new File(url.getPath());
         raf = new RandomAccessFile(e, "rw");
         raf.seek(raf.length());
         raf.write(data);
      } catch (IOException var12) {
         throw new ValidationException("Failed to append to server file:" + url + " " + var12.getMessage());
      } finally {
         if(raf != null) {
            try {
               raf.close();
            } catch (IOException var11) {
               throw new EJBException("Failed to close random access file:" + var11.getMessage(), var11);
            }
         }

      }

   }

   public int issueCellCalcImportTask(int userId, String clientSideURL, String serverSideURL, int issuingTaskId) throws ValidationException, EJBException {
      try {
         CellCalcImportTaskRequest e = new CellCalcImportTaskRequest();
         e.setClientURL(clientSideURL);
         e.setSourceURL(serverSideURL);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), false, e, userId, issuingTaskId);
      } catch (EJBException var6) {
         throw var6;
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new EJBException(var7.getMessage());
      }
   }

   public int issueDynamicCellCalcImportTask(int userId, List<Pair<String, String>> clientAndServerURLS, int issuingTaskId) throws ValidationException, EJBException {
      try {
         DynamicCellCalcImportTaskRequest e = new DynamicCellCalcImportTaskRequest();
         e.setClientServerURLs(clientAndServerURLS);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), false, e, userId, issuingTaskId);
      } catch (EJBException var5) {
         throw var5;
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new EJBException(var6.getMessage());
      }
   }

   private int getNextId() {
      return this.mNextId--;
   }
}
