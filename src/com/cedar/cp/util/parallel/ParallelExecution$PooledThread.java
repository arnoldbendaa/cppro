// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.parallel;

import com.cedar.cp.util.parallel.ParallelExecution;
import com.cedar.cp.util.parallel.PooledParallelExecution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParallelExecution$PooledThread extends Thread {

   private List<Runnable> mWorkList = Collections.synchronizedList(new ArrayList(1));
   private ParallelExecution mExecutor;


   public ParallelExecution$PooledThread(ParallelExecution pe, int id) {
      super(pe, "TP" + id);
      super.setDaemon(true);
      this.mExecutor = pe;
   }

   public void run() {
      while(true) {
         try {
            if(!this.isInterrupted()) {
               this.mExecutor.getTasks().dequeue(this.mWorkList);
               if(this.mExecutor instanceof PooledParallelExecution) {
                  PooledParallelExecution t = (PooledParallelExecution)this.mExecutor;
                  if(!t.mOutOfPool) {
                     System.out.println("Doing work when inside the pool!!!!!");
                  }
               }

               Runnable t1 = (Runnable)this.mWorkList.get(0);
               t1.run();
               this.mWorkList.clear();
               continue;
            }
         } catch (InterruptedException var6) {
            ;
         } catch (Throwable var7) {
            this.mExecutor.setLastError(var7);
         } finally {
            this.mWorkList = null;
         }

         return;
      }
   }

   public boolean isProcessing() {
      return this.isAlive() && !this.mWorkList.isEmpty();
   }
}
