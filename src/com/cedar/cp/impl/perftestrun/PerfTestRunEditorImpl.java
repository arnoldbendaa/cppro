// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftestrun;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRun;
import com.cedar.cp.api.perftestrun.PerfTestRunEditor;
import com.cedar.cp.api.perftestrun.PerfTestRunResult;
import com.cedar.cp.api.perftestrun.PerfTestRunResultEditor;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionSSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunImpl;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.perftestrun.PerfTestRunAdapter;
import com.cedar.cp.impl.perftestrun.PerfTestRunEditorSessionImpl;
import com.cedar.cp.impl.perftestrun.PerfTestRunResultEditorImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;

public class PerfTestRunEditorImpl extends BusinessEditorImpl implements PerfTestRunEditor, SubBusinessEditorOwner {

   private PerfTestRunResultEditor mSubEditor;
   private PerfTestRunEditorSessionSSO mServerSessionData;
   private PerfTestRunImpl mEditorData;
   private PerfTestRunAdapter mEditorDataAdapter;


   public PerfTestRunEditorImpl(PerfTestRunEditorSessionImpl session, PerfTestRunEditorSessionSSO serverSessionData, PerfTestRunImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(PerfTestRunEditorSessionSSO serverSessionData, PerfTestRunImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setShipped(boolean newShipped) throws ValidationException {
      this.validateShipped(newShipped);
      if(this.mEditorData.isShipped() != newShipped) {
         this.setContentModified();
         this.mEditorData.setShipped(newShipped);
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

   public void setWhenRan(Timestamp newWhenRan) throws ValidationException {
      this.validateWhenRan(newWhenRan);
      if(this.mEditorData.getWhenRan() == null || !this.mEditorData.getWhenRan().equals(newWhenRan)) {
         this.setContentModified();
         this.mEditorData.setWhenRan(newWhenRan);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a PerfTestRun");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 255) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 255 on a PerfTestRun");
      }
   }

   public void validateShipped(boolean newShipped) throws ValidationException {}

   public void validateWhenRan(Timestamp newWhenRan) throws ValidationException {}

   public PerfTestRun getPerfTestRun() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new PerfTestRunAdapter((PerfTestRunEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public PerfTestRunResultEditor getEditor(PerfTestRunResult runResult) throws ValidationException {
      if(this.mSubEditor != null) {
         throw new IllegalStateException("Only one subeditor supported at a time");
      } else {
         this.mSubEditor = new PerfTestRunResultEditorImpl(this, (PerfTestRunResultImpl)runResult);
         return this.mSubEditor;
      }
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
      if(this.mSubEditor != editor) {
         throw new CPException("Unknown sub editor in removeSubBusinessEditor");
      } else {
         this.mSubEditor = null;
      }
   }

   public void insertRunResult(PerfTestRunResultImpl runResult) throws ValidationException {
      this.mEditorData.addRunResult(runResult);
      this.setContentModified();
   }
}
