// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSecurityStructureElementsELO extends AbstractELO implements Serializable {

   private transient int mStructureElementId;


   public AllSecurityStructureElementsELO() {
      super(new String[]{"StructureElementId"});
   }

   public void add(int id) {
      ArrayList l = new ArrayList();
      l.add(new Integer(id));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      byte var10002 = index;
      int index1 = index + 1;
      this.mStructureElementId = ((Integer)l.get(var10002)).intValue();
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }
}
