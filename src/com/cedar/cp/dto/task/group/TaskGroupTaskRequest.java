// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class TaskGroupTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private EntityRef mTaskGroupRef;


   public TaskGroupTaskRequest(EntityRef taskGroupRef) {
      this.mTaskGroupRef = taskGroupRef;
      this.addExclusiveAccess("TaskGroupTask");
   }

   public EntityRef getTaskGroupRef() {
      return this.mTaskGroupRef;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add(this.mTaskGroupRef.getPrimaryKey().toString() + " - " + this.mTaskGroupRef.getNarrative());
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.TaskGroupTask";
   }

   public String getIdentifier() {
      return "TaskGroup " + this.mTaskGroupRef.getNarrative();
   }
}
