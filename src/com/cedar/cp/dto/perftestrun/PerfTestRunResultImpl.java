// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import com.cedar.cp.api.perftestrun.PerfTestRunResult;

public class PerfTestRunResultImpl implements PerfTestRunResult {

   private PerfTestRunRef mPerfTestRunRef;
   private long mExecutionTime;
   private int mPerfTestId;
   private int mPerfTestRunId;
   private Object mPrimaryKey;


   public PerfTestRunRef getPerfTestRunRef() {
      return this.mPerfTestRunRef;
   }

   public void setPerfTestRunRef(PerfTestRunRef perfTestRunRef) {
      this.mPerfTestRunRef = perfTestRunRef;
   }

   public long getExecutionTime() {
      return this.mExecutionTime;
   }

   public void setExecutionTime(long executionTime) {
      this.mExecutionTime = executionTime;
   }

   public int getPerfTestId() {
      return this.mPerfTestId;
   }

   public void setPerfTestId(int perfTestId) {
      this.mPerfTestId = perfTestId;
   }

   public int getPerfTestRunId() {
      return this.mPerfTestRunId;
   }

   public void setPerfTestRunId(int perfTestRunId) {
      this.mPerfTestRunId = perfTestRunId;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object primaryKey) {
      this.mPrimaryKey = primaryKey;
   }
}
