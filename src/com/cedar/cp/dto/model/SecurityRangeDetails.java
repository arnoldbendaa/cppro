// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import java.util.List;

public class SecurityRangeDetails {

   private int rangeId;
   private int dimensionId;
   private String identifier;
   private List row;


   public int getRangeId() {
      return this.rangeId;
   }

   public void setRangeId(int rangeId) {
      this.rangeId = rangeId;
   }

   public int getDimensionId() {
      return this.dimensionId;
   }

   public void setDimensionId(int dimensionId) {
      this.dimensionId = dimensionId;
   }

   public String getIdentifier() {
      return this.identifier;
   }

   public void setIdentifier(String identifier) {
      this.identifier = identifier;
   }

   public List getRow() {
      return this.row;
   }

   public void setRow(List row) {
      this.row = row;
   }
}
