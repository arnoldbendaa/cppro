// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDimensionElementsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DimensionElement", "Dimension"};
   private transient DimensionElementRef mDimensionElementEntityRef;
   private transient DimensionRef mDimensionEntityRef;


   public AllDimensionElementsELO() {
      super(new String[]{"DimensionElement", "Dimension"});
   }

   public void add(DimensionElementRef eRefDimensionElement, DimensionRef eRefDimension) {
      ArrayList l = new ArrayList();
      l.add(eRefDimensionElement);
      l.add(eRefDimension);
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
      this.mDimensionElementEntityRef = (DimensionElementRef)l.get(index);
      this.mDimensionEntityRef = (DimensionRef)l.get(var4++);
   }

   public DimensionElementRef getDimensionElementEntityRef() {
      return this.mDimensionElementEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
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
