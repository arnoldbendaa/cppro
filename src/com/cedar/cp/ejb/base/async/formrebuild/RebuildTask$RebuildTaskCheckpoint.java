// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.formrebuild;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class RebuildTask$RebuildTaskCheckpoint extends AbstractTaskCheckpoint {

   private transient int mMaxBatchSize = 1;
   private int mListIndex = -1;
   private transient int mBatchCount = 0;


   public int getListIndex() {
      return this.mListIndex;
   }

   public int getNextIndex() {
      ++this.mBatchCount;
      return ++this.mListIndex;
   }

   public boolean shouldCheckpoint() {
      return this.mBatchCount == this.getMaxBatchSize();
   }

   public int getMaxBatchSize() {
      return this.mMaxBatchSize;
   }

   public void setMaxBatchSize(int maxBatchSize) {
      this.mMaxBatchSize = maxBatchSize;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add(Integer.valueOf(this.mListIndex).toString());
      return l;
   }
}
