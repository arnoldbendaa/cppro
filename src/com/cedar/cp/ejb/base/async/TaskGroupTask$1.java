// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.TaskGroupTask;
import com.cedar.cp.ejb.impl.task.group.TgRowEVO;
import java.util.Comparator;

class TaskGroupTask$1 implements Comparator<TgRowEVO> {

   // $FF: synthetic field
   final TaskGroupTask this$0;


   TaskGroupTask$1(TaskGroupTask var1) {
      this.this$0 = var1;
   }

   public int compare(TgRowEVO o1, TgRowEVO o2) {
      return o1.getTgRowIndex() - o2.getTgRowIndex();
   }
}
