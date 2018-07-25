// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.task.RestartTaskMessage;
import com.cedar.cp.dto.task.TaskObjects;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.impl.task.TaskAccessor;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.task.TaskEVO;
import com.cedar.cp.ejb.impl.task.TaskMessage;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.NewTaskMessage;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJBException;
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
@MessageDriven(name = "multiTaskReceiverMEJB", activationConfig = {
@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
@ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true"),
@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/cp/multiTaskReceiverQueue"),
@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class MultiTaskReceiverMEJB implements MessageDrivenBean, MessageListener {

   private Context _jndiContext;
   private transient Log mLog = new Log(this.getClass());
   private transient UserAccessor mUserAccessor;
   private transient TaskAccessor mTaskAccessor;
   private JmsConnection mReceiverJmsConnection;
   private JmsConnection mDespatcherJmsConnection;
   private JmsConnection mRunnerJmsConnection;
   private transient TaskDAO mTaskDAO;


   public void onMessage(Message message_) {
      ObjectMessage om = (ObjectMessage)message_;

      try {
         Serializable e = om.getObject();
         if(e instanceof NewTaskMessage) {
            NewTaskMessage msg = (NewTaskMessage)e;
            this.mLog.debug("onMessage", "received new task message for taskId :" + msg.getTaskId() + ", userId: " + msg.getUserId());
            this.processNewTaskMessage(msg);
         } else {
            if(!(e instanceof RestartTaskMessage)) {
               throw new IllegalStateException("unexpected message class: " + e.getClass().getName());
            }

            RestartTaskMessage msg1 = (RestartTaskMessage)e;
            this.mLog.debug("onMessage", "received restart task message " + msg1.getTaskPK());
            this.processRestartTaskMessage(msg1.getTaskPK());
         }

         this.mTaskDAO = null;
      } catch (Exception var5) {
         this.mLog.error("onMessage", "", var5);
         throw new RuntimeException(var5.getMessage());
      }
   }

   private void processNewTaskMessage(NewTaskMessage msg) throws Exception {
      if(!this.getTaskDAO().exists(new TaskPK(msg.getTaskId()))) {
         if(msg.getRetryCount() < 60) {
            boolean delaySeconds1 = false;
            byte delaySeconds2;
            switch(msg.getRetryCount()) {
            case 0:
               delaySeconds2 = 1;
               break;
            case 1:
               delaySeconds2 = 30;
               break;
            default:
               delaySeconds2 = 60;
            }

            this.mLog.warn("processNewTaskMessage", msg.getTaskId() + (msg.getRetryCount() == 0?"":" - retry " + msg.getRetryCount()) + " - task not found - resending (delaySecs=" + delaySeconds2 + ")");
            msg.incrementRetryCount();
            this.sendReceiverMessage(msg, Integer.valueOf(delaySeconds2 * 1000));
         } else {
            this.mLog.error("processNewTaskMessage", msg.getTaskId() + " - final attempt " + msg.getRetryCount() + ": task not found - giving up");
         }
      } else {
         TaskEVO delaySeconds = this.getTaskAccessor().getDetails(new TaskPK(msg.getTaskId()), "");
         if(delaySeconds.getUserId() == msg.getUserId() && delaySeconds.getSystemTimeMillis() == msg.getOriginalSendTime()) {
            UserEVO userEVO = this.getUserAccessor().getDetails(new UserPK(msg.getUserId()), "");
            TaskObjects tro = new TaskObjects(delaySeconds.getObjects());
            List eal = tro.getRequest().getExclusiveAccessList();
            TaskMessage runMessage;
            if(eal != null && eal.size() > 0) {
               delaySeconds.setStatus(1);
               this.getTaskAccessor().setDetails(delaySeconds);
               runMessage = new TaskMessage(2, delaySeconds.getPK(), userEVO.getName());
               this.sendDespatchMessage(runMessage, Integer.valueOf(200));
            } else {
               delaySeconds.setStatus(2);
               this.getTaskAccessor().setDetails(delaySeconds);
               runMessage = new TaskMessage(3, delaySeconds.getPK(), userEVO.getName());
               this.sendRunnerMessage(runMessage, (Integer)null);
            }

         }
      }
   }

   private void processRestartTaskMessage(TaskPK taskPK) throws Exception {
      TaskEVO taskEVO = this.getTaskAccessor().getDetails(taskPK, "");
      TaskObjects taskObjects = null;

      try {
         taskObjects = new TaskObjects(taskEVO.getObjects());
      } catch (Exception var7) {
         this.getTaskAccessor().logEvent(taskEVO.getTaskId(), 5, "*** unable to unpack task objects:");
         return;
      }

      TaskCheckpoint checkpoint = taskObjects.getCheckpoint();
      UserEVO userEVO = this.getUserAccessor().getDetails(new UserPK(taskEVO.getUserId()), "");
      TaskMessage runMessage = new TaskMessage(3, taskEVO.getPK(), userEVO.getName());
      if(checkpoint != null) {
         runMessage.setCheckpointNumber(checkpoint.getCheckpointNumber());
      }

      this.sendDespatchMessage(runMessage, (Integer)null);
   }

   public void sendReceiverMessage(NewTaskMessage msg_, Integer delayMillis) throws Exception {
      this.mReceiverJmsConnection.createSession();
      if(delayMillis == null) {
         this.mReceiverJmsConnection.send(msg_);
      } else {
         this.mReceiverJmsConnection.send(msg_, (long)delayMillis.intValue() + System.currentTimeMillis());
      }

      this.mReceiverJmsConnection.closeSession();
   }

   public void sendDespatchMessage(TaskMessage msg_, Integer delayMillis) throws Exception {
      this.mDespatcherJmsConnection.createSession();
      if(delayMillis == null) {
         this.mDespatcherJmsConnection.send(msg_);
      } else {
         this.mDespatcherJmsConnection.send(msg_, (long)delayMillis.intValue() + System.currentTimeMillis());
      }

      this.mDespatcherJmsConnection.closeSession();
   }

   public void sendRunnerMessage(TaskMessage msg_, Integer delayMillis) throws Exception {
      this.mRunnerJmsConnection.createSession();
      if(delayMillis == null) {
         this.mRunnerJmsConnection.send(msg_);
      } else {
         this.mRunnerJmsConnection.send(msg_, (long)delayMillis.intValue() + System.currentTimeMillis());
      }

      this.mRunnerJmsConnection.closeSession();
   }

   public void ejbRemove() {
      try {
         this.mReceiverJmsConnection.closeConnection();
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
         this.mReceiverJmsConnection = new JmsConnectionImpl(this._jndiContext, 1, "multiTaskReceiverQueue");
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

   private UserAccessor getUserAccessor() {
      if(this.mUserAccessor == null) {
         this.mUserAccessor = new UserAccessor(this.getInitialContext());
      }

      return this.mUserAccessor;
   }

   private TaskAccessor getTaskAccessor() {
      if(this.mTaskAccessor == null) {
         this.mTaskAccessor = new TaskAccessor(this.getInitialContext());
      }

      return this.mTaskAccessor;
   }

   private TaskDAO getTaskDAO() {
      if(this.mTaskDAO == null) {
         this.mTaskDAO = new TaskDAO();
      }

      return this.mTaskDAO;
   }
}