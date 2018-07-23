// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.task.TaskObjects;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.impl.task.TaskAccessor;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.task.TaskDespatcherMEJB$1;
import com.cedar.cp.ejb.impl.task.TaskEVO;
import com.cedar.cp.ejb.impl.task.TaskMessage;
import com.cedar.cp.ejb.impl.task.UserMailer;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.ResourceLock;
import com.cedar.cp.util.ResourceLockHandler;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb3.annotation.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;

@MessageDriven(name = "TaskDespatcherMEJB", activationConfig = {
@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
@ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true"),
@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/cp/taskDespatcherQueue"),
@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class TaskDespatcherMEJB implements MessageDrivenBean, MessageListener {

   private Context _jndiContext;
   private transient Log mLog = new Log(this.getClass());
   private transient TaskAccessor mTaskAccessor;
   private TaskMessage mTaskMessage;
   private JmsConnection mDespatcherJmsConnection;
   private JmsConnection mRunnerJmsConnection;


   public void onMessage(Message message_) {
      ObjectMessage om = (ObjectMessage)message_;

      try {
         Serializable e = om.getObject();
         if(e instanceof String) {
            this.processStringMessage((String)e);
         } else {
            this.mTaskMessage = (TaskMessage)e;
            this.mLog.debug("onMessage", this.mTaskMessage.toString());
            this.processTaskMessage();
         }
      } catch (Exception var4) {
         this.mLog.error("onMessage", "", var4);
      }

   }

   private void processStringMessage(String msg) throws Exception {
      if(msg.indexOf("ContextListener") > -1) {
         this.appServerStartUpChecks();
      } else {
         this.mLog.info("checkTaskQueue", "initiated by " + msg);
      }

      this.issueWaitingTasks();
   }

   private void appServerStartUpChecks() {
      int runningTasks = (new TaskDAO()).resetRunningTasks();
      if(runningTasks > 0) {
         this.mLog.info("appServerStartUpChecks", runningTasks + " tasks have been changed from running to failed");
      }

      int newTasks = (new TaskDAO()).resetNewTasks();
      if(newTasks > 0) {
         this.mLog.info("appServerStartUpChecks", newTasks + " tasks have been changed from created/despatch to received");
      }

   }

   private void processTaskMessage() throws Exception {
      switch(this.mTaskMessage.getMessageType()) {
      case 2:
         this.checkTaskReceived();
         break;
      case 3:
         TaskEVO taskEvo = this.getTaskAccessor().getDetails(this.mTaskMessage.getTaskPK(), "");
         if(taskEvo.getStatus() == 7) {
            this.mLog.info("processTaskMessage", "ignored request to run " + this.mTaskMessage.getTaskPK());
         } else {
            this.sendRunnerMessage(this.mTaskMessage, (Integer)null);
         }
         break;
      case 4:
         this.checkTaskFinished();
         break;
      case 5:
         this.checkTaskQueue();
         break;
      case 6:
         this.failTask(this.mTaskMessage);
         break;
      case 7:
      default:
         this.mLog.warn("processMessage", "unexpected message type: " + this.mTaskMessage.getMessageType());
         break;
      case 8:
         this.checkTaskFinished();
         break;
      case 9:
         this.checkTaskGroupRestart();
      }

   }

   private void checkTaskReceived() throws Exception {
      TaskEVO taskEvo = this.getTaskAccessor().getDetails(this.mTaskMessage.getTaskPK(), "");
      if(taskEvo.getStatus() == 2) {
         if(this.mTaskMessage.getRetryCount() < 10) {
            this.mLog.warn("checkTaskReceived", "task <" + taskEvo.getTaskId() + "> waiting for task to enter received state" + " attempt " + (this.mTaskMessage.getRetryCount() + 1));
            this.mTaskMessage.incrementRetryCount();
            this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(1000 * this.mTaskMessage.getRetryCount()));
            return;
         }

         this.mLog.warn("checkTaskReceived", "task <" + taskEvo.getTaskId() + "> task has not entered received state");
         taskEvo.setStatus(4);
         taskEvo.setStep("task has not entered received state");
         this.mTaskMessage.clearRetryCount();
         this.failTask(this.mTaskMessage);
      } else if(taskEvo.getStatus() != 1) {
         this.mLog.info("checkTaskReceived", "task <" + taskEvo.getTaskId() + "> task has unexpectedly entered status " + TaskEVO.getStatusString(taskEvo.getStatus()));
      }

      this.checkTaskQueue();
   }

   private void checkTaskFinished() throws Exception {
      TaskEVO taskEvo = this.getTaskAccessor().getDetails(this.mTaskMessage.getTaskPK(), "");
      if(taskEvo.getStatus() != 5 && taskEvo.getStatus() != 9) {
         if(taskEvo.getStatus() == 8) {
            if(this.mTaskMessage.getRetryCount() < 10) {
               this.mLog.warn("checkTaskFinished", "task <" + taskEvo.getTaskId() + "> waiting for task to commit final state" + " attempt " + (this.mTaskMessage.getRetryCount() + 1));
               this.mTaskMessage.incrementRetryCount();
               this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(1000 * this.mTaskMessage.getRetryCount()));
               return;
            }

            this.mLog.warn("checkTaskFinished", "task <" + taskEvo.getTaskId() + "> task has not commited final state");
            taskEvo.setStatus(4);
            taskEvo.setStep("task has not commited final state");
            this.mTaskMessage.clearRetryCount();
            this.failTask(this.mTaskMessage);
         }
      } else {
         if(this.mTaskMessage.getMessageType() == 8) {
            this.getTaskAccessor().remove(this.mTaskMessage.getTaskPK());
            this.mLog.info("processMessage", "auto-deleted " + this.mTaskMessage.getTaskPK());
         }

         this.checkTaskQueue();
      }

   }

   private void checkTaskQueue() throws Exception {
      EntityListImpl incompleteTasks = (EntityListImpl)this.getTaskAccessor().getIncompleteTasks();
      if(this.mTaskMessage.getMessageType() == 2) {
         this.mLog.debug("checkTaskQueue", "initiated by despatch of " + this.mTaskMessage.getTaskPK().getTaskId());

         try {
            this.issueTask(this.mTaskMessage.getTaskPK().getTaskId(), incompleteTasks);
         } catch (FinderException var6) {
            if(this.mTaskMessage.getRetryCount() < 4) {
               this.mLog.warn("checkTaskQueue", this.mTaskMessage.getTaskPK().getTaskId() + " - attempt " + (this.mTaskMessage.getRetryCount() + 1) + ": detected possible nonXact message - resending");
               this.mTaskMessage.incrementRetryCount();
               this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(100 * this.mTaskMessage.getRetryCount()));
            } else {
               this.mLog.error("checkTaskQueue", this.mTaskMessage.getTaskPK().getTaskId() + " - final attempt " + (this.mTaskMessage.getRetryCount() + 1) + ": detected possible nonXact message - giving up");
               throw new IllegalStateException(var6.getMessage());
            }
         }
      } else {
         int i;
         if(this.mTaskMessage.getMessageType() == 4) {
            this.mLog.debug("checkTaskQueue", "initiated by end of " + this.mTaskMessage.getTaskPK().getTaskId());

            for(int restartableTaskGroupIds = 0; restartableTaskGroupIds < incompleteTasks.getNumRows(); ++restartableTaskGroupIds) {
               i = ((BigDecimal)incompleteTasks.getValueAt(restartableTaskGroupIds, "TASK_ID")).intValue();
               if(i == this.mTaskMessage.getTaskPK().getTaskId()) {
                  incompleteTasks.remove(restartableTaskGroupIds);
                  break;
               }
            }

            this.restartIssuingTask(this.mTaskMessage.getTaskPK(), incompleteTasks);
            this.issueWaitingTasks(incompleteTasks);
         } else {
            EntityList var7 = this.getTaskAccessor().getRestartableTaskGroups();

            for(i = 0; i < var7.getNumRows(); ++i) {
               int tgId = ((BigDecimal)var7.getValueAt(i, "TASK_ID")).intValue();
               this.mLog.debug("checkTaskGroupRestart", "no incomplete issued tasks for TaskId=" + tgId + " - sending restart message");
               TaskMessage m = new TaskMessage(3, new TaskPK(tgId), this.mTaskMessage.getUserName());
               this.sendRunnerMessage(m, (Integer)null);
            }

            this.mLog.debug("checkTaskQueue", "initiated by wake-up message");
            this.issueWaitingTasks(incompleteTasks);
         }

      }
   }

   private void issueWaitingTasks() throws Exception {
      EntityList incompleteTasks = this.getTaskAccessor().getIncompleteTasks();
      this.issueWaitingTasks(incompleteTasks);
   }

   private void issueWaitingTasks(EntityList incompleteTasks) throws Exception {
      for(int i = 0; i < incompleteTasks.getNumRows(); ++i) {
         int taskId = ((BigDecimal)incompleteTasks.getValueAt(i, "TASK_ID")).intValue();
         int stat = ((BigDecimal)incompleteTasks.getValueAt(i, "STATUS")).intValue();
         String taskName = (String)incompleteTasks.getValueAt(i, "TASK_NAME");
         if(stat == 1) {
            boolean taskIssued = this.issueTask(taskId, taskName, incompleteTasks);
            if(taskIssued) {
               TaskMessage msg = new TaskMessage(5);
               this.sendDespatchMessage(msg, Integer.valueOf(1000));
               return;
            }
         }
      }

   }

   private void checkTaskGroupRestart() throws Exception {
      TaskEVO groupEvo = this.getTaskAccessor().getDetails(this.mTaskMessage.getTaskPK(), "");
      if(this.getTaskAccessor().areAllIssuedTasksComplete(groupEvo.getTaskId(), groupEvo.getTaskId())) {
         if(groupEvo.getTaskType() == 1) {
            if(groupEvo.getStatus() == 6) {
               this.mLog.debug("checkTaskGroupRestart", "no incomplete issued tasks for TaskId=" + groupEvo.getTaskId() + " - sending restart message");
               TaskMessage m = new TaskMessage(3, new TaskPK(groupEvo.getTaskId()), this.mTaskMessage.getUserName());
               this.sendRunnerMessage(m, (Integer)null);
            } else {
               this.logTaskProgressEvent(groupEvo.getTaskId(), "** TaskDespatcherMEJB: status was expected to be 6 - found <" + groupEvo.getStatus() + ">");
            }
         }

      }
   }

   private void restartIssuingTask(TaskPK finishedTaskPK, EntityList incompleteTasks) throws Exception {
      TaskEVO finishedTaskEvo = this.getTaskAccessor().getDetails(finishedTaskPK, "");
      if(finishedTaskEvo.getOriginalTaskId() != 0) {
         this.logTaskProgressEvent(finishedTaskEvo.getOriginalTaskId(), finishedTaskPK.toString() + " - " + finishedTaskEvo.getTaskName() + " - " + TaskEVO.getStatusString(finishedTaskEvo.getStatus()));
         if(!this.getTaskAccessor().areAllIssuedTasksComplete(finishedTaskEvo.getOriginalTaskId(), finishedTaskEvo.getTaskId())) {
            this.mLog.debug("restartIssuingTask", "issued tasks are still outstanding for " + finishedTaskEvo.getOriginalTaskId());
         } else {
            TaskEVO originalTaskEvo = this.getTaskAccessor().getDetails(new TaskPK(finishedTaskEvo.getOriginalTaskId()), "");
            if(originalTaskEvo.getTaskType() == 1) {
               if(originalTaskEvo.getStatus() == 6) {
                  this.mLog.debug("restartIssuingTask", "all issued tasks complete for TaskId=" + originalTaskEvo.getTaskId() + " - sending restart message");
                  TaskMessage m = new TaskMessage(3, new TaskPK(originalTaskEvo.getTaskId()), this.mTaskMessage.getUserName());
                  this.sendRunnerMessage(m, (Integer)null);
               } else {
                  this.logTaskProgressEvent(originalTaskEvo.getTaskId(), "** TaskDespatcherMEJB: status was expected to be 6 - found <" + originalTaskEvo.getStatus() + ">");
               }
            }

         }
      }
   }

   private boolean issueTask(int taskId_, EntityList incompleteTasks) throws Exception {
      return this.issueTask(taskId_, this.mTaskMessage.getUserName(), incompleteTasks);
   }

   private boolean issueTask(int taskId_, String userName_, EntityList incompleteTasks) throws Exception {
      TaskEVO taskEVO = this.getTaskAccessor().getDetails(new TaskPK(taskId_), "");
      ResourceLockHandler rlh = this.buildResourceLocks(incompleteTasks);
      if(taskEVO.getStatus() != 1) {
         return false;
      } else {
         TaskObjects taskobjects = null;

         try {
            taskobjects = new TaskObjects(taskEVO.getObjects());
         } catch (Exception var10) {
            this.mLog.debug("issueTask", "*** unable to unpack task objects for task <" + taskId_ + ">");
            return false;
         }

//         List eal = taskobjects.getRequest().getExclusiveAccessList();
//         if(eal != null) {
//            ResourceLock msg = rlh.getFirstConflict(String.valueOf(taskId_), eal);
//            if(rlh.getFirstConflict(String.valueOf(taskId_), eal) != null) {
//               String lockMsg = "locked out by task " + msg.getOwner() + ", resource " + msg.getResource();
//               this.mLog.info("issueWaitingTasks", taskId_ + " " + lockMsg);
//               if(!taskEVO.getStep().equals(lockMsg)) {
//                  this.logTaskProgressEvent(taskId_, lockMsg);
//                  taskEVO.setStep(lockMsg);
//                  this.getTaskAccessor().setDetails(taskEVO);
//               }
//
//               return false;
//            }
//         }

         taskEVO.setStatus(2);
         this.getTaskAccessor().setDetails(taskEVO);
         this.mLog.info("issueTask", "issuing " + taskId_);
         TaskMessage msg1 = new TaskMessage(3, taskEVO.getPK(), userName_);
         this.sendRunnerMessage(msg1, (Integer)null);
         return true;
      }
   }

   private ResourceLockHandler buildResourceLocks(EntityList incompleteTasks) {
      Map resourceLocks = Collections.synchronizedMap(new HashMap());

      for(int rlh = 0; rlh < incompleteTasks.getNumRows(); ++rlh) {
         int taskId = ((BigDecimal)incompleteTasks.getValueAt(rlh, "TASK_ID")).intValue();
         int stat = ((BigDecimal)incompleteTasks.getValueAt(rlh, "STATUS")).intValue();
         byte[] objects = (byte[])((byte[])incompleteTasks.getValueAt(rlh, "OBJECTS"));
         if(stat == 3 || stat == 4) {
            TaskObjects tro = null;

            try {
               tro = new TaskObjects(objects);
            } catch (Exception var11) {
               Exception eal = var11;
               this.mLog.debug("buildResourceLocks", "*** unable to unpack task objects for task <" + taskId + ">");

               try {
                  TaskEVO e2 = this.getTaskAccessor().getDetails(new TaskPK(taskId), "");
                  e2.setStatus(4);
                  this.getTaskAccessor().setDetails(e2);
                  this.getTaskAccessor().logEvent(taskId, 1, "*** unable to unpack task objects:");
                  this.logExceptionEvent(taskId, eal);
               } catch (Exception var10) {
                  ;
               }
               continue;
            }

            if(tro.getRequest() != null) {
               List var13 = tro.getRequest().getExclusiveAccessList();
               if(var13 != null) {
                  this.mLog.debug("buildResourceLocks", taskId + " eal=" + StringUtils.arrayToString(var13, ","));
                  resourceLocks.put(String.valueOf(taskId), tro.getRequest().getExclusiveAccessList());
               }
            }
         }
      }

      ResourceLockHandler var12 = new ResourceLockHandler();
      var12.setCurrentLocks(resourceLocks);
      return var12;
   }

   private void failTask(TaskMessage failMessage) {
      try {
         TaskDAO e = new TaskDAO();
         TaskEVO taskEvo = e.getDetails(failMessage.getTaskPK(), "");
         taskEvo.setStatus(4);
         e.setDetails(taskEvo);
         e.store();
         if(failMessage.getException() != null) {
            this.logExceptionEvent(failMessage.getTaskPK().getTaskId(), failMessage.getException());
         }

         this.mailUserTaskFailed(failMessage.getUserId(), failMessage.getTaskPK().getTaskId(), failMessage.getTaskName(), failMessage.getException());
         this.sendEntityEventMessage("Task", failMessage.getTaskPK(), 3);
         TaskMessage runMessage = new TaskMessage(5);
         this.sendDespatchMessage(runMessage, Integer.valueOf(1000));
      } catch (Exception var5) {
         if(failMessage.getRetryCount() > 4) {
            this.mLog.error("failTask", "unable to note failed task - giving up", var5);
            this.mLog.error("failtask", "original exception", failMessage.getException());
         } else {
            this.mLog.warn("failTask", "unable to note failed task - retrying");
            failMessage.incrementRetryCount();
            this.sendDespatchMessage(failMessage, Integer.valueOf(100 * failMessage.getRetryCount()));
         }
      }

   }

   private void logExceptionEvent(int taskId, Throwable error) throws Exception {
      String stackTrace = " ";
      String exceptionMessage = " ";
      if(error == null) {
         exceptionMessage = "";
      } else {
         exceptionMessage = error.getMessage();
         if(exceptionMessage != null && exceptionMessage.length() > 255) {
            exceptionMessage = exceptionMessage.substring(0, 255);
         }

         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         error.printStackTrace(new PrintStream(bos));
         stackTrace = bos.toString();
         if(stackTrace != null) {
            stackTrace = StringUtils.replaceAll(stackTrace, "\r", "");
            if(stackTrace.length() > 4000) {
               stackTrace = stackTrace.substring(0, 4000);
            }
         }
      }

      this.getTaskAccessor().logEvent(taskId, 5, stackTrace);
   }

   private void logTaskProgressEvent(int taskId, String eventText) throws Exception {
      this.getTaskAccessor().logEvent(taskId, 1, eventText);
   }

   public void sendDespatchMessage(TaskMessage msg_, Integer delayMillis) {
      this.mDespatcherJmsConnection.createSession();
      if(delayMillis == null) {
         this.mDespatcherJmsConnection.send(msg_);
      } else {
         this.mDespatcherJmsConnection.send(msg_, (long)delayMillis.intValue() + System.currentTimeMillis());
      }

      this.mDespatcherJmsConnection.closeSession();
   }

   public void sendRunnerMessage(TaskMessage msg_, Integer delayMillis) {
      this.mRunnerJmsConnection.createSession();
      if(delayMillis == null) {
         this.mRunnerJmsConnection.send(msg_);
      } else {
         this.mRunnerJmsConnection.send(msg_, (long)delayMillis.intValue() + System.currentTimeMillis());
      }

      this.mRunnerJmsConnection.closeSession();
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("sendEntityEventMessage", em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (CPException var6) {
         var6.printStackTrace();
      }

   }

   public void ejbRemove() {
      try {
         this.mDespatcherJmsConnection.closeConnection();
         this.mRunnerJmsConnection.closeConnection();
         this._jndiContext.close();
      } catch (Exception var2) {
         ;
      }

   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   public void ejbCreate() {
      try {
         this.mDespatcherJmsConnection = new JmsConnectionImpl(this._jndiContext, 1, "taskDespatcherQueue");
         this.mRunnerJmsConnection = new JmsConnectionImpl(this._jndiContext, 1, "taskRunnerQueue");
      } catch (Exception var2) {
         throw new RuntimeException(var2.getMessage());
      }
   }

   public void setMessageDrivenContext(MessageDrivenContext mdc_) {
      try {
         this._jndiContext = new InitialContext();
      } catch (NamingException var3) {
         throw new EJBException(var3);
      }
   }

   private InitialContext getInitialContext() {
      return (InitialContext)this._jndiContext;
   }

   private TaskAccessor getTaskAccessor() {
      if(this.mTaskAccessor == null) {
         this.mTaskAccessor = new TaskAccessor(this.getInitialContext());
      }

      return this.mTaskAccessor;
   }

   private void mailUserTaskFailed(int userId, int taskId, String taskName, Throwable e) {
      UserMailer mailer = null;

      try {
         mailer = new UserMailer(new TaskDespatcherMEJB$1(this, userId));
         mailer.sendUserMailMessage(MessageFormat.format("Task Id {0} {1} failed", new Object[]{String.valueOf(taskId), taskName}), MessageFormat.format("The following exception was thrown:\n{0}", new Object[]{e.getMessage()}));
      } catch (Throwable var10) {
         this.mLog.error("mailUserTaskFailed", var10.getMessage());
      } finally {
         if(mailer != null) {
            mailer.closeDown();
         }

      }

   }
}
