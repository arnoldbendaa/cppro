// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:38
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcDeploymentValidationException;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.CellCalcRebuildTaskRequest;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.cc.AllCcDeploymentsELO;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionCSO;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentLineImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentRefImpl;
import com.cedar.cp.dto.model.cc.CcMappingLineImpl;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByCCDeploymentIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByCCDeploymentIdELO;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.base.cube.RtCubeUtilsDAO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDataTypeEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEditorSessionSEJB$1;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEntryEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.cc.CcMappingEntryEVO;
import com.cedar.cp.ejb.impl.model.cc.CcMappingLineEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionAccessor;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class CcDeploymentEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<42><43><44><45><46>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<42><43><44><45><46>";
   private static final String DEPENDANTS_FOR_UPDATE = "<42><43><44><45><46>";
   private static final String DEPENDANTS_FOR_DELETE = "<42><43><44><45><46>";
   private int mNextId = -1;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private CcDeploymentEditorSessionSSO mSSO;
   private CcDeploymentCK mThisTableKey;
   private ModelEVO mModelEVO;
   private CcDeploymentEVO mCcDeploymentEVO;


   public CcDeploymentEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (CcDeploymentCK)paramKey;

      CcDeploymentEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<42><43><44><45><46>");
         this.mCcDeploymentEVO = this.mModelEVO.getCellCalcDeploymentsItem(this.mThisTableKey.getCcDeploymentPK());
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
      this.mSSO = new CcDeploymentEditorSessionSSO();
      CcDeploymentImpl editorData = this.buildCcDeploymentEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(CcDeploymentImpl editorData) throws Exception {
      this.load(this.mCcDeploymentEVO, editorData);
   }

   private CcDeploymentImpl buildCcDeploymentEditData(Object thisKey) throws Exception {
      CcDeploymentImpl editorData = new CcDeploymentImpl(thisKey);
      editorData.setModelId(this.mCcDeploymentEVO.getModelId());
      editorData.setVisId(this.mCcDeploymentEVO.getVisId());
      editorData.setDescription(this.mCcDeploymentEVO.getDescription());
      editorData.setXmlformId(this.mCcDeploymentEVO.getXmlformId());
      editorData.setDimContext0(this.mCcDeploymentEVO.getDimContext0());
      editorData.setDimContext1(this.mCcDeploymentEVO.getDimContext1());
      editorData.setDimContext2(this.mCcDeploymentEVO.getDimContext2());
      editorData.setDimContext3(this.mCcDeploymentEVO.getDimContext3());
      editorData.setDimContext4(this.mCcDeploymentEVO.getDimContext4());
      editorData.setDimContext5(this.mCcDeploymentEVO.getDimContext5());
      editorData.setDimContext6(this.mCcDeploymentEVO.getDimContext6());
      editorData.setDimContext7(this.mCcDeploymentEVO.getDimContext7());
      editorData.setDimContext8(this.mCcDeploymentEVO.getDimContext8());
      editorData.setDimContext9(this.mCcDeploymentEVO.getDimContext9());
      editorData.setVersionNum(this.mCcDeploymentEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeCcDeploymentEditData(editorData);
      return editorData;
   }

   private void completeCcDeploymentEditData(CcDeploymentImpl editorData) throws Exception {}

   public CcDeploymentEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      CcDeploymentEditorSessionSSO var4;
      try {
         this.mSSO = new CcDeploymentEditorSessionSSO();
         CcDeploymentImpl e = new CcDeploymentImpl((Object)null);
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

   private void completeGetNewItemData(CcDeploymentImpl editorData) throws Exception {}

   public CcDeploymentCK insert(CcDeploymentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CcDeploymentImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mCcDeploymentEVO = new CcDeploymentEVO();
         this.mCcDeploymentEVO.setModelId(editorData.getModelId());
         this.mCcDeploymentEVO.setVisId(editorData.getVisId());
         this.mCcDeploymentEVO.setDescription(editorData.getDescription());
         this.mCcDeploymentEVO.setXmlformId(editorData.getXmlformId());
         this.mCcDeploymentEVO.setDimContext0(editorData.getDimContext0());
         this.mCcDeploymentEVO.setDimContext1(editorData.getDimContext1());
         this.mCcDeploymentEVO.setDimContext2(editorData.getDimContext2());
         this.mCcDeploymentEVO.setDimContext3(editorData.getDimContext3());
         this.mCcDeploymentEVO.setDimContext4(editorData.getDimContext4());
         this.mCcDeploymentEVO.setDimContext5(editorData.getDimContext5());
         this.mCcDeploymentEVO.setDimContext6(editorData.getDimContext6());
         this.mCcDeploymentEVO.setDimContext7(editorData.getDimContext7());
         this.mCcDeploymentEVO.setDimContext8(editorData.getDimContext8());
         this.mCcDeploymentEVO.setDimContext9(editorData.getDimContext9());
         this.updateCcDeploymentRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addCellCalcDeploymentsItem(this.mCcDeploymentEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<41>");
         Iterator e = this.mModelEVO.getCellCalcDeployments().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mCcDeploymentEVO = (CcDeploymentEVO)e.next();
               if(!this.mCcDeploymentEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("CcDeployment", this.mCcDeploymentEVO.getPK(), 1);
            CcDeploymentCK var5 = new CcDeploymentCK(this.mModelEVO.getPK(), this.mCcDeploymentEVO.getPK());
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

   private void updateCcDeploymentRelationships(CcDeploymentImpl editorData) throws ValidationException {}

   private void completeInsertSetup(CcDeploymentImpl editorData) throws Exception {
      this.update(editorData, this.mCcDeploymentEVO);
   }

   private void insertIntoAdditionalTables(CcDeploymentImpl editorData, boolean isInsert) throws Exception {
      this.getModelAccessor().flush(this.mModelEVO.getPK());
      this.updateRtCubeDeployments(this.mCcDeploymentEVO);
      boolean validateCellCalcDeploymnets = SystemPropertyHelper.queryBooleanSystemProperty((Connection)null, "SYS: Validate Cell Calc Deployment", true);
      if(validateCellCalcDeploymnets) {
         CcDeploymentCK key = this.mCcDeploymentEVO.getCK(this.mModelEVO);
         EntityList overlappingDeployments = (new RtCubeUtilsDAO()).queryDeploymentIntersections(this.mCcDeploymentEVO.getModelId(), key.getCcDeploymentPK().getCcDeploymentId());
         if(overlappingDeployments.getNumRows() > 0) {
            throw new CcDeploymentValidationException(overlappingDeployments);
         }
      }

   }

   private void validateInsert() throws ValidationException {}

   public CcDeploymentCK copy(CcDeploymentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CcDeploymentImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CcDeploymentCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<42><43><44><45><46>");
         CcDeploymentEVO e = this.mModelEVO.getCellCalcDeploymentsItem(this.mThisTableKey.getCcDeploymentPK());
         this.mCcDeploymentEVO = e.deepClone();
         this.mCcDeploymentEVO.setModelId(editorData.getModelId());
         this.mCcDeploymentEVO.setVisId(editorData.getVisId());
         this.mCcDeploymentEVO.setDescription(editorData.getDescription());
         this.mCcDeploymentEVO.setXmlformId(editorData.getXmlformId());
         this.mCcDeploymentEVO.setDimContext0(editorData.getDimContext0());
         this.mCcDeploymentEVO.setDimContext1(editorData.getDimContext1());
         this.mCcDeploymentEVO.setDimContext2(editorData.getDimContext2());
         this.mCcDeploymentEVO.setDimContext3(editorData.getDimContext3());
         this.mCcDeploymentEVO.setDimContext4(editorData.getDimContext4());
         this.mCcDeploymentEVO.setDimContext5(editorData.getDimContext5());
         this.mCcDeploymentEVO.setDimContext6(editorData.getDimContext6());
         this.mCcDeploymentEVO.setDimContext7(editorData.getDimContext7());
         this.mCcDeploymentEVO.setDimContext8(editorData.getDimContext8());
         this.mCcDeploymentEVO.setDimContext9(editorData.getDimContext9());
         this.mCcDeploymentEVO.setVersionNum(0);
         this.updateCcDeploymentRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mCcDeploymentEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addCellCalcDeploymentsItem(this.mCcDeploymentEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<41><42><43><44><45><46>");
         Iterator iter = this.mModelEVO.getCellCalcDeployments().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mCcDeploymentEVO = (CcDeploymentEVO)iter.next();
               if(!this.mCcDeploymentEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new CcDeploymentCK(this.mModelEVO.getPK(), this.mCcDeploymentEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("CcDeployment", this.mCcDeploymentEVO.getPK(), 1);
            CcDeploymentCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(CcDeploymentImpl editorData) throws Exception {}

   public void update(CcDeploymentEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      CcDeploymentImpl editorData = cso.getEditorData();
      this.mThisTableKey = (CcDeploymentCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<42><43><44><45><46>");
         this.mCcDeploymentEVO = this.mModelEVO.getCellCalcDeploymentsItem(this.mThisTableKey.getCcDeploymentPK());
         this.preValidateUpdate(editorData);
         this.mCcDeploymentEVO.setModelId(editorData.getModelId());
         this.mCcDeploymentEVO.setVisId(editorData.getVisId());
         this.mCcDeploymentEVO.setDescription(editorData.getDescription());
         this.mCcDeploymentEVO.setXmlformId(editorData.getXmlformId());
         this.mCcDeploymentEVO.setDimContext0(editorData.getDimContext0());
         this.mCcDeploymentEVO.setDimContext1(editorData.getDimContext1());
         this.mCcDeploymentEVO.setDimContext2(editorData.getDimContext2());
         this.mCcDeploymentEVO.setDimContext3(editorData.getDimContext3());
         this.mCcDeploymentEVO.setDimContext4(editorData.getDimContext4());
         this.mCcDeploymentEVO.setDimContext5(editorData.getDimContext5());
         this.mCcDeploymentEVO.setDimContext6(editorData.getDimContext6());
         this.mCcDeploymentEVO.setDimContext7(editorData.getDimContext7());
         this.mCcDeploymentEVO.setDimContext8(editorData.getDimContext8());
         this.mCcDeploymentEVO.setDimContext9(editorData.getDimContext9());
         if(editorData.getVersionNum() != this.mCcDeploymentEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mCcDeploymentEVO.getVersionNum());
         }

         this.updateCcDeploymentRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("CcDeployment", this.mCcDeploymentEVO.getPK(), 3);
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

   private void preValidateUpdate(CcDeploymentImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(CcDeploymentImpl editorData) throws Exception {
      this.update(editorData, this.mCcDeploymentEVO);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (CcDeploymentCK)paramKey;

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

   private void updateAdditionalTables(CcDeploymentImpl editorData) throws Exception {
      this.getModelAccessor().flush(new ModelPK(editorData.getModelId()));
      this.updateRtCubeDeployments(this.mCcDeploymentEVO);
      CcDeploymentCK key = (CcDeploymentCK)editorData.getPrimaryKey();
      boolean validateCellCalcDeploymnets = SystemPropertyHelper.queryBooleanSystemProperty((Connection)null, "SYS: Validate Cell Calc Deployment", true);
      if(validateCellCalcDeploymnets) {
         EntityList overlappingDeployments = (new RtCubeUtilsDAO()).queryDeploymentIntersections(editorData.getModelId(), key.getCcDeploymentPK().getCcDeploymentId());
         if(overlappingDeployments.getNumRows() > 0) {
            throw new CcDeploymentValidationException(overlappingDeployments);
         }
      }

      this.checkForChanges(editorData);
   }

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (CcDeploymentCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<42><43><44><45><46>");
         this.mCcDeploymentEVO = this.mModelEVO.getCellCalcDeploymentsItem(this.mThisTableKey.getCcDeploymentPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteCellCalcDeploymentsItem(this.mThisTableKey.getCcDeploymentPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("CcDeployment", this.mThisTableKey.getCcDeploymentPK(), 2);
         this.getModelAccessor().flush(this.mModelEVO.getPK());
         this.updateRtCubeDeployments(this.mCcDeploymentEVO);
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

   private void validateDelete() throws ValidationException, Exception {
      ReportDefinitionAccessor reportDefAccessor = new ReportDefinitionAccessor(this.getInitialContext());
      AllReportDefCalcByCCDeploymentIdELO calclist = reportDefAccessor.getAllReportDefCalcByCCDeploymentId(this.mCcDeploymentEVO.getCcDeploymentId());
      AllReportDefSummaryCalcByCCDeploymentIdELO summaryCalclist = reportDefAccessor.getAllReportDefSummaryCalcByCCDeploymentId(this.mCcDeploymentEVO.getCcDeploymentId());
      if(calclist != null && calclist.getNumRows() > 0 || summaryCalclist != null && summaryCalclist.getNumRows() > 0) {
         throw new ValidationException("Cell Calculation Deployment is in use");
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

   private void load(CcDeploymentEVO evo, CcDeploymentImpl impl) {
      DimensionRef[] dimensionRefs = this.getModelDimensionRefs(evo.getModelId());
      HashSet seKeys = new HashSet();
      Map dataTypesMap = this.getAllDataTypes();
      impl.setDimensionRefs(dimensionRefs);
      DimensionRef[] explicitDimensionRefs = impl.getExplicitMappingDimensionRefs();
      if(evo.getCCDeploymentLines() != null) {
         Iterator seDAO = evo.getCCDeploymentLines().iterator();

         int deploymentDataTypes;
         while(seDAO.hasNext()) {
            CcDeploymentLineEVO seList = (CcDeploymentLineEVO)seDAO.next();
            Iterator seVisIds;
            if(seList.getCCDeploymentEntries() != null) {
               seVisIds = seList.getCCDeploymentEntries().iterator();

               while(seVisIds.hasNext()) {
                  CcDeploymentEntryEVO i$ = (CcDeploymentEntryEVO)seVisIds.next();
                  int lineEVO = i$.getStructureId();
                  int deploymentEntries = i$.getStructureElementId();
                  seKeys.add(new StructureElementKeyImpl(lineEVO, deploymentEntries));
               }
            }

            if(seList.getCCMappingLines() != null) {
               seVisIds = seList.getCCMappingLines().iterator();

               while(seVisIds.hasNext()) {
                  CcMappingLineEVO var31 = (CcMappingLineEVO)seVisIds.next();
                  if(var31.getCCMappingEntries() != null) {
                     Iterator var29 = var31.getCCMappingEntries().iterator();

                     while(var29.hasNext()) {
                        CcMappingEntryEVO var33 = (CcMappingEntryEVO)var29.next();
                        deploymentDataTypes = var33.getStructureId();
                        int lineImpl = var33.getStructureElementId();
                        seKeys.add(new StructureElementKeyImpl(deploymentDataTypes, lineImpl));
                     }
                  }
               }
            }
         }

         StructureElementDAO var26 = new StructureElementDAO();
         EntityList var27 = var26.queryPathToRoots(seKeys);
         HashMap var28 = new HashMap();

         for(int var32 = 0; var32 < var27.getNumRows(); ++var32) {
            var28.put(new StructureElementKeyImpl(((Integer)var27.getValueAt(var32, "StructureId")).intValue(), ((Integer)var27.getValueAt(var32, "StructureElementId")).intValue()), (String)var27.getValueAt(var32, "VisId"));
         }

         CcDeploymentLineImpl var37;
         for(Iterator var30 = evo.getCCDeploymentLines().iterator(); var30.hasNext(); impl.getDeploymentLines().add(var37)) {
            CcDeploymentLineEVO var35 = (CcDeploymentLineEVO)var30.next();
            HashMap var34 = new HashMap();

            for(deploymentDataTypes = 0; deploymentDataTypes < dimensionRefs.length; ++deploymentDataTypes) {
               var34.put(dimensionRefs[deploymentDataTypes], new HashMap());
            }

            HashSet var36 = new HashSet();
            var37 = new CcDeploymentLineImpl(impl, var35.getPK(), var35.getLineIndex(), var35.getCalLevel(), var34, var36, new ArrayList());
            Iterator i$1;
            if(var35.getCCDeploymentEntries() != null) {
               i$1 = var35.getCCDeploymentEntries().iterator();

               while(i$1.hasNext()) {
                  CcDeploymentEntryEVO mappingLineEVO = (CcDeploymentEntryEVO)i$1.next();
                  int mappingLineImpl = mappingLineEVO.getStructureId();
                  int i$2 = mappingLineEVO.getStructureElementId();
                  boolean mappingEntryEVO = mappingLineEVO.getSelectedInd();
                  StructureElementKeyImpl dimRef = new StructureElementKeyImpl(mappingLineImpl, i$2);
                  StructureElementRefImpl explicitDimensionIndex = new StructureElementRefImpl(new StructureElementPK(mappingLineImpl, i$2), (String)var28.get(dimRef));
                  DimensionRef structureId = dimensionRefs[mappingLineEVO.getDimSeq()];
                  ((Map)var34.get(structureId)).put(explicitDimensionIndex, Boolean.valueOf(mappingEntryEVO));
               }
            }

            if(var35.getCCDeploymentDataTypes() != null) {
               i$1 = var35.getCCDeploymentDataTypes().iterator();

               while(i$1.hasNext()) {
                  CcDeploymentDataTypeEVO var39 = (CcDeploymentDataTypeEVO)i$1.next();
                  DataTypeRef var41 = (DataTypeRef)dataTypesMap.get(Integer.valueOf(var39.getDataTypeId()));
                  if(var41 == null) {
                     throw new IllegalStateException("Failed to locate deployment data type id=" + var39.getDataTypeId());
                  }

                  var36.add(var41);
               }
            }

            CcMappingLineImpl var40;
            if(var35.getCCMappingLines() != null) {
               for(i$1 = var35.getCCMappingLines().iterator(); i$1.hasNext(); var37.getMappingLines().add(var40)) {
                  CcMappingLineEVO var38 = (CcMappingLineEVO)i$1.next();
                  var40 = new CcMappingLineImpl(var38.getPK(), (DataTypeRef)dataTypesMap.get(Integer.valueOf(var38.getDataTypeId())), var37, var38.getFormFieldName(), Arrays.asList(new StructureElementRef[explicitDimensionRefs.length]));
                  if(var38.getCCMappingEntries() != null) {
                     Iterator var43 = var38.getCCMappingEntries().iterator();

                     while(var43.hasNext()) {
                        CcMappingEntryEVO var42 = (CcMappingEntryEVO)var43.next();
                        DimensionRef var44 = dimensionRefs[var42.getDimSeq()];
                        int var46 = this.getDimIndex(var44, explicitDimensionRefs);
                        int var45 = var42.getStructureId();
                        int structureElementId = var42.getStructureElementId();
                        StructureElementKeyImpl seKey = new StructureElementKeyImpl(var45, structureElementId);
                        StructureElementRefImpl seRefImpl = new StructureElementRefImpl(new StructureElementPK(var45, structureElementId), (String)var28.get(seKey));
                        var40.getEntries().set(var46, seRefImpl);
                     }
                  }
               }
            }
         }

         Collections.sort(impl.getDeploymentLines(), new CcDeploymentEditorSessionSEJB$1(this));
      }

   }

   private Map<Integer, DataTypeRef> getAllDataTypes() {
      DataTypeDAO dtDAO = new DataTypeDAO();
      AllDataTypesELO dataTypes = dtDAO.getAllDataTypes();
      HashMap dtMap = new HashMap();

      for(int i = 0; i < dataTypes.getNumRows(); ++i) {
         DataTypeRefImpl dtRef = (DataTypeRefImpl)dataTypes.getValueAt(i, "DataType");
         dtMap.put(Integer.valueOf(dtRef.getDataTypePK().getDataTypeId()), dtRef);
      }

      return dtMap;
   }

   private void update(CcDeploymentImpl impl, CcDeploymentEVO evo) {
      DimensionRef[] dimensionRefs = this.getModelDimensionRefs(((ModelRefImpl)impl.getModelRef()).getModelPK().getModelId());
      ArrayList newDeploymentLines = new ArrayList(impl.getDeploymentLines());
      ArrayList existingDeploymentLines = evo.getCCDeploymentLines() != null?new ArrayList(evo.getCCDeploymentLines()):new ArrayList();
      Iterator i$ = existingDeploymentLines.iterator();

      while(i$.hasNext()) {
         CcDeploymentLineEVO lineImpl = (CcDeploymentLineEVO)i$.next();
         CcDeploymentLineImpl CcDeploymentLineId = (CcDeploymentLineImpl)impl.getDeploymentLine(lineImpl.getPK());
         if(CcDeploymentLineId != null) {
            this.update(CcDeploymentLineId, lineImpl, dimensionRefs);
            newDeploymentLines.remove(CcDeploymentLineId);
         } else {
            evo.deleteCCDeploymentLinesItem(lineImpl.getPK());
         }
      }

      i$ = newDeploymentLines.iterator();

      while(i$.hasNext()) {
         CcDeploymentLine var21 = (CcDeploymentLine)i$.next();
         int var22 = this.getNextId();
         CcDeploymentLineEVO lineEVO = new CcDeploymentLineEVO(var22, evo.getCcDeploymentId(), var21.getIndex(), var21.getCalendarLevel(), new ArrayList(), new ArrayList(), new ArrayList());

         for(int i$1 = 0; i$1 < dimensionRefs.length; ++i$1) {
            DimensionRef mappingLine = dimensionRefs[i$1];
            Map mappingLineImpl = (Map)var21.getDeploymentEntries().get(mappingLine);
            if(mappingLineImpl != null) {
               Iterator ccMappingLineId = mappingLineImpl.entrySet().iterator();

               while(ccMappingLineId.hasNext()) {
                  Entry mappingLineEVO = (Entry)ccMappingLineId.next();
                  StructureElementRefImpl explicitDimensionRefs = (StructureElementRefImpl)mappingLineEVO.getKey();
                  CcDeploymentEntryEVO explicitDimensionIndex = new CcDeploymentEntryEVO(this.getNextId(), var22, explicitDimensionRefs.getStructureElementPK().getStructureId(), explicitDimensionRefs.getStructureElementPK().getStructureElementId(), i$1, ((Boolean)mappingLineEVO.getValue()).booleanValue());
                  lineEVO.addCCDeploymentEntriesItem(explicitDimensionIndex);
               }
            }
         }

         Iterator var24 = var21.getDeploymentDataTypes().iterator();

         while(var24.hasNext()) {
            DataTypeRef var23 = (DataTypeRef)var24.next();
            DataTypeRefImpl var25 = (DataTypeRefImpl)var23;
            CcDeploymentDataTypeEVO var29 = new CcDeploymentDataTypeEVO();
            var29.setCcDeploymentDataTypeId(this.getNextId());
            var29.setCcDeploymentLineId(lineEVO.getCcDeploymentLineId());
            var29.setDataTypeId(var25.getDataTypePK().getDataTypeId());
            lineEVO.addCCDeploymentDataTypesItem(var29);
         }

         var24 = var21.getMappingLines().iterator();

         while(var24.hasNext()) {
            CcMappingLine var27 = (CcMappingLine)var24.next();
            CcMappingLineImpl var26 = (CcMappingLineImpl)var27;
            int var28 = this.getNextId();
            CcMappingLineEVO var31 = new CcMappingLineEVO(var28, var22, ((DataTypeRefImpl)var26.getDataType()).getDataTypePK().getDataTypeId(), var27.getFormField(), new ArrayList());
            DimensionRef[] var30 = impl.getExplicitMappingDimensionRefs();

            for(int var32 = 0; var32 < var30.length; ++var32) {
               DimensionRef dimRef = var30[var32];
               StructureElementRef seRef = (StructureElementRef)var26.getEntries().get(var32);
               StructureElementPK sePK = (StructureElementPK)seRef.getPrimaryKey();
               CcMappingEntryEVO mappingEntryEVO = new CcMappingEntryEVO(this.getNextId(), var28, sePK.getStructureId(), sePK.getStructureElementId(), this.getDimIndex(dimRef, dimensionRefs));
               var31.addCCMappingEntriesItem(mappingEntryEVO);
            }

            lineEVO.addCCMappingLinesItem(var31);
         }

         evo.addCCDeploymentLinesItem(lineEVO);
      }

   }

   private int getDimIndex(DimensionRef targetDimRef, DimensionRef[] dimensionRefs) {
      for(int i = 0; i < dimensionRefs.length; ++i) {
         if(dimensionRefs[i].equals(targetDimRef)) {
            return i;
         }
      }

      return -1;
   }

   private void update(CcDeploymentLineImpl lineImpl, CcDeploymentLineEVO lineEVO, DimensionRef[] dimensionRefs) {
      Iterator i$;
      for(int existingDeploymentDataTypeEVOs = 0; existingDeploymentDataTypeEVOs < dimensionRefs.length; ++existingDeploymentDataTypeEVOs) {
         DimensionRef newDeploymentDataTypeRefs = dimensionRefs[existingDeploymentDataTypeEVOs];
         Map existingMappingLineEVOs = (Map)lineImpl.getDeploymentEntries().get(newDeploymentDataTypeRefs);
         HashSet newMappingLineImpls = new HashSet();
         CcDeploymentEntryEVO ccMappingLineId;
         if(existingMappingLineEVOs != null) {
            for(i$ = existingMappingLineEVOs.entrySet().iterator(); i$.hasNext(); newMappingLineImpls.add(ccMappingLineId.getPK())) {
               Entry mappingLine = (Entry)i$.next();
               StructureElementRefImpl mappingLineImpl = (StructureElementRefImpl)mappingLine.getKey();
               ccMappingLineId = lineEVO.findDeploymentEntry(existingDeploymentDataTypeEVOs, mappingLineImpl.getStructureElementPK().getStructureId(), mappingLineImpl.getStructureElementPK().getStructureElementId());
               if(ccMappingLineId == null) {
                  ccMappingLineId = new CcDeploymentEntryEVO(this.getNextId(), lineEVO.getCcDeploymentLineId(), mappingLineImpl.getStructureElementPK().getStructureId(), mappingLineImpl.getStructureElementPK().getStructureElementId(), existingDeploymentDataTypeEVOs, ((Boolean)mappingLine.getValue()).booleanValue());
                  lineEVO.addCCDeploymentEntriesItem(ccMappingLineId);
               } else {
                  ccMappingLineId.setSelectedInd(((Boolean)mappingLine.getValue()).booleanValue());
               }
            }
         }

         if(lineEVO.getCCDeploymentEntries() != null) {
            i$ = lineEVO.getCCDeploymentEntries().iterator();

            while(i$.hasNext()) {
               CcDeploymentEntryEVO var27 = (CcDeploymentEntryEVO)i$.next();
               if(var27.getDimSeq() == existingDeploymentDataTypeEVOs && !newMappingLineImpls.contains(var27.getPK())) {
                  lineEVO.deleteCCDeploymentEntriesItem(var27.getPK());
               }
            }
         }

         newMappingLineImpls.clear();
      }

      ArrayList var19 = lineEVO.getCCDeploymentDataTypes() != null?new ArrayList(lineEVO.getCCDeploymentDataTypes()):new ArrayList();
      HashSet var20 = new HashSet(lineImpl.getDeploymentDataTypes());
      Iterator var21 = var19.iterator();

      while(var21.hasNext()) {
         CcDeploymentDataTypeEVO var22 = (CcDeploymentDataTypeEVO)var21.next();
         DataTypeRef var26 = lineImpl.findDataTypeDeployment(var22.getDataTypeId());
         if(var26 != null) {
            var20.remove(var26);
         } else {
            lineEVO.deleteCCDeploymentDataTypesItem(var22.getPK());
         }
      }

      var21 = var20.iterator();

      while(var21.hasNext()) {
         DataTypeRef var25 = (DataTypeRef)var21.next();
         CcDeploymentDataTypeEVO var28 = new CcDeploymentDataTypeEVO(this.getNextId(), lineEVO.getCcDeploymentLineId(), ((DataTypeRefImpl)var25).getDataTypePK().getDataTypeId());
         lineEVO.addCCDeploymentDataTypesItem(var28);
      }

      ArrayList var23 = lineEVO.getCCMappingLines() != null?new ArrayList(lineEVO.getCCMappingLines()):new ArrayList();
      ArrayList var24 = new ArrayList(lineImpl.getMappingLines());
      i$ = var23.iterator();

      CcMappingLineImpl var31;
      while(i$.hasNext()) {
         CcMappingLineEVO var30 = (CcMappingLineEVO)i$.next();
         var31 = (CcMappingLineImpl)lineImpl.getMappingLine(var30.getPK());
         if(var31 != null) {
            this.update(var31, var30, dimensionRefs);
            var24.remove(var31);
         } else {
            lineEVO.deleteCCMappingLinesItem(var30.getPK());
         }
      }

      i$ = var24.iterator();

      while(i$.hasNext()) {
         CcMappingLine var29 = (CcMappingLine)i$.next();
         var31 = (CcMappingLineImpl)var29;
         int var32 = this.getNextId();
         CcMappingLineEVO mappingLineEVO = new CcMappingLineEVO(var32, lineEVO.getCcDeploymentLineId(), ((DataTypeRefImpl)var31.getDataType()).getDataTypePK().getDataTypeId(), var29.getFormField(), new ArrayList());
         DimensionRef[] explicitDimensionRefs = var31.getExplicitMappingDimensionRefs();

         for(int explicitDimensionIndex = 0; explicitDimensionIndex < explicitDimensionRefs.length; ++explicitDimensionIndex) {
            DimensionRef dimRef = explicitDimensionRefs[explicitDimensionIndex];
            StructureElementRef seRef = (StructureElementRef)var31.getEntries().get(explicitDimensionIndex);
            StructureElementPK sePK = (StructureElementPK)seRef.getPrimaryKey();
            CcMappingEntryEVO mappingEntryEVO = new CcMappingEntryEVO(this.getNextId(), var32, sePK.getStructureId(), sePK.getStructureElementId(), this.getDimIndex(dimRef, dimensionRefs));
            mappingLineEVO.addCCMappingEntriesItem(mappingEntryEVO);
         }

         lineEVO.addCCMappingLinesItem(mappingLineEVO);
      }

   }

   private void update(CcMappingLineImpl mappingLineImpl, CcMappingLineEVO mappingLineEVO, DimensionRef[] dimensionRefs) {
      mappingLineEVO.setDataTypeId(((DataTypeRefImpl)mappingLineImpl.getDataType()).getDataTypePK().getDataTypeId());
      mappingLineEVO.setFormFieldName(mappingLineImpl.getFormField());
      DimensionRef[] explicitDimensionRefs = mappingLineImpl.getExplicitMappingDimensionRefs();

      for(int i$ = 0; i$ < explicitDimensionRefs.length; ++i$) {
         DimensionRef mappingEntryEVO = explicitDimensionRefs[i$];
         StructureElementRef seRef = (StructureElementRef)mappingLineImpl.getEntries().get(i$);
         StructureElementPK sePK = (StructureElementPK)seRef.getPrimaryKey();
         int dimensionIndex = this.getDimIndex(explicitDimensionRefs[i$], dimensionRefs);
         CcMappingEntryEVO mappingEntryEVO1 = mappingLineEVO.findMappingEntry(dimensionIndex);
         if(mappingEntryEVO1 == null) {
            mappingEntryEVO1 = new CcMappingEntryEVO(this.getNextId(), mappingLineEVO.getCcMappingLineId(), sePK.getStructureId(), sePK.getStructureElementId(), this.getDimIndex(mappingEntryEVO, dimensionRefs));
            mappingLineEVO.addCCMappingEntriesItem(mappingEntryEVO1);
         } else {
            mappingEntryEVO1.setStructureId(sePK.getStructureId());
            mappingEntryEVO1.setStructureElementId(sePK.getStructureElementId());
         }
      }

      if(mappingLineEVO.getCCMappingEntries() != null) {
         Iterator var11 = mappingLineEVO.getCCMappingEntries().iterator();

         while(var11.hasNext()) {
            CcMappingEntryEVO var12 = (CcMappingEntryEVO)var11.next();
            if(this.invalidMappingSequence(var12.getDimSeq(), explicitDimensionRefs, dimensionRefs)) {
               mappingLineEVO.deleteCCMappingEntriesItem(var12.getPK());
            }
         }
      }

   }

   private boolean invalidMappingSequence(int dimIndex, DimensionRef[] explicitDimensionRefs, DimensionRef[] dimensionRefs) {
      DimensionRef[] arr$ = explicitDimensionRefs;
      int len$ = explicitDimensionRefs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DimensionRef dimRef = arr$[i$];
         if(this.getDimIndex(dimRef, dimensionRefs) == dimIndex) {
            return false;
         }
      }

      return true;
   }

   private DimensionRef[] getModelDimensionRefs(int modelId) {
      ModelDAO modelDAO = new ModelDAO();
      ModelDimensionsELO modelDimensions = modelDAO.getModelDimensions(modelId);
      DimensionRef[] dimRefs = new DimensionRef[modelDimensions.size()];

      for(int i = 0; i < dimRefs.length; ++i) {
         dimRefs[i] = (DimensionRef)modelDimensions.getValueAt(i, "Dimension");
      }

      return dimRefs;
   }

   public String testDeployment(int modelId, StructureElementRef[] origin, boolean[] cascade) throws ValidationException {
      int[] seIds = new int[origin.length];
      ArrayList dynamicDimensions = new ArrayList();

      for(int dataTypeDAO = 0; dataTypeDAO < seIds.length; ++dataTypeDAO) {
         seIds[dataTypeDAO] = ((StructureElementRefImpl)origin[dataTypeDAO]).getStructureElementPK().getStructureElementId();
         if(cascade[dataTypeDAO]) {
            dynamicDimensions.add(Integer.valueOf(dataTypeDAO));
         }
      }

      DataTypeDAO var15 = new DataTypeDAO();
      AllDataTypesELO allDataTypes = var15.getAllDataTypes();
      HashSet dataTypes = new HashSet();

      for(int dao = 0; dao < allDataTypes.getNumRows(); ++dao) {
         dataTypes.add(((DataTypeRef)allDataTypes.getValueAt(dao, "DataType")).getNarrative());
      }

      CcDeploymentDAO var16 = new CcDeploymentDAO();
      List cellCalcDeployments = var16.getRelevantCubeDeployments(modelId, 0, seIds, dataTypes, dynamicDimensions, false);
      StringBuffer result = new StringBuffer();
      if(cellCalcDeployments.isEmpty()) {
         result.append("No cell calculation deployments found.");
      } else {
         AllCcDeploymentsELO allCcDeployments = var16.getAllCcDeployments();
         Iterator i$ = cellCalcDeployments.iterator();

         while(i$.hasNext()) {
            RuntimeCubeDeployment deployment = (RuntimeCubeDeployment)i$.next();
            this.addDetailsToOutputBuffer((RuntimeCcDeployment)deployment, allCcDeployments, result);
         }
      }

      return result.toString();
   }

   private void addDetailsToOutputBuffer(RuntimeCcDeployment deployment, EntityList allCcDeploymentys, StringBuffer result) {
      for(int i = 0; i < allCcDeploymentys.getNumRows(); ++i) {
         CcDeploymentRefImpl deploymentRef = (CcDeploymentRefImpl)allCcDeploymentys.getValueAt(i, "CcDeployment");
         if(deploymentRef.getCcDeploymentPK().getCcDeploymentId() == deployment.getCcDeploymentId()) {
            result.append(deploymentRef);
            result.append(" - ");
            result.append(allCcDeploymentys.getValueAt(i, "Description"));
            result.append("\n");
         }
      }

   }

   private void checkForChanges(CcDeploymentImpl editorData) {
      try {
         CcDeploymentDAO e = new CcDeploymentDAO();
         e.doLoad(this.mThisTableKey.getCcDeploymentPK());
         CcDeploymentEVO original = e.mDetails;
         boolean contextChanged = false;
         Integer[] origContexts = original.getDimContextArray();
         Integer[] contexts = editorData.getDimContextArray();

         for(int linesChanged = 0; !contextChanged && linesChanged < origContexts.length; ++linesChanged) {
            if(contexts[linesChanged] == null && origContexts[linesChanged] != null) {
               contextChanged = true;
            } else if(contexts[linesChanged] != null && !contexts[linesChanged].equals(origContexts[linesChanged])) {
               contextChanged = true;
            }
         }

         e.getDetails("<42><43><45><46>");
         boolean var34 = false;
         List deploymentLines = editorData.getDeploymentLines();
         CcDeploymentLineEVO[] origDeploymentLines = new CcDeploymentLineEVO[deploymentLines.size()];
         origDeploymentLines = (CcDeploymentLineEVO[])original.getCCDeploymentLines().toArray(origDeploymentLines);
         if(origDeploymentLines.length != deploymentLines.size()) {
            var34 = true;
         }

         if(!var34) {
            for(int mappingsChanged = 0; !var34 && mappingsChanged < deploymentLines.size(); ++mappingsChanged) {
               CcDeploymentLine mappingLines = (CcDeploymentLine)deploymentLines.get(mappingsChanged);
               CcDeploymentLineEVO allOrigMappingLines = origDeploymentLines[mappingsChanged];
               Map i$ = mappingLines.getDeploymentEntries();
               Map mappingLine = allOrigMappingLines.getCCDeploymentEntriesMap();
               Collection dataType = i$.values();
               HashMap dataTypePK = new HashMap();
               Iterator formField = dataType.iterator();

               while(formField.hasNext()) {
                  Map elements = (Map)formField.next();
                  dataTypePK.putAll(elements);
               }

               HashMap var48 = new HashMap();
               Iterator var51 = mappingLine.values().iterator();

               while(var51.hasNext()) {
                  CcDeploymentEntryEVO found = (CcDeploymentEntryEVO)var51.next();
                  var48.put(Integer.valueOf(found.getStructureElementId()), Boolean.valueOf(found.getSelectedInd()));
               }

               if(dataTypePK.size() != var48.size()) {
                  var34 = true;
               }

               var51 = dataTypePK.keySet().iterator();

               while(var51.hasNext()) {
                  StructureElementRef var49 = (StructureElementRef)var51.next();
                  StructureElementPK i$1 = (StructureElementPK)var49.getPrimaryKey();
                  Boolean origMappingLineEVO = (Boolean)dataTypePK.get(var49);
                  Boolean origDataTypeId = (Boolean)var48.get(Integer.valueOf(i$1.getStructureElementId()));
                  if(origDataTypeId == null) {
                     var34 = true;
                  } else if(!origMappingLineEVO.equals(origDataTypeId)) {
                     var34 = true;
                  }
               }
            }
         }

         boolean var36 = false;
         if(!var34) {
            List var35 = editorData.getAllMappingLines();
            ArrayList var37 = new ArrayList();
            CcDeploymentLineEVO[] var39 = origDeploymentLines;
            int var41 = origDeploymentLines.length;

            for(int var40 = 0; var40 < var41; ++var40) {
               CcDeploymentLineEVO var44 = var39[var40];
               Collection var47 = var44.getCCMappingLines();
               var37.addAll(var47);
            }

            if(var35.size() != var37.size()) {
               var36 = true;
            }

            if(!var36) {
               Iterator var38 = var35.iterator();

               while(var38.hasNext()) {
                  CcMappingLine var42 = (CcMappingLine)var38.next();
                  DataTypeRef var45 = var42.getDataType();
                  DataTypePK var43 = (DataTypePK)var45.getPrimaryKey();
                  String var46 = var42.getFormField();
                  List var50 = var42.getEntries();
                  boolean var53 = false;
                  Iterator var52 = var37.iterator();

                  while(var52.hasNext()) {
                     CcMappingLineEVO var55 = (CcMappingLineEVO)var52.next();
                     int var54 = var55.getDataTypeId();
                     String origFormField = var55.getFormFieldName();
                     Collection origElements = var55.getCCMappingEntries();
                     if(var43.getDataTypeId() == var54 && var46.equals(origFormField)) {
                        boolean elementsMatch = true;
                        if(origElements.size() == var50.size()) {
                           Iterator i$2 = origElements.iterator();

                           while(i$2.hasNext()) {
                              CcMappingEntryEVO mappingEntry = (CcMappingEntryEVO)i$2.next();
                              int structureElementId = mappingEntry.getStructureElementId();
                              boolean elementMatch = false;

                              for(int i = 0; !elementMatch && i < var50.size(); ++i) {
                                 StructureElementRef ref = (StructureElementRef)var50.get(i);
                                 StructureElementPK refPK = (StructureElementPK)ref.getPrimaryKey();
                                 if(structureElementId == refPK.getStructureElementId()) {
                                    elementMatch = true;
                                 }
                              }

                              if(!elementMatch) {
                                 elementsMatch = false;
                                 break;
                              }
                           }
                        } else {
                           elementsMatch = false;
                        }

                        if(elementsMatch) {
                           var53 = true;
                        }
                     }
                  }

                  if(!var53) {
                     var36 = true;
                     break;
                  }
               }
            }
         }

         if(contextChanged || var34 || var36) {
            this.submitCellCalcRebuild(editorData, contextChanged, var34, var36);
         }
      } catch (Exception var33) {
         var33.printStackTrace();
      }

   }

   private void submitCellCalcRebuild(CcDeploymentImpl editorData, boolean contextChanged, boolean linesChanged, boolean mappingsChanged) {
      CellCalcRebuildTaskRequest request = new CellCalcRebuildTaskRequest(this.mThisTableKey.getModelPK().getModelId(), this.mThisTableKey, editorData.getVisId(), editorData.getDescription());
      request.setContextChanged(contextChanged);
      request.setLinesChanged(linesChanged);
      request.setMappingsChanged(mappingsChanged);

      try {
         int e = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, this.getUserId());
         this.mLog.debug("issueCellCalcRebuild", "taskId=" + e);
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new EJBException(var7);
      }
   }

   public int[] issueCellCalcRebuildTask(int userId, List<Object[]> rebuildList) throws ValidationException {
      ArrayList taskIdList = new ArrayList();

      for(int taskIds = 0; taskIds < rebuildList.size(); ++taskIds) {
         Object[] i = (Object[])rebuildList.get(taskIds);
         EntityRef deploymentRef = (EntityRef)i[0];
         String descr = (String)i[1];
         CellCalcRebuildTaskRequest request = new CellCalcRebuildTaskRequest(((CcDeploymentCK)deploymentRef.getPrimaryKey()).getModelPK().getModelId(), (CcDeploymentCK)deploymentRef.getPrimaryKey(), deploymentRef.getNarrative(), descr);

         try {
            int e = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId);
            taskIdList.add(Integer.valueOf(e));
            this.mLog.info("issueCellCalcRebuildTask", "taskId=" + e + " " + deploymentRef.getNarrative());
         } catch (Exception var10) {
            var10.printStackTrace();
            throw new EJBException(var10);
         }
      }

      int[] var11 = new int[taskIdList.size()];

      for(int var12 = 0; var12 < taskIdList.size(); ++var12) {
         var11[var12] = ((Integer)taskIdList.get(var12)).intValue();
      }

      return var11;
   }

   private int getNextId() {
      return this.mNextId--;
   }

   private void updateRtCubeDeployments(CcDeploymentEVO ccDeploymentEVO) {
      (new RtCubeUtilsDAO()).updateRtCubeDeployments(ccDeploymentEVO.getModelId(), -1, ccDeploymentEVO.getCcDeploymentId(), RtCubeUtilsDAO.RT_CUBE_CELL_CALC_TYPE);
   }
}
