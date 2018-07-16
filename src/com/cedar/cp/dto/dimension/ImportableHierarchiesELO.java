// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportableHierarchiesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Hierarchy", "Dimension", "HierarchyElement", "HierarchyElementFeed", "AugHierarchyElement", "Model"};
   private transient HierarchyRef mHierarchyEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient String mDescription;


   public ImportableHierarchiesELO() {
      super(new String[]{"Hierarchy", "Dimension", "Description"});
   }

   public void add(HierarchyRef eRefHierarchy, DimensionRef eRefDimension, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefHierarchy);
      l.add(eRefDimension);
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
      this.mHierarchyEntityRef = (HierarchyRef)l.get(index);
      this.mDimensionEntityRef = (DimensionRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public HierarchyRef getHierarchyEntityRef() {
      return this.mHierarchyEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
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
