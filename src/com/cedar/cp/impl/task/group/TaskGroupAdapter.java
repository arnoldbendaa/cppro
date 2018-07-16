// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.task.group;

import com.cedar.cp.api.task.group.TaskGroup;
import com.cedar.cp.api.task.group.TaskGroupRow;
import com.cedar.cp.dto.task.group.TaskGroupImpl;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.impl.task.group.TaskGroupEditorSessionImpl;
import java.sql.Timestamp;
import java.util.List;

public class TaskGroupAdapter implements TaskGroup {

   private TaskGroupImpl mEditorData;
   private TaskGroupEditorSessionImpl mEditorSessionImpl;


   public TaskGroupAdapter(TaskGroupEditorSessionImpl e, TaskGroupImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected TaskGroupEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected TaskGroupImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(TaskGroupPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public Timestamp getLastSubmit() {
      return this.mEditorData.getLastSubmit();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setLastSubmit(Timestamp p) {
      this.mEditorData.setLastSubmit(p);
   }

   public List<TaskGroupRow> getTGRows() {
      return this.mEditorData.getTGRows();
   }
}
