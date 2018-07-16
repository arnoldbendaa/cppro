// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.pack;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.pack.ReportPack;
import com.cedar.cp.api.report.pack.ReportPackEditor;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionSSO;
import com.cedar.cp.dto.report.pack.ReportPackImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.pack.ReportPackAdapter;
import com.cedar.cp.impl.report.pack.ReportPackEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class ReportPackEditorImpl extends BusinessEditorImpl implements ReportPackEditor {

   private ReportPackEditorSessionSSO mServerSessionData;
   private ReportPackImpl mEditorData;
   private ReportPackAdapter mEditorDataAdapter;


   public ReportPackEditorImpl(ReportPackEditorSessionImpl session, ReportPackEditorSessionSSO serverSessionData, ReportPackImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ReportPackEditorSessionSSO serverSessionData, ReportPackImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setGroupAttachment(boolean newGroupAttachment) throws ValidationException {
      this.validateGroupAttachment(newGroupAttachment);
      if(this.mEditorData.isGroupAttachment() != newGroupAttachment) {
         this.setContentModified();
         this.mEditorData.setGroupAttachment(newGroupAttachment);
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

   public void setParamExample(String newParamExample) throws ValidationException {
      if(newParamExample != null) {
         newParamExample = StringUtils.rtrim(newParamExample);
      }

      this.validateParamExample(newParamExample);
      if(this.mEditorData.getParamExample() == null || !this.mEditorData.getParamExample().equals(newParamExample)) {
         this.setContentModified();
         this.mEditorData.setParamExample(newParamExample);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a ReportPack");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a ReportPack");
      }
   }

   public void validateGroupAttachment(boolean newGroupAttachment) throws ValidationException {}

   public void validateParamExample(String newParamExample) throws ValidationException {
      if(newParamExample != null && newParamExample.length() > 256) {
         throw new ValidationException("length (" + newParamExample.length() + ") of ParamExample must not exceed 256 on a ReportPack");
      }
   }

   public ReportPack getReportPack() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ReportPackAdapter((ReportPackEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
