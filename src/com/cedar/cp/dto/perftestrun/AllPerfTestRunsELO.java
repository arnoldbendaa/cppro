// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllPerfTestRunsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"PerfTestRun", "PerfTestRunResult"};
   private transient PerfTestRunRef mPerfTestRunEntityRef;
   private transient int mPerfTestRunId;
   private transient String mVisId;
   private transient String mDescription;
   private transient boolean mShipped;
   private transient Timestamp mWhenRan;


   public AllPerfTestRunsELO() {
      super(new String[]{"PerfTestRun", "PerfTestRunId", "VisId", "Description", "Shipped", "WhenRan"});
   }

   public void add(PerfTestRunRef eRefPerfTestRun, int col1, String col2, String col3, boolean col4, Timestamp col5) {
      ArrayList l = new ArrayList();
      l.add(eRefPerfTestRun);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(new Boolean(col4));
      l.add(col5);
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
      this.mPerfTestRunEntityRef = (PerfTestRunRef)l.get(index);
      this.mPerfTestRunId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mShipped = ((Boolean)l.get(var4++)).booleanValue();
      this.mWhenRan = (Timestamp)l.get(var4++);
   }

   public PerfTestRunRef getPerfTestRunEntityRef() {
      return this.mPerfTestRunEntityRef;
   }

   public int getPerfTestRunId() {
      return this.mPerfTestRunId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getShipped() {
      return this.mShipped;
   }

   public Timestamp getWhenRan() {
      return this.mWhenRan;
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
