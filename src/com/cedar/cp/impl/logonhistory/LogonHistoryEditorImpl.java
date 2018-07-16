// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.logonhistory;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logonhistory.LogonHistory;
import com.cedar.cp.api.logonhistory.LogonHistoryEditor;
import com.cedar.cp.dto.logonhistory.LogonHistoryEditorSessionSSO;
import com.cedar.cp.dto.logonhistory.LogonHistoryImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.logonhistory.LogonHistoryAdapter;
import com.cedar.cp.impl.logonhistory.LogonHistoryEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;

public class LogonHistoryEditorImpl extends BusinessEditorImpl implements LogonHistoryEditor {

   private LogonHistoryEditorSessionSSO mServerSessionData;
   private LogonHistoryImpl mEditorData;
   private LogonHistoryAdapter mEditorDataAdapter;


   public LogonHistoryEditorImpl(LogonHistoryEditorSessionImpl session, LogonHistoryEditorSessionSSO serverSessionData, LogonHistoryImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(LogonHistoryEditorSessionSSO serverSessionData, LogonHistoryImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setEventType(int newEventType) throws ValidationException {
      this.validateEventType(newEventType);
      if(this.mEditorData.getEventType() != newEventType) {
         this.setContentModified();
         this.mEditorData.setEventType(newEventType);
      }
   }

   public void setUserName(String newUserName) throws ValidationException {
      if(newUserName != null) {
         newUserName = StringUtils.rtrim(newUserName);
      }

      this.validateUserName(newUserName);
      if(this.mEditorData.getUserName() == null || !this.mEditorData.getUserName().equals(newUserName)) {
         this.setContentModified();
         this.mEditorData.setUserName(newUserName);
      }
   }

   public void setEventDate(Timestamp newEventDate) throws ValidationException {
      this.validateEventDate(newEventDate);
      if(this.mEditorData.getEventDate() == null || !this.mEditorData.getEventDate().equals(newEventDate)) {
         this.setContentModified();
         this.mEditorData.setEventDate(newEventDate);
      }
   }

   public void validateUserName(String newUserName) throws ValidationException {
      if(newUserName != null && newUserName.length() > 255) {
         throw new ValidationException("length (" + newUserName.length() + ") of UserName must not exceed 255 on a LogonHistory");
      }
   }

   public void validateEventDate(Timestamp newEventDate) throws ValidationException {}

   public void validateEventType(int newEventType) throws ValidationException {}

   public LogonHistory getLogonHistory() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new LogonHistoryAdapter((LogonHistoryEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
