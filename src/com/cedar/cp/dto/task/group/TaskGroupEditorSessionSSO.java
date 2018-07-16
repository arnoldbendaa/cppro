// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.dto.task.group.TaskGroupImpl;
import java.io.Serializable;

public class TaskGroupEditorSessionSSO implements Serializable {

   private TaskGroupImpl mEditorData;


   public TaskGroupEditorSessionSSO() {}

   public TaskGroupEditorSessionSSO(TaskGroupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(TaskGroupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public TaskGroupImpl getEditorData() {
      return this.mEditorData;
   }
}
