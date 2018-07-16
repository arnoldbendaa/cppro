// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task;

import com.cedar.cp.dto.task.TaskPK;
import java.io.Serializable;

public class RestartTaskMessage implements Serializable {

   private TaskPK mTaskPK;


   public RestartTaskMessage(TaskPK taskPK) {
      this.mTaskPK = taskPK;
   }

   public TaskPK getTaskPK() {
      return this.mTaskPK;
   }
}
