package com.cedar.cp.impl.user.loggedinusers;

import com.cedar.cp.api.user.loggedinusers.LoggedInUsers;
import com.cedar.cp.api.user.loggedinusers.LoggedInUsersEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionSSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersImpl;
import com.cedar.cp.impl.user.loggedinusers.LoggedInUsersAdapter;
import com.cedar.cp.impl.user.loggedinusers.LoggedInUsersEditorSessionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.util.StringUtils;

public class LoggedInUsersEditorImpl extends BusinessEditorImpl implements LoggedInUsersEditor {

   private LoggedInUsersEditorSessionSSO mServerSessionData;
   private LoggedInUsersImpl mEditorData;
   private LoggedInUsersAdapter mEditorDataAdapter;


   public LoggedInUsersEditorImpl(LoggedInUsersEditorSessionImpl session, LoggedInUsersEditorSessionSSO serverSessionData, LoggedInUsersImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(LoggedInUsersEditorSessionSSO serverSessionData, LoggedInUsersImpl editorData) {
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
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 28 on a LoggedInUsers");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a LoggedInUsers");
      }
   }

   public LoggedInUsers getLoggedInUsers() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new LoggedInUsersAdapter((LoggedInUsersEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
