// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.dimension.DimensionElementRef;
import java.io.Serializable;

public class DimensionElementDTO implements Serializable {

   private DimensionElementRef mDimElement;
   private int mDimensionId;
   private int mDimensionElementId;
   private String mElementDescription;


   public DimensionElementDTO(DimensionElementRef dimElement, String elementDescription) {
      this.mDimElement = dimElement;
      this.mElementDescription = elementDescription;
   }

   public DimensionElementRef getDimElement() {
      return this.mDimElement;
   }

   public void setDimElement(DimensionElementRef dimElement) {
      this.mDimElement = dimElement;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public void setDimensionId(int dimensionId) {
      this.mDimensionId = dimensionId;
   }

   public int getDimensionElementId() {
      return this.mDimensionElementId;
   }

   public void setDimensionElementId(int dimensionElementId) {
      this.mDimensionElementId = dimensionElementId;
   }

   public String getElementDescription() {
      return this.mElementDescription;
   }

   public void setElementDescription(String elementDescription) {
      this.mElementDescription = elementDescription;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      if(this.getDimElement() != null) {
         sb.append(this.getDimElement());
      }

      if(this.getDimElement() != null && this.getElementDescription() != null) {
         sb.append(" - ");
      }

      if(this.getElementDescription() != null) {
         sb.append(this.getElementDescription());
      }

      return sb.toString();
   }
}
