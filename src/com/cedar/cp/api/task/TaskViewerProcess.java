// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskViewerSession;

public interface TaskViewerProcess extends BusinessProcess {

   EntityList getTasks(int var1);

   void restartTask(int var1) throws ValidationException;

   boolean canRestartTask(int var1);

   void refresh();

   TaskViewerSession getTaskViewerSession(Object var1);

   TaskViewerSession getTaskViewerSession(int var1);

   void failTask(int var1) throws ValidationException;

   void unsafeDeleteTask(int var1, int var2) throws ValidationException;

   void wakeUpDespatcher();
}
