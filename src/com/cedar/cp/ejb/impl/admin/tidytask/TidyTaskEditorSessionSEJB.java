// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.admin.tidytask;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.admin.tidytask.OrderedChildrenELO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionCSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionSSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskRequest;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.test.TestRollbackTaskRequest;
import com.cedar.cp.dto.test.TestTaskRequest;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionServer;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskAccessor;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskEVO;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskLinkDAO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class TidyTaskEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient TidyTaskAccessor mTidyTaskAccessor;
   private TidyTaskEditorSessionSSO mSSO;
   private TidyTaskPK mThisTableKey;
   private TidyTaskEVO mTidyTaskEVO;


   public TidyTaskEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (TidyTaskPK)paramKey;

      TidyTaskEditorSessionSSO e;
      try {
         this.mTidyTaskEVO = this.getTidyTaskAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new TidyTaskEditorSessionSSO();
      TidyTaskImpl editorData = this.buildTidyTaskEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(TidyTaskImpl editorData) throws Exception {}

   private TidyTaskImpl buildTidyTaskEditData(Object thisKey) throws Exception {
      TidyTaskImpl editorData = new TidyTaskImpl(thisKey);
      editorData.setVisId(this.mTidyTaskEVO.getVisId());
      editorData.setDescription(this.mTidyTaskEVO.getDescription());
      editorData.setVersionNum(this.mTidyTaskEVO.getVersionNum());
      this.completeTidyTaskEditData(editorData);
      return editorData;
   }

   private void completeTidyTaskEditData(TidyTaskImpl editorData) throws Exception {
      ArrayList tasks = new ArrayList();
      TidyTaskLinkDAO dao = new TidyTaskLinkDAO();
      OrderedChildrenELO eList = dao.getOrderedChildren(this.mTidyTaskEVO.getTidyTaskId());
      int size = eList.getNumRows();

      for(int i = 0; i < size; ++i) {
         ArrayList row = new ArrayList();
         int type = ((Integer)eList.getValueAt(i, "Type")).intValue();
         switch(type) {
         case 1:
            row.add("Query");
            break;
         case 2:
            row.add("Package (report)");
            break;
         case 3:
            row.add("Java Class");
            break;
         case 4:
            row.add("Package (update)");
            break;
         default:
            row.add("Update");
         }

         row.add(eList.getValueAt(i, "Cmd"));
         tasks.add(row);
      }

      editorData.setTidyList(tasks);
   }

   public TidyTaskEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      TidyTaskEditorSessionSSO var4;
      try {
         this.mSSO = new TidyTaskEditorSessionSSO();
         TidyTaskImpl e = new TidyTaskImpl((Object)null);
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

   private void completeGetNewItemData(TidyTaskImpl editorData) throws Exception {}

   public TidyTaskPK insert(TidyTaskEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      TidyTaskImpl editorData = cso.getEditorData();

      TidyTaskPK e;
      try {
         this.mTidyTaskEVO = new TidyTaskEVO();
         this.mTidyTaskEVO.setVisId(editorData.getVisId());
         this.mTidyTaskEVO.setDescription(editorData.getDescription());
         this.updateTidyTaskRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mTidyTaskEVO = this.getTidyTaskAccessor().create(this.mTidyTaskEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("TidyTask", this.mTidyTaskEVO.getPK(), 1);
         e = this.mTidyTaskEVO.getPK();
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

   private void updateTidyTaskRelationships(TidyTaskImpl editorData) throws ValidationException {}

   private void completeInsertSetup(TidyTaskImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(TidyTaskImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public TidyTaskPK copy(TidyTaskEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      TidyTaskImpl editorData = cso.getEditorData();
      this.mThisTableKey = (TidyTaskPK)editorData.getPrimaryKey();

      TidyTaskPK var5;
      try {
         TidyTaskEVO e = this.getTidyTaskAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mTidyTaskEVO = e.deepClone();
         this.mTidyTaskEVO.setVisId(editorData.getVisId());
         this.mTidyTaskEVO.setDescription(editorData.getDescription());
         this.mTidyTaskEVO.setVersionNum(0);
         this.updateTidyTaskRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mTidyTaskEVO.prepareForInsert();
         this.mTidyTaskEVO = this.getTidyTaskAccessor().create(this.mTidyTaskEVO);
         this.mThisTableKey = this.mTidyTaskEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("TidyTask", this.mTidyTaskEVO.getPK(), 1);
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

   private void completeCopySetup(TidyTaskImpl editorData) throws Exception {}

   public void update(TidyTaskEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      TidyTaskImpl editorData = cso.getEditorData();
      this.mThisTableKey = (TidyTaskPK)editorData.getPrimaryKey();

      try {
         this.mTidyTaskEVO = this.getTidyTaskAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mTidyTaskEVO.setVisId(editorData.getVisId());
         this.mTidyTaskEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mTidyTaskEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mTidyTaskEVO.getVersionNum());
         }

         this.updateTidyTaskRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getTidyTaskAccessor().setDetails(this.mTidyTaskEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("TidyTask", this.mTidyTaskEVO.getPK(), 3);
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

   private void preValidateUpdate(TidyTaskImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(TidyTaskImpl editorData) throws Exception {}

   private void updateAdditionalTables(TidyTaskImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (TidyTaskPK)paramKey;

      try {
         this.mTidyTaskEVO = this.getTidyTaskAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mTidyTaskAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("TidyTask", this.mThisTableKey, 2);
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

   private TidyTaskAccessor getTidyTaskAccessor() throws Exception {
      if(this.mTidyTaskAccessor == null) {
         this.mTidyTaskAccessor = new TidyTaskAccessor(this.getInitialContext());
      }

      return this.mTidyTaskAccessor;
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

   public int issueTidyTask(EntityRef ref, int userId, int issuingTaskId) throws EJBException {
      try {
         TidyTaskEditorSessionServer e = new TidyTaskEditorSessionServer(this.getInitialContext(), true);
         TidyTaskImpl tt = e.getItemData(ref.getPrimaryKey()).getEditorData();
         String taskName = tt.getVisId();
         List commands = tt.getTaskList();
         TidyTaskRequest request = new TidyTaskRequest(taskName, commands);
         int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, issuingTaskId);
         this.mLog.debug(taskName, "taskId=" + taskId);
         return taskId;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10);
      }
   }

   public int issueTestTask(Integer testid, int userId) throws EJBException {
      try {
         new TidyTaskEditorSessionServer(this.getInitialContext(), true);
         TestTaskRequest request = new TestTaskRequest();
         return TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, 0);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new EJBException(var5);
      }
   }

   public int issueTestRollbackTask(int userId) throws EJBException {
      try {
         TestRollbackTaskRequest e = new TestRollbackTaskRequest();
         return TaskMessageFactory.issueNewTask(new InitialContext(), false, e, userId, 0);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new EJBException(var3);
      }
   }
}
