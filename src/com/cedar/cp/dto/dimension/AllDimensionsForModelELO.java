// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDimensionsForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Dimension", "DimensionElement", "CalendarSpec", "CalendarYearSpec", "Hierarchy", "HierarchyElement", "HierarchyElementFeed", "AugHierarchyElement", "SecurityRange", "SecurityRangeRow", "ModelDimensionRel", "Model", "Model"};
   private transient DimensionRef mDimensionEntityRef;
   private transient ModelDimensionRelRef mModelDimensionRelEntityRef;
   private transient int mDimensionId;
   private transient String mVisId;
   private transient String mDescription;
   private transient int mType;
   private transient String mCol5;


   public AllDimensionsForModelELO() {
      super(new String[]{"Dimension", "ModelDimensionRel", "DimensionId", "VisId", "Description", "Type", "col5"});
   }

   public void add(DimensionRef eRefDimension, ModelDimensionRelRef eRefModelDimensionRel, int col1, String col2, String col3, int col4, String col5) {
      ArrayList l = new ArrayList();
      l.add(eRefDimension);
      l.add(eRefModelDimensionRel);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(new Integer(col4));
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
      this.mDimensionEntityRef = (DimensionRef)l.get(index);
      this.mModelDimensionRelEntityRef = (ModelDimensionRelRef)l.get(var4++);
      this.mDimensionId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mType = ((Integer)l.get(var4++)).intValue();
      this.mCol5 = (String)l.get(var4++);
   }

   public DimensionRef getDimensionEntityRef() {
      return this.mDimensionEntityRef;
   }

   public ModelDimensionRelRef getModelDimensionRelEntityRef() {
      return this.mModelDimensionRelEntityRef;
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

   public int getType() {
      return this.mType;
   }

   public String getCol5() {
      return this.mCol5;
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
