// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.task;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionCSO;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionSSO;
import com.cedar.cp.dto.report.task.ReportGroupingImpl;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingAccessor;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingEVO;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingFileEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Arrays;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ReportGroupingEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ReportGroupingAccessor mReportGroupingAccessor;
   private ReportGroupingEditorSessionSSO mSSO;
   private ReportGroupingPK mThisTableKey;
   private ReportGroupingEVO mReportGroupingEVO;


   public ReportGroupingEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ReportGroupingPK)paramKey;

      ReportGroupingEditorSessionSSO e;
      try {
         this.mReportGroupingEVO = this.getReportGroupingAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new ReportGroupingEditorSessionSSO();
      ReportGroupingImpl editorData = this.buildReportGroupingEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ReportGroupingImpl editorData) throws Exception {}

   private ReportGroupingImpl buildReportGroupingEditData(Object thisKey) throws Exception {
      ReportGroupingImpl editorData = new ReportGroupingImpl(thisKey);
      editorData.setParentTaskId(this.mReportGroupingEVO.getParentTaskId());
      editorData.setTaskId(this.mReportGroupingEVO.getTaskId());
      editorData.setDistributionType(this.mReportGroupingEVO.getDistributionType());
      editorData.setMessageType(this.mReportGroupingEVO.getMessageType());
      editorData.setMessageId(this.mReportGroupingEVO.getMessageId());
      this.completeReportGroupingEditData(editorData);
      return editorData;
   }

   private void completeReportGroupingEditData(ReportGroupingImpl editorData) throws Exception {}

   public ReportGroupingEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ReportGroupingEditorSessionSSO var4;
      try {
         this.mSSO = new ReportGroupingEditorSessionSSO();
         ReportGroupingImpl e = new ReportGroupingImpl((Object)null);
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

   private void completeGetNewItemData(ReportGroupingImpl editorData) throws Exception {}

   public ReportGroupingPK insert(ReportGroupingEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportGroupingImpl editorData = cso.getEditorData();

      ReportGroupingPK e;
      try {
         this.mReportGroupingEVO = new ReportGroupingEVO();
         this.mReportGroupingEVO.setParentTaskId(editorData.getParentTaskId());
         this.mReportGroupingEVO.setTaskId(editorData.getTaskId());
         this.mReportGroupingEVO.setDistributionType(editorData.getDistributionType());
         this.mReportGroupingEVO.setMessageType(editorData.getMessageType());
         this.mReportGroupingEVO.setMessageId(editorData.getMessageId());
         this.updateReportGroupingRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mReportGroupingEVO = this.getReportGroupingAccessor().create(this.mReportGroupingEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ReportGrouping", this.mReportGroupingEVO.getPK(), 1);
         e = this.mReportGroupingEVO.getPK();
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

   private void updateReportGroupingRelationships(ReportGroupingImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ReportGroupingImpl editorData) throws Exception {
      int id = 0;
      Iterator i$ = editorData.getFiles().iterator();

      while(i$.hasNext()) {
         CPFileWrapper file = (CPFileWrapper)i$.next();
         ReportGroupingFileEVO evo = new ReportGroupingFileEVO();
         --id;
         evo.setReportGroupingFileId(id);
         evo.setFileName(file.getName());
         evo.setFileData(file.getData());
         this.mReportGroupingEVO.addReportGroupFilesItem(evo);
      }

   }

   private void insertIntoAdditionalTables(ReportGroupingImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ReportGroupingPK copy(ReportGroupingEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportGroupingImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportGroupingPK)editorData.getPrimaryKey();

      ReportGroupingPK var5;
      try {
         ReportGroupingEVO e = this.getReportGroupingAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mReportGroupingEVO = e.deepClone();
         this.mReportGroupingEVO.setParentTaskId(editorData.getParentTaskId());
         this.mReportGroupingEVO.setTaskId(editorData.getTaskId());
         this.mReportGroupingEVO.setDistributionType(editorData.getDistributionType());
         this.mReportGroupingEVO.setMessageType(editorData.getMessageType());
         this.mReportGroupingEVO.setMessageId(editorData.getMessageId());
         this.updateReportGroupingRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mReportGroupingEVO.prepareForInsert();
         this.mReportGroupingEVO = this.getReportGroupingAccessor().create(this.mReportGroupingEVO);
         this.mThisTableKey = this.mReportGroupingEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ReportGrouping", this.mReportGroupingEVO.getPK(), 1);
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

   private void completeCopySetup(ReportGroupingImpl editorData) throws Exception {}

   public void update(ReportGroupingEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportGroupingImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportGroupingPK)editorData.getPrimaryKey();

      try {
         this.mReportGroupingEVO = this.getReportGroupingAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mReportGroupingEVO.setParentTaskId(editorData.getParentTaskId());
         this.mReportGroupingEVO.setTaskId(editorData.getTaskId());
         this.mReportGroupingEVO.setDistributionType(editorData.getDistributionType());
         this.mReportGroupingEVO.setMessageType(editorData.getMessageType());
         this.mReportGroupingEVO.setMessageId(editorData.getMessageId());
         this.updateReportGroupingRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getReportGroupingAccessor().setDetails(this.mReportGroupingEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ReportGrouping", this.mReportGroupingEVO.getPK(), 3);
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

   private void preValidateUpdate(ReportGroupingImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ReportGroupingImpl editorData) throws Exception {
      int id = 0;
      Iterator i$ = editorData.getFiles().iterator();

      while(i$.hasNext()) {
         CPFileWrapper file = (CPFileWrapper)i$.next();
         boolean doAdd = true;
         Iterator evo = this.mReportGroupingEVO.getReportGroupFiles().iterator();

         while(true) {
            if(evo.hasNext()) {
               ReportGroupingFileEVO fileEVO = (ReportGroupingFileEVO)evo.next();
               if(!fileEVO.getFileName().equals(file.getName()) || !Arrays.equals(fileEVO.getFileData(), file.getData())) {
                  continue;
               }

               doAdd = false;
            }

            if(doAdd) {
               ReportGroupingFileEVO var8 = new ReportGroupingFileEVO();
               --id;
               var8.setReportGroupingFileId(id);
               var8.setFileName(file.getName());
               var8.setFileData(file.getData());
               this.mReportGroupingEVO.addReportGroupFilesItem(var8);
            }
            break;
         }
      }

   }

   private void updateAdditionalTables(ReportGroupingImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ReportGroupingPK)paramKey;

      try {
         this.mReportGroupingEVO = this.getReportGroupingAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mReportGroupingAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ReportGrouping", this.mThisTableKey, 2);
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

   private ReportGroupingAccessor getReportGroupingAccessor() throws Exception {
      if(this.mReportGroupingAccessor == null) {
         this.mReportGroupingAccessor = new ReportGroupingAccessor(this.getInitialContext());
      }

      return this.mReportGroupingAccessor;
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
