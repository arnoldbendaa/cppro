// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.type;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.type.ReportType;
import com.cedar.cp.api.report.type.ReportTypeEditor;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionSSO;
import com.cedar.cp.dto.report.type.ReportTypeImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.type.ReportTypeAdapter;
import com.cedar.cp.impl.report.type.ReportTypeEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class ReportTypeEditorImpl extends BusinessEditorImpl implements ReportTypeEditor {

   private ReportTypeEditorSessionSSO mServerSessionData;
   private ReportTypeImpl mEditorData;
   private ReportTypeAdapter mEditorDataAdapter;


   public ReportTypeEditorImpl(ReportTypeEditorSessionImpl session, ReportTypeEditorSessionSSO serverSessionData, ReportTypeImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ReportTypeEditorSessionSSO serverSessionData, ReportTypeImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setType(int newType) throws ValidationException {
      this.validateType(newType);
      if(this.mEditorData.getType() != newType) {
         this.setContentModified();
         this.mEditorData.setType(newType);
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
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a ReportType");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a ReportType");
      }
   }

   public void validateType(int newType) throws ValidationException {}

   public ReportType getReportType() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ReportTypeAdapter((ReportTypeEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
