// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.ValueDescriptionDTO;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.report.ReportColumnMapDTO;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionCalculationSummaryRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionFormRunDetails;
import com.cedar.cp.api.report.definition.ReportDefinitionMappingRunDetails;
import com.cedar.cp.api.report.definition.WebReportOption;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.ReportChildrenForParentToRelativeDepthELO;
import com.cedar.cp.dto.dimension.ReportLeavesForParentELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelDetailsWebELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.cc.AllCcDeploymentsELO;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.dto.report.definition.ReportDefinitionCalculationRunDetailsImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionCalculationSummaryRunDetailsImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionCSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionSSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionForVisIdELO;
import com.cedar.cp.dto.report.definition.ReportDefinitionFormRunDetailsImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionMappingRunDetailsImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.dto.report.mappingtemplate.AllReportMappingTemplatesELO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.dto.report.pack.CheckReportDefELO;
import com.cedar.cp.dto.report.pack.PackLineTaskRequest;
import com.cedar.cp.dto.report.pack.PackTaskRequest;
import com.cedar.cp.dto.report.template.AllReportTemplatesELO;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.dto.report.type.param.AllReportTypeParamsforTypeELO;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.ejb.api.model.cc.CcDeploymentEditorSessionServer;
import com.cedar.cp.ejb.api.report.mappingtemplate.ReportMappingTemplateEditorSessionServer;
import com.cedar.cp.ejb.api.report.template.ReportTemplateEditorSessionServer;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefCalculatorEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefFormEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefMappedExcelEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefSummaryCalcEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionAccessor;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionEVO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLinkDAO;
import com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamDAO;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.ValueMapping;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportDefinitionEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1><2><3>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0><1><2><3>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0><1><2><3>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1><2><3>";
   private ListSessionServer mListSessionServer;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ReportDefinitionAccessor mReportDefinitionAccessor;
   private ReportDefinitionEditorSessionSSO mSSO;
   private ReportDefinitionPK mThisTableKey;
   private ReportDefinitionEVO mReportDefinitionEVO;


   public ReportDefinitionEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ReportDefinitionPK)paramKey;

      ReportDefinitionEditorSessionSSO e;
      try {
         this.mReportDefinitionEVO = this.getReportDefinitionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3>");
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
      this.mSSO = new ReportDefinitionEditorSessionSSO();
      ReportDefinitionImpl editorData = this.buildReportDefinitionEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ReportDefinitionImpl editorData) throws Exception {}

   private ReportDefinitionImpl buildReportDefinitionEditData(Object thisKey) throws Exception {
      ReportDefinitionImpl editorData = new ReportDefinitionImpl(thisKey);
      editorData.setVisId(this.mReportDefinitionEVO.getVisId());
      editorData.setDescription(this.mReportDefinitionEVO.getDescription());
      editorData.setReportTypeId(this.mReportDefinitionEVO.getReportTypeId());
      editorData.setIsPublic(this.mReportDefinitionEVO.getIsPublic());
      editorData.setVersionNum(this.mReportDefinitionEVO.getVersionNum());
      this.completeReportDefinitionEditData(editorData);
      return editorData;
   }

   private void completeReportDefinitionEditData(ReportDefinitionImpl editorData) throws Exception {
      int reportTypeId = this.mReportDefinitionEVO.getReportTypeId();
      switch(reportTypeId) {
      case 1:
         ReportDefFormEVO formEVO = this.mReportDefinitionEVO.getReportFormItem();
         this.populateFormEVOToParams(formEVO, editorData);
         break;
      case 2:
         ReportDefMappedExcelEVO mappedEVO = this.mReportDefinitionEVO.getReportMappedExcelItem();
         this.populateMappedExcelEVOToParams(mappedEVO, editorData);
         break;
      case 3:
         ReportDefCalculatorEVO calculatorEVO = this.mReportDefinitionEVO.getReportCalculatorItem();
         this.populateCalculatorEVOToParams(calculatorEVO, editorData);
         break;
      case 4:
         ReportDefSummaryCalcEVO summaryCalcEVO = this.mReportDefinitionEVO.getSummaryCalcReportItem();
         this.populateSummaryCalcEVOToParams(summaryCalcEVO, editorData);
         break;
      default:
         throw new Exception("No report details for type:" + reportTypeId);
      }

   }

   public ReportDefinitionEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ReportDefinitionEditorSessionSSO var4;
      try {
         this.mSSO = new ReportDefinitionEditorSessionSSO();
         ReportDefinitionImpl e = new ReportDefinitionImpl((Object)null);
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

   private void completeGetNewItemData(ReportDefinitionImpl editorData) throws Exception {}

   public ReportDefinitionPK insert(ReportDefinitionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportDefinitionImpl editorData = cso.getEditorData();

      ReportDefinitionPK e;
      try {
         this.mReportDefinitionEVO = new ReportDefinitionEVO();
         this.mReportDefinitionEVO.setVisId(editorData.getVisId());
         this.mReportDefinitionEVO.setDescription(editorData.getDescription());
         this.mReportDefinitionEVO.setReportTypeId(editorData.getReportTypeId());
         this.mReportDefinitionEVO.setIsPublic(editorData.isIsPublic());
         this.updateReportDefinitionRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mReportDefinitionEVO = this.getReportDefinitionAccessor().create(this.mReportDefinitionEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ReportDefinition", this.mReportDefinitionEVO.getPK(), 1);
         e = this.mReportDefinitionEVO.getPK();
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

   private void updateReportDefinitionRelationships(ReportDefinitionImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ReportDefinitionImpl editorData) throws Exception {
      if(editorData != null) {
         int reportDefinitionType = editorData.getReportTypeId();
         switch(reportDefinitionType) {
         case 1:
            Collection forms = this.mReportDefinitionEVO.getReportForm();
            ReportDefFormEVO mappedExcels1;
            if(forms != null && forms.size() > 0) {
               mappedExcels1 = this.mReportDefinitionEVO.getReportFormItem();
               this.populateParamsToFormEVO(mappedExcels1, editorData);
            } else {
               mappedExcels1 = new ReportDefFormEVO();
               this.populateParamsToFormEVO(mappedExcels1, editorData);
               this.mReportDefinitionEVO.addReportFormItem(mappedExcels1);
            }
            break;
         case 2:
            Collection mappedExcels = this.mReportDefinitionEVO.getReportMappedExcel();
            ReportDefMappedExcelEVO calculatorEVOs1;
            if(mappedExcels != null && mappedExcels.size() > 0) {
               calculatorEVOs1 = this.mReportDefinitionEVO.getReportMappedExcelItem();
               this.populateParamsToMappedExcelEVO(calculatorEVOs1, editorData);
            } else {
               calculatorEVOs1 = new ReportDefMappedExcelEVO();
               this.populateParamsToMappedExcelEVO(calculatorEVOs1, editorData);
               this.mReportDefinitionEVO.addReportMappedExcelItem(calculatorEVOs1);
            }
            break;
         case 3:
            Collection calculatorEVOs = this.mReportDefinitionEVO.getReportCalculator();
            ReportDefCalculatorEVO summaryCalcEVOs1;
            if(calculatorEVOs != null && calculatorEVOs.size() > 0) {
               summaryCalcEVOs1 = this.mReportDefinitionEVO.getReportCalculatorItem();
               this.populateParamsToCalculatorEVO(summaryCalcEVOs1, editorData);
            } else {
               summaryCalcEVOs1 = new ReportDefCalculatorEVO();
               this.populateParamsToCalculatorEVO(summaryCalcEVOs1, editorData);
               this.mReportDefinitionEVO.addReportCalculatorItem(summaryCalcEVOs1);
            }
            break;
         case 4:
            Collection summaryCalcEVOs = this.mReportDefinitionEVO.getSummaryCalcReport();
            ReportDefSummaryCalcEVO newEVO;
            if(summaryCalcEVOs != null && summaryCalcEVOs.size() > 0) {
               newEVO = this.mReportDefinitionEVO.getSummaryCalcReportItem();
               this.populateParamsToSummaryCalcEVO(newEVO, editorData);
            } else {
               newEVO = new ReportDefSummaryCalcEVO();
               this.populateParamsToSummaryCalcEVO(newEVO, editorData);
               this.mReportDefinitionEVO.addSummaryCalcReportItem(newEVO);
            }
            break;
         default:
            throw new Exception("No Report Detail for Report Type:" + reportDefinitionType);
         }
      }

   }

   private void insertIntoAdditionalTables(ReportDefinitionImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ReportDefinitionPK copy(ReportDefinitionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportDefinitionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportDefinitionPK)editorData.getPrimaryKey();

      ReportDefinitionPK var5;
      try {
         ReportDefinitionEVO e = this.getReportDefinitionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3>");
         this.mReportDefinitionEVO = e.deepClone();
         this.mReportDefinitionEVO.setVisId(editorData.getVisId());
         this.mReportDefinitionEVO.setDescription(editorData.getDescription());
         this.mReportDefinitionEVO.setReportTypeId(editorData.getReportTypeId());
         this.mReportDefinitionEVO.setIsPublic(editorData.isIsPublic());
         this.mReportDefinitionEVO.setVersionNum(0);
         this.updateReportDefinitionRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mReportDefinitionEVO.prepareForInsert();
         this.mReportDefinitionEVO = this.getReportDefinitionAccessor().create(this.mReportDefinitionEVO);
         this.mThisTableKey = this.mReportDefinitionEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ReportDefinition", this.mReportDefinitionEVO.getPK(), 1);
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

   private void completeCopySetup(ReportDefinitionImpl editorData) throws Exception {}

   public void update(ReportDefinitionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportDefinitionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportDefinitionPK)editorData.getPrimaryKey();

      try {
         this.mReportDefinitionEVO = this.getReportDefinitionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3>");
         this.preValidateUpdate(editorData);
         this.mReportDefinitionEVO.setVisId(editorData.getVisId());
         this.mReportDefinitionEVO.setDescription(editorData.getDescription());
         this.mReportDefinitionEVO.setReportTypeId(editorData.getReportTypeId());
         this.mReportDefinitionEVO.setIsPublic(editorData.isIsPublic());
         if(editorData.getVersionNum() != this.mReportDefinitionEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mReportDefinitionEVO.getVersionNum());
         }

         this.updateReportDefinitionRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getReportDefinitionAccessor().setDetails(this.mReportDefinitionEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ReportDefinition", this.mReportDefinitionEVO.getPK(), 3);
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

   private void preValidateUpdate(ReportDefinitionImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ReportDefinitionImpl editorData) throws Exception {
      this.completeInsertSetup(editorData);
   }

   private void updateAdditionalTables(ReportDefinitionImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ReportDefinitionPK)paramKey;

      try {
         this.mReportDefinitionEVO = this.getReportDefinitionAccessor().getDetails(this.mThisTableKey, "<0><1><2><3>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mReportDefinitionAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ReportDefinition", this.mThisTableKey, 2);
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
      ReportPackLinkDAO rplDAO = new ReportPackLinkDAO();
      CheckReportDefELO eList = rplDAO.getCheckReportDef(this.mReportDefinitionEVO.getReportDefinitionId());
      if(eList.getNumRows() > 0) {
         throw new ValidationException("Report Definition is in use by report pack");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ReportDefinitionAccessor getReportDefinitionAccessor() throws Exception {
      if(this.mReportDefinitionAccessor == null) {
         this.mReportDefinitionAccessor = new ReportDefinitionAccessor(this.getInitialContext());
      }

      return this.mReportDefinitionAccessor;
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

   private ListSessionServer getListServer() throws Exception {
      if(this.mListSessionServer == null) {
         this.mListSessionServer = new ListSessionServer(this.getInitialContext(), true);
      }

      return this.mListSessionServer;
   }

   private EntityRef getRefFromKey(Object pk) throws Exception {
      EntityRef ref = this.getListServer().getEntityRef(pk);
      return ref;
   }

   private String getDescriptionForRef(Object key) throws Exception {
      String result = "";
      Object elo = null;
      String column = "Description";
      if(key instanceof ModelPK) {
         elo = this.getListServer().getModelDetailsWeb(((ModelPK)key).getModelId());
      } else if(key instanceof XmlFormPK) {
         elo = this.getListServer().getXMLFormDefinition(((XmlFormPK)key).getXmlFormId());
      } else if(key instanceof StructureElementPK) {
         elo = this.getListServer().getStructureElementForIds(((StructureElementPK)key).getStructureId(), ((StructureElementPK)key).getStructureElementId());
      } else if(key instanceof ReportTemplatePK) {
         AllReportTemplatesELO elo1 = this.getListServer().getAllReportTemplates();
         elo = elo1.getRowData(this.getRefFromKey(key), "ReportTemplate");
      } else if(key instanceof CcDeploymentPK) {
         AllCcDeploymentsELO elo2 = this.getListServer().getAllCcDeployments();
         elo = elo2.getRowData(this.getRefFromKey(key), "CcDeployment");
      } else if(key instanceof ReportMappingTemplatePK) {
         AllReportMappingTemplatesELO elo3 = this.getListServer().getAllReportMappingTemplates();
         elo = elo3.getRowData(this.getRefFromKey(key), "ReportMappingTemplate");
      }

      if(elo != null && ((EntityList)elo).getNumRows() == 1) {
         result = ((EntityList)elo).getValueAt(0, column).toString();
      }

      return result;
   }

   public ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails() throws ValidationException, EJBException {
      return this.getReportDefinitionFormRunDetails((Integer)null, (Integer)null);
   }

   public ReportDefinitionFormRunDetails getReportDefinitionFormRunDetails(Integer pDepth, Integer pStructureElementId) throws ValidationException, EJBException {
      ReportDefinitionFormRunDetailsImpl formReport = new ReportDefinitionFormRunDetailsImpl();
      if(this.mReportDefinitionEVO.getReportTypeId() != 1) {
         throw new ValidationException("Can only get Report type DataEntryForm");
      } else {
         try {
            Collection ex = this.mReportDefinitionEVO.getReportForm();
            if(ex != null && ex.size() > 0) {
               ReportDefFormEVO reportDefFormEVO = this.mReportDefinitionEVO.getReportFormItem();
               int budgetLocationStructureId = 0;
               int budgetLocationStructureElementId = 0;
               HashMap selection = new HashMap();
               int modelId = reportDefFormEVO.getModelId();
               formReport.setModelId(String.valueOf(modelId));
               int xmlFormId = reportDefFormEVO.getFormId();
               formReport.setXMLFormId(xmlFormId);
               DataTypePK dataTypePK = new DataTypePK((short)reportDefFormEVO.getDataTypeId());
               EntityRef dataTypeRef = this.getRefFromKey(dataTypePK);
               formReport.setDataType(dataTypeRef.getNarrative());
               boolean maxStructure = true;

               int reportDepth;
               for(int reportTemplatePk = 0; reportTemplatePk < 9; ++reportTemplatePk) {
                  if(reportTemplatePk == 0) {
                     budgetLocationStructureId = reportDefFormEVO.getStructureId0();
                     if(pStructureElementId == null) {
                        budgetLocationStructureElementId = reportDefFormEVO.getStructureElementId0();
                     } else {
                        budgetLocationStructureElementId = pStructureElementId.intValue();
                     }
                  } else {
                     Object server = reportDefFormEVO.getClass().getMethod("getStructureElementId" + reportTemplatePk, new Class[0]).invoke(reportDefFormEVO, (Object[])null);
                     reportDepth = Integer.valueOf(server.toString()).intValue();
                     if(reportDepth <= 0) {
                        break;
                     }

                     selection.put(Integer.valueOf(reportTemplatePk), Integer.valueOf(reportDepth));
                  }
               }

               formReport.setSelection(selection);
               formReport.setDepth(reportDefFormEVO.getAutoExpandDepth());
               ReportTemplatePK var24 = new ReportTemplatePK(reportDefFormEVO.getReportTemplateId());
               ReportTemplateEditorSessionServer var23 = new ReportTemplateEditorSessionServer(this.getInitialContext(), true);
               formReport.setTemplate(var23.getItemData(var24).getEditorData().getDocument());
               boolean var25 = false;
               if(pDepth == null) {
                  reportDepth = reportDefFormEVO.getReportDepth();
               } else {
                  reportDepth = pDepth.intValue();
               }

               ArrayList budgetLocations = new ArrayList();
               StructureElementDAO strucDAO = new StructureElementDAO();
               Object childList = null;
               if(reportDepth == -1) {
                  childList = strucDAO.getReportLeavesForParent(budgetLocationStructureId, budgetLocationStructureId, budgetLocationStructureElementId, budgetLocationStructureId, budgetLocationStructureElementId);
               } else {
                  childList = strucDAO.getReportChildrenForParentToRelativeDepth(budgetLocationStructureId, budgetLocationStructureId, budgetLocationStructureElementId, budgetLocationStructureId, budgetLocationStructureElementId, budgetLocationStructureId, budgetLocationStructureElementId, reportDepth);
               }

               int size = ((EntityList)childList).getNumRows();

               for(int i = 0; i < size; ++i) {
                  budgetLocations.add(((EntityList)childList).getRowData(i));
               }

               formReport.setBudgetLocations(budgetLocations);
            }

            return formReport;
         } catch (Exception var22) {
            var22.printStackTrace();
            throw new ValidationException("Error getting def: " + var22.getMessage());
         }
      }
   }

   public ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails() throws ValidationException, EJBException {
      return this.getReportDefinitionMappingRunDetails((Integer)null, (Integer)null);
   }

   public ReportDefinitionMappingRunDetails getReportDefinitionMappingRunDetails(Integer pDepth, Integer pStructureElementId) throws ValidationException, EJBException {
      ReportDefinitionMappingRunDetailsImpl mappingReport = new ReportDefinitionMappingRunDetailsImpl();
      if(this.mReportDefinitionEVO.getReportTypeId() != 2) {
         throw new ValidationException("Can only get Report type Excel Mapping");
      } else {
         try {
            Collection e = this.mReportDefinitionEVO.getReportMappedExcel();
            if(e != null && e.size() > 0) {
               Iterator iter = e.iterator();

               while(iter.hasNext()) {
                  ReportDefMappedExcelEVO mappedExcelEVO = (ReportDefMappedExcelEVO)iter.next();
                  mappingReport.setModelId(String.valueOf(mappedExcelEVO.getModelId()));
                  int structureId = mappedExcelEVO.getStructureId();
                  boolean structureElementId = false;
                  int var18;
                  if(pStructureElementId == null) {
                     var18 = mappedExcelEVO.getStructureElementId();
                  } else {
                     var18 = pStructureElementId.intValue();
                  }

                  boolean reportDepth = false;
                  int var19;
                  if(pDepth == null) {
                     var19 = mappedExcelEVO.getReportDepth();
                  } else {
                     var19 = pDepth.intValue();
                  }

                  ArrayList budgetLocations = new ArrayList();
                  StructureElementDAO strucDAO = new StructureElementDAO();
                  Object childList = null;
                  if(var19 == -1) {
                     childList = strucDAO.getReportLeavesForParent(structureId, structureId, var18, structureId, var18);
                  } else {
                     childList = strucDAO.getReportChildrenForParentToRelativeDepth(structureId, structureId, var18, structureId, var18, structureId, var18, var19);
                  }

                  int size = ((EntityList)childList).getNumRows();

                  for(int templatePK = 0; templatePK < size; ++templatePK) {
                     budgetLocations.add(((EntityList)childList).getRowData(templatePK));
                  }

                  mappingReport.setBudgetLocations(budgetLocations);
                  ReportMappingTemplatePK var20 = new ReportMappingTemplatePK(mappedExcelEVO.getReportTemplateId());
                  ReportMappingTemplateEditorSessionServer server = new ReportMappingTemplateEditorSessionServer(this.getInitialContext(), true);
                  byte[] templateBytes = server.getItemData(var20).getEditorData().getDocument();
                  mappingReport.setTemplate(templateBytes);
               }
            }

            return mappingReport;
         } catch (Exception var17) {
            throw new ValidationException("Error getting def: " + var17.getMessage());
         }
      }
   }

   public ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails() throws ValidationException, EJBException {
      return this.getReportDefinitionCalculationRunDetails((Integer)null);
   }

   public ReportDefinitionCalculationRunDetails getReportDefinitionCalculationRunDetails(Integer pStructureElementId) throws ValidationException, EJBException {
      ReportDefinitionCalculationRunDetailsImpl calculationReport = new ReportDefinitionCalculationRunDetailsImpl();
      if(this.mReportDefinitionEVO.getReportTypeId() != 3) {
         throw new ValidationException("Can only get Report type CalculationDeployment");
      } else {
         try {
            Collection ex = this.mReportDefinitionEVO.getReportCalculator();
            if(ex != null && ex.size() > 0) {
               ReportDefCalculatorEVO calculatorEVO = this.mReportDefinitionEVO.getReportCalculatorItem();
               String modelId = String.valueOf(calculatorEVO.getModelId());
               int ccDeploymentId = calculatorEVO.getCcDeploymentId();
               calculationReport.setModelId(modelId);
               calculationReport.setDeploymentId(ccDeploymentId);
               CcDeploymentPK deploymentPK = new CcDeploymentPK(ccDeploymentId);
               EntityRef deploymentRef = this.getRefFromKey(deploymentPK);
               CcDeploymentEditorSessionServer deploymentServer = new CcDeploymentEditorSessionServer(this.getInitialContext(), true);
               CcDeploymentEditorSessionSSO deploymentSSO = deploymentServer.getItemData(deploymentRef.getPrimaryKey());
               CcDeploymentImpl ccDeployment = deploymentSSO.getEditorData();
               int xmlFormId = ccDeployment.getXmlformId();
               calculationReport.setXMLFormId(xmlFormId);
               Integer[] contextArray = ccDeployment.getDimContextArray();
               ArrayList contextDims = new ArrayList();
               ModelDAO modelDao = new ModelDAO();
               ModelDimensionsELO dimsList = modelDao.getModelDimensions(calculatorEVO.getModelId());
               int calIndex = dimsList.getNumRows() - 1;
               contextDims.add(Integer.valueOf(calIndex));

               int reportTemplateId;
               for(reportTemplateId = 0; reportTemplateId < contextArray.length; ++reportTemplateId) {
                  if(contextArray[reportTemplateId] != null && contextArray[reportTemplateId].intValue() == 0) {
                     contextDims.add(Integer.valueOf(reportTemplateId));
                  }
               }

               calculationReport.setContextDimensionIndexes(contextDims);
               reportTemplateId = calculatorEVO.getReportTemplateId();
               ReportTemplatePK templatePK = new ReportTemplatePK(reportTemplateId);
               EntityRef template = this.getRefFromKey(templatePK);
               ReportTemplateEditorSessionServer server = new ReportTemplateEditorSessionServer(this.getInitialContext(), true);
               calculationReport.setTemplate(server.getItemData(template.getPrimaryKey()).getEditorData().getDocument());
               StructureElementDAO strucDAO = new StructureElementDAO();
               int structureId = calculatorEVO.getStructureId();
               boolean structureElementId = false;
               int var30;
               if(pStructureElementId == null) {
                  var30 = calculatorEVO.getStructureElementId();
               } else {
                  var30 = pStructureElementId.intValue();
               }

               ReportLeavesForParentELO childList = strucDAO.getReportLeavesForParent(structureId, structureId, var30, structureId, var30);
               ArrayList budgetLocations = new ArrayList();
               int size = childList.getNumRows();

               for(int i = 0; i < size; ++i) {
                  budgetLocations.add(childList.getRowData(i));
               }

               calculationReport.setBudgetLocations(budgetLocations);
               return calculationReport;
            } else {
               throw new ValidationException("No Report Details for ReportId:" + this.mReportDefinitionEVO.getReportDefinitionId());
            }
         } catch (Exception var29) {
            var29.printStackTrace();
            throw new ValidationException("Error getting def: " + var29.getMessage());
         }
      }
   }

   public ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails() throws ValidationException, EJBException {
      return this.getReportDefinitionSumaryCalculationRunDetails((Integer)null);
   }

   public ReportDefinitionCalculationSummaryRunDetails getReportDefinitionSumaryCalculationRunDetails(Integer pStructureElementId) throws ValidationException, EJBException {
      ReportDefinitionCalculationSummaryRunDetailsImpl summaryCalcReport = new ReportDefinitionCalculationSummaryRunDetailsImpl();
      if(this.mReportDefinitionEVO.getReportTypeId() != 4) {
         throw new ValidationException("Can only get Report type CalculationDeployment");
      } else {
         try {
            Collection e = this.mReportDefinitionEVO.getSummaryCalcReport();
            if(e != null && e.size() > 0) {
               ReportDefSummaryCalcEVO summaryCalcEVO = this.mReportDefinitionEVO.getSummaryCalcReportItem();
               boolean budgetLocationStructureId = false;
               boolean budgetLocationStructureElementId = false;
               boolean decendDepth = false;
               int modelId = summaryCalcEVO.getModelId();
               summaryCalcReport.setModelId(String.valueOf(modelId));
               int var31 = summaryCalcEVO.getStructureId();
               budgetLocationStructureElementId = false;
               int var32;
               if(pStructureElementId == null) {
                  var32 = summaryCalcEVO.getStructureElementId();
               } else {
                  var32 = pStructureElementId.intValue();
               }

               int deploymentId = summaryCalcEVO.getCcDeploymentId();
               summaryCalcReport.setDeploymentId(deploymentId);
               CcDeploymentPK deployementPK = new CcDeploymentPK(deploymentId);
               EntityRef deploymentRef = this.getRefFromKey(deployementPK);
               CcDeploymentEditorSessionServer deploymentServer = new CcDeploymentEditorSessionServer(this.getInitialContext(), true);
               CcDeploymentEditorSessionSSO deploymentSSO = deploymentServer.getItemData(deploymentRef.getPrimaryKey());
               CcDeploymentImpl ccDeployment = deploymentSSO.getEditorData();
               int xmlFormId = ccDeployment.getXmlformId();
               summaryCalcReport.setXMLFormId(xmlFormId);
               Integer[] contextArray = ccDeployment.getDimContextArray();
               ArrayList contextDims = new ArrayList();
               ModelDAO modelDao = new ModelDAO();
               ModelDimensionsELO dimsList = modelDao.getModelDimensions(modelId);
               int calIndex = dimsList.getNumRows() - 1;
               contextDims.add(Integer.valueOf(calIndex));

               for(int templatePK = 0; templatePK < contextArray.length; ++templatePK) {
                  if(contextArray[templatePK] != null && contextArray[templatePK].intValue() == 0) {
                     contextDims.add(Integer.valueOf(templatePK));
                  }
               }

               summaryCalcReport.setContextDimensionIndexes(contextDims);
               ReportTemplatePK var34 = new ReportTemplatePK(summaryCalcEVO.getReportTemplateId());
               EntityRef template = this.getRefFromKey(var34);
               ReportTemplateEditorSessionServer server = new ReportTemplateEditorSessionServer(this.getInitialContext(), true);
               summaryCalcReport.setTemplate(server.getItemData(template.getPrimaryKey()).getEditorData().getDocument());
               String columnMapJSONStr = summaryCalcEVO.getColumnMap();
               summaryCalcReport.setColumnSettings(columnMapJSONStr);
               int var33 = summaryCalcEVO.getHierarchyDepth();
               ArrayList budgetLocations = new ArrayList();
               StructureElementDAO strucDAO = new StructureElementDAO();
               ReportChildrenForParentToRelativeDepthELO locationList = strucDAO.getReportChildrenForParentToRelativeDepth(var31, var31, var32, var31, var32, var31, var32, var33);
               int size = locationList.getNumRows();

               for(int i = 0; i < size; ++i) {
                  budgetLocations.add(locationList.getRowData(i));
               }

               summaryCalcReport.setBudgetLocations(budgetLocations);
               return summaryCalcReport;
            } else {
               throw new ValidationException("No Report Details for ReportId:" + this.mReportDefinitionEVO.getReportDefinitionId());
            }
         } catch (Exception var30) {
            throw new ValidationException("Error getting def: " + var30.getMessage());
         }
      }
   }

   private Integer parseEntityKey(String key) {
      return this.parseEntityKey(key, true);
   }

   private Integer parseEntityKey(String key, boolean forcePK) {
      boolean start = false;
      int start1;
      if(key.lastIndexOf("|") > 0) {
         start1 = key.lastIndexOf("|") + 1;
         key = key.substring(start1, key.length());
      }

      if(key.lastIndexOf(",") > 0) {
         start1 = key.lastIndexOf(",") + 1;
         if(forcePK) {
            key = key.substring(start1, key.length());
         } else {
            key = key.substring(0, start1 - 1);
         }
      }

      return new Integer(key);
   }

   public void populateParamsToCalculatorEVO(ReportDefCalculatorEVO calculatorEVO, ReportDefinitionImpl editorData) throws Exception {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      AllReportTypeParamsforTypeELO eList = dao.getAllReportTypeParamsforType(editorData.getReportTypeId());
      int size = eList.getNumRows();
      List params = editorData.getReportParams();
      if(params.size() != size) {
         throw new Exception("Wrong param values setting for Report Type:" + editorData.getReportTypeId());
      } else {
         calculatorEVO.setReportDefinitionId(this.mReportDefinitionEVO.getReportDefinitionId());
         Iterator iterator = params.iterator();

         while(iterator.hasNext()) {
            List row = (List)iterator.next();
            EntityList metaData = (EntityList)row.get(0);
            int seq = ((Integer)metaData.getValueAt(0, "Seq")).intValue();
            EntityRef reportTemplateEntityRef;
            int templateId;
            switch(seq) {
            case 0:
               reportTemplateEntityRef = this.getRefFromRow(row.get(1));
               templateId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), false).intValue();
               calculatorEVO.setModelId(templateId);
               break;
            case 1:
               reportTemplateEntityRef = this.getRefFromRow(row.get(1));
               templateId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), false).intValue();
               int structureElementId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), true).intValue();
               calculatorEVO.setStructureId(templateId);
               calculatorEVO.setStructureElementId(structureElementId);
               break;
            case 2:
               reportTemplateEntityRef = this.getRefFromRow(row.get(1));
               templateId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), false).intValue();
               calculatorEVO.setCcDeploymentId(templateId);
               break;
            case 3:
               reportTemplateEntityRef = this.getRefFromRow(row.get(1));
               templateId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), false).intValue();
               calculatorEVO.setReportTemplateId(templateId);
            }
         }

      }
   }

   public void populateParamsToSummaryCalcEVO(ReportDefSummaryCalcEVO summaryCalcEVO, ReportDefinitionImpl editorData) throws Exception {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      AllReportTypeParamsforTypeELO eList = dao.getAllReportTypeParamsforType(editorData.getReportTypeId());
      int size = eList.getNumRows();
      List params = editorData.getReportParams();
      if(params.size() != size) {
         throw new Exception("Wrong param values setting for Report Type:" + editorData.getReportTypeId());
      } else {
         summaryCalcEVO.setReportDefinitionId(this.mReportDefinitionEVO.getReportDefinitionId());
         Iterator iterator = params.iterator();

         while(iterator.hasNext()) {
            List row = (List)iterator.next();
            EntityList metaData = (EntityList)row.get(0);
            int seq = ((Integer)metaData.getValueAt(0, "Seq")).intValue();
            int jsonStr1;
            EntityRef map2;
            switch(seq) {
            case 0:
               map2 = this.getRefFromRow(row.get(1));
               jsonStr1 = this.parseEntityKey(map2.getTokenizedKey(), false).intValue();
               summaryCalcEVO.setModelId(jsonStr1);
               break;
            case 1:
               map2 = this.getRefFromRow(row.get(1));
               jsonStr1 = this.parseEntityKey(map2.getTokenizedKey(), false).intValue();
               int structureElementId = this.parseEntityKey(map2.getTokenizedKey(), true).intValue();
               summaryCalcEVO.setStructureId(jsonStr1);
               summaryCalcEVO.setStructureElementId(structureElementId);
               break;
            case 2:
               map2 = this.getRefFromRow(row.get(1));
               jsonStr1 = this.parseEntityKey(map2.getTokenizedKey(), false).intValue();
               summaryCalcEVO.setCcDeploymentId(jsonStr1);
               break;
            case 3:
               map2 = this.getRefFromRow(row.get(1));
               jsonStr1 = this.parseEntityKey(map2.getTokenizedKey(), false).intValue();
               summaryCalcEVO.setReportTemplateId(jsonStr1);
               break;
            case 4:
               String map1 = String.valueOf(row.get(1));
               map1 = String.valueOf(this.buildReportDefValueMapping().getValue(map1));
               summaryCalcEVO.setHierarchyDepth(Integer.valueOf(map1).intValue());
               break;
            case 5:
               Map map = (Map)row.get(1);
               String jsonStr = this.buildColumnMapJSONStr(map);
               summaryCalcEVO.setColumnMap(jsonStr);
            }
         }

      }
   }

   private String buildColumnMapJSONStr(Map<Integer, ReportColumnMapDTO> map) {
      if(map != null && map.size() > 0) {
         JSONArray jsonArray = new JSONArray();
         Iterator iterator = map.keySet().iterator();

         while(iterator.hasNext()) {
            int colIndx = ((Integer)iterator.next()).intValue();
            ReportColumnMapDTO reportMapDTO = (ReportColumnMapDTO)map.get(Integer.valueOf(colIndx));
            JSONObject jsonObj = new JSONObject();
            try {
				jsonObj.put("col", reportMapDTO.getColumnId());
	            jsonObj.put("mode", reportMapDTO.getFunctionId());
	            jsonArray.put(jsonObj);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }

         return jsonArray.toString();
      } else {
         return "";
      }
   }

   public void populateParamsToFormEVO(ReportDefFormEVO formEVO, ReportDefinitionImpl editorData) throws Exception {
      List params = editorData.getReportParams();
      formEVO.setReportDefinitionId(this.mReportDefinitionEVO.getReportDefinitionId());
      Iterator iterator = params.iterator();

      while(iterator.hasNext()) {
         List row = (List)iterator.next();
         EntityList metaData = (EntityList)row.get(0);
         int seq = ((Integer)metaData.getValueAt(0, "Seq")).intValue();
         EntityRef reportTemplateEntityRef;
         int templateId;
         String var13;
         switch(seq) {
         case 0:
            reportTemplateEntityRef = this.getRefFromRow(row.get(1));
            templateId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), false).intValue();
            formEVO.setModelId(templateId);
            break;
         case 1:
            reportTemplateEntityRef = this.getRefFromRow(row.get(1));
            templateId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), false).intValue();
            formEVO.setFormId(templateId);
            break;
         case 2:
            Object[] var14 = (Object[])((Object[])row.get(1));
            if(var14 == null || var14.length <= 0) {
               break;
            }

            templateId = 0;

            for(; templateId < var14.length - 1; ++templateId) {
               EntityRef dataTypeId = (EntityRef)var14[templateId];
               int structureId = this.parseEntityKey(dataTypeId.getTokenizedKey(), false).intValue();
               int structureElementId = this.parseEntityKey(dataTypeId.getTokenizedKey(), true).intValue();
               if(structureId != 0 && structureElementId != 0) {
                  formEVO.getClass().getMethod("setStructureId" + templateId, new Class[]{Integer.TYPE}).invoke(formEVO, new Object[]{Integer.valueOf(structureId)});
                  formEVO.getClass().getMethod("setStructureElementId" + templateId, new Class[]{Integer.TYPE}).invoke(formEVO, new Object[]{Integer.valueOf(structureElementId)});
               }
            }

            EntityRef var17 = (EntityRef)var14[var14.length - 1];
            int var16 = this.parseEntityKey(var17.getTokenizedKey(), false).intValue();
            formEVO.setDataTypeId(var16);
            break;
         case 3:
            var13 = String.valueOf(row.get(1));
            String var15 = String.valueOf(this.buildReportDefValueMapping().getValue(var13));
            formEVO.setReportDepth(Integer.valueOf(var15).intValue());
            break;
         case 4:
            var13 = String.valueOf(row.get(1));
            var13 = String.valueOf(this.buildAutoExpendDepthValueMapping().getValue(var13));
            formEVO.setAutoExpandDepth(Integer.valueOf(var13).intValue());
            break;
         case 5:
            reportTemplateEntityRef = this.getRefFromRow(row.get(1));
            templateId = this.parseEntityKey(reportTemplateEntityRef.getTokenizedKey(), false).intValue();
            formEVO.setReportTemplateId(templateId);
         }
      }

   }

   public void populateParamsToMappedExcelEVO(ReportDefMappedExcelEVO mappedExcelEVO, ReportDefinitionImpl editorData) throws Exception {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      AllReportTypeParamsforTypeELO eList = dao.getAllReportTypeParamsforType(editorData.getReportTypeId());
      int size = eList.getNumRows();
      List params = editorData.getReportParams();
      if(params.size() != size) {
         throw new Exception("Wrong param values setting for Report Type:" + editorData.getReportTypeId());
      } else {
         mappedExcelEVO.setReportDefinitionId(this.mReportDefinitionEVO.getReportDefinitionId());
         Iterator iterator = params.iterator();

         while(iterator.hasNext()) {
            List row = (List)iterator.next();
            EntityList metaData = (EntityList)row.get(0);
            int seq = ((Integer)metaData.getValueAt(0, "Seq")).intValue();
            String param;
            int templateId;
            EntityRef param1;
            switch(seq) {
            case 0:
               param1 = this.getRefFromRow(row.get(1));
               templateId = this.parseEntityKey(param1.getTokenizedKey(), false).intValue();
               mappedExcelEVO.setModelId(templateId);
               break;
            case 1:
               param1 = this.getRefFromRow(row.get(1));
               templateId = this.parseEntityKey(param1.getTokenizedKey(), false).intValue();
               int structureElementId = this.parseEntityKey(param1.getTokenizedKey(), true).intValue();
               mappedExcelEVO.setStructureId(templateId);
               mappedExcelEVO.setStructureElementId(structureElementId);
               break;
            case 2:
               param = String.valueOf(row.get(1));
               String templateId1 = String.valueOf(this.buildReportDefValueMapping().getValue(param));
               mappedExcelEVO.setReportDepth(Integer.valueOf(templateId1).intValue());
               break;
            case 3:
               param1 = this.getRefFromRow(row.get(1));
               templateId = this.parseEntityKey(param1.getTokenizedKey(), false).intValue();
               mappedExcelEVO.setReportTemplateId(templateId);
               break;
            case 4:
               param = String.valueOf(row.get(1));
               mappedExcelEVO.setParam(param);
            }
         }

      }
   }

   public void populateFormEVOToParams(ReportDefFormEVO formEVO, ReportDefinitionImpl editorData) throws Exception {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      AllReportTypeParamsforTypeELO eList = dao.getAllReportTypeParamsforType(editorData.getReportTypeId());
      int size = eList.getNumRows();
      ArrayList data = new ArrayList(size);

      for(int i = 0; i < size; ++i) {
         ArrayList row = new ArrayList(2);
         row.add(eList.getRowData(i));
         EntityList paramDetails = eList.getRowData(i);
         int seq = ((Integer)paramDetails.getValueAt(0, "Seq")).intValue();
         int templateId;
         EntityRef templateRef;
         Object var20;
         switch(seq) {
         case 0:
            templateId = formEVO.getModelId();
            ModelPK var22 = new ModelPK(templateId);
            templateRef = this.getRefFromKey(var22);
            row.add(templateRef);
            ModelDetailsWebELO var28 = this.getListServer().getModelDetailsWeb(templateId);
            row.add(this.getDescriptionForRef(var22));
            break;
         case 1:
            templateId = formEVO.getFormId();
            XmlFormPK var25 = new XmlFormPK(templateId);
            templateRef = this.getRefFromKey(var25);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(var25));
            break;
         case 2:
            boolean var21 = true;
            ArrayList var23 = new ArrayList();

            int var24;
            for(var24 = 0; var24 < 10; ++var24) {
               Object dataTypePK = formEVO.getClass().getMethod("getStructureId" + var24, new Class[0]).invoke(formEVO, (Object[])null);
               Object entityRef = formEVO.getClass().getMethod("getStructureElementId" + var24, new Class[0]).invoke(formEVO, (Object[])null);
               int entityRefs = Integer.valueOf(dataTypePK.toString()).intValue();
               int structureElementId = Integer.valueOf(entityRef.toString()).intValue();
               if(entityRefs <= 0 || structureElementId <= 0) {
                  break;
               }

               StructureElementPK structureElementPk = new StructureElementPK(entityRefs, structureElementId);
               EntityRef structureElementRef = this.getRefFromKey(structureElementPk);
               var23.add(structureElementRef);
            }

            var24 = formEVO.getDataTypeId();
            DataTypePK var27 = new DataTypePK((short)var24);
            EntityRef var26 = this.getRefFromKey(var27);
            var23.add(var26);
            EntityRef[] var29 = (EntityRef[])var23.toArray(new EntityRef[0]);
            row.add(var29);
            row.add("N/A");
            break;
         case 3:
            templateId = formEVO.getReportDepth();
            var20 = this.buildReportDefValueMapping().getLiteral(Integer.valueOf(templateId));
            row.add(var20);
            row.add("N/A");
            break;
         case 4:
            templateId = formEVO.getAutoExpandDepth();
            var20 = this.buildAutoExpendDepthValueMapping().getLiteral(Integer.valueOf(templateId));
            row.add(var20);
            row.add("N/A");
            break;
         case 5:
            templateId = formEVO.getReportTemplateId();
            ReportTemplatePK reportTemplatePk = new ReportTemplatePK(templateId);
            templateRef = this.getRefFromKey(reportTemplatePk);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(reportTemplatePk));
            break;
         default:
            throw new Exception("Wrong Report Param setting for type:" + editorData.getReportTypeId());
         }

         row.add(Boolean.TRUE);
         data.add(row);
      }

      editorData.setReportParams(data);
   }

   public void populateSummaryCalcEVOToParams(ReportDefSummaryCalcEVO summaryCalcEVO, ReportDefinitionImpl editorData) throws Exception {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      AllReportTypeParamsforTypeELO eList = dao.getAllReportTypeParamsforType(editorData.getReportTypeId());
      int size = eList.getNumRows();
      ArrayList data = new ArrayList(size);

      for(int i = 0; i < size; ++i) {
         ArrayList row = new ArrayList(2);
         row.add(eList.getRowData(i));
         EntityList paramDetails = eList.getRowData(i);
         int seq = ((Integer)paramDetails.getValueAt(0, "Seq")).intValue();
         EntityRef templateRef;
         int var16;
         switch(seq) {
         case 0:
            var16 = summaryCalcEVO.getModelId();
            ModelPK var20 = new ModelPK(var16);
            templateRef = this.getRefFromKey(var20);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(var20));
            break;
         case 1:
            var16 = summaryCalcEVO.getStructureId();
            int var22 = summaryCalcEVO.getStructureElementId();
            StructureElementPK var19 = new StructureElementPK(var16, var22);
            EntityRef structureElementRef = this.getRefFromKey(var19);
            ValueDescriptionDTO vdRef = new ValueDescriptionDTO(structureElementRef);
            row.add(vdRef);
            row.add(this.getDescriptionForRef(var19));
            break;
         case 2:
            var16 = summaryCalcEVO.getCcDeploymentId();
            CcDeploymentPK var21 = new CcDeploymentPK(var16);
            templateRef = this.getRefFromKey(var21);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(var21));
            break;
         case 3:
            var16 = summaryCalcEVO.getReportTemplateId();
            ReportTemplatePK var18 = new ReportTemplatePK(var16);
            templateRef = this.getRefFromKey(var18);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(var18));
            break;
         case 4:
            var16 = summaryCalcEVO.getHierarchyDepth();
            Object var17 = this.buildReportDefValueMapping().getLiteral(Integer.valueOf(var16));
            row.add(var17);
            row.add("N/A");
            break;
         case 5:
            String columnMapStr = summaryCalcEVO.getColumnMap();
            Map map = ReportColumnMapDTO.buildMap(columnMapStr);
            row.add(map);
            row.add("N/A");
            break;
         default:
            throw new Exception("Wrong Report Param setting for type:" + editorData.getReportTypeId());
         }

         row.add(Boolean.TRUE);
         data.add(row);
      }

      editorData.setReportParams(data);
   }

   public void populateCalculatorEVOToParams(ReportDefCalculatorEVO calculatorEVO, ReportDefinitionImpl editorData) throws Exception {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      AllReportTypeParamsforTypeELO eList = dao.getAllReportTypeParamsforType(editorData.getReportTypeId());
      int size = eList.getNumRows();
      ArrayList data = new ArrayList(size);

      for(int i = 0; i < size; ++i) {
         ArrayList row = new ArrayList(2);
         row.add(eList.getRowData(i));
         EntityList paramDetails = eList.getRowData(i);
         int seq = ((Integer)paramDetails.getValueAt(0, "Seq")).intValue();
         int templateId;
         EntityRef templateRef;
         switch(seq) {
         case 0:
            templateId = calculatorEVO.getModelId();
            ModelPK var19 = new ModelPK(templateId);
            templateRef = this.getRefFromKey(var19);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(var19));
            break;
         case 1:
            templateId = calculatorEVO.getStructureId();
            int var17 = calculatorEVO.getStructureElementId();
            StructureElementPK var18 = new StructureElementPK(templateId, var17);
            EntityRef structureElementRef = this.getRefFromKey(var18);
            ValueDescriptionDTO vdRef = new ValueDescriptionDTO(structureElementRef);
            row.add(vdRef);
            row.add(this.getDescriptionForRef(var18));
            break;
         case 2:
            templateId = calculatorEVO.getCcDeploymentId();
            CcDeploymentPK var16 = new CcDeploymentPK(templateId);
            templateRef = this.getRefFromKey(var16);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(var16));
            break;
         case 3:
            templateId = calculatorEVO.getReportTemplateId();
            ReportTemplatePK reportTemplatePk = new ReportTemplatePK(templateId);
            templateRef = this.getRefFromKey(reportTemplatePk);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(reportTemplatePk));
            break;
         default:
            throw new Exception("Wrong Report Param setting for type:" + editorData.getReportTypeId());
         }

         row.add(Boolean.TRUE);
         data.add(row);
      }

      editorData.setReportParams(data);
   }

   public void populateMappedExcelEVOToParams(ReportDefMappedExcelEVO mappedExcelEVO, ReportDefinitionImpl editorData) throws Exception {
      ReportTypeParamDAO dao = new ReportTypeParamDAO();
      AllReportTypeParamsforTypeELO eList = dao.getAllReportTypeParamsforType(editorData.getReportTypeId());
      int size = eList.getNumRows();
      ArrayList data = new ArrayList(size);

      for(int i = 0; i < size; ++i) {
         ArrayList row = new ArrayList(2);
         row.add(eList.getRowData(i));
         EntityList paramDetails = eList.getRowData(i);
         int seq = ((Integer)paramDetails.getValueAt(0, "Seq")).intValue();
         int templateId;
         EntityRef templateRef;
         switch(seq) {
         case 0:
            templateId = mappedExcelEVO.getModelId();
            ModelPK var18 = new ModelPK(templateId);
            templateRef = this.getRefFromKey(var18);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(var18));
            break;
         case 1:
            templateId = mappedExcelEVO.getStructureId();
            int var17 = mappedExcelEVO.getStructureElementId();
            StructureElementPK var19 = new StructureElementPK(templateId, var17);
            EntityRef structureElementRef = this.getRefFromKey(var19);
            ValueDescriptionDTO vdRef = new ValueDescriptionDTO(structureElementRef);
            row.add(vdRef);
            row.add(this.getDescriptionForRef(var19));
            break;
         case 2:
            templateId = mappedExcelEVO.getReportDepth();
            Object var16 = this.buildReportDefValueMapping().getLiteral(Integer.valueOf(templateId));
            row.add(var16);
            row.add("N/A");
            break;
         case 3:
            templateId = mappedExcelEVO.getReportTemplateId();
            ReportMappingTemplatePK mappingTemplatePk = new ReportMappingTemplatePK(templateId);
            templateRef = this.getRefFromKey(mappingTemplatePk);
            row.add(templateRef);
            row.add(this.getDescriptionForRef(mappingTemplatePk));
            break;
         case 4:
            row.add(mappedExcelEVO.getParam());
            row.add("N/A");
            break;
         default:
            throw new Exception("Wrong Report Param setting for type:" + editorData.getReportTypeId());
         }

         row.add(Boolean.TRUE);
         data.add(row);
      }

      editorData.setReportParams(data);
   }

   public ValueMapping buildReportDefValueMapping() {
      String[] literals = new String[]{"Leaf Only", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "All"};
      Object[] values = new Object[]{Integer.valueOf(-1), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(99)};
      return new DefaultValueMapping(literals, values);
   }

   public ValueMapping buildAutoExpendDepthValueMapping() {
      String[] literals = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "All"};
      Object[] values = new Object[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(99)};
      return new DefaultValueMapping(literals, values);
   }

   public int issueReport(int userId, WebReportOption wro) throws EJBException {
      return this.issueReport(userId, wro, 0);
   }

   public int issueReport(int userId, WebReportOption wro, int issuingTaskId) throws EJBException {
      int var5;
      try {
         this.setUserId(userId);
         PackTaskRequest e = new PackTaskRequest();
         e.setWebOptions(wro);
         var5 = TaskMessageFactory.issueNewTask(new InitialContext(), 1, false, e, userId, issuingTaskId);
      } catch (Exception var9) {
         var9.printStackTrace();
         throw new EJBException(var9);
      } finally {
         this.setUserId(0);
      }

      return var5;
   }

   public List issueWebReport(int userId, WebReportOption wro, int issuingTaskId) throws EJBException {
      ArrayList var30;
      try {
         this.setUserId(userId);
         ReportDefinitionDAO e = new ReportDefinitionDAO();
         ReportDefinitionForVisIdELO elo = e.getReportDefinitionForVisId(wro.getReportVisId());
         ArrayList ids;
         if(!elo.hasNext()) {
            ids = null;
            return ids;
         }

         elo.next();
         this.getItemData(userId, elo.getReportDefinitionEntityRef().getPrimaryKey());
         ids = new ArrayList();
         int reportType = this.mReportDefinitionEVO.getReportTypeId();
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
         byte[] var31;
         switch(reportType) {
         case 1:
            ReportDefinitionFormRunDetails var33 = this.getReportDefinitionFormRunDetails(Integer.valueOf(wro.getDepth()), Integer.valueOf(wro.getCCId()));
            modelId = var33.getModelId();
            xmlFormId = var33.getXMLFormId();
            dataType = var33.getDataType();
            depth = var33.getDepth();
            selection = var33.getSelection();
            var31 = var33.getTemplate();
            location = var33.getBudgetLocations();
            break;
         case 2:
            ReportDefinitionMappingRunDetails var34 = this.getReportDefinitionMappingRunDetails(Integer.valueOf(wro.getDepth()), Integer.valueOf(wro.getCCId()));
            modelId = var34.getModelId();
            var31 = var34.getTemplate();
            location = var34.getBudgetLocations();
            break;
         case 3:
            ReportDefinitionCalculationRunDetails var35 = this.getReportDefinitionCalculationRunDetails(Integer.valueOf(wro.getCCId()));
            modelId = var35.getModelId();
            xmlFormId = var35.getXMLFormId();
            deploymentId = var35.getDeploymentId();
            contextDimIndexes = var35.getContextDimenionIndexes();
            var31 = var35.getTemplate();
            location = var35.getBudgetLocations();
            break;
         case 4:
            ReportDefinitionCalculationSummaryRunDetails batchCount = this.getReportDefinitionSumaryCalculationRunDetails(Integer.valueOf(wro.getCCId()));
            modelId = batchCount.getModelId();
            xmlFormId = batchCount.getXMLFormId();
            deploymentId = batchCount.getDeploymentId();
            contextDimIndexes = batchCount.getContextDimenionIndexes();
            var31 = batchCount.getTemplate();
            location = batchCount.getBudgetLocations();
            columnSettings = batchCount.getColumnSettings();
            break;
         default:
            throw new UnsupportedOperationException("Unsupported report type");
         }

         int var32 = 0;
         int size = location.size();
         int maxJobs = SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "SYS: Report Pack max jobs", 2);
         int batchSize = size / maxJobs;
         if(batchSize * maxJobs < size) {
            ++batchSize;
         }

         ArrayList respArea = new ArrayList();
         Iterator locationIter = location.iterator();

         while(locationIter.hasNext()) {
            ++var32;
            respArea.add(locationIter.next());
            if(var32 == batchSize || !locationIter.hasNext()) {
               PackLineTaskRequest request = new PackLineTaskRequest();
               request.setSelf(wro.isSelf());
               request.setReportType(reportType);
               request.setModelId(modelId);
               request.setFormId(xmlFormId);
               request.setDepth(depth);
               request.setDataType(dataType);
               request.setSelectionCriteria(selection);
               request.setRespArea(respArea);
               request.setGroup(wro.isGroup());
               request.setTemplate(var31);
               request.setContextDims(contextDimIndexes);
               request.setDeploymentId(deploymentId);
               request.setColumnSettings(columnSettings);
               request.setOption(wro.getReportOption());
               ids.add(Integer.valueOf(TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, issuingTaskId)));
               var32 = 0;
               respArea = new ArrayList();
            }
         }

         var30 = ids;
      } catch (Exception var28) {
         var28.printStackTrace();
         throw new EJBException(var28);
      } finally {
         this.setUserId(0);
      }

      return var30;
   }

   private EntityRef getRefFromRow(Object o) {
      EntityRef result = null;
      if(o instanceof ValueDescriptionDTO) {
         result = (EntityRef)((ValueDescriptionDTO)o).getObject();
      } else if(o instanceof EntityRef) {
         result = (EntityRef)o;
      }

      return result;
   }
}
