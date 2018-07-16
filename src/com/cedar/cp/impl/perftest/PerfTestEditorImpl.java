// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftest;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftest.PerfTest;
import com.cedar.cp.api.perftest.PerfTestEditor;
import com.cedar.cp.dto.perftest.PerfTestEditorSessionSSO;
import com.cedar.cp.dto.perftest.PerfTestImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.perftest.PerfTestAdapter;
import com.cedar.cp.impl.perftest.PerfTestEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class PerfTestEditorImpl extends BusinessEditorImpl implements PerfTestEditor {

   private PerfTestEditorSessionSSO mServerSessionData;
   private PerfTestImpl mEditorData;
   private PerfTestAdapter mEditorDataAdapter;


   public PerfTestEditorImpl(PerfTestEditorSessionImpl session, PerfTestEditorSessionSSO serverSessionData, PerfTestImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(PerfTestEditorSessionSSO serverSessionData, PerfTestImpl editorData) {
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

   public void setClassName(String newClassName) throws ValidationException {
      if(newClassName != null) {
         newClassName = StringUtils.rtrim(newClassName);
      }

      this.validateClassName(newClassName);
      if(this.mEditorData.getClassName() == null || !this.mEditorData.getClassName().equals(newClassName)) {
         this.setContentModified();
         this.mEditorData.setClassName(newClassName);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a PerfTest");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 255) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 255 on a PerfTest");
      }
   }

   public void validateClassName(String newClassName) throws ValidationException {
      if(newClassName != null && newClassName.length() > 255) {
         throw new ValidationException("length (" + newClassName.length() + ") of ClassName must not exceed 255 on a PerfTest");
      }
   }

   public PerfTest getPerfTest() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new PerfTestAdapter((PerfTestEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
