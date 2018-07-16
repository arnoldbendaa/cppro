// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.task.TaskCheckpoint;

public abstract class AbstractTaskCheckpoint implements TaskCheckpoint {

   private int mReportId;
   private int mReportLineNumber;
   private int mCheckpointNumber;


   public int getReportId() {
      return this.mReportId;
   }

   public void setReportId(int reportId) {
      this.mReportId = reportId;
   }

   public int getNextReportLineNumber() {
      ++this.mReportLineNumber;
      return this.mReportLineNumber;
   }

   public int setCheckpointNumberUp() {
      ++this.mCheckpointNumber;
      return this.mCheckpointNumber;
   }

   public void setCheckpointNumber(int checkpointNumber) {
      this.mCheckpointNumber = checkpointNumber;
   }

   public int getCheckpointNumber() {
      return this.mCheckpointNumber;
   }
}
