// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.parallel;

import com.cedar.cp.util.parallel.PooledObject;
import java.util.LinkedList;

public abstract class AbstractPool {

   private int mMaxCount;
   private int mPooledObjectCount;
   private LinkedList<PooledObject> mPooledEntries;


   protected AbstractPool() {
      this(0, Integer.MAX_VALUE);
   }

   protected AbstractPool(int maxCount) {
      this(0, maxCount);
   }

   protected AbstractPool(int minCount, int maxCount) {
      if(minCount < 0) {
         throw new IllegalArgumentException("Min count must be >= 0");
      } else if(minCount > maxCount) {
         throw new IllegalArgumentException("Max count is less than min count");
      } else {
         this.mMaxCount = maxCount;
         this.mPooledEntries = new LinkedList();

         for(int i = 0; i < minCount; ++i) {
            PooledObject entry = this.createPooledObject();
            entry.setPool(this);
            this.mPooledEntries.add(entry);
         }

         this.mPooledObjectCount = minCount;
      }
   }

   protected abstract PooledObject createPooledObject();

   public synchronized PooledObject acquire() {
      PooledObject entry = null;
      if(!this.mPooledEntries.isEmpty()) {
         entry = (PooledObject)this.mPooledEntries.removeFirst();
      } else if(this.mPooledObjectCount < this.mMaxCount) {
         entry = this.createPooledObject();
         entry.setPool(this);
         ++this.mPooledObjectCount;
      } else {
         try {
            do {
               this.wait();
               if(!this.mPooledEntries.isEmpty()) {
                  entry = (PooledObject)this.mPooledEntries.removeFirst();
               }
            } while(entry == null);
         } catch (InterruptedException var3) {
            return null;
         }
      }

      entry.takenFromPool();
      return entry;
   }

   public synchronized void release(PooledObject entry) {
      if(entry == null) {
         throw new IllegalArgumentException("can\'t return null entries to the pool");
      } else if(entry.getPool() != this) {
         throw new IllegalArgumentException("The \'PooledObject\' was not taken from this pool");
      } else {
         entry.returnedToPool();
         this.mPooledEntries.add(entry);
         this.notify();
      }
   }

   public synchronized void discard(PooledObject entry) {
      if(entry == null) {
         throw new IllegalArgumentException("can\'t discard null entries from the pool");
      } else if(entry.getPool() != this) {
         throw new IllegalArgumentException("The \'PooledObject\' was not taken from this pool");
      } else if(this.mPooledObjectCount == 0) {
         throw new IllegalArgumentException("The \'ObjectPool\' can not discard from an empty pool");
      } else {
         --this.mPooledObjectCount;
      }
   }
}
