// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cc;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.Arrays;
import java.util.List;

public class CellCalcImportCheckPoint extends AbstractTaskCheckpoint {

   private int mBatchesProcessed;
   private int mCalcsProcessed;


   public List toDisplay() {
      return Arrays.asList(new String[]{"Cell calc import", "Imports batches processed: " + this.mBatchesProcessed, "Calcs processed:" + this.mCalcsProcessed});
   }

   public int getBatchesProcessed() {
      return this.mBatchesProcessed;
   }

   public void setBatchesProcessed(int batchesProcessed) {
      this.mBatchesProcessed = batchesProcessed;
   }

   public int getCalcsProcessed() {
      return this.mCalcsProcessed;
   }

   public void setCalcsProcessed(int calcsProcessed) {
      this.mCalcsProcessed = calcsProcessed;
   }
}
