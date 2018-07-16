// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.group.TaskGroupRow;
import com.cedar.cp.api.task.group.TaskGroupRowParam;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionCSO;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionSSO;
import com.cedar.cp.dto.task.group.TaskGroupImpl;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.dto.task.group.TaskGroupTaskRequest;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.task.group.TaskGroupAccessor;
import com.cedar.cp.ejb.impl.task.group.TaskGroupEVO;
import com.cedar.cp.ejb.impl.task.group.TaskRIChecker;
import com.cedar.cp.ejb.impl.task.group.TgRowEVO;
import com.cedar.cp.ejb.impl.task.group.TgRowParamEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.util.Collections;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class TaskGroupEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0><1>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0><1>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient TaskGroupAccessor mTaskGroupAccessor;
   private TaskGroupEditorSessionSSO mSSO;
   private TaskGroupPK mThisTableKey;
   private TaskGroupEVO mTaskGroupEVO;


   public TaskGroupEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (TaskGroupPK)paramKey;

      TaskGroupEditorSessionSSO e;
      try {
         this.mTaskGroupEVO = this.getTaskGroupAccessor().getDetails(this.mThisTableKey, "<0><1>");
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
      this.mSSO = new TaskGroupEditorSessionSSO();
      TaskGroupImpl editorData = this.buildTaskGroupEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(TaskGroupImpl editorData) throws Exception {
      Iterator i$ = this.mTaskGroupEVO.getTaskGroupRows().iterator();

      while(i$.hasNext()) {
         TgRowEVO evo = (TgRowEVO)i$.next();
         TaskGroupRow row = new TaskGroupRow();
         row.setType(Integer.valueOf(evo.getRowType()));
         row.setIndex(Integer.valueOf(evo.getTgRowIndex()));

         TaskGroupRowParam param;
         for(Iterator i$1 = evo.getTGRowsParams().iterator(); i$1.hasNext(); row.getParams().add(param)) {
            TgRowParamEVO paramEvo = (TgRowParamEVO)i$1.next();
            param = new TaskGroupRowParam();
            EntityList sKey;
            int eKey;
            EntityRef extValues;
            String listName;
            String var12;
            Object var13;
            String[] var14;
            switch(row.getType().intValue()) {
            case 1:
               param.setKey(paramEvo.getKey());
               param.setValue(paramEvo.getParam());
               break;
            case 3:
               sKey = this.getCPConnection().getListHelper().getAllAmmModels();

               for(eKey = 0; eKey < sKey.getNumRows(); ++eKey) {
                  extValues = (EntityRef)sKey.getValueAt(eKey, "AmmModel");
                  if(extValues.getTokenizedKey().equals(paramEvo.getParam())) {
                     param.setDisplayValue(sKey.getValueAt(eKey, "col2").toString());
                     break;
                  }
               }
            case 4:
               sKey = this.getCPConnection().getListHelper().getAllMappedModels();

               for(eKey = 0; eKey < sKey.getNumRows(); ++eKey) {
                  extValues = (EntityRef)sKey.getValueAt(eKey, "MappedModel");
                  if(extValues.getTokenizedKey().equals(paramEvo.getParam())) {
                     param.setDisplayValue(sKey.getValueAt(eKey, "Model").toString());
                     break;
                  }
               }
            case 2:
            default:
               var12 = paramEvo.getParam();
               var13 = this.getCPConnection().getEntityKeyFactory().getKeyFromTokens(var12);
               var14 = var12.split("[|]");
               listName = var14[0].substring(0, var14[0].length() - 2);
               param.setKey(paramEvo.getKey());
               param.setRef(this.getCPConnection().getListHelper().getEntityRef(var13, listName));
               break;
            case 5:
               if("ID".equals(paramEvo.getKey())) {
                  var12 = paramEvo.getParam();
                  var13 = this.getCPConnection().getEntityKeyFactory().getKeyFromTokens(var12);
                  var14 = var12.split("[|]");
                  listName = var14[0].substring(0, var14[0].length() - 2);
                  param.setKey(paramEvo.getKey());
                  param.setRef(this.getCPConnection().getListHelper().getEntityRef(var13, listName));
               } else {
                  param.setKey(paramEvo.getKey());
                  param.setValue(paramEvo.getParam());
               }
            }
         }

         editorData.getTGRows().add(row);
      }

      Collections.sort(editorData.getTGRows());
   }

   private TaskGroupImpl buildTaskGroupEditData(Object thisKey) throws Exception {
      TaskGroupImpl editorData = new TaskGroupImpl(thisKey);
      editorData.setVisId(this.mTaskGroupEVO.getVisId());
      editorData.setDescription(this.mTaskGroupEVO.getDescription());
      editorData.setLastSubmit(this.mTaskGroupEVO.getLastSubmit());
      this.completeTaskGroupEditData(editorData);
      return editorData;
   }

   private void completeTaskGroupEditData(TaskGroupImpl editorData) throws Exception {}

   public TaskGroupEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      TaskGroupEditorSessionSSO var4;
      try {
         this.mSSO = new TaskGroupEditorSessionSSO();
         TaskGroupImpl e = new TaskGroupImpl((Object)null);
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

   private void completeGetNewItemData(TaskGroupImpl editorData) throws Exception {}

   public TaskGroupPK insert(TaskGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      TaskGroupImpl editorData = cso.getEditorData();

      TaskGroupPK e;
      try {
         this.mTaskGroupEVO = new TaskGroupEVO();
         this.mTaskGroupEVO.setVisId(editorData.getVisId());
         this.mTaskGroupEVO.setDescription(editorData.getDescription());
         this.mTaskGroupEVO.setLastSubmit(editorData.getLastSubmit());
         this.updateTaskGroupRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mTaskGroupEVO = this.getTaskGroupAccessor().create(this.mTaskGroupEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("TaskGroup", this.mTaskGroupEVO.getPK(), 1);
         e = this.mTaskGroupEVO.getPK();
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

   private void updateTaskGroupRelationships(TaskGroupImpl editorData) throws ValidationException {}

   private void completeInsertSetup(TaskGroupImpl editorData) throws Exception {
      int rowId = 0;
      int rowIndex = 0;
      int rowParamId = 0;
      Iterator i$ = editorData.getTGRows().iterator();

      while(i$.hasNext()) {
         TaskGroupRow row = (TaskGroupRow)i$.next();
         TgRowEVO tgEvo = new TgRowEVO();
         --rowId;
         tgEvo.setTgRowId(rowId);
         tgEvo.setTgRowIndex(rowIndex++);
         tgEvo.setRowType(row.getType().intValue());
         Iterator i$1 = row.getParams().iterator();

         while(i$1.hasNext()) {
            TaskGroupRowParam rowParam = (TaskGroupRowParam)i$1.next();
            TgRowParamEVO paramEvo = new TgRowParamEVO();
            --rowParamId;
            paramEvo.setTgRowParamId(rowParamId);
            paramEvo.setKey(rowParam.getKey());
            paramEvo.setParam(rowParam.getSaveVale());
            tgEvo.addTGRowsParamsItem(paramEvo);
         }

         this.mTaskGroupEVO.addTaskGroupRowsItem(tgEvo);
      }

   }

   private void insertIntoAdditionalTables(TaskGroupImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public TaskGroupPK copy(TaskGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      TaskGroupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (TaskGroupPK)editorData.getPrimaryKey();

      TaskGroupPK var5;
      try {
         TaskGroupEVO e = this.getTaskGroupAccessor().getDetails(this.mThisTableKey, "<0><1>");
         this.mTaskGroupEVO = e.deepClone();
         this.mTaskGroupEVO.setVisId(editorData.getVisId());
         this.mTaskGroupEVO.setDescription(editorData.getDescription());
         this.mTaskGroupEVO.setLastSubmit(editorData.getLastSubmit());
         this.updateTaskGroupRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mTaskGroupEVO.prepareForInsert();
         this.mTaskGroupEVO = this.getTaskGroupAccessor().create(this.mTaskGroupEVO);
         this.mThisTableKey = this.mTaskGroupEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("TaskGroup", this.mTaskGroupEVO.getPK(), 1);
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

   private void completeCopySetup(TaskGroupImpl editorData) throws Exception {}

   public void update(TaskGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      TaskGroupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (TaskGroupPK)editorData.getPrimaryKey();

      try {
         this.mTaskGroupEVO = this.getTaskGroupAccessor().getDetails(this.mThisTableKey, "<0><1>");
         this.preValidateUpdate(editorData);
         this.mTaskGroupEVO.setVisId(editorData.getVisId());
         this.mTaskGroupEVO.setDescription(editorData.getDescription());
         this.mTaskGroupEVO.setLastSubmit(editorData.getLastSubmit());
         this.updateTaskGroupRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getTaskGroupAccessor().setDetails(this.mTaskGroupEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("TaskGroup", this.mTaskGroupEVO.getPK(), 3);
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

   private void preValidateUpdate(TaskGroupImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(TaskGroupImpl editorData) throws Exception {
      Iterator rowId = this.mTaskGroupEVO.getTaskGroupRows().iterator();

      while(rowId.hasNext()) {
         TgRowEVO rowIndex = (TgRowEVO)rowId.next();
         this.mTaskGroupEVO.deleteTaskGroupRowsItem(rowIndex.getPK());
      }

      int var11 = 0;
      int var12 = 0;
      int rowParamId = 0;
      Iterator i$ = editorData.getTGRows().iterator();

      while(i$.hasNext()) {
         TaskGroupRow row = (TaskGroupRow)i$.next();
         TgRowEVO tgEvo = new TgRowEVO();
         --var11;
         tgEvo.setTgRowId(var11);
         tgEvo.setTgRowIndex(var12++);
         tgEvo.setRowType(row.getType().intValue());
         Iterator i$1 = row.getParams().iterator();

         while(i$1.hasNext()) {
            TaskGroupRowParam rowParam = (TaskGroupRowParam)i$1.next();
            TgRowParamEVO paramEvo = new TgRowParamEVO();
            --rowParamId;
            paramEvo.setTgRowParamId(rowParamId);
            paramEvo.setKey(rowParam.getKey());
            paramEvo.setParam(rowParam.getSaveVale());
            tgEvo.addTGRowsParamsItem(paramEvo);
         }

         this.mTaskGroupEVO.addTaskGroupRowsItem(tgEvo);
      }

   }

   private void updateAdditionalTables(TaskGroupImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (TaskGroupPK)paramKey;

      try {
         this.mTaskGroupEVO = this.getTaskGroupAccessor().getDetails(this.mThisTableKey, "<0><1>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mTaskGroupAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("TaskGroup", this.mThisTableKey, 2);
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
         TaskRIChecker.isInUseTaskGroup(this.getCPConnection(), this.mTaskGroupEVO.getPK(), 100);
      } catch (ValidationException var2) {
         throw new ValidationException("Task Group " + var2.getMessage() + " is in use in TaskGroup ");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private TaskGroupAccessor getTaskGroupAccessor() throws Exception {
      if(this.mTaskGroupAccessor == null) {
         this.mTaskGroupAccessor = new TaskGroupAccessor(this.getInitialContext());
      }

      return this.mTaskGroupAccessor;
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

   public int submitGroup(EntityRef ref, int userId, int issuingTaskId) {
      try {
         this.setUserId(userId);
         TaskGroupTaskRequest e = new TaskGroupTaskRequest(ref);
         int taskId = TaskMessageFactory.issueNewTask(this.getInitialContext(), 1, false, e, this.getUserId(), issuingTaskId);
         int var6 = taskId;
         return var6;
      } catch (Exception var10) {
         var10.printStackTrace();
      } finally {
         this.setUserId(0);
      }

      return 0;
   }
}
