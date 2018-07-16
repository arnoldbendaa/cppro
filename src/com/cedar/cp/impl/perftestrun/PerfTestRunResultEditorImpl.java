// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.perftestrun;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import com.cedar.cp.api.perftestrun.PerfTestRunResult;
import com.cedar.cp.api.perftestrun.PerfTestRunResultEditor;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.perftestrun.PerfTestRunEditorImpl;

public class PerfTestRunResultEditorImpl extends SubBusinessEditorImpl implements PerfTestRunResultEditor {

   private PerfTestRunResultImpl mRunResult;
   private boolean mInsert;
   private int mPerfTestId;
   private int mPerfTestRunId;
   private PerfTestRunRef mPerfTestRunRef;
   private long mExecutionTime;


   public PerfTestRunResultEditorImpl(PerfTestRunEditorImpl parent, PerfTestRunResultImpl runResult) {
      super(parent.getBusinessSession(), parent);
      this.mRunResult = runResult;
      if(this.mRunResult != null) {
         this.mPerfTestId = this.mRunResult.getPerfTestId();
         this.mPerfTestRunId = this.mRunResult.getPerfTestRunId();
         this.mPerfTestRunRef = this.mRunResult.getPerfTestRunRef();
         this.mExecutionTime = this.mRunResult.getExecutionTime();
         this.mInsert = false;
      } else {
         this.mInsert = true;
         this.mRunResult = new PerfTestRunResultImpl();
      }

   }

   public void setPerfTestRunId(int newPerfTestRunId) throws ValidationException {
      this.mPerfTestRunId = newPerfTestRunId;
      this.setContentModified();
   }

   public void setPerfTestId(int newPerfTestId) throws ValidationException {
      this.mPerfTestId = newPerfTestId;
      this.setContentModified();
   }

   public void setExecutionTime(long newExecutionTime) throws ValidationException {
      this.mExecutionTime = newExecutionTime;
      this.setContentModified();
   }

   public void setPerfTestRunRef(PerfTestRunRef paramPerfTestRunRef) throws ValidationException {
      this.mPerfTestRunRef = paramPerfTestRunRef;
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      throw new IllegalStateException("Not yet implemented");
   }

   public PerfTestRunResult getPerfTestRunResult() {
      return this.mRunResult;
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      PerfTestRunEditorImpl owner = (PerfTestRunEditorImpl)this.getOwner();
      if(this.mInsert) {
         this.mRunResult.setPerfTestId(this.mPerfTestId);
         this.mRunResult.setPerfTestRunId(this.mPerfTestRunId);
         this.mRunResult.setExecutionTime(this.mExecutionTime);
         this.mRunResult.setPerfTestRunRef(this.mPerfTestRunRef);
         owner.insertRunResult(this.mRunResult);
      }

   }
}
