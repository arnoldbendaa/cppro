// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionCSO;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionSSO;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.datatype.DataTypeRelPK;
import com.cedar.cp.dto.datatype.VirtualExprParser;
import com.cedar.cp.dto.model.AllFinanceCubeDataTypesELO;
import com.cedar.cp.dto.model.AllFinanceCubesForDataTypeELO;
import com.cedar.cp.dto.model.BudgetCycleIntegrityELO;
import com.cedar.cp.dto.model.CellCalcIntegrityELO;
import com.cedar.cp.dto.model.CubeAdminTaskRequest;
import com.cedar.cp.dto.model.FinanceCubesUsingDataTypeELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.mapping.CreateAllExternalViewsTaskRequest;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeAccessor;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.datatype.DataTypeRelEVO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.CellCalcDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDataTypeDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class DataTypeEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient boolean mIssueCreateAllExternalViews = false;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient DataTypeAccessor mDataTypeAccessor;
   private DataTypeEditorSessionSSO mSSO;
   private DataTypePK mThisTableKey;
   private DataTypeEVO mDataTypeEVO;


   public DataTypeEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (DataTypePK)paramKey;

      DataTypeEditorSessionSSO e;
      try {
         this.mDataTypeEVO = this.getDataTypeAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new DataTypeEditorSessionSSO();
      DataTypeImpl editorData = this.buildDataTypeEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(DataTypeImpl editorData) throws Exception {
      this.loadDataTypeRefs(this.mDataTypeEVO, editorData);
   }

   private DataTypeImpl buildDataTypeEditData(Object thisKey) throws Exception {
      DataTypeImpl editorData = new DataTypeImpl(thisKey);
      editorData.setVisId(this.mDataTypeEVO.getVisId());
      editorData.setDescription(this.mDataTypeEVO.getDescription());
      editorData.setReadOnlyFlag(this.mDataTypeEVO.getReadOnlyFlag());
      editorData.setAvailableForImport(this.mDataTypeEVO.getAvailableForImport());
      editorData.setAvailableForExport(this.mDataTypeEVO.getAvailableForExport());
      editorData.setSubType(this.mDataTypeEVO.getSubType());
      editorData.setFormulaExpr(this.mDataTypeEVO.getFormulaExpr());
      editorData.setMeasureClass(this.mDataTypeEVO.getMeasureClass());
      editorData.setMeasureLength(this.mDataTypeEVO.getMeasureLength());
      editorData.setMeasureScale(this.mDataTypeEVO.getMeasureScale());
      editorData.setMeasureValidation(this.mDataTypeEVO.getMeasureValidation());
      editorData.setVersionNum(this.mDataTypeEVO.getVersionNum());
      this.completeDataTypeEditData(editorData);
      return editorData;
   }

   private void completeDataTypeEditData(DataTypeImpl editorData) throws Exception {
      FinanceCubeDataTypeDAO fcDataTypeDAO = new FinanceCubeDataTypeDAO();
      AllFinanceCubesForDataTypeELO fCubes = fcDataTypeDAO.getAllFinanceCubesForDataType(((DataTypePK)editorData.getPrimaryKey()).getDataTypeId());
      editorData.setDeployed(fCubes != null && fCubes.getNumRows() > 0);
   }

   public DataTypeEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      DataTypeEditorSessionSSO var4;
      try {
         this.mSSO = new DataTypeEditorSessionSSO();
         DataTypeImpl e = new DataTypeImpl((Object)null);
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

   private void completeGetNewItemData(DataTypeImpl editorData) throws Exception {}

   public DataTypePK insert(DataTypeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DataTypeImpl editorData = cso.getEditorData();

      DataTypePK e;
      try {
         this.mDataTypeEVO = new DataTypeEVO();
         this.mDataTypeEVO.setVisId(editorData.getVisId());
         this.mDataTypeEVO.setDescription(editorData.getDescription());
         this.mDataTypeEVO.setReadOnlyFlag(editorData.isReadOnlyFlag());
         this.mDataTypeEVO.setAvailableForImport(editorData.isAvailableForImport());
         this.mDataTypeEVO.setAvailableForExport(editorData.isAvailableForExport());
         this.mDataTypeEVO.setSubType(editorData.getSubType());
         this.mDataTypeEVO.setFormulaExpr(editorData.getFormulaExpr());
         this.mDataTypeEVO.setMeasureClass(editorData.getMeasureClass());
         this.mDataTypeEVO.setMeasureLength(editorData.getMeasureLength());
         this.mDataTypeEVO.setMeasureScale(editorData.getMeasureScale());
         this.mDataTypeEVO.setMeasureValidation(editorData.getMeasureValidation());
         this.updateDataTypeRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mDataTypeEVO = this.getDataTypeAccessor().create(this.mDataTypeEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("DataType", this.mDataTypeEVO.getPK(), 1);
         e = this.mDataTypeEVO.getPK();
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

   private void updateDataTypeRelationships(DataTypeImpl editorData) throws ValidationException {}

   private void completeInsertSetup(DataTypeImpl editorData) throws Exception {
      this.updateEVO(editorData, this.mDataTypeEVO);
   }

   private void insertIntoAdditionalTables(DataTypeImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public DataTypePK copy(DataTypeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DataTypeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (DataTypePK)editorData.getPrimaryKey();

      DataTypePK var5;
      try {
         DataTypeEVO e = this.getDataTypeAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mDataTypeEVO = e.deepClone();
         this.mDataTypeEVO.setVisId(editorData.getVisId());
         this.mDataTypeEVO.setDescription(editorData.getDescription());
         this.mDataTypeEVO.setReadOnlyFlag(editorData.isReadOnlyFlag());
         this.mDataTypeEVO.setAvailableForImport(editorData.isAvailableForImport());
         this.mDataTypeEVO.setAvailableForExport(editorData.isAvailableForExport());
         this.mDataTypeEVO.setSubType(editorData.getSubType());
         this.mDataTypeEVO.setFormulaExpr(editorData.getFormulaExpr());
         this.mDataTypeEVO.setMeasureClass(editorData.getMeasureClass());
         this.mDataTypeEVO.setMeasureLength(editorData.getMeasureLength());
         this.mDataTypeEVO.setMeasureScale(editorData.getMeasureScale());
         this.mDataTypeEVO.setMeasureValidation(editorData.getMeasureValidation());
         this.mDataTypeEVO.setVersionNum(0);
         this.updateDataTypeRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mDataTypeEVO.prepareForInsert();
         this.mDataTypeEVO = this.getDataTypeAccessor().create(this.mDataTypeEVO);
         this.mThisTableKey = this.mDataTypeEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("DataType", this.mDataTypeEVO.getPK(), 1);
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

   private void completeCopySetup(DataTypeImpl editorData) throws Exception {}

   public void update(DataTypeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      DataTypeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (DataTypePK)editorData.getPrimaryKey();

      try {
         this.mDataTypeEVO = this.getDataTypeAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mDataTypeEVO.setVisId(editorData.getVisId());
         this.mDataTypeEVO.setDescription(editorData.getDescription());
         this.mDataTypeEVO.setReadOnlyFlag(editorData.isReadOnlyFlag());
         this.mDataTypeEVO.setAvailableForImport(editorData.isAvailableForImport());
         this.mDataTypeEVO.setAvailableForExport(editorData.isAvailableForExport());
         this.mDataTypeEVO.setSubType(editorData.getSubType());
         this.mDataTypeEVO.setFormulaExpr(editorData.getFormulaExpr());
         this.mDataTypeEVO.setMeasureClass(editorData.getMeasureClass());
         this.mDataTypeEVO.setMeasureLength(editorData.getMeasureLength());
         this.mDataTypeEVO.setMeasureScale(editorData.getMeasureScale());
         this.mDataTypeEVO.setMeasureValidation(editorData.getMeasureValidation());
         if(editorData.getVersionNum() != this.mDataTypeEVO.getVersionNum()) {
            //throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mDataTypeEVO.getVersionNum());
             throw new VersionValidationException("Version update failure. The entity you have been editing has been updated by another user.");
         }

         this.updateDataTypeRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getDataTypeAccessor().setDetails(this.mDataTypeEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("DataType", this.mDataTypeEVO.getPK(), 3);
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

   private void preValidateUpdate(DataTypeImpl editorData) throws ValidationException {
      if(editorData.isAvailableForExport() != this.mDataTypeEVO.getAvailableForExport()) {
         this.mIssueCreateAllExternalViews = true;
      }

      if(editorData.isAvailableForImport() != this.mDataTypeEVO.getAvailableForImport()) {
         this.mIssueCreateAllExternalViews = true;
      }

      if(editorData.getSubType() != this.mDataTypeEVO.getSubType() && (editorData.getSubType() == 3 || this.mDataTypeEVO.getSubType() == 3)) {
         this.mIssueCreateAllExternalViews = true;
      }

   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(DataTypeImpl editorData) throws Exception {
      this.updateEVO(editorData, this.mDataTypeEVO);
   }

   private void updateAdditionalTables(DataTypeImpl editorData) throws Exception {
      this.checkFinanceCubeDeployments();
      if(this.mIssueCreateAllExternalViews) {
         this.issueCreateAllExternalViews(this.getUserId());
      }

   }

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (DataTypePK)paramKey;

      try {
         this.mDataTypeEVO = this.getDataTypeAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mDataTypeAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("DataType", this.mThisTableKey, 2);
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
      (new DataTypeDAO()).validateDataTypeDelete(this.mDataTypeEVO.getPK().getDataTypeId());
      FinanceCubeDataTypeDAO dao = new FinanceCubeDataTypeDAO();
      AllFinanceCubeDataTypesELO elo = dao.getAllFinanceCubeDataTypes();
      int numRows = elo.getNumRows();

      for(int ccdao = 0; ccdao < numRows; ++ccdao) {
         Short bcdao = (Short)elo.getValueAt(ccdao, "DataTypeId");
         if(this.mDataTypeEVO.getPK().getDataTypeId() == bcdao.intValue()) {
            throw new ValidationException("DataType is in use by Finance Cube");
         }
      }

      CellCalcDAO var10 = new CellCalcDAO();
      CellCalcIntegrityELO var8 = var10.getCellCalcIntegrity();
      numRows = var8.getNumRows();

      for(int var11 = 0; var11 < numRows; ++var11) {
         Integer i = (Integer)var8.getValueAt(var11, "DataTypeId");
         if(this.mDataTypeEVO.getPK().getDataTypeId() == i.intValue()) {
            throw new ValidationException("DataType is in use by Cell Calculation");
         }
      }

      BudgetCycleDAO var12 = new BudgetCycleDAO();
      BudgetCycleIntegrityELO var9 = var12.getBudgetCycleIntegrity();
      numRows = var9.getNumRows();

      for(int var13 = 0; var13 < numRows; ++var13) {
         String id = (String)var9.getValueAt(var13, "XmlFormDataType");
         if(this.mDataTypeEVO.getVisId().equals(id)) {
            throw new ValidationException("DataType is in use by Budget Cycle");
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

   private DataTypeAccessor getDataTypeAccessor() throws Exception {
      if(this.mDataTypeAccessor == null) {
         this.mDataTypeAccessor = new DataTypeAccessor(this.getInitialContext());
      }

      return this.mDataTypeAccessor;
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

   private void loadDataTypeRefs(DataTypeEVO evo, DataTypeImpl impl) throws ValidationException {
      if(evo.getDataTypeDependencies() != null) {
         impl.setDataTypeRefs(this.queryReferencedDataTypes(impl));
      } else {
         impl.setDataTypeRefs(new ArrayList());
      }

   }

   private void updateEVO(DataTypeImpl impl, DataTypeEVO evo) throws ValidationException {
      if(impl.getSubType() == 3) {
         List i = this.queryReferencedDataTypes(impl);
         Object relEVO = evo.getDataTypeDependencies() != null?new ArrayList(evo.getDataTypeDependencies()):Collections.EMPTY_LIST;
         short dataTypeId = impl.getPrimaryKey() != null?((DataTypePK)impl.getPrimaryKey()).getDataTypeId():-1;
         Iterator eiIter = i.iterator();

         while(eiIter.hasNext()) {
            DataTypeRefImpl relEVO1 = (DataTypeRefImpl)eiIter.next();
            DataTypeRelPK key = new DataTypeRelPK(dataTypeId, relEVO1.getDataTypePK().getDataTypeId());
            DataTypeRelEVO dtRelEVO = null;
            if(evo.getDataTypeDependencies() != null) {
               dtRelEVO = evo.getDataTypeDependenciesItem(key);
            }

            if(dtRelEVO == null) {
               dtRelEVO = new DataTypeRelEVO(key.getDataTypeId(), key.getRefDataTypeId());
               evo.addDataTypeDependenciesItem(dtRelEVO);
            } else {
               ((Collection)relEVO).remove(dtRelEVO);
            }
         }

         eiIter = ((Collection)relEVO).iterator();

         while(eiIter.hasNext()) {
            DataTypeRelEVO relEVO3 = (DataTypeRelEVO)eiIter.next();
            evo.deleteDataTypeDependenciesItem(relEVO3.getPK());
         }
      } else if(evo.getDataTypeDependencies() != null) {
         Iterator i1 = evo.getDataTypeDependencies().iterator();

         while(i1.hasNext()) {
            DataTypeRelEVO relEVO2 = (DataTypeRelEVO)i1.next();
            evo.deleteDataTypeDependenciesItem(relEVO2.getPK());
         }
      }

   }

   private List queryReferencedDataTypes(DataTypeImpl impl) throws ValidationException {
      ArrayList result = new ArrayList();
      if(impl.getFormulaExpr() != null && impl.getFormulaExpr().trim().length() > 0) {
         Map dtMap = this.getAllDataTypesInMap();
         VirtualExprParser parser = new VirtualExprParser(impl.getFormulaExpr());
         Iterator dIter = parser.getIDs().iterator();

         while(dIter.hasNext()) {
            String token = (String)dIter.next();
            DataTypeRefImpl dtRef = (DataTypeRefImpl)dtMap.get(token);
            if(dtRef == null) {
               throw new ValidationException("Unknown data type referenced:" + token);
            }

            result.add(dtRef);
         }
      }

      return result;
   }

   private Map getAllDataTypesInMap() {
      HashMap m = new HashMap();
      AllDataTypesELO dataTypes = (new DataTypeDAO()).getAllDataTypes();

      for(int row = 0; row < dataTypes.getNumRows(); ++row) {
         DataTypeRefImpl ref = (DataTypeRefImpl)dataTypes.getValueAt(row, "DataType");
         m.put(ref.getNarrative(), ref);
      }

      return m;
   }

   private void checkFinanceCubeDeployments() throws Exception {
      if(this.mDataTypeEVO.getSubType() == 3) {
         FinanceCubeDAO fcDAO = new FinanceCubeDAO();
         FinanceCubesUsingDataTypeELO cubes = fcDAO.getFinanceCubesUsingDataType(this.mDataTypeEVO.getDataTypeId());

         for(int i = 0; i < cubes.getNumRows(); ++i) {
            int modelId = ((Integer)cubes.getValueAt(i, "ModelId")).intValue();
            int financeCubeId = ((Integer)cubes.getValueAt(i, "FinanceCubeId")).intValue();
            FinanceCubeRef financeCubeRef = (FinanceCubeRef)cubes.getValueAt(i, "FinanceCube");
            String description = (String)cubes.getValueAt(i, "Description");
            this.issueFinanceCubeAdminTask(modelId, financeCubeId, financeCubeRef.getNarrative(), description, 2);
            this.mIssueCreateAllExternalViews = true;
         }
      }

   }

   private int issueFinanceCubeAdminTask(int modelId, int financeCubeId, String visId, String description, int action) throws Exception {
      ModelDimensionsELO modelDimensions = (new ModelDAO()).getModelDimensions(modelId);

      try {
         CubeAdminTaskRequest e = new CubeAdminTaskRequest(financeCubeId, visId, description, modelDimensions.getNumRows(), action);
         e.addExclusiveAccess("admin");
         int taskNum = TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, this.getUserId());
         this.mLog.info("issueFinanceCubeAdminTask", "taskId=" + taskNum);
         return taskNum;
      } catch (Exception var9) {
         var9.printStackTrace();
         this.mLog.warn("issueFinanceCubeAdminTask", "unable to issue task");
         throw new EJBException(var9);
      }
   }

   public int issueCreateAllExternalViews(int userId) throws EJBException {
      try {
         CreateAllExternalViewsTaskRequest e = new CreateAllExternalViewsTaskRequest();
         e.addExclusiveAccess("admin");
         int taskNum = TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, userId);
         this.mLog.info("issueCreateAllExternalViews", "taskId=" + taskNum);
         return taskNum;
      } catch (Exception var4) {
         var4.printStackTrace();
         this.mLog.warn("issueCreateAllExternalViews", "unable to issue task");
         throw new EJBException(var4);
      }
   }
}
