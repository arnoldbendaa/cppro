// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.destination.internal;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.internal.InternalDestination;
import com.cedar.cp.api.report.destination.internal.InternalDestinationEditor;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.destination.internal.InternalDestinationAdapter;
import com.cedar.cp.impl.report.destination.internal.InternalDestinationEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class InternalDestinationEditorImpl extends BusinessEditorImpl implements InternalDestinationEditor {

   private InternalDestinationEditorSessionSSO mServerSessionData;
   private InternalDestinationImpl mEditorData;
   private InternalDestinationAdapter mEditorDataAdapter;


   public InternalDestinationEditorImpl(InternalDestinationEditorSessionImpl session, InternalDestinationEditorSessionSSO serverSessionData, InternalDestinationImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(InternalDestinationEditorSessionSSO serverSessionData, InternalDestinationImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setMessageType(int newMessageType) throws ValidationException {
      this.validateMessageType(newMessageType);
      if(this.mEditorData.getMessageType() != newMessageType) {
         this.setContentModified();
         this.mEditorData.setMessageType(newMessageType);
      }
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
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a InternalDestination");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a InternalDestination");
      }
   }

   public void validateMessageType(int newMessageType) throws ValidationException {}

   public InternalDestination getInternalDestination() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new InternalDestinationAdapter((InternalDestinationEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void addUser(Object o) {
      this.mEditorData.getUserList().add(o);
      this.setContentModified();
   }
}
