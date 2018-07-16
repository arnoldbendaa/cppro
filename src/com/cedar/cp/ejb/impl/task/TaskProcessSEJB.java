// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.task.Task;
import com.cedar.cp.dto.task.TaskDetails;
import com.cedar.cp.dto.task.TaskObjects;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.task.TaskEVO;
import com.cedar.cp.ejb.impl.task.TaskMessage;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TaskProcessSEJB implements SessionBean {

   private transient Log mLog = new Log(this.getClass());
   private SessionContext mContext;


   public int newTask(int userId, int taskType, TaskRequest request, long systemTime, int issuingTaskId) throws EJBException {
      try {
         int e = this.getNewTaskId();
         TaskEVO taskEVO = new TaskEVO(e, taskType, systemTime, request.getIdentifier(), userId, issuingTaskId, 0, false, (new TaskObjects(request, (Exception)null, (TaskCheckpoint)null)).getBytes(), (Timestamp)null, (Timestamp)null, "Queued");
         Timestamp now = new Timestamp((new Date()).getTime());
         taskEVO.setCreateDate(now);
         TaskDAO dao = new TaskDAO();
         dao.setDetails(taskEVO);
         dao.doCreate();
         return e;
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new EJBException("Failed to create new task:", var11);
      }
   }

   public EntityList getTasks() {
      try {
         return (new TaskDAO()).getTasks();
      } catch (Exception var2) {
         throw new EJBException(var2);
      }
   }
   
   public EntityList getPageTasks(int page, int offset) {
     try {
        return (new TaskDAO()).getPageTasks(page, offset);
     } catch (Exception var2) {
        throw new EJBException(var2);
     }
  }

   public TaskDetails getTask(TaskPK key) throws ValidationException {
      TaskDAO dao = new TaskDAO();
      dao.load(key);
      TaskEVO taskEVO = dao.getDetails("");
      List taskEal = null;
      Exception taskException = null;

      try {
         TaskObjects taskDetails = new TaskObjects(taskEVO.getObjects());
         taskEal = taskDetails.getRequest().getExclusiveAccessList();
         taskException = taskDetails.getException();
      } catch (Exception var7) {
         taskEal = null;
      }

      TaskDetails taskDetails1 = new TaskDetails(taskEVO.getTaskId(), taskEVO.getSystemTimeMillis(), taskEVO.getTaskName(), taskEVO.getUserId(), taskEVO.getOriginalTaskId(), taskEVO.getStatus(), taskException, taskEal, taskEVO.getCreateDate(), taskEVO.getEndDate(), taskEVO.getStep());
      return taskDetails1;
   }

   public EntityList getTaskEvents(int taskId) {
      return (new TaskDAO()).getEvents(taskId);
   }

   public void delete(TaskPK key) throws ValidationException {
      TaskDAO dao = new TaskDAO();
      TaskEVO evo = null;
      evo = dao.getDetails(key, "");
      switch(evo.getStatus()) {
      case 0:
         throw new ValidationException("Can\'t delete the task - it is in the CREATED state");
      case 1:
         throw new ValidationException("Can\'t delete the task - it is in the RECEIVED state");
      case 2:
         throw new ValidationException("Can\'t delete the task - it is in the DESPATCHED state");
      case 3:
         throw new ValidationException("Can\'t delete the task - it is in the STARTED state");
      case 4:
         if(evo.getMustComplete()) {
            throw new ValidationException("Task must be run to completion");
         }
         break;
      case 5:
    	  break;
      case 6:
         throw new ValidationException("Can\'t delete the task - it is waiting for issued tasks to complete");
      case 7:
         throw new ValidationException("Can\'t delete the task - it is in the COMMIT_STATE_STARTED state");
      case 8:
         throw new ValidationException("Can\'t delete the task - it is in the COMMIT_STATE_COMPLETE state");
      case 9:
    	  break;
      case 10:
         throw new ValidationException("The task is marked as unsafe deleted");
      }
      
      Task task = evo.createTask();
      if(task != null) {
         task.tidyActions();
      }

      dao.remove();
      this.sendEntityEventMessage("Task", evo.getPK(), 2);
      this.wakeUpDespatcher();
   }

   public void failTask(TaskPK key) throws ValidationException {
      TaskDAO dao = new TaskDAO();
      dao.load(key);
      TaskEVO evo = dao.getDetails("");
      switch(evo.getStatus()) {
      case 0:
    	  break;
      case 1:
    	  break;
      case 2:
    	  break;
      case 3:
    	  break;
      case 4:
          throw new ValidationException("The task is already marked as failed");
       case 5:
          throw new ValidationException("The task has already completed");
      case 6:
    	  break;
      case 7:
    	  break;
      case 8:
    	  break;
      case 9:
         throw new ValidationException("The task has already completed");
      case 10:
         throw new ValidationException("The task is marked as unsafe deleted");
      }
      
      try {
          evo.setStatus(4);
          dao.setDetails(evo);
          dao.store();
          this.sendEntityEventMessage("Task", evo.getPK(), 3);
          this.wakeUpDespatcher();
          return;
       } catch (ValidationException var5) {
          throw new CPException(var5.getMessage(), var5);
       }
   }

   public void unsafeDeleteTask(int userId, TaskPK key) throws ValidationException {
      TaskDAO dao = new TaskDAO();
      UserDAO udao = new UserDAO();
      TaskEVO evo = null;
      UserEVO uevo = null;

      try {
         evo = dao.getDetails(key, "");
      } catch (ValidationException var11) {
         throw new CPException("unable to get task", var11);
      }

      try {
         uevo = udao.getDetails(new UserPK(userId), "");
      } catch (ValidationException var10) {
      }

      switch(evo.getStatus()) {
      case 0:
    	  break;
      case 1:
    	  break;
      case 2:
    	  break;
      case 3:
    	  break;
      case 4:
    	  break;
      case 5:
          throw new ValidationException("The task has already completed");
      case 6:
    	  break;
      case 7:
    	  break;
      case 8:
    	  break;
      case 9:
         throw new ValidationException("The task has already completed");
      case 10:
         dao.remove();
         this.sendEntityEventMessage("Task", evo.getPK(), 2);
         this.wakeUpDespatcher();
         return;
      }
      
      Task task = evo.createTask();
      task.tidyActions();
      evo.setStatus(10);
      evo.setStep("unsafe delete by user " + uevo.getName());
      dao.setDetails(evo);

      try {
         dao.store();
      } catch (ValidationException var9) {
         throw new CPException(var9.getMessage(), var9);
      }

      if(evo.getOriginalTaskId() != 0) {
         dao.logEvent(evo.getOriginalTaskId(), 1, evo.getPK() + " - " + evo.getTaskName() + " - unsafe deleted");
      }

      this.sendEntityEventMessage("Task", evo.getPK(), 3);
      this.wakeUpDespatcher();
   }

   public void restartTask(int taskId) throws ValidationException, EJBException {
      TaskPK taskPK = new TaskPK(taskId);
      TaskDAO dao = new TaskDAO();
      dao.load(taskPK);
      TaskEVO evo = dao.getDetails("");
      switch(evo.getStatus()) {
      case 0:
         throw new ValidationException("Can\'t restart the task - it is in the CREATED state");
      case 1:
         throw new ValidationException("Can\'t restart the task - it is in the RECEIVED state");
      case 2:
         throw new ValidationException("Can\'t restart the task - it is in the DESPATCHED state");
      case 3:
         throw new ValidationException("Can\'t restart the task - it is already started");
      case 4:
          break;
      case 5:
         throw new ValidationException("The task has already completed");
      case 6:
         boolean e = true;
         EntityList el = dao.getIncompleteTasks();

         for(int i = 0; i < el.getNumRows(); ++i) {
            int originalTaskId = ((BigDecimal)el.getValueAt(i, "ORIGINAL_TASK_ID")).intValue();
            int status = ((BigDecimal)el.getValueAt(i, "STATUS")).intValue();
            if(originalTaskId == taskId && status != 5) {
               e = false;
            }
         }

         if(!e) {
            throw new ValidationException("Can\'t restart the task - it is waiting for issued tasks to complete");
         }
         break;
      case 7:
         throw new ValidationException("Can\'t restart the task - it is already started");
      case 8:
         throw new ValidationException("Can\'t restart the task - it is already started");
      case 9:
         throw new ValidationException("The task has already completed");
      case 10:
         throw new ValidationException("The task is marked as unsafe deleted");
      }
      
      try {
          TaskMessageFactory.restartTask(this.getInitialContext(), taskPK);
          this.sendEntityEventMessage("Task", taskPK, 3);
          this.mLog.debug("restartTask", taskPK.toString());
          return;
       } catch (Exception var10) {
          var10.printStackTrace();
          throw new EJBException(var10);
       }
   }

   public void wakeUpDespatcher() throws EJBException {
      try {
         TaskMessage e = new TaskMessage(5);
         JmsConnectionImpl jms = new JmsConnectionImpl(this.getInitialContext(), 1, "taskDespatcherQueue");
         jms.createSession();
         jms.send(e);
         jms.closeSession();
         jms.closeConnection();
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new EJBException(var3);
      }
   }

   public void setServiceStep(TaskPK key, String serviceStep) throws DuplicateNameValidationException, VersionValidationException, ValidationException {
      TaskDAO dao = new TaskDAO();
      dao.load(key);
      TaskEVO evo = dao.getDetails("");
      evo.setStep(serviceStep);
      dao.setDetails(evo);
      dao.store();
   }

   public int getNewTaskId() throws EJBException {
      return (new TaskDAO()).getNewTaskId();
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
      } catch (CPException var6) {
         var6.printStackTrace();
      }

   }

   private InitialContext getInitialContext() {
      try {
         return new InitialContext();
      } catch (NamingException var2) {
         throw new CPException(var2.getMessage(), var2);
      }
   }

   public void setSessionContext(SessionContext context) {
      this.mContext = context;
   }

   public void ejbRemove() {}

   public void ejbActivate() {}

   public void ejbPassivate() {}

   public void ejbCreate() {}
}
