// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.ReportEditorSessionCSO;
import com.cedar.cp.dto.report.ReportEditorSessionSSO;
import com.cedar.cp.dto.report.ReportImpl;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.dto.report.ReportUpdateTaskRequest;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.ReportAccessor;
import com.cedar.cp.ejb.impl.report.ReportDAO;
import com.cedar.cp.ejb.impl.report.ReportEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.text.MessageFormat;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ReportEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ReportAccessor mReportAccessor;
   private ReportEditorSessionSSO mSSO;
   private ReportPK mThisTableKey;
   private ReportEVO mReportEVO;


   public ReportEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ReportPK)paramKey;

      ReportEditorSessionSSO e;
      try {
         this.mReportEVO = this.getReportAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new ReportEditorSessionSSO();
      ReportImpl editorData = this.buildReportEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ReportImpl editorData) throws Exception {}

   private ReportImpl buildReportEditData(Object thisKey) throws Exception {
      ReportImpl editorData = new ReportImpl(thisKey);
      editorData.setUserId(this.mReportEVO.getUserId());
      editorData.setReportType(this.mReportEVO.getReportType());
      editorData.setTaskId(this.mReportEVO.getTaskId());
      editorData.setComplete(this.mReportEVO.getComplete());
      editorData.setReportText(this.mReportEVO.getReportText());
      editorData.setHasUpdates(this.mReportEVO.getHasUpdates());
      editorData.setUpdatesApplied(this.mReportEVO.getUpdatesApplied());
      editorData.setUpdateTaskId(this.mReportEVO.getUpdateTaskId());
      editorData.setBudgetCycleId(this.mReportEVO.getBudgetCycleId());
      editorData.setActivityType(this.mReportEVO.getActivityType());
      editorData.setActivityDetail(this.mReportEVO.getActivityDetail());
      editorData.setVersionNum(this.mReportEVO.getVersionNum());
      this.completeReportEditData(editorData);
      return editorData;
   }

   private void completeReportEditData(ReportImpl editorData) throws Exception {}

   public ReportEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ReportEditorSessionSSO var4;
      try {
         this.mSSO = new ReportEditorSessionSSO();
         ReportImpl e = new ReportImpl((Object)null);
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

   private void completeGetNewItemData(ReportImpl editorData) throws Exception {}

   public ReportPK insert(ReportEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportImpl editorData = cso.getEditorData();

      ReportPK e;
      try {
         this.mReportEVO = new ReportEVO();
         this.mReportEVO.setUserId(editorData.getUserId());
         this.mReportEVO.setReportType(editorData.getReportType());
         this.mReportEVO.setTaskId(editorData.getTaskId());
         this.mReportEVO.setComplete(editorData.isComplete());
         this.mReportEVO.setReportText(editorData.getReportText());
         this.mReportEVO.setHasUpdates(editorData.isHasUpdates());
         this.mReportEVO.setUpdatesApplied(editorData.isUpdatesApplied());
         this.mReportEVO.setUpdateTaskId(editorData.getUpdateTaskId());
         this.mReportEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mReportEVO.setActivityType(editorData.getActivityType());
         this.mReportEVO.setActivityDetail(editorData.getActivityDetail());
         this.updateReportRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mReportEVO = this.getReportAccessor().create(this.mReportEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("Report", this.mReportEVO.getPK(), 1);
         e = this.mReportEVO.getPK();
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

   private void updateReportRelationships(ReportImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ReportImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ReportImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ReportPK copy(ReportEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportPK)editorData.getPrimaryKey();

      ReportPK var5;
      try {
         ReportEVO e = this.getReportAccessor().getDetails(this.mThisTableKey, "");
         this.mReportEVO = e.deepClone();
         this.mReportEVO.setUserId(editorData.getUserId());
         this.mReportEVO.setReportType(editorData.getReportType());
         this.mReportEVO.setTaskId(editorData.getTaskId());
         this.mReportEVO.setComplete(editorData.isComplete());
         this.mReportEVO.setReportText(editorData.getReportText());
         this.mReportEVO.setHasUpdates(editorData.isHasUpdates());
         this.mReportEVO.setUpdatesApplied(editorData.isUpdatesApplied());
         this.mReportEVO.setUpdateTaskId(editorData.getUpdateTaskId());
         this.mReportEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mReportEVO.setActivityType(editorData.getActivityType());
         this.mReportEVO.setActivityDetail(editorData.getActivityDetail());
         this.mReportEVO.setVersionNum(0);
         this.updateReportRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mReportEVO.prepareForInsert();
         this.mReportEVO = this.getReportAccessor().create(this.mReportEVO);
         this.mThisTableKey = this.mReportEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("Report", this.mReportEVO.getPK(), 1);
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

   private void completeCopySetup(ReportImpl editorData) throws Exception {}

   public void update(ReportEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportPK)editorData.getPrimaryKey();

      try {
         this.mReportEVO = this.getReportAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mReportEVO.setUserId(editorData.getUserId());
         this.mReportEVO.setReportType(editorData.getReportType());
         this.mReportEVO.setTaskId(editorData.getTaskId());
         this.mReportEVO.setComplete(editorData.isComplete());
         this.mReportEVO.setReportText(editorData.getReportText());
         this.mReportEVO.setHasUpdates(editorData.isHasUpdates());
         this.mReportEVO.setUpdatesApplied(editorData.isUpdatesApplied());
         this.mReportEVO.setUpdateTaskId(editorData.getUpdateTaskId());
         this.mReportEVO.setBudgetCycleId(editorData.getBudgetCycleId());
         this.mReportEVO.setActivityType(editorData.getActivityType());
         this.mReportEVO.setActivityDetail(editorData.getActivityDetail());
         if(editorData.getVersionNum() != this.mReportEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mReportEVO.getVersionNum());
         }

         this.updateReportRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getReportAccessor().setDetails(this.mReportEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("Report", this.mReportEVO.getPK(), 3);
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

   private void preValidateUpdate(ReportImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ReportImpl editorData) throws Exception {}

   private void updateAdditionalTables(ReportImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ReportPK)paramKey;

      try {
         this.mReportEVO = this.getReportAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mReportAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("Report", this.mThisTableKey, 2);
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

   private ReportAccessor getReportAccessor() throws Exception {
      if(this.mReportAccessor == null) {
         this.mReportAccessor = new ReportAccessor(this.getInitialContext());
      }

      return this.mReportAccessor;
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

   private int queryReportId(Object key) {
      if(key instanceof ReportPK) {
         return ((ReportPK)key).getReportId();
      } else if(key instanceof Integer) {
         return ((Integer)key).intValue();
      } else {
         throw new IllegalArgumentException("Unexpected report key type:" + key);
      }
   }

   public int issueReportUpdateTask(int userId, Object reportKey, boolean rollback) throws ValidationException, EJBException {
      int reportId = this.queryReportId(reportKey);
      if(reportKey instanceof Integer) {
         reportKey = new ReportPK(((Integer)reportKey).intValue());
      }

      ReportEditorSessionSSO sso = this.getItemData(userId, reportKey);
      ReportImpl report = sso.getEditorData();
      if(!report.isComplete()) {
         throw new ValidationException(MessageFormat.format("Report id {0} is still being written by another task.", new Object[]{new Integer(reportId)}));
      } else {
         String financeCubeId1;
         if(!report.isHasUpdates()) {
            financeCubeId1 = MessageFormat.format("Report {0} does not have any updates associated with it.", new Object[]{new Integer(reportId)});
            throw new ValidationException(financeCubeId1);
         } else if(rollback && !report.isUpdatesApplied()) {
            financeCubeId1 = MessageFormat.format("Report {0} updates have already been undone.", new Object[]{new Integer(reportId)});
            throw new ValidationException(financeCubeId1);
         } else if(!rollback && report.isUpdatesApplied()) {
            financeCubeId1 = MessageFormat.format("Report {0} updates have already been applied to the database.", new Object[]{new Integer(reportId)});
            throw new ValidationException(financeCubeId1);
         } else if(report.getUpdateTaskId() != null) {
            financeCubeId1 = MessageFormat.format("Report {0} already has a task {1} associated with it.", new Object[]{new Integer(reportId), report.getUpdateTaskId()});
            throw new ValidationException(financeCubeId1);
         } else {
            int financeCubeId = (new ReportDAO()).queryFinanceCubeId(reportId);
            ReportUpdateTaskRequest taskRequest = new ReportUpdateTaskRequest(reportId, financeCubeId);

            int taskId;
            try {
               taskId = TaskMessageFactory.issueNewTask(this.getInitialContext(), false, taskRequest, userId);
            } catch (Exception var11) {
               var11.printStackTrace();
               throw new EJBException(var11);
            }

            sso.getEditorData().setUpdateTaskId(new Integer(taskId));
            this.update(new ReportEditorSessionCSO(userId, sso.getEditorData()));
            return taskId;
         }
      }
   }
}
