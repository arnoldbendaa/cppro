// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.DimensionNode;

public class DimensionNodeImpl implements DimensionNode {

   private int mDimensionId;
   private String mVisId;
   private String mDescription;


   public int getDimensionId() {
      return this.mDimensionId;
   }

   public void setDimensionId(int dimensionId) {
      this.mDimensionId = dimensionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String toString() {
      return this.getVisId();
   }
}
