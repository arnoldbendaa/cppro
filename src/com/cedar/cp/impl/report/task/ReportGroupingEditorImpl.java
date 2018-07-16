// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.task;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.task.ReportGrouping;
import com.cedar.cp.api.report.task.ReportGroupingEditor;
import com.cedar.cp.dto.report.task.ReportGroupingEditorSessionSSO;
import com.cedar.cp.dto.report.task.ReportGroupingImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.task.ReportGroupingAdapter;
import com.cedar.cp.impl.report.task.ReportGroupingEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class ReportGroupingEditorImpl extends BusinessEditorImpl implements ReportGroupingEditor {

   private ReportGroupingEditorSessionSSO mServerSessionData;
   private ReportGroupingImpl mEditorData;
   private ReportGroupingAdapter mEditorDataAdapter;


   public ReportGroupingEditorImpl(ReportGroupingEditorSessionImpl session, ReportGroupingEditorSessionSSO serverSessionData, ReportGroupingImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ReportGroupingEditorSessionSSO serverSessionData, ReportGroupingImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setParentTaskId(int newParentTaskId) throws ValidationException {
      this.validateParentTaskId(newParentTaskId);
      if(this.mEditorData.getParentTaskId() != newParentTaskId) {
         this.setContentModified();
         this.mEditorData.setParentTaskId(newParentTaskId);
      }
   }

   public void setTaskId(int newTaskId) throws ValidationException {
      this.validateTaskId(newTaskId);
      if(this.mEditorData.getTaskId() != newTaskId) {
         this.setContentModified();
         this.mEditorData.setTaskId(newTaskId);
      }
   }

   public void setDistributionType(int newDistributionType) throws ValidationException {
      this.validateDistributionType(newDistributionType);
      if(this.mEditorData.getDistributionType() != newDistributionType) {
         this.setContentModified();
         this.mEditorData.setDistributionType(newDistributionType);
      }
   }

   public void setMessageType(int newMessageType) throws ValidationException {
      this.validateMessageType(newMessageType);
      if(this.mEditorData.getMessageType() != newMessageType) {
         this.setContentModified();
         this.mEditorData.setMessageType(newMessageType);
      }
   }

   public void setMessageId(String newMessageId) throws ValidationException {
      if(newMessageId != null) {
         newMessageId = StringUtils.rtrim(newMessageId);
      }

      this.validateMessageId(newMessageId);
      if(this.mEditorData.getMessageId() == null || !this.mEditorData.getMessageId().equals(newMessageId)) {
         this.setContentModified();
         this.mEditorData.setMessageId(newMessageId);
      }
   }

   public void validateParentTaskId(int newParentTaskId) throws ValidationException {}

   public void validateTaskId(int newTaskId) throws ValidationException {}

   public void validateDistributionType(int newDistributionType) throws ValidationException {}

   public void validateMessageType(int newMessageType) throws ValidationException {}

   public void validateMessageId(String newMessageId) throws ValidationException {
      if(newMessageId != null && newMessageId.length() > 255) {
         throw new ValidationException("length (" + newMessageId.length() + ") of MessageId must not exceed 255 on a ReportGrouping");
      }
   }

   public ReportGrouping getReportGrouping() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ReportGroupingAdapter((ReportGroupingEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
