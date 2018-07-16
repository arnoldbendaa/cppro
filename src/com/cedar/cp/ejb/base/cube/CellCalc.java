// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CellCalcData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellCalc implements Serializable {

   private static final int S_NEW_OBJECT_VALUE_BOUNDARY = -500000000;
   private int mShortId;
   private List mCellCalcData = new ArrayList();


   public int getShortId() {
      return this.mShortId;
   }

   public void setShortId(int shortId) {
      this.mShortId = shortId;
   }

   public void addCellCalcData(CellCalcData cellCalcData) {
      this.mCellCalcData.add(cellCalcData);
   }

   public List getCellCalcData() {
      return this.mCellCalcData;
   }

   public int getCellCalcDataCount() {
      return this.mCellCalcData.size();
   }

   public boolean isNewKey() {
      return this.mShortId < -500000000;
   }

   public boolean equals(Object o) {
      if(this == o) {
         return true;
      } else if(o != null && this.getClass() == o.getClass()) {
         CellCalc cellCalc = (CellCalc)o;
         return this.mShortId == cellCalc.mShortId;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.mShortId;
   }
}
