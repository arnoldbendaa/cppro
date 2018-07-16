// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.task.group;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.group.TaskGroup;
import com.cedar.cp.api.task.group.TaskGroupEditor;
import com.cedar.cp.dto.task.group.TaskGroupEditorSessionSSO;
import com.cedar.cp.dto.task.group.TaskGroupImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.task.group.TaskGroupAdapter;
import com.cedar.cp.impl.task.group.TaskGroupEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;

public class TaskGroupEditorImpl extends BusinessEditorImpl implements TaskGroupEditor {

   private TaskGroupEditorSessionSSO mServerSessionData;
   private TaskGroupImpl mEditorData;
   private TaskGroupAdapter mEditorDataAdapter;


   public TaskGroupEditorImpl(TaskGroupEditorSessionImpl session, TaskGroupEditorSessionSSO serverSessionData, TaskGroupImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(TaskGroupEditorSessionSSO serverSessionData, TaskGroupImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void setLastSubmit(Timestamp newLastSubmit) throws ValidationException {
      this.validateLastSubmit(newLastSubmit);
      if(this.mEditorData.getLastSubmit() == null || !this.mEditorData.getLastSubmit().equals(newLastSubmit)) {
         this.setContentModified();
         this.mEditorData.setLastSubmit(newLastSubmit);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a TaskGroup");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a TaskGroup");
      }
   }

   public void validateLastSubmit(Timestamp newLastSubmit) throws ValidationException {}

   public TaskGroup getTaskGroup() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new TaskGroupAdapter((TaskGroupEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
