// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.template;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefFormcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByReportTemplateIdELO;
import com.cedar.cp.dto.report.template.ReportTemplateEditorSessionCSO;
import com.cedar.cp.dto.report.template.ReportTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.template.ReportTemplateImpl;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionAccessor;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateAccessor;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ReportTemplateEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ReportTemplateAccessor mReportTemplateAccessor;
   private ReportTemplateEditorSessionSSO mSSO;
   private ReportTemplatePK mThisTableKey;
   private ReportTemplateEVO mReportTemplateEVO;


   public ReportTemplateEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ReportTemplatePK)paramKey;

      ReportTemplateEditorSessionSSO e;
      try {
         this.mReportTemplateEVO = this.getReportTemplateAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new ReportTemplateEditorSessionSSO();
      ReportTemplateImpl editorData = this.buildReportTemplateEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ReportTemplateImpl editorData) throws Exception {}

   private ReportTemplateImpl buildReportTemplateEditData(Object thisKey) throws Exception {
      ReportTemplateImpl editorData = new ReportTemplateImpl(thisKey);
      editorData.setVisId(this.mReportTemplateEVO.getVisId());
      editorData.setDescription(this.mReportTemplateEVO.getDescription());
      editorData.setDocumentName(this.mReportTemplateEVO.getDocumentName());
      editorData.setDocument(this.mReportTemplateEVO.getDocument());
      editorData.setVersionNum(this.mReportTemplateEVO.getVersionNum());
      this.completeReportTemplateEditData(editorData);
      return editorData;
   }

   private void completeReportTemplateEditData(ReportTemplateImpl editorData) throws Exception {}

   public ReportTemplateEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ReportTemplateEditorSessionSSO var4;
      try {
         this.mSSO = new ReportTemplateEditorSessionSSO();
         ReportTemplateImpl e = new ReportTemplateImpl((Object)null);
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

   private void completeGetNewItemData(ReportTemplateImpl editorData) throws Exception {}

   public ReportTemplatePK insert(ReportTemplateEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportTemplateImpl editorData = cso.getEditorData();

      ReportTemplatePK e;
      try {
         this.mReportTemplateEVO = new ReportTemplateEVO();
         this.mReportTemplateEVO.setVisId(editorData.getVisId());
         this.mReportTemplateEVO.setDescription(editorData.getDescription());
         this.mReportTemplateEVO.setDocumentName(editorData.getDocumentName());
         this.mReportTemplateEVO.setDocument(editorData.getDocument());
         this.updateReportTemplateRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mReportTemplateEVO = this.getReportTemplateAccessor().create(this.mReportTemplateEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ReportTemplate", this.mReportTemplateEVO.getPK(), 1);
         e = this.mReportTemplateEVO.getPK();
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

   private void updateReportTemplateRelationships(ReportTemplateImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ReportTemplateImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ReportTemplateImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {
      if(this.mReportTemplateEVO.getDocument() == null || this.mReportTemplateEVO.getDocument().length == 0) {
         throw new ValidationException("No template has been uploaded");
      }
   }

   public ReportTemplatePK copy(ReportTemplateEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportTemplateImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportTemplatePK)editorData.getPrimaryKey();

      ReportTemplatePK var5;
      try {
         ReportTemplateEVO e = this.getReportTemplateAccessor().getDetails(this.mThisTableKey, "");
         this.mReportTemplateEVO = e.deepClone();
         this.mReportTemplateEVO.setVisId(editorData.getVisId());
         this.mReportTemplateEVO.setDescription(editorData.getDescription());
         this.mReportTemplateEVO.setDocumentName(editorData.getDocumentName());
         this.mReportTemplateEVO.setDocument(editorData.getDocument());
         this.mReportTemplateEVO.setVersionNum(0);
         this.updateReportTemplateRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mReportTemplateEVO.prepareForInsert();
         this.mReportTemplateEVO = this.getReportTemplateAccessor().create(this.mReportTemplateEVO);
         this.mThisTableKey = this.mReportTemplateEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ReportTemplate", this.mReportTemplateEVO.getPK(), 1);
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

   private void completeCopySetup(ReportTemplateImpl editorData) throws Exception {}

   public void update(ReportTemplateEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportTemplateImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportTemplatePK)editorData.getPrimaryKey();

      try {
         this.mReportTemplateEVO = this.getReportTemplateAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mReportTemplateEVO.setVisId(editorData.getVisId());
         this.mReportTemplateEVO.setDescription(editorData.getDescription());
         this.mReportTemplateEVO.setDocumentName(editorData.getDocumentName());
         this.mReportTemplateEVO.setDocument(editorData.getDocument());
         if(editorData.getVersionNum() != this.mReportTemplateEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mReportTemplateEVO.getVersionNum());
         }

         this.updateReportTemplateRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getReportTemplateAccessor().setDetails(this.mReportTemplateEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ReportTemplate", this.mReportTemplateEVO.getPK(), 3);
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

   private void preValidateUpdate(ReportTemplateImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ReportTemplateImpl editorData) throws Exception {}

   private void updateAdditionalTables(ReportTemplateImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ReportTemplatePK)paramKey;

      try {
         this.mReportTemplateEVO = this.getReportTemplateAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mReportTemplateAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ReportTemplate", this.mThisTableKey, 2);
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
      AllReportDefCalcByReportTemplateIdELO calclist = reportDefAccessor.getAllReportDefCalcByReportTemplateId(this.mReportTemplateEVO.getReportTemplateId());
      AllReportDefFormcByReportTemplateIdELO formlist = reportDefAccessor.getAllReportDefFormcByReportTemplateId(this.mReportTemplateEVO.getReportTemplateId());
      AllReportDefSummaryCalcByReportTemplateIdELO summarylist = reportDefAccessor.getAllReportDefSummaryCalcByReportTemplateId(this.mReportTemplateEVO.getReportTemplateId());
      if(calclist != null && calclist.getNumRows() > 0 || formlist != null && formlist.getNumRows() > 0 || summarylist != null && summarylist.getNumRows() > 0) {
         throw new ValidationException("Report Template is in use");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ReportTemplateAccessor getReportTemplateAccessor() throws Exception {
      if(this.mReportTemplateAccessor == null) {
         this.mReportTemplateAccessor = new ReportTemplateAccessor(this.getInitialContext());
      }

      return this.mReportTemplateAccessor;
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
