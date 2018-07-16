// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.dimension.DimensionElementRef;
import java.io.Serializable;

public class AmmUnmappedElement implements Serializable {

   private DimensionElementRef mElement;
   private String mDescription;


   public AmmUnmappedElement(DimensionElementRef element, String description) {
      this.mElement = element;
      this.mDescription = description;
   }

   public DimensionElementRef getElement() {
      return this.mElement;
   }

   public void setElement(DimensionElementRef element) {
      this.mElement = element;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }
}
