// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.picker;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.utc.picker.ElementDTO;
import java.util.List;

public class DimensionDTO extends ElementDTO {

   private List mHierarchy;
   private int mDimensionType;
   private DimensionRef mDimensionRef;


   public List getHierarchy() {
      return this.mHierarchy;
   }

   public void setHierarchy(List hierarchy) {
      this.mHierarchy = hierarchy;
   }

   public int getDimensionType() {
      return this.mDimensionType;
   }

   public void setDimensionType(int dimensionType) {
      this.mDimensionType = dimensionType;
   }

   public boolean isTypeCalendar() {
      return this.mDimensionType == 3;
   }

   public DimensionRef getDimensionRef() {
      return this.mDimensionRef;
   }

   public void setDimensionRef(DimensionRef dimensionRef) {
      this.mDimensionRef = dimensionRef;
   }
}
