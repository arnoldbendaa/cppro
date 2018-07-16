// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.report.definition.ReportDefinition;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.api.report.distribution.DistributionDetail;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.api.report.distribution.DistributionRef;
import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.report.pack.ReportPackProjection;
import com.cedar.cp.api.report.pack.ReportPackProjectionData;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.report.definition.AllReportDefinitionsELO;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.dto.report.distribution.AllDistributionsELO;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.dto.report.pack.PackLineTaskRequest;
import com.cedar.cp.dto.report.pack.PackTaskRequest;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionCSO;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionSSO;
import com.cedar.cp.dto.report.pack.ReportPackImpl;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackAccessor;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLinkEVO;
import com.cedar.cp.ejb.impl.task.group.TaskRIChecker;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class ReportPackEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ReportPackAccessor mReportPackAccessor;
   private ReportPackEditorSessionSSO mSSO;
   private ReportPackPK mThisTableKey;
   private ReportPackEVO mReportPackEVO;


   public ReportPackEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ReportPackPK)paramKey;

      ReportPackEditorSessionSSO e;
      try {
         this.mReportPackEVO = this.getReportPackAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new ReportPackEditorSessionSSO();
      ReportPackImpl editorData = this.buildReportPackEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ReportPackImpl editorData) throws Exception {}

   private ReportPackImpl buildReportPackEditData(Object thisKey) throws Exception {
      ReportPackImpl editorData = new ReportPackImpl(thisKey);
      editorData.setVisId(this.mReportPackEVO.getVisId());
      editorData.setDescription(this.mReportPackEVO.getDescription());
      editorData.setGroupAttachment(this.mReportPackEVO.getGroupAttachment());
      editorData.setParamExample(this.mReportPackEVO.getParamExample());
      editorData.setVersionNum(this.mReportPackEVO.getVersionNum());
      this.completeReportPackEditData(editorData);
      return editorData;
   }

   private void completeReportPackEditData(ReportPackImpl editorData) throws Exception {
      ArrayList selection = new ArrayList();
      Iterator iter = this.mReportPackEVO.getReportPackDefinitionDistributionList().iterator();
      ReportDefinitionDAO rdDAO = new ReportDefinitionDAO();
      DistributionDAO dDAO = new DistributionDAO();

      while(iter.hasNext()) {
         EntityRef[] row = new EntityRef[2];
         ReportPackLinkEVO evo = (ReportPackLinkEVO)iter.next();
         ReportDefinitionPK rdPK = new ReportDefinitionPK(evo.getReportDefId());
         AllReportDefinitionsELO eList = rdDAO.getAllReportDefinitions();
         int i = 0;

         while(true) {
            EntityRef ref;
            if(i < eList.getNumRows()) {
               ref = (EntityRef)eList.getValueAt(i, "ReportDefinition");
               if(!ref.getPrimaryKey().equals(rdPK)) {
                  ++i;
                  continue;
               }

               row[0] = ref;
            }

            DistributionPK dPK = new DistributionPK(evo.getDistributionId());
            AllDistributionsELO var13 = dDAO.getAllDistributions();

            for(i = 0; i < var13.getNumRows(); ++i) {
               ref = (EntityRef)var13.getValueAt(i, "Distribution");
               if(ref.getPrimaryKey().equals(dPK)) {
                  row[1] = ref;
                  break;
               }
            }

            selection.add(row);
            break;
         }
      }

      editorData.setSelection(selection);
   }

   public ReportPackEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ReportPackEditorSessionSSO var4;
      try {
         this.mSSO = new ReportPackEditorSessionSSO();
         ReportPackImpl e = new ReportPackImpl((Object)null);
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

   private void completeGetNewItemData(ReportPackImpl editorData) throws Exception {}

   public ReportPackPK insert(ReportPackEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportPackImpl editorData = cso.getEditorData();

      ReportPackPK e;
      try {
         this.mReportPackEVO = new ReportPackEVO();
         this.mReportPackEVO.setVisId(editorData.getVisId());
         this.mReportPackEVO.setDescription(editorData.getDescription());
         this.mReportPackEVO.setGroupAttachment(editorData.isGroupAttachment());
         this.mReportPackEVO.setParamExample(editorData.getParamExample());
         this.updateReportPackRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mReportPackEVO = this.getReportPackAccessor().create(this.mReportPackEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ReportPack", this.mReportPackEVO.getPK(), 1);
         e = this.mReportPackEVO.getPK();
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

   private void updateReportPackRelationships(ReportPackImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ReportPackImpl editorData) throws Exception {
      List selection = editorData.getSelection();
      EntityRef[] row = null;
      int selectionid = 0;

      for(int i = 0; i < selection.size(); ++i) {
         row = (EntityRef[])((EntityRef[])selection.get(i));
         ReportDefinitionRef rdREF = (ReportDefinitionRef)row[0];
         ReportDefinitionPK rdPK = (ReportDefinitionPK)rdREF.getPrimaryKey();
         DistributionRef disREF = (DistributionRef)row[1];
         DistributionPK disPK = (DistributionPK)disREF.getPrimaryKey();
         ReportPackLinkEVO evo = new ReportPackLinkEVO();
         --selectionid;
         evo.setReportPackLinkId(selectionid);
         evo.setReportDefId(rdPK.getReportDefinitionId());
         evo.setDistributionId(disPK.getDistributionId());
         this.mReportPackEVO.addReportPackDefinitionDistributionListItem(evo);
      }

   }

   private void insertIntoAdditionalTables(ReportPackImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ReportPackPK copy(ReportPackEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportPackImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportPackPK)editorData.getPrimaryKey();

      ReportPackPK var5;
      try {
         ReportPackEVO e = this.getReportPackAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mReportPackEVO = e.deepClone();
         this.mReportPackEVO.setVisId(editorData.getVisId());
         this.mReportPackEVO.setDescription(editorData.getDescription());
         this.mReportPackEVO.setGroupAttachment(editorData.isGroupAttachment());
         this.mReportPackEVO.setParamExample(editorData.getParamExample());
         this.mReportPackEVO.setVersionNum(0);
         this.updateReportPackRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mReportPackEVO.prepareForInsert();
         this.mReportPackEVO = this.getReportPackAccessor().create(this.mReportPackEVO);
         this.mThisTableKey = this.mReportPackEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ReportPack", this.mReportPackEVO.getPK(), 1);
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

   private void completeCopySetup(ReportPackImpl editorData) throws Exception {}

   public void update(ReportPackEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportPackImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportPackPK)editorData.getPrimaryKey();

      try {
         this.mReportPackEVO = this.getReportPackAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mReportPackEVO.setVisId(editorData.getVisId());
         this.mReportPackEVO.setDescription(editorData.getDescription());
         this.mReportPackEVO.setGroupAttachment(editorData.isGroupAttachment());
         this.mReportPackEVO.setParamExample(editorData.getParamExample());
         if(editorData.getVersionNum() != this.mReportPackEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mReportPackEVO.getVersionNum());
         }

         this.updateReportPackRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getReportPackAccessor().setDetails(this.mReportPackEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ReportPack", this.mReportPackEVO.getPK(), 3);
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

   private void preValidateUpdate(ReportPackImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ReportPackImpl editorData) throws Exception {
      Iterator iter = this.mReportPackEVO.getReportPackDefinitionDistributionList().iterator();

      while(iter.hasNext()) {
         ReportPackLinkEVO evo = (ReportPackLinkEVO)iter.next();
         this.mReportPackEVO.deleteReportPackDefinitionDistributionListItem(evo.getPK());
      }

      this.completeInsertSetup(editorData);
   }

   private void updateAdditionalTables(ReportPackImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ReportPackPK)paramKey;

      try {
         this.mReportPackEVO = this.getReportPackAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mReportPackAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ReportPack", this.mThisTableKey, 2);
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
      try {
         TaskRIChecker.isInUseTaskGroup(this.getCPConnection(), this.mReportPackEVO.getPK(), 5);
      } catch (ValidationException var2) {
         throw new ValidationException("ReportPack " + var2.getMessage() + " is in use in TaskGroup ");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ReportPackAccessor getReportPackAccessor() throws Exception {
      if(this.mReportPackAccessor == null) {
         this.mReportPackAccessor = new ReportPackAccessor(this.getInitialContext());
      }

      return this.mReportPackAccessor;
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

   public int issueReport(int intUserId, EntityRef ref, ReportPackOption options, int issuingTaskId) throws EJBException {
      int var6;
      try {
         this.setUserId(intUserId);
         PackTaskRequest e = new PackTaskRequest();
         e.setPackRef(ref);
         e.setOption(options);
         var6 = TaskMessageFactory.issueNewTask(new InitialContext(), 1, false, e, intUserId, issuingTaskId);
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10);
      } finally {
         this.setUserId(0);
      }

      return var6;
   }

   public List issueReportLine(int intUserId, EntityRef definition, EntityRef distribution, ReportPackOption options, boolean group, int taskId) throws EJBException {
      try {
         this.setUserId(intUserId);
         ArrayList e = new ArrayList();
         ReportDefinition reportDef = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionEditorSession(definition.getNarrative()).getReportDefinitionEditor().getReportDefinition();
         int reportType = reportDef.getReportTypeId();
         String modelId = "";
         int xmlFormId = 0;
         int depth = 0;
         String dataType = "";
         Object template = null;
         String columnSettings = null;
         Map selection = Collections.EMPTY_MAP;
         List location = Collections.EMPTY_LIST;
         List contextDimIndexes = Collections.EMPTY_LIST;
         int deploymentId = 0;
         byte[] var35;
         switch(reportType) {
         case 1:
            ReportDefinitionFormRunDetails var32 = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionFormRunDetails(reportDef.getVisId());
            modelId = var32.getModelId();
            xmlFormId = var32.getXMLFormId();
            dataType = var32.getDataType();
            depth = var32.getDepth();
            selection = var32.getSelection();
            var35 = var32.getTemplate();
            location = var32.getBudgetLocations();
            break;
         case 2:
            ReportDefinitionMappingRunDetails var36 = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionMappingRunDetails(reportDef.getVisId());
            modelId = var36.getModelId();
            var35 = var36.getTemplate();
            location = var36.getBudgetLocations();
            break;
         case 3:
            ReportDefinitionCalculationRunDetails var37 = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionCalculationRunDetails(reportDef.getVisId());
            modelId = var37.getModelId();
            xmlFormId = var37.getXMLFormId();
            deploymentId = var37.getDeploymentId();
            contextDimIndexes = var37.getContextDimenionIndexes();
            var35 = var37.getTemplate();
            location = var37.getBudgetLocations();
            break;
         case 4:
            ReportDefinitionCalculationSummaryRunDetails batchCount = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionSumaryCalculationRunDetails(reportDef.getVisId());
            modelId = batchCount.getModelId();
            xmlFormId = batchCount.getXMLFormId();
            deploymentId = batchCount.getDeploymentId();
            contextDimIndexes = batchCount.getContextDimenionIndexes();
            var35 = batchCount.getTemplate();
            location = batchCount.getBudgetLocations();
            columnSettings = batchCount.getColumnSettings();
            break;
         default:
            throw new UnsupportedOperationException("Unsupported report type");
         }

         int var33 = 0;
         int size = location.size();
         int maxJobs = SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "SYS: Report Pack max jobs", 2);
         int batchSize = size / maxJobs;
         if(batchSize * maxJobs < size) {
            ++batchSize;
         }

         ArrayList respArea = new ArrayList();
         Iterator locationIter = location.iterator();

         while(locationIter.hasNext()) {
            ++var33;
            respArea.add(locationIter.next());
            if(var33 == batchSize || !locationIter.hasNext()) {
               PackLineTaskRequest request = new PackLineTaskRequest();
               request.setReportType(reportType);
               request.setModelId(modelId);
               request.setFormId(xmlFormId);
               request.setDepth(depth);
               request.setDataType(dataType);
               request.setSelectionCriteria(selection);
               request.setRespArea(respArea);
               request.setGroup(group);
               request.setOption(options);
               request.setDistribution(distribution);
               request.setTemplate(var35);
               request.setContextDims(contextDimIndexes);
               request.setDeploymentId(deploymentId);
               request.setColumnSettings(columnSettings);
               e.add(Integer.valueOf(TaskMessageFactory.issueNewTask(new InitialContext(), false, request, intUserId, taskId)));
               var33 = 0;
               respArea = new ArrayList();
            }
         }

         ArrayList var34 = e;
         return var34;
      } catch (Exception var30) {
         var30.printStackTrace();
         throw new EJBException(var30);
      } finally {
         this.setUserId(0);
      }
   }

   public ReportPackProjection getReportPackProjection(int intUserId, Object key) {
      ReportPackProjection result = new ReportPackProjection();

      try {
         this.setUserId(intUserId);
         ReportPackAccessor e = new ReportPackAccessor(new InitialContext());
         ReportPackEVO pack = e.getDetails(key, "<0>");
         result.setIdentifier(pack.getVisId());
         result.setDescription(pack.getDescription());
         ArrayList resultData = new ArrayList();
         EntityList packList = this.getCPConnection().getListHelper().getReportDefDistList(pack.getVisId());
         int packSize = packList.getNumRows();
         String reportVisId = null;
         List locations = null;
         String modelId = null;

         for(int i = 0; i < packSize; ++i) {
            EntityRef def = (EntityRef)packList.getValueAt(i, "ReportDefinition");
            EntityRef dis = (EntityRef)packList.getValueAt(i, "Distribution");
            ReportDefinition reportDef = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionEditorSession(def.getNarrative()).getReportDefinitionEditor().getReportDefinition();
            int reportType = reportDef.getReportTypeId();
            reportVisId = reportDef.getVisId();
            switch(reportType) {
            case 1:
               ReportDefinitionFormRunDetails var34 = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionFormRunDetails(reportDef.getVisId());
               locations = var34.getBudgetLocations();
               modelId = var34.getModelId();
               break;
            case 2:
               ReportDefinitionMappingRunDetails var35 = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionMappingRunDetails(reportDef.getVisId());
               modelId = var35.getModelId();
               locations = var35.getBudgetLocations();
               break;
            case 3:
               ReportDefinitionCalculationRunDetails var36 = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionCalculationRunDetails(reportDef.getVisId());
               modelId = var36.getModelId();
               locations = var36.getBudgetLocations();
               break;
            case 4:
               ReportDefinitionCalculationSummaryRunDetails detailCount = this.getCPConnection().getReportDefinitionsProcess().getReportDefinitionSumaryCalculationRunDetails(reportDef.getVisId());
               modelId = detailCount.getModelId();
               locations = detailCount.getBudgetLocations();
               break;
            default:
               locations = Collections.EMPTY_LIST;
               modelId = "1";
            }

            boolean var37 = false;
            Iterator i$ = locations.iterator();

            while(i$.hasNext()) {
               Object location = i$.next();
               int locationId = 0;
               StructureElementPK rowData;
               if(location instanceof Integer) {
                  locationId = ((Integer)location).intValue();
               } else if(location instanceof EntityList) {
                  EntityList dd = (EntityList)location;
                  EntityRef budgetLocation = (EntityRef)dd.getValueAt(0, "StructureElement");
                  rowData = (StructureElementPK)budgetLocation.getPrimaryKey();
                  locationId = rowData.getStructureElementId();
               }

               DistributionDetails var39 = this.getCPConnection().getDistributionsProcess().getDistributionDetailList(dis.getNarrative(), Integer.parseInt(modelId), locationId, dis);
               String var38 = var39.getBudgetLocation();
               rowData = null;
               ReportPackProjectionData var40 = new ReportPackProjectionData(reportVisId, var38);
               Iterator i$1 = var39.getDistributionList().iterator();

               while(i$1.hasNext()) {
                  Object ddObject = i$1.next();
                  DistributionDetail dDetail = (DistributionDetail)ddObject;
                  Iterator i$2 = dDetail.getUsers().iterator();

                  while(i$2.hasNext()) {
                     Object usr = i$2.next();
                     var40.addUser(usr.toString());
                  }
               }

               resultData.add(var40);
            }
         }

         Collections.sort(resultData);
         result.setData(resultData);
      } catch (Exception var32) {
         var32.printStackTrace();
      } finally {
         this.setUserId(0);
      }

      return result;
   }
}
