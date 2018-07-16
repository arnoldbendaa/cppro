// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.destination.external;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.destination.external.ExternalDestination;
import com.cedar.cp.api.report.destination.external.ExternalDestinationEditor;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.destination.external.ExternalDestinationAdapter;
import com.cedar.cp.impl.report.destination.external.ExternalDestinationEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class ExternalDestinationEditorImpl extends BusinessEditorImpl implements ExternalDestinationEditor {

   private ExternalDestinationEditorSessionSSO mServerSessionData;
   private ExternalDestinationImpl mEditorData;
   private ExternalDestinationAdapter mEditorDataAdapter;


   public ExternalDestinationEditorImpl(ExternalDestinationEditorSessionImpl session, ExternalDestinationEditorSessionSSO serverSessionData, ExternalDestinationImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ExternalDestinationEditorSessionSSO serverSessionData, ExternalDestinationImpl editorData) {
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
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a ExternalDestination");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a ExternalDestination");
      }
   }

   public ExternalDestination getExternalDestination() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ExternalDestinationAdapter((ExternalDestinationEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void addUser(String address) {
      this.mEditorData.getUserList().add(address);
      this.setContentModified();
   }
}
