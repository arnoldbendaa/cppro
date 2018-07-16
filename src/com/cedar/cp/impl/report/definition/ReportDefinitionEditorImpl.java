// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.definition;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.definition.ReportDefinition;
import com.cedar.cp.api.report.definition.ReportDefinitionEditor;
import com.cedar.cp.dto.report.definition.ReportDefinitionEditorSessionSSO;
import com.cedar.cp.dto.report.definition.ReportDefinitionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.definition.ReportDefinitionAdapter;
import com.cedar.cp.impl.report.definition.ReportDefinitionEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.List;

public class ReportDefinitionEditorImpl extends BusinessEditorImpl implements ReportDefinitionEditor {

   private ReportDefinitionEditorSessionSSO mServerSessionData;
   private ReportDefinitionImpl mEditorData;
   private ReportDefinitionAdapter mEditorDataAdapter;


   public ReportDefinitionEditorImpl(ReportDefinitionEditorSessionImpl session, ReportDefinitionEditorSessionSSO serverSessionData, ReportDefinitionImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ReportDefinitionEditorSessionSSO serverSessionData, ReportDefinitionImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setReportTypeId(int newReportTypeId) throws ValidationException {
      this.validateReportTypeId(newReportTypeId);
      if(this.mEditorData.getReportTypeId() != newReportTypeId) {
         this.setContentModified();
         this.mEditorData.setReportTypeId(newReportTypeId);
      }
   }

   public void setIsPublic(boolean newIsPublic) throws ValidationException {
      this.validateIsPublic(newIsPublic);
      if(this.mEditorData.isIsPublic() != newIsPublic) {
         this.setContentModified();
         this.mEditorData.setIsPublic(newIsPublic);
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
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a ReportDefinition");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a ReportDefinition");
      }
   }

   public void validateReportTypeId(int newReportTypeId) throws ValidationException {}

   public void validateIsPublic(boolean newIsPublic) throws ValidationException {}

   public ReportDefinition getReportDefinition() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ReportDefinitionAdapter((ReportDefinitionEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void setReportParams(List l) throws ValidationException {
      if(l == null) {
         throw new ValidationException("List must not be null");
      } else {
         this.mEditorData.setReportParams(l);
         this.setContentModified();
      }
   }

   public void setReportType(String visId) {
      this.mEditorData.setReportTypeVisId(visId);
      this.setContentModified();
   }
}
