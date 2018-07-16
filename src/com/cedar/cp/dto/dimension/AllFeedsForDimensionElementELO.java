// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyElementFeedRef;
import com.cedar.cp.api.dimension.HierarchyElementRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllFeedsForDimensionElementELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"HierarchyElementFeed", "HierarchyElement", "Hierarchy", "Dimension"};
   private transient HierarchyElementFeedRef mHierarchyElementFeedEntityRef;
   private transient HierarchyElementRef mHierarchyElementEntityRef;
   private transient HierarchyRef mHierarchyEntityRef;
   private transient DimensionRef mDimensionEntityRef;


   public AllFeedsForDimensionElementELO() {
      super(new String[]{"HierarchyElementFeed", "HierarchyElement", "Hierarchy", "Dimension"});
   }

   public void add(HierarchyElementFeedRef eRefHierarchyElementFeed, HierarchyElementRef eRefHierarchyElement, HierarchyRef eRefHierarchy, DimensionRef eRefDimension) {
      ArrayList l = new ArrayList();
      l.add(eRefHierarchyElementFeed);
      l.add(eRefHierarchyElement);
      l.add(eRefHierarchy);
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
      this.mHierarchyElementFeedEntityRef = (HierarchyElementFeedRef)l.get(index);
      this.mHierarchyElementEntityRef = (HierarchyElementRef)l.get(var4++);
      this.mHierarchyEntityRef = (HierarchyRef)l.get(var4++);
      this.mDimensionEntityRef = (DimensionRef)l.get(var4++);
   }

   public HierarchyElementFeedRef getHierarchyElementFeedEntityRef() {
      return this.mHierarchyElementFeedEntityRef;
   }

   public HierarchyElementRef getHierarchyElementEntityRef() {
      return this.mHierarchyElementEntityRef;
   }

   public HierarchyRef getHierarchyEntityRef() {
      return this.mHierarchyEntityRef;
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