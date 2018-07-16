// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.Report;
import com.cedar.cp.api.report.ReportEditor;
import com.cedar.cp.dto.report.ReportEditorSessionSSO;
import com.cedar.cp.dto.report.ReportImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.ReportAdapter;
import com.cedar.cp.impl.report.ReportEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class ReportEditorImpl extends BusinessEditorImpl implements ReportEditor {

   private ReportEditorSessionSSO mServerSessionData;
   private ReportImpl mEditorData;
   private ReportAdapter mEditorDataAdapter;


   public ReportEditorImpl(ReportEditorSessionImpl session, ReportEditorSessionSSO serverSessionData, ReportImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ReportEditorSessionSSO serverSessionData, ReportImpl editorData) {
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

   public void setReportType(int newReportType) throws ValidationException {
      this.validateReportType(newReportType);
      if(this.mEditorData.getReportType() != newReportType) {
         this.setContentModified();
         this.mEditorData.setReportType(newReportType);
      }
   }

   public void setTaskId(int newTaskId) throws ValidationException {
      this.validateTaskId(newTaskId);
      if(this.mEditorData.getTaskId() != newTaskId) {
         this.setContentModified();
         this.mEditorData.setTaskId(newTaskId);
      }
   }

   public void setComplete(boolean newComplete) throws ValidationException {
      this.validateComplete(newComplete);
      if(this.mEditorData.isComplete() != newComplete) {
         this.setContentModified();
         this.mEditorData.setComplete(newComplete);
      }
   }

   public void setHasUpdates(boolean newHasUpdates) throws ValidationException {
      this.validateHasUpdates(newHasUpdates);
      if(this.mEditorData.isHasUpdates() != newHasUpdates) {
         this.setContentModified();
         this.mEditorData.setHasUpdates(newHasUpdates);
      }
   }

   public void setUpdatesApplied(boolean newUpdatesApplied) throws ValidationException {
      this.validateUpdatesApplied(newUpdatesApplied);
      if(this.mEditorData.isUpdatesApplied() != newUpdatesApplied) {
         this.setContentModified();
         this.mEditorData.setUpdatesApplied(newUpdatesApplied);
      }
   }

   public void setReportText(String newReportText) throws ValidationException {
      if(newReportText != null) {
         newReportText = StringUtils.rtrim(newReportText);
      }

      this.validateReportText(newReportText);
      if(this.mEditorData.getReportText() == null || !this.mEditorData.getReportText().equals(newReportText)) {
         this.setContentModified();
         this.mEditorData.setReportText(newReportText);
      }
   }

   public void setUpdateTaskId(Integer newUpdateTaskId) throws ValidationException {
      this.validateUpdateTaskId(newUpdateTaskId);
      if(this.mEditorData.getUpdateTaskId() == null || !this.mEditorData.getUpdateTaskId().equals(newUpdateTaskId)) {
         this.setContentModified();
         this.mEditorData.setUpdateTaskId(newUpdateTaskId);
      }
   }

   public void setBudgetCycleId(Integer newBudgetCycleId) throws ValidationException {
      this.validateBudgetCycleId(newBudgetCycleId);
      if(this.mEditorData.getBudgetCycleId() == null || !this.mEditorData.getBudgetCycleId().equals(newBudgetCycleId)) {
         this.setContentModified();
         this.mEditorData.setBudgetCycleId(newBudgetCycleId);
      }
   }

   public void setActivityType(Integer newActivityType) throws ValidationException {
      this.validateActivityType(newActivityType);
      if(this.mEditorData.getActivityType() == null || !this.mEditorData.getActivityType().equals(newActivityType)) {
         this.setContentModified();
         this.mEditorData.setActivityType(newActivityType);
      }
   }

   public void setActivityDetail(String newActivityDetail) throws ValidationException {
      if(newActivityDetail != null) {
         newActivityDetail = StringUtils.rtrim(newActivityDetail);
      }

      this.validateActivityDetail(newActivityDetail);
      if(this.mEditorData.getActivityDetail() == null || !this.mEditorData.getActivityDetail().equals(newActivityDetail)) {
         this.setContentModified();
         this.mEditorData.setActivityDetail(newActivityDetail);
      }
   }

   public void validateUserId(int newUserId) throws ValidationException {}

   public void validateReportType(int newReportType) throws ValidationException {}

   public void validateTaskId(int newTaskId) throws ValidationException {}

   public void validateComplete(boolean newComplete) throws ValidationException {}

   public void validateReportText(String newReportText) throws ValidationException {}

   public void validateHasUpdates(boolean newHasUpdates) throws ValidationException {}

   public void validateUpdatesApplied(boolean newUpdatesApplied) throws ValidationException {}

   public void validateUpdateTaskId(Integer newUpdateTaskId) throws ValidationException {}

   public void validateBudgetCycleId(Integer newBudgetCycleId) throws ValidationException {}

   public void validateActivityType(Integer newActivityType) throws ValidationException {}

   public void validateActivityDetail(String newActivityDetail) throws ValidationException {}

   public Report getReport() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ReportAdapter((ReportEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
