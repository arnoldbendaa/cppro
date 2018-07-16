// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportableDimensionsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Dimension", "DimensionElement", "CalendarSpec", "CalendarYearSpec", "Hierarchy", "HierarchyElement", "HierarchyElementFeed", "AugHierarchyElement", "SecurityRange", "SecurityRangeRow", "DimensionElement", "Model", "Model"};
   private transient DimensionRef mDimensionEntityRef;
   private transient DimensionElementRef mDimensionElementEntityRef;
   private transient String mDescription;


   public ImportableDimensionsELO() {
      super(new String[]{"Dimension", "DimensionElement", "Description"});
   }

   public void add(DimensionRef eRefDimension, DimensionElementRef eRefDimensionElement, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefDimension);
      l.add(eRefDimensionElement);
      l.add(col1);
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
      this.mDimensionElementEntityRef = (DimensionElementRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public DimensionElementRef getDimensionElementEntityRef() {
      return this.mDimensionElementEntityRef;
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
