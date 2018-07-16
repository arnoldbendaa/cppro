// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.Cell;

public class CellCalcLink extends Cell {

   private static final int S_NEW_OBJECT_VALUE_BOUNDARY = -500000000;
   private int mShortId;
   private int mCellCalcId;


   public int getShortId() {
      return this.mShortId;
   }

   public void setShortId(int shortId) {
      this.mShortId = shortId;
   }

   public int getCellCalcId() {
      return this.mCellCalcId;
   }

   public void setCellCalcId(int cellCalcId) {
      this.mCellCalcId = cellCalcId;
   }

   public boolean isNewKey() {
      return this.mShortId < -500000000;
   }
}
