// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.mappingtemplate;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByReportTemplateIdELO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionCSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateImpl;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionAccessor;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateAccessor;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ReportMappingTemplateEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ReportMappingTemplateAccessor mReportMappingTemplateAccessor;
   private ReportMappingTemplateEditorSessionSSO mSSO;
   private ReportMappingTemplatePK mThisTableKey;
   private ReportMappingTemplateEVO mReportMappingTemplateEVO;


   public ReportMappingTemplateEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ReportMappingTemplatePK)paramKey;

      ReportMappingTemplateEditorSessionSSO e;
      try {
         this.mReportMappingTemplateEVO = this.getReportMappingTemplateAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new ReportMappingTemplateEditorSessionSSO();
      ReportMappingTemplateImpl editorData = this.buildReportMappingTemplateEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ReportMappingTemplateImpl editorData) throws Exception {}

   private ReportMappingTemplateImpl buildReportMappingTemplateEditData(Object thisKey) throws Exception {
      ReportMappingTemplateImpl editorData = new ReportMappingTemplateImpl(thisKey);
      editorData.setVisId(this.mReportMappingTemplateEVO.getVisId());
      editorData.setDescription(this.mReportMappingTemplateEVO.getDescription());
      editorData.setDocumentName(this.mReportMappingTemplateEVO.getDocumentName());
      editorData.setDocument(this.mReportMappingTemplateEVO.getDocument());
      editorData.setVersionNum(this.mReportMappingTemplateEVO.getVersionNum());
      this.completeReportMappingTemplateEditData(editorData);
      return editorData;
   }

   private void completeReportMappingTemplateEditData(ReportMappingTemplateImpl editorData) throws Exception {}

   public ReportMappingTemplateEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ReportMappingTemplateEditorSessionSSO var4;
      try {
         this.mSSO = new ReportMappingTemplateEditorSessionSSO();
         ReportMappingTemplateImpl e = new ReportMappingTemplateImpl((Object)null);
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

   private void completeGetNewItemData(ReportMappingTemplateImpl editorData) throws Exception {}

   public ReportMappingTemplatePK insert(ReportMappingTemplateEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportMappingTemplateImpl editorData = cso.getEditorData();

      ReportMappingTemplatePK e;
      try {
         this.mReportMappingTemplateEVO = new ReportMappingTemplateEVO();
         this.mReportMappingTemplateEVO.setVisId(editorData.getVisId());
         this.mReportMappingTemplateEVO.setDescription(editorData.getDescription());
         this.mReportMappingTemplateEVO.setDocumentName(editorData.getDocumentName());
         this.mReportMappingTemplateEVO.setDocument(editorData.getDocument());
         this.updateReportMappingTemplateRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mReportMappingTemplateEVO = this.getReportMappingTemplateAccessor().create(this.mReportMappingTemplateEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ReportMappingTemplate", this.mReportMappingTemplateEVO.getPK(), 1);
         e = this.mReportMappingTemplateEVO.getPK();
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

   private void updateReportMappingTemplateRelationships(ReportMappingTemplateImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ReportMappingTemplateImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ReportMappingTemplateImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {
      if(this.mReportMappingTemplateEVO.getDocument() == null || this.mReportMappingTemplateEVO.getDocument().length == 0) {
         throw new ValidationException("No template has been uploaded");
      }
   }

   public ReportMappingTemplatePK copy(ReportMappingTemplateEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportMappingTemplateImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportMappingTemplatePK)editorData.getPrimaryKey();

      ReportMappingTemplatePK var5;
      try {
         ReportMappingTemplateEVO e = this.getReportMappingTemplateAccessor().getDetails(this.mThisTableKey, "");
         this.mReportMappingTemplateEVO = e.deepClone();
         this.mReportMappingTemplateEVO.setVisId(editorData.getVisId());
         this.mReportMappingTemplateEVO.setDescription(editorData.getDescription());
         this.mReportMappingTemplateEVO.setDocumentName(editorData.getDocumentName());
         this.mReportMappingTemplateEVO.setDocument(editorData.getDocument());
         this.mReportMappingTemplateEVO.setVersionNum(0);
         this.updateReportMappingTemplateRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mReportMappingTemplateEVO.prepareForInsert();
         this.mReportMappingTemplateEVO = this.getReportMappingTemplateAccessor().create(this.mReportMappingTemplateEVO);
         this.mThisTableKey = this.mReportMappingTemplateEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ReportMappingTemplate", this.mReportMappingTemplateEVO.getPK(), 1);
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

   private void completeCopySetup(ReportMappingTemplateImpl editorData) throws Exception {}

   public void update(ReportMappingTemplateEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportMappingTemplateImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportMappingTemplatePK)editorData.getPrimaryKey();

      try {
         this.mReportMappingTemplateEVO = this.getReportMappingTemplateAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mReportMappingTemplateEVO.setVisId(editorData.getVisId());
         this.mReportMappingTemplateEVO.setDescription(editorData.getDescription());
         this.mReportMappingTemplateEVO.setDocumentName(editorData.getDocumentName());
         this.mReportMappingTemplateEVO.setDocument(editorData.getDocument());
         if(editorData.getVersionNum() != this.mReportMappingTemplateEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mReportMappingTemplateEVO.getVersionNum());
         }

         this.updateReportMappingTemplateRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getReportMappingTemplateAccessor().setDetails(this.mReportMappingTemplateEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ReportMappingTemplate", this.mReportMappingTemplateEVO.getPK(), 3);
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

   private void preValidateUpdate(ReportMappingTemplateImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ReportMappingTemplateImpl editorData) throws Exception {}

   private void updateAdditionalTables(ReportMappingTemplateImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ReportMappingTemplatePK)paramKey;

      try {
         this.mReportMappingTemplateEVO = this.getReportMappingTemplateAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mReportMappingTemplateAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ReportMappingTemplate", this.mThisTableKey, 2);
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
      AllReportDefMappedExcelcByReportTemplateIdELO mappedlist = reportDefAccessor.getAllReportDefMappedExcelcByReportTemplateId(this.mReportMappingTemplateEVO.getReportMappingTemplateId());
      if(mappedlist != null && mappedlist.getNumRows() > 0) {
         throw new ValidationException("Report Mapping Template is in use");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ReportMappingTemplateAccessor getReportMappingTemplateAccessor() throws Exception {
      if(this.mReportMappingTemplateAccessor == null) {
         this.mReportMappingTemplateAccessor = new ReportMappingTemplateAccessor(this.getInitialContext());
      }

      return this.mReportMappingTemplateAccessor;
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
}
