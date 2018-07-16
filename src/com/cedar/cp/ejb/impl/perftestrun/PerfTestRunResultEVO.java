// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRunResultRef;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultCK;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultPK;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultRefImpl;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunEVO;
import java.io.Serializable;

public class PerfTestRunResultEVO implements Serializable {

   private transient PerfTestRunResultPK mPK;
   private int mPerfTestRunResultId;
   private int mPerfTestRunId;
   private int mPerfTestId;
   private long mExecutionTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public PerfTestRunResultEVO() {}

   public PerfTestRunResultEVO(int newPerfTestRunResultId, int newPerfTestRunId, int newPerfTestId, long newExecutionTime) {
      this.mPerfTestRunResultId = newPerfTestRunResultId;
      this.mPerfTestRunId = newPerfTestRunId;
      this.mPerfTestId = newPerfTestId;
      this.mExecutionTime = newExecutionTime;
   }

   public PerfTestRunResultPK getPK() {
      if(this.mPK == null) {
         this.mPK = new PerfTestRunResultPK(this.mPerfTestRunResultId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getPerfTestRunResultId() {
      return this.mPerfTestRunResultId;
   }

   public int getPerfTestRunId() {
      return this.mPerfTestRunId;
   }

   public int getPerfTestId() {
      return this.mPerfTestId;
   }

   public long getExecutionTime() {
      return this.mExecutionTime;
   }

   public void setPerfTestRunResultId(int newPerfTestRunResultId) {
      if(this.mPerfTestRunResultId != newPerfTestRunResultId) {
         this.mModified = true;
         this.mPerfTestRunResultId = newPerfTestRunResultId;
         this.mPK = null;
      }
   }

   public void setPerfTestRunId(int newPerfTestRunId) {
      if(this.mPerfTestRunId != newPerfTestRunId) {
         this.mModified = true;
         this.mPerfTestRunId = newPerfTestRunId;
      }
   }

   public void setPerfTestId(int newPerfTestId) {
      if(this.mPerfTestId != newPerfTestId) {
         this.mModified = true;
         this.mPerfTestId = newPerfTestId;
      }
   }

   public void setExecutionTime(long newExecutionTime) {
      if(this.mExecutionTime != newExecutionTime) {
         this.mModified = true;
         this.mExecutionTime = newExecutionTime;
      }
   }

   public void setDetails(PerfTestRunResultEVO newDetails) {
      this.setPerfTestRunResultId(newDetails.getPerfTestRunResultId());
      this.setPerfTestRunId(newDetails.getPerfTestRunId());
      this.setPerfTestId(newDetails.getPerfTestId());
      this.setExecutionTime(newDetails.getExecutionTime());
   }

   public PerfTestRunResultEVO deepClone() {
      PerfTestRunResultEVO cloned = new PerfTestRunResultEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mPerfTestRunResultId = this.mPerfTestRunResultId;
      cloned.mPerfTestRunId = this.mPerfTestRunId;
      cloned.mPerfTestId = this.mPerfTestId;
      cloned.mExecutionTime = this.mExecutionTime;
      return cloned;
   }

   public void prepareForInsert(PerfTestRunEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mPerfTestRunResultId > 0) {
         newKey = true;
         if(parent == null) {
            this.setPerfTestRunResultId(-this.mPerfTestRunResultId);
         } else {
            parent.changeKey(this, -this.mPerfTestRunResultId);
         }
      } else if(this.mPerfTestRunResultId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mPerfTestRunResultId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(PerfTestRunEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mPerfTestRunResultId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public PerfTestRunResultRef getEntityRef(PerfTestRunEVO evoPerfTestRun, String entityText) {
      return new PerfTestRunResultRefImpl(new PerfTestRunResultCK(evoPerfTestRun.getPK(), this.getPK()), entityText);
   }

   public PerfTestRunResultCK getCK(PerfTestRunEVO evoPerfTestRun) {
      return new PerfTestRunResultCK(evoPerfTestRun.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("PerfTestRunResultId=");
      sb.append(String.valueOf(this.mPerfTestRunResultId));
      sb.append(' ');
      sb.append("PerfTestRunId=");
      sb.append(String.valueOf(this.mPerfTestRunId));
      sb.append(' ');
      sb.append("PerfTestId=");
      sb.append(String.valueOf(this.mPerfTestId));
      sb.append(' ');
      sb.append("ExecutionTime=");
      sb.append(String.valueOf(this.mExecutionTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("PerfTestRunResult: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
