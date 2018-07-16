// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.parallel;

import com.cedar.cp.util.parallel.AbstractPool;
import com.cedar.cp.util.parallel.ParallelExecution;
import com.cedar.cp.util.parallel.PooledObject;

public class PooledParallelExecution extends ParallelExecution implements PooledObject {

   private AbstractPool mPool;
   public boolean mOutOfPool = false;


   public PooledParallelExecution(int threadCount, int maxQueueLength) {
      super(threadCount, maxQueueLength);
   }

   public AbstractPool getPool() {
      return this.mPool;
   }

   public void setPool(AbstractPool pool) {
      this.mPool = pool;
   }

   public void returnedToPool() {
      this.mOutOfPool = false;
      if(this.getActiveRequests() > 0) {
         throw new IllegalStateException("PooledParallelExecution is still processing");
      }
   }

   public void takenFromPool() {
      this.mOutOfPool = true;
      if(this.getActiveRequests() > 0) {
         throw new IllegalStateException("PooledParallelExecution is still processing");
      }
   }
}
