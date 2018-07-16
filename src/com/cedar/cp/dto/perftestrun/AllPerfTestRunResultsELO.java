// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllPerfTestRunResultsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"PerfTestRunResult", "PerfTestRun"};
   private transient int mPerfTestId;
   private transient int mPerfTestRunId;
   private transient long mExecutionTime;


   public AllPerfTestRunResultsELO() {
      super(new String[]{"PerfTestId", "PerfTestRunId", "ExecutionTime"});
   }

   public void add(int col1, int col2, long col3) {
      ArrayList l = new ArrayList();
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Long(col3));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mPerfTestId = ((Integer)l.get(index)).intValue();
      this.mPerfTestRunId = ((Integer)l.get(var4++)).intValue();
      this.mExecutionTime = ((Long)l.get(var4++)).longValue();
   }

   public int getPerfTestId() {
      return this.mPerfTestId;
   }

   public int getPerfTestRunId() {
      return this.mPerfTestRunId;
   }

   public long getExecutionTime() {
      return this.mExecutionTime;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
