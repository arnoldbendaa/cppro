// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AvailableDimensionsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Dimension", "DimensionElement", "CalendarSpec", "CalendarYearSpec", "Hierarchy", "HierarchyElement", "HierarchyElementFeed", "AugHierarchyElement", "SecurityRange", "SecurityRangeRow", "Model", "Model"};
   private transient DimensionRef mDimensionEntityRef;
   private transient int mType;
   private transient String mDescription;


   public AvailableDimensionsELO() {
      super(new String[]{"Dimension", "Type", "Description"});
   }

   public void add(DimensionRef eRefDimension, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefDimension);
      l.add(new Integer(col1));
      l.add(col2);
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
      this.mDimensionEntityRef = (DimensionRef)l.get(index);
      this.mType = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public int getType() {
      return this.mType;
   }

   public String getDescription() {
      return this.mDescription;
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
