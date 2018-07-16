// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.dto.task.group.TaskGroupImpl;
import java.io.Serializable;

public class TaskGroupEditorSessionCSO implements Serializable {

   private int mUserId;
   private TaskGroupImpl mEditorData;


   public TaskGroupEditorSessionCSO(int userId, TaskGroupImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public TaskGroupImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
