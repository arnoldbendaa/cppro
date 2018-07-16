// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import java.io.Serializable;
import java.math.BigDecimal;

public class CellCalcData implements Serializable {

   private int mRowId;
   private String mColId;
   private String mStringValue;
   private BigDecimal mNumericValue;


   public int getRowId() {
      return this.mRowId;
   }

   public void setRowId(int rowId) {
      this.mRowId = rowId;
   }

   public String getColId() {
      return this.mColId;
   }

   public void setColId(String colId) {
      this.mColId = colId;
   }

   public String getStringValue() {
      return this.mStringValue;
   }

   public void setStringValue(String stringValue) {
      this.mStringValue = stringValue;
   }

   public BigDecimal getNumericValue() {
      return this.mNumericValue;
   }

   public void setNumericValue(BigDecimal numericValue) {
      this.mNumericValue = numericValue;
   }
}
