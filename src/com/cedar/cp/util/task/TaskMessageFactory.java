// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.task;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.task.RestartTaskMessage;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.api.task.TaskProcessServer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.NewTaskMessage;
import javax.naming.InitialContext;

public class TaskMessageFactory {

   public static int issueNewTask(InitialContext ctxt, boolean remote, TaskRequest request, int userId) throws Exception {
      return issueNewTask(ctxt, 0, remote, request, userId, 0);
   }

   public static int issueNewTask(InitialContext ctxt, boolean remote, TaskRequest request, int userId, int issuingTaskId) throws Exception {
      return issueNewTask(ctxt, 0, remote, request, userId, issuingTaskId);
   }

   public static int issueNewTask(InitialContext ctxt, int taskType, boolean remote, TaskRequest request, int userId, int issuingTaskId) throws Exception {
      long systemTime = System.currentTimeMillis();
      int taskId = (new TaskProcessServer(ctxt, remote)).newTask(userId, taskType, request, systemTime, issuingTaskId);
      NewTaskMessage msg = new NewTaskMessage(taskId, userId, systemTime);
      JmsConnectionImpl jms = new JmsConnectionImpl(ctxt, 1, "taskReceiverQueue");
      jms.createSession();
      jms.send(msg, System.currentTimeMillis() + 500L);
      jms.closeSession();
      jms.closeConnection();
      return taskId;
   }

   public static void restartTask(InitialContext ctxt, TaskPK taskPK) throws Exception {
      RestartTaskMessage msg = new RestartTaskMessage(taskPK);
      JmsConnectionImpl jms = new JmsConnectionImpl(ctxt, 1, "taskReceiverQueue");
      jms.createSession();
      jms.send(msg, System.currentTimeMillis() + 500L);
      jms.closeSession();
      jms.closeConnection();
   }
}
