// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.template;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.template.ReportTemplate;
import com.cedar.cp.api.report.template.ReportTemplateEditor;
import com.cedar.cp.dto.report.template.ReportTemplateEditorSessionSSO;
import com.cedar.cp.dto.report.template.ReportTemplateImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.template.ReportTemplateAdapter;
import com.cedar.cp.impl.report.template.ReportTemplateEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class ReportTemplateEditorImpl extends BusinessEditorImpl implements ReportTemplateEditor {

   private ReportTemplateEditorSessionSSO mServerSessionData;
   private ReportTemplateImpl mEditorData;
   private ReportTemplateAdapter mEditorDataAdapter;


   public ReportTemplateEditorImpl(ReportTemplateEditorSessionImpl session, ReportTemplateEditorSessionSSO serverSessionData, ReportTemplateImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ReportTemplateEditorSessionSSO serverSessionData, ReportTemplateImpl editorData) {
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

   public void setDocumentName(String newDocumentName) throws ValidationException {
      if(newDocumentName != null) {
         newDocumentName = StringUtils.rtrim(newDocumentName);
      }

      this.validateDocumentName(newDocumentName);
      if(this.mEditorData.getDocumentName() == null || !this.mEditorData.getDocumentName().equals(newDocumentName)) {
         this.setContentModified();
         this.mEditorData.setDocumentName(newDocumentName);
      }
   }

   public void setDocument(byte[] newDocument) throws ValidationException {
      this.validateDocument(newDocument);
      if(this.mEditorData.getDocument() == null || !this.mEditorData.getDocument().equals(newDocument)) {
         this.setContentModified();
         this.mEditorData.setDocument(newDocument);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a ReportTemplate");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a ReportTemplate");
      }
   }

   public void validateDocumentName(String newDocumentName) throws ValidationException {
      if(newDocumentName != null && newDocumentName.length() > 256) {
         throw new ValidationException("length (" + newDocumentName.length() + ") of DocumentName must not exceed 256 on a ReportTemplate");
      }
   }

   public void validateDocument(byte[] newDocument) throws ValidationException {}

   public ReportTemplate getReportTemplate() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ReportTemplateAdapter((ReportTemplateEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
