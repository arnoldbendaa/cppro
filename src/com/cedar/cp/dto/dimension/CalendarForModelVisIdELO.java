// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalendarForModelVisIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Hierarchy", "Dimension", "HierarchyElement", "HierarchyElementFeed", "AugHierarchyElement", "Model", "ModelDimensionRel", "Model"};
   private transient HierarchyRef mHierarchyEntityRef;
   private transient DimensionRef mDimensionEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient int mHierarchyId;


   public CalendarForModelVisIdELO() {
      super(new String[]{"Hierarchy", "Dimension", "Model", "ModelDimensionRel", "HierarchyId"});
   }

   public void add(HierarchyRef eRefHierarchy, DimensionRef eRefDimension, ModelRef eRefModel, ModelDimensionRelRef eRefModelDimensionRel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefHierarchy);
      l.add(eRefDimension);
      l.add(eRefModel);
      l.add(eRefModelDimensionRel);
      l.add(new Integer(col1));
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
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
      this.mHierarchyId = ((Integer)l.get(var4++)).intValue();
   }

   public HierarchyRef getHierarchyEntityRef() {
      return this.mHierarchyEntityRef;
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
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
