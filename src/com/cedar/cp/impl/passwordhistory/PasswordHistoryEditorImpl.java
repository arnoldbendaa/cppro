// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.passwordhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.passwordhistory.PasswordHistory;
import com.cedar.cp.api.passwordhistory.PasswordHistoryEditor;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryEditorSessionSSO;
import com.cedar.cp.dto.passwordhistory.PasswordHistoryImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.passwordhistory.PasswordHistoryAdapter;
import com.cedar.cp.impl.passwordhistory.PasswordHistoryEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;

public class PasswordHistoryEditorImpl extends BusinessEditorImpl implements PasswordHistoryEditor {

   private PasswordHistoryEditorSessionSSO mServerSessionData;
   private PasswordHistoryImpl mEditorData;
   private PasswordHistoryAdapter mEditorDataAdapter;


   public PasswordHistoryEditorImpl(PasswordHistoryEditorSessionImpl session, PasswordHistoryEditorSessionSSO serverSessionData, PasswordHistoryImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(PasswordHistoryEditorSessionSSO serverSessionData, PasswordHistoryImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setUserId(int newUserId) throws ValidationException {
      this.validateUserId(newUserId);
      if(this.mEditorData.getUserId() != newUserId) {
         this.setContentModified();
         this.mEditorData.setUserId(newUserId);
      }
   }

   public void setPasswordBytes(String newPasswordBytes) throws ValidationException {
      if(newPasswordBytes != null) {
         newPasswordBytes = StringUtils.rtrim(newPasswordBytes);
      }

      this.validatePasswordBytes(newPasswordBytes);
      if(this.mEditorData.getPasswordBytes() == null || !this.mEditorData.getPasswordBytes().equals(newPasswordBytes)) {
         this.setContentModified();
         this.mEditorData.setPasswordBytes(newPasswordBytes);
      }
   }

   public void setPasswordDate(Timestamp newPasswordDate) throws ValidationException {
      this.validatePasswordDate(newPasswordDate);
      if(this.mEditorData.getPasswordDate() == null || !this.mEditorData.getPasswordDate().equals(newPasswordDate)) {
         this.setContentModified();
         this.mEditorData.setPasswordDate(newPasswordDate);
      }
   }

   public void validateUserId(int newUserId) throws ValidationException {}

   public void validatePasswordBytes(String newPasswordBytes) throws ValidationException {
      if(newPasswordBytes != null && newPasswordBytes.length() > 100) {
         throw new ValidationException("length (" + newPasswordBytes.length() + ") of PasswordBytes must not exceed 100 on a PasswordHistory");
      }
   }

   public void validatePasswordDate(Timestamp newPasswordDate) throws ValidationException {}

   public PasswordHistory getPasswordHistory() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new PasswordHistoryAdapter((PasswordHistoryEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
