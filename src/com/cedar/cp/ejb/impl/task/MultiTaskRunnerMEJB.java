// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb3.annotation.ResourceAdapter;

//import com.adv.stats.perf.J2EEPerformanceLogger;//arnold
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.globalmapping2.ImportGlobalMappedModel2TaskRequest;
import com.cedar.cp.dto.task.Task;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.base.async.importglobaltask2.ImportGlobalMappedModel2Task;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;


@MessageDriven(name = "multiTaskRunnerMEJB", activationConfig = {
@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
@ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true"),
@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/cp/multiTaskRunnerQueue"),
@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class MultiTaskRunnerMEJB implements MessageDrivenBean, MessageListener {

   private transient Context _jndiContext;
   private transient Log mLog = new Log(this.getClass());
   private transient TaskAccessor mTaskAccessor;
   private transient TaskMessage mTaskMessage;
   private transient TaskEVO mTaskEVO;
   private transient String mUserName;
//   private transient J2EEPerformanceLogger mPLogger = new J2EEPerformanceLogger();//arnold
   private JmsConnection mDespatcherJmsConnection;
   private JmsConnection mRunnerJmsConnection;
   private ArrayList< Map<String, Integer> > companiesList = null;
   private ArrayList< Integer > mCompanies = null;
   private int financeCubeID;

   public void onMessage(Message message_) {
      ObjectMessage om = (ObjectMessage)message_;

      try {
         if(message_.getJMSExpiration() > 0L) {
            if(!message_.getJMSRedelivered()) {
               this.mLog.warn("onMessage", "Expiry=" + new Date(message_.getJMSExpiration()));
            }

            if(message_.getJMSExpiration() <= (new Date()).getTime()) {
               return;
            }
         }
      } catch (JMSException var5) {
         this.mLog.error("onMessage", var5);
         return;
      }

      try {
         this.mTaskMessage = (TaskMessage)om.getObject();
         if(this.mTaskMessage.getMessageType() == 3) {
            this.mLog.debug("onMessage", "received run-task message " + this.mTaskMessage.getTaskPK());
            this.runTask(message_.getJMSRedelivered());
         } else {
            this.mLog.warn("processMessage", "unexpected message type: " + this.mTaskMessage.getMessageType());
         }
      } catch (Throwable var4) {
         var4.printStackTrace();
         this.handleException(message_, var4);
      }

   }

   private void handleException(Message message_, Throwable throwable) {
      Throwable t;
      TaskMessage failMessage;
      try {
         try {
            message_.setJMSExpiration((new Date()).getTime());
         } catch (JMSException var7) {
            this.mLog.error("handleException", "unable to set message expiration", var7);
         }

         t = throwable;
         if(throwable instanceof EJBException && throwable.getCause() != null) {
            t = throwable.getCause();
         }

         if(t.getMessage() != null && t.getMessage().indexOf("Deadlock detected, timing out call after") > -1 && this.mTaskMessage.getRetryCount() < 10) {
            t.printStackTrace();
            this.hilightedLog("handleException", "<" + this.mTaskMessage.getTaskPK().getTaskId() + "> deadlock detected - retrying");

            try {
               this.logTaskProgressEvent("deadlock detected - retrying");
            } catch (Exception var6) {
               ;
            }

            this.mTaskMessage.incrementRetryCount();
            this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(100 * this.mTaskMessage.getRetryCount()));
         } else {
            this.hilightedLog("handleException", "<" + this.mTaskMessage.getTaskPK().getTaskId() + "> failed");
            failMessage = new TaskMessage(6, this.mTaskEVO.getPK(), this.mUserName, this.mTaskEVO.getStep(), this.mTaskEVO.getUserId(), this.mTaskEVO.getTaskName(), throwable);
            this.sendDespatchMessage(failMessage, (Integer)null);
         }
      } catch (Throwable var8) {
         t = var8;
         throwable.printStackTrace();
         var8.printStackTrace();
         this.hilightedLog("handleException", "unable to handle " + throwable.getClass().getName() + " - it caused " + var8.getClass().getName());

         try {
            this.logTaskProgressEvent("");
            this.logTaskExceptionEvent(this.getStackTrace(throwable));
            this.logTaskProgressEvent("");
            this.logTaskInfoEvent("the following exception occured during exception handling:");
            this.logTaskInfoEvent(this.getStackTrace(t));
            this.logTaskProgressEvent("");
            this.logTaskInfoEvent("- the above exception occured during the handling of the orignal task exception");
            (new TaskDAO()).autonomousUpdateTask(this.mTaskEVO.getTaskId(), 4, this.mTaskEVO.getSystemTimeMillis(), "failed");
         } catch (Exception var5) {
            this.hilightedLog("handleException", "can\'t mark task as failed " + var5.getMessage());
         }

         failMessage = new TaskMessage(6, this.mTaskEVO.getPK(), this.mUserName, this.mTaskEVO.getStep(), this.mTaskEVO.getUserId(), this.mTaskEVO.getTaskName(), throwable);
         this.sendDespatchMessage(failMessage, (Integer)null);
      }

      if(throwable instanceof EJBException) {
         throw (EJBException)throwable;
      } else if(throwable instanceof Exception) {
         throw new EJBException((Exception)throwable);
      } else {
         throw new EJBException(throwable.getMessage());
      }
   }

   private String getStackTrace(Throwable t) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      t.printStackTrace(new PrintStream(bos));
      String stackTrace = bos.toString();
      if(stackTrace != null) {
         stackTrace = StringUtils.replaceAll(stackTrace, "\r", "");
         if(stackTrace.length() > 4000) {
            stackTrace = stackTrace.substring(0, 4000);
         }
      }

      return stackTrace;
   }

   private void runTask(boolean redelivered) throws Exception {
      this.mUserName = this.mTaskMessage.getUserName();
      TaskPK taskPK = this.mTaskMessage.getTaskPK();
      this.mTaskEVO = this.getTaskAccessor().getDetails(taskPK, "");
      
      if ( (this.mTaskEVO.getTaskName().equals("ImportGlobalModel")) && (this.companiesList == null) ) {
    	  this.setAllCompanies( this.mTaskEVO.getRequest() );
      }
      
      if(this.mTaskEVO.getStatus() == 7) {
         this.mLog.warn("runTask", this.getTaskMessagePrefix() + " attempt to start uncommited task");
         this.logTaskProgressEvent("attempt to start task that has not yet committed");
      } else if(this.mTaskEVO.getStatus() == 8) {
         this.mLog.warn("runTask", this.getTaskMessagePrefix() + " attempt to start task which is waiting to commit complete state");
         this.logTaskProgressEvent("attempt to start task which is waiting to commit complete state");
      } else if(this.mTaskEVO.getStatus() == 4 && redelivered) {
         this.mLog.warn("runTask", "ignored redelivered failed task <" + this.mTaskEVO.getTaskId() + ">");
      } else if(this.mTaskEVO.getStatus() != 5 && this.mTaskEVO.getStatus() != 9) {
         Task task = this.mTaskEVO.createTask();
         boolean firstRun = task.getCheckpoint() == null;
         int checkpointNumber = firstRun?0:task.getCheckpointNumber();
         if(this.mTaskEVO.getTaskType() == 0 && this.mTaskMessage.getCheckpointNumber() != checkpointNumber) {
            if(this.mTaskMessage.getRetryCount() < 10) {
               this.mLog.warn("runTask", this.getTaskMessagePrefix() + " checkpoint mismatch - attempt " + (this.mTaskMessage.getRetryCount() + 1));
               this.mTaskMessage.incrementRetryCount();
               this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(1000 * this.mTaskMessage.getRetryCount()));
            } else {
               throw new Exception(this.getTaskMessagePrefix() + " checkpoint mismatch - target=" + this.mTaskMessage.getCheckpointNumber() + ", task=" + checkpointNumber);
            }
         } else {
            this.mTaskMessage.clearRetryCount();
            String stepText;
            if(firstRun) {
               this.mTaskEVO.setSystemTimeMillis(System.currentTimeMillis());
               stepText = "checkpoint 0 - " + GeneralUtils.listToString(task.getRequest().toDisplay(), 256);
            } else {
               stepText = "checkpoint " + checkpointNumber + " - " + GeneralUtils.listToString(task.getCheckpoint().toDisplay(), 256);
            }

            this.hilightedLog("runTask", this.getTaskMessagePrefix() + " " + stepText);
            (new TaskDAO()).autonomousUpdateTask(this.mTaskEVO.getTaskId(), 3, this.mTaskEVO.getSystemTimeMillis(), stepText);
            this.logTaskStartEvent(stepText);
            Timer timer = new Timer(this.mLog);
            task.setDespatcherJmsConnection(this.mDespatcherJmsConnection);
            
            if( task instanceof ImportGlobalMappedModel2Task && this.mCompanies != null ) {
            	((ImportGlobalMappedModel2Task) task).setCompany( this.mCompanies.get( 0 ) );
            }
            
            // run task
            task.runUnitOfWork(this.getInitialContext());
            
            if( task instanceof ImportGlobalMappedModel2Task && this.mCompanies == null ) {
            	this.mCompanies = ((ImportGlobalMappedModel2Task) task).getAllCompanies();
            	((ImportGlobalMappedModel2Task) task).setCompany( this.mCompanies.get( 0 ) );
            	this.financeCubeID = ((ImportGlobalMappedModel2Task) task).getFinanceCubeID();
            }
            
            this.hilightedLog("runTask", this.getTaskMessagePrefix() + "  end of checkpoint " + checkpointNumber + " [timer=" + timer.getElapsed() + "]");
            this.logTaskProgressEvent("[" + timer.getElapsed() + "] end of checkpoint " + checkpointNumber);
            if(task.getCheckpoint() == null) {
               if( (this.mTaskEVO.getTaskName().equals("ImportGlobalModel2")) && (this.mCompanies.size() > 1) ) {
                	this.mCompanies.remove(0);
					this.mTaskEVO.setCheckpoint((TaskCheckpoint) null);
					this.mTaskEVO.setStatus(3);
					this.getTaskAccessor().setDetails(this.mTaskEVO);
					this.mTaskMessage = new TaskMessage(3, this.mTaskEVO.getPK(), this.mUserName);
					this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(500));
                } else {
                	this.companiesList = null;
                	this.mCompanies = null;
                	(new TaskDAO()).autonomousUpdateTask(this.mTaskEVO.getTaskId(), 8, this.mTaskEVO.getSystemTimeMillis(), "commiting task completion");
	               this.mTaskEVO.setEndDate(new Timestamp(System.currentTimeMillis()));
	               long elapsed = this.mTaskEVO.getEndDate().getTime() - this.mTaskEVO.getSystemTimeMillis();
	               String elapsedString = Timer.getFormatted(elapsed, 4);
	               this.hilightedLog("runTask", this.getTaskMessagePrefix() + " completed [totalElapsed=" + elapsedString + "]" + (task.getCompletionExceptionMessage() == null?"":" - " + task.getCompletionExceptionMessage()));
	               task.tidyActions();
	               this.mTaskEVO.setStep("Elapsed=" + elapsedString);
	               this.mTaskEVO.setStatus(task.getCompletionExceptionMessage() == null?5:9);
	               this.mTaskEVO.setMustComplete(false);
	               this.mTaskEVO.setCheckpoint((TaskCheckpoint)null);
	               this.mTaskEVO.setRequest((TaskRequest)null);
	               this.getTaskAccessor().setDetails(this.mTaskEVO);
	               this.logTaskEndEvent("Task ended: total elapsed=" + elapsedString);
	               StringBuffer mailMsg = new StringBuffer();
	               mailMsg.append(task.getCompletionMessage());
	               mailMsg.append(task.getCompletionFooter());
	               if(this.mTaskEVO.getTaskType() == 1 || this.mTaskEVO.getTaskType() == 0 && this.mTaskEVO.getOriginalTaskId() == 0) {
	                  this.mailUserTaskComplete(this.mTaskEVO.getUserId(), mailMsg.toString());
	                  this.postTimingToStatsServer();
	               }
	
	               TaskMessage runMessage;
	               if(task.autoDeleteWhenComplete()) {
	                  runMessage = new TaskMessage(8, this.mTaskEVO.getPK(), this.mUserName);
	               } else {
	                  runMessage = new TaskMessage(4, this.mTaskEVO.getPK(), this.mUserName);
	               }
	               
		           this.sendDespatchMessage(runMessage, Integer.valueOf(200));
		           this.sendEntityEventMessage("Task", this.mTaskEVO.getPK(), 3);
                }
            } else {
               (new TaskDAO()).autonomousUpdateTask(this.mTaskEVO.getTaskId(), 7, this.mTaskEVO.getSystemTimeMillis(), "commiting checkpoint " + checkpointNumber);
               task.getCheckpoint().setCheckpointNumberUp();
               this.mTaskEVO.setEndDate((Timestamp)null);
               this.mTaskEVO.setStep("checkpoint " + checkpointNumber + " taken");
               this.mTaskEVO.setCheckpoint(task.getCheckpoint());
               if(this.mTaskEVO.getTaskType() == 0) {
                  this.mTaskEVO.setStatus(3);
               } else {
                  this.mTaskEVO.setStatus(6);
               }

               this.mTaskEVO.setMustComplete(task.mustComplete());
               this.getTaskAccessor().setDetails(this.mTaskEVO);
               if(this.mTaskEVO.getTaskType() == 0) {
                  this.mTaskMessage = new TaskMessage(3, this.mTaskEVO.getPK(), this.mUserName);
                  this.mTaskMessage.setCheckpointNumber(task.getCheckpointNumber());
                  this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(500));
               } else {
                  this.mTaskMessage = new TaskMessage(9, this.mTaskEVO.getPK(), this.mUserName);
                  this.mTaskMessage.setCheckpointNumber(task.getCheckpointNumber());
                  this.sendDespatchMessage(this.mTaskMessage, Integer.valueOf(100));
               }
            }

         }
      } else {
         this.mLog.warn("runTask", "ignored run request for completed task <" + this.mTaskEVO.getTaskId() + ">");
      }
   }

   private String getTaskMessagePrefix() {
      return "<" + this.mTaskEVO.getTaskId() + "," + this.mTaskEVO.getTaskName() + ">";
   }

   private void hilightedLog(String method, String message) {
      this.mLog.info(method, "-----------------------------------");
      this.mLog.info(method, message);
      this.mLog.info(method, "-----------------------------------");
   }

   private void logTaskStartEvent(String eventText) throws Exception {
      (new TaskDAO()).logEvent(this.mTaskEVO.getTaskId(), 0, eventText);
   }

   private void logTaskEndEvent(String eventText) throws Exception {
      (new TaskDAO()).logEvent(this.mTaskEVO.getTaskId(), 3, eventText);
   }

   private void logTaskProgressEvent(String eventText) throws Exception {
      (new TaskDAO()).logEvent(this.mTaskEVO.getTaskId(), 1, eventText);
   }

   private void logTaskInfoEvent(String eventText) throws Exception {
      (new TaskDAO()).logEvent(this.mTaskEVO.getTaskId(), 4, eventText);
   }

   private void logTaskExceptionEvent(String eventText) throws Exception {
      (new TaskDAO()).logEvent(this.mTaskEVO.getTaskId(), 5, eventText);
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

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("sendEntityEventMessage", em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   private void mailUserTaskComplete(int userId, String messageBody) {
      UserMailer mailer = null;

//      try {
//         mailer = new UserMailer(new TaskRunnerMEJB$1(this, userId));
//         mailer.sendUserMailMessage(MessageFormat.format("Task Id {0} {1} completed", new Object[]{String.valueOf(this.mTaskEVO.getTaskId()), this.mTaskEVO.getTaskName()}), messageBody);
//      } catch (Throwable var8) {
//         this.mLog.error("mailUserTaskComplete", var8.getMessage());
//      } finally {
//         if(mailer != null) {
//            mailer.closeDown();
//         }
//
//      }

   }

   private void postTimingToStatsServer() {
      try {
         int e = SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "SYS: Performance Log Level", 0);
         if(e > 1) {
            long start = this.mTaskEVO.getCreateDate().getTime();
            long finish = this.mTaskEVO.getEndDate().getTime();
//            this.mPLogger.log("CP", this.mTaskEVO.getTaskName(), 0, finish - start, start);
         }
      } catch (Exception var6) {
         ;
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
         this.mDespatcherJmsConnection = new JmsConnectionImpl(this._jndiContext, 1, "multiTaskDespatcherQueue");
         this.mRunnerJmsConnection = new JmsConnectionImpl(this._jndiContext, 1, "multiTaskRunnerQueue");
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
   
	private void setAllCompanies(TaskRequest req) {
		ExternalSystemDAO dao = new ExternalSystemDAO();
		int mappedModelId = ((ImportGlobalMappedModel2TaskRequest) req).getMappedModelIds()[0];
		
		companiesList = dao.getAllCompanies( mappedModelId );
	}
}
