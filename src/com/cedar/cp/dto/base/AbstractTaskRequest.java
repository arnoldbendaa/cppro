// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.api.task.TaskRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTaskRequest implements TaskRequest {

   private List mExclusiveAccessList = new ArrayList();
   private int mTaskId = -1;


   public List getExclusiveAccessList() {
      return this.mExclusiveAccessList;
   }

   public void addExclusiveAccess(Object o) {
      this.mExclusiveAccessList.add(o);
   }

   public String getIdentifier() {
      String s = this.getService().substring(this.getService().lastIndexOf(46) + 1);
      int p = s.lastIndexOf("Task");
      return p > 0?s.substring(0, p):s;
   }

   public abstract List toDisplay();

   public abstract String getService();

   public void setTaskId(int taskId) {
      this.mTaskId = taskId;
   }

   public int getTaskId() {
      return this.mTaskId;
   }
}
