// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task;

import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRequest;
import javax.naming.InitialContext;

public interface Task {

   int EVENT_TYPE_TASK_START = 0;
   int EVENT_TYPE_TASK_PROGRESS = 1;
   int EVENT_TYPE_TASK_SQL = 2;
   int EVENT_TYPE_TASK_END = 3;
   int EVENT_TYPE_TASK_INFO = 4;
   int EVENT_TYPE_TASK_ERROR = 5;


   void runUnitOfWork(InitialContext var1) throws Exception;

   void setUserId(int var1);

   void setTaskId(int var1);

   void setOriginalTaskId(int var1);

   void setRequest(TaskRequest var1);

   void setCheckpoint(TaskCheckpoint var1);

   int getUserId();

   int getTaskId();

   int getOriginalTaskId();

   TaskRequest getRequest();

   TaskCheckpoint getCheckpoint();

   int getCheckpointNumber();

   void setCompletionExceptionMessage(String var1);

   String getCompletionExceptionMessage();

   String getCompletionMessage();

   String getCompletionFooter();

   void setDespatcherJmsConnection(JmsConnection var1);

   boolean mustComplete();

   boolean autoDeleteWhenComplete();

   void tidyActions();
}
