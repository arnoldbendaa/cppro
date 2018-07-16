// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.api.task.group.TaskGroupRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import java.io.Serializable;

public class TaskGroupRefImpl extends EntityRefImpl implements TaskGroupRef, Serializable {

   public TaskGroupRefImpl(TaskGroupPK key, String narrative) {
      super(key, narrative);
   }

   public TaskGroupPK getTaskGroupPK() {
      return (TaskGroupPK)this.mKey;
   }
}
