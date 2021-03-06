// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HierarcyDetailsIncRootNodeFromDimIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Hierarchy", "Dimension", "HierarchyElement", "HierarchyElementFeed", "AugHierarchyElement", "StructureElement", "Model"};
   private transient HierarchyRef mHierarchyEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient StructureElementRef mStructureElementEntityRef;
   private transient int mHierarchyId;
   private transient int mDimensionId;
   private transient String mVisId;
   private transient String mDescription;
   private transient int mStructureElementId;


   public HierarcyDetailsIncRootNodeFromDimIdELO() {
      super(new String[]{"Hierarchy", "Dimension", "StructureElement", "HierarchyId", "DimensionId", "VisId", "Description", "StructureElementId"});
   }

   public void add(HierarchyRef eRefHierarchy, DimensionRef eRefDimension, StructureElementRef eRefStructureElement, int col1, int col2, String col3, String col4, int col5) {
      ArrayList l = new ArrayList();
      l.add(eRefHierarchy);
      l.add(eRefDimension);
      l.add(eRefStructureElement);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
      l.add(col4);
      l.add(new Integer(col5));
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
      this.mStructureElementEntityRef = (StructureElementRef)l.get(var4++);
      this.mHierarchyId = ((Integer)l.get(var4++)).intValue();
      this.mDimensionId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
   }

   public HierarchyRef getHierarchyEntityRef() {
      return this.mHierarchyEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
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
