// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRequest;
import javax.naming.InitialContext;

public interface TaskStep {

   TaskCheckpoint runStep(InitialContext var1, int var2, TaskRequest var3, TaskCheckpoint var4, String var5) throws Exception;
}
