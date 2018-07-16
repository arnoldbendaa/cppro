// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.parallel;

import com.cedar.cp.util.parallel.BlockingQueue;
import com.cedar.cp.util.parallel.ParallelExecution$NestedException;
import com.cedar.cp.util.parallel.ParallelExecution$PooledThread;

public class ParallelExecution extends ThreadGroup {

   private BlockingQueue mTasks;
   private ParallelExecution$PooledThread[] mPooledThreads;
   private ParallelExecution$NestedException mLastError;


   public ParallelExecution(int threadCount, int maxQueueLength) {
      super("ParallelExecution(" + threadCount + ")");
      this.mTasks = new BlockingQueue(maxQueueLength);
      this.mPooledThreads = new ParallelExecution$PooledThread[threadCount];

      for(int i = 0; i < threadCount; ++i) {
         this.mPooledThreads[i] = new ParallelExecution$PooledThread(this, i);
         this.mPooledThreads[i].start();
      }

   }

   public void execute(Runnable work) throws ParallelExecution$NestedException {
      if(this.mLastError != null) {
         throw this.mLastError;
      } else {
         try {
            this.mTasks.enqueue(work);
         } catch (InterruptedException var3) {
            ;
         }

      }
   }

   public int getMaxQueueLength() {
      return this.mTasks.getMaxQueueLength();
   }

   public Throwable getLastThrowable() {
      return this.mLastError;
   }

   public int getOutstandingWorkCount() {
      return this.mTasks.size();
   }

   public boolean isAlive() {
      ParallelExecution$PooledThread[] arr$ = this.mPooledThreads;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ParallelExecution$PooledThread mPooledThread = arr$[i$];
         if(!mPooledThread.isAlive()) {
            return false;
         }
      }

      return true;
   }

   public int getActiveRequests() {
      int count = 0;
      ParallelExecution$PooledThread[] arr$ = this.mPooledThreads;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ParallelExecution$PooledThread mPooledThread = arr$[i$];
         if(mPooledThread.isProcessing()) {
            ++count;
         }
      }

      return count;
   }

   public void waitForWorkToComplete() throws ParallelExecution$NestedException {
      if(this.mLastError != null) {
         throw this.mLastError;
      } else {
         do {
            if(this.getOutstandingWorkCount() <= 0 && this.getActiveRequests() <= 0) {
               if(this.mLastError != null) {
                  throw this.mLastError;
               }

               return;
            }

            try {
               Thread.sleep(500L);
            } catch (Exception var2) {
               Thread.currentThread().interrupt();
            }
         } while(this.mLastError == null);

         throw this.mLastError;
      }
   }

   public void setLastError(Throwable t) {
      this.mLastError = null;
      if(t != null) {
         this.mLastError = new ParallelExecution$NestedException(t);
      }

   }

   protected BlockingQueue getTasks() {
      return this.mTasks;
   }
}
