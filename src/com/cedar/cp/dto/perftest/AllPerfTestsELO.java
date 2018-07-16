// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.perftest;

import com.cedar.cp.api.perftest.PerfTestRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllPerfTestsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"PerfTest"};
   private transient PerfTestRef mPerfTestEntityRef;
   private transient int mPerfTestId;
   private transient String mVisId;
   private transient String mDescription;
   private transient String mClassName;


   public AllPerfTestsELO() {
      super(new String[]{"PerfTest", "PerfTestId", "VisId", "Description", "ClassName"});
   }

   public void add(PerfTestRef eRefPerfTest, int col1, String col2, String col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefPerfTest);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(col4);
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
      this.mPerfTestEntityRef = (PerfTestRef)l.get(index);
      this.mPerfTestId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mClassName = (String)l.get(var4++);
   }

   public PerfTestRef getPerfTestEntityRef() {
      return this.mPerfTestEntityRef;
   }

   public int getPerfTestId() {
      return this.mPerfTestId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getClassName() {
      return this.mClassName;
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
