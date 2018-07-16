// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.ejb.impl.task.TaskRunnerMEJB;
import com.cedar.cp.ejb.impl.task.UserMailerOwner;

class TaskRunnerMEJB$1 implements UserMailerOwner {

   // $FF: synthetic field
   final int val$userId;
   // $FF: synthetic field
   final TaskRunnerMEJB this$0;


   TaskRunnerMEJB$1(TaskRunnerMEJB var1, int var2) {
      this.this$0 = var1;
      this.val$userId = var2;
   }

   public int getUserId() {
      return this.val$userId;
   }
}
