// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTask;
import com.cedar.cp.api.admin.tidytask.TidyTaskEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.admin.tidytask.TidyTaskEditorSessionSSO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import com.cedar.cp.impl.admin.tidytask.TidyTaskAdapter;
import com.cedar.cp.impl.admin.tidytask.TidyTaskEditorSessionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.util.StringUtils;

public class TidyTaskEditorImpl extends BusinessEditorImpl implements TidyTaskEditor {

   private TidyTaskEditorSessionSSO mServerSessionData;
   private TidyTaskImpl mEditorData;
   private TidyTaskAdapter mEditorDataAdapter;


   public TidyTaskEditorImpl(TidyTaskEditorSessionImpl session, TidyTaskEditorSessionSSO serverSessionData, TidyTaskImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(TidyTaskEditorSessionSSO serverSessionData, TidyTaskImpl editorData) {
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

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 28) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 28 on a TidyTask");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a TidyTask");
      }
   }

   public TidyTask getTidyTask() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new TidyTaskAdapter((TidyTaskEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
