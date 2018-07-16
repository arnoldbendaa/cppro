// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.impl.task.group.TgRowEVO;
import java.util.ArrayList;
import java.util.List;

public class TaskGroupTask$MyCheckpoint extends AbstractTaskCheckpoint {

   public List<TgRowEVO> mTaskGroupRows;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      if(this.getCheckpointNumber() > this.getTaskGroupRows().size()) {
         l.add("finish off");
      } else {
         l.add("issue next task");
      }

      return l;
   }

   public void setTaskGroupRows(List<TgRowEVO> tgRows) {
      this.mTaskGroupRows = tgRows;
   }

   public List<TgRowEVO> getTaskGroupRows() {
      return this.mTaskGroupRows;
   }
}
