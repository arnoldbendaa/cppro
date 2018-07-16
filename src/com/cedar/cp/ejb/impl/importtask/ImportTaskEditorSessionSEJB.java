package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.importtask.OrderedChildrenELO;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionCSO;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionSSO;
import com.cedar.cp.dto.importtask.ImportTaskImpl;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.dto.importtask.ImportTaskRequest;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.test.TestRollbackTaskRequest;
import com.cedar.cp.dto.test.TestTaskRequest;
import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionServer;
import com.cedar.cp.ejb.impl.importtask.ImportTaskAccessor;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEVO;
import com.cedar.cp.ejb.impl.importtask.ImportTaskLinkDAO;
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

public class ImportTaskEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ImportTaskAccessor mImportTaskAccessor;
   private ImportTaskEditorSessionSSO mSSO;
   private ImportTaskPK mThisTableKey;
   private ImportTaskEVO mImportTaskEVO;


   public ImportTaskEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ImportTaskPK)paramKey;

      ImportTaskEditorSessionSSO e;
      try {
         this.mImportTaskEVO = this.getImportTaskAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new ImportTaskEditorSessionSSO();
      ImportTaskImpl editorData = this.buildImportTaskEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ImportTaskImpl editorData) throws Exception {}

   private ImportTaskImpl buildImportTaskEditData(Object thisKey) throws Exception {
      ImportTaskImpl editorData = new ImportTaskImpl(thisKey);
      editorData.setVisId(this.mImportTaskEVO.getVisId());
      editorData.setDescription(this.mImportTaskEVO.getDescription());
      editorData.setVersionNum(this.mImportTaskEVO.getVersionNum());
      this.completeImportTaskEditData(editorData);
      return editorData;
   }

   private void completeImportTaskEditData(ImportTaskImpl editorData) throws Exception {
      ArrayList tasks = new ArrayList();
      ImportTaskLinkDAO dao = new ImportTaskLinkDAO();
      OrderedChildrenELO eList = dao.getOrderedChildren(this.mImportTaskEVO.getImportTaskId());
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

      editorData.setImportList(tasks);
   }

   public ImportTaskEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ImportTaskEditorSessionSSO var4;
      try {
         this.mSSO = new ImportTaskEditorSessionSSO();
         ImportTaskImpl e = new ImportTaskImpl((Object)null);
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

   private void completeGetNewItemData(ImportTaskImpl editorData) throws Exception {}

   public ImportTaskPK insert(ImportTaskEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ImportTaskImpl editorData = cso.getEditorData();

      ImportTaskPK e;
      try {
         this.mImportTaskEVO = new ImportTaskEVO();
         this.mImportTaskEVO.setVisId(editorData.getVisId());
         this.mImportTaskEVO.setDescription(editorData.getDescription());
         this.updateImportTaskRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mImportTaskEVO = this.getImportTaskAccessor().create(this.mImportTaskEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ImportTask", this.mImportTaskEVO.getPK(), 1);
         e = this.mImportTaskEVO.getPK();
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

   private void updateImportTaskRelationships(ImportTaskImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ImportTaskImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ImportTaskImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ImportTaskPK copy(ImportTaskEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ImportTaskImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ImportTaskPK)editorData.getPrimaryKey();

      ImportTaskPK var5;
      try {
         ImportTaskEVO e = this.getImportTaskAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mImportTaskEVO = e.deepClone();
         this.mImportTaskEVO.setVisId(editorData.getVisId());
         this.mImportTaskEVO.setDescription(editorData.getDescription());
         this.mImportTaskEVO.setVersionNum(0);
         this.updateImportTaskRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mImportTaskEVO.prepareForInsert();
         this.mImportTaskEVO = this.getImportTaskAccessor().create(this.mImportTaskEVO);
         this.mThisTableKey = this.mImportTaskEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ImportTask", this.mImportTaskEVO.getPK(), 1);
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

   private void completeCopySetup(ImportTaskImpl editorData) throws Exception {}

   public void update(ImportTaskEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ImportTaskImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ImportTaskPK)editorData.getPrimaryKey();

      try {
         this.mImportTaskEVO = this.getImportTaskAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mImportTaskEVO.setVisId(editorData.getVisId());
         this.mImportTaskEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mImportTaskEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mImportTaskEVO.getVersionNum());
         }

         this.updateImportTaskRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getImportTaskAccessor().setDetails(this.mImportTaskEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ImportTask", this.mImportTaskEVO.getPK(), 3);
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

   private void preValidateUpdate(ImportTaskImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ImportTaskImpl editorData) throws Exception {}

   private void updateAdditionalTables(ImportTaskImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ImportTaskPK)paramKey;

      try {
         this.mImportTaskEVO = this.getImportTaskAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mImportTaskAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ImportTask", this.mThisTableKey, 2);
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

   private ImportTaskAccessor getImportTaskAccessor() throws Exception {
      if(this.mImportTaskAccessor == null) {
         this.mImportTaskAccessor = new ImportTaskAccessor(this.getInitialContext());
      }

      return this.mImportTaskAccessor;
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

   public int issueImportTask(EntityRef ref, int userId, int issuingTaskId, String externalSystemVisId) throws EJBException {
      try {
         ImportTaskEditorSessionServer e = new ImportTaskEditorSessionServer(this.getInitialContext(), true);
         ImportTaskImpl tt = e.getItemData(ref.getPrimaryKey()).getEditorData();
         String taskName = tt.getVisId();
//         List commands = tt.getTaskList();
         System.out.println(externalSystemVisId);
         ImportTaskRequest request = new ImportTaskRequest(taskName, externalSystemVisId);
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
         new ImportTaskEditorSessionServer(this.getInitialContext(), true);
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
