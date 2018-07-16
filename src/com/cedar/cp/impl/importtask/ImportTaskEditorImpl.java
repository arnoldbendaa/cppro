package com.cedar.cp.impl.importtask;

import com.cedar.cp.api.importtask.ImportTask;
import com.cedar.cp.api.importtask.ImportTaskEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionSSO;
import com.cedar.cp.dto.importtask.ImportTaskImpl;
import com.cedar.cp.impl.importtask.ImportTaskAdapter;
import com.cedar.cp.impl.importtask.ImportTaskEditorSessionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.util.StringUtils;

public class ImportTaskEditorImpl extends BusinessEditorImpl implements ImportTaskEditor {

   private ImportTaskEditorSessionSSO mServerSessionData;
   private ImportTaskImpl mEditorData;
   private ImportTaskAdapter mEditorDataAdapter;


   public ImportTaskEditorImpl(ImportTaskEditorSessionImpl session, ImportTaskEditorSessionSSO serverSessionData, ImportTaskImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ImportTaskEditorSessionSSO serverSessionData, ImportTaskImpl editorData) {
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
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 28 on a ImportTask");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a ImportTask");
      }
   }

   public ImportTask getImportTask() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ImportTaskAdapter((ImportTaskEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
