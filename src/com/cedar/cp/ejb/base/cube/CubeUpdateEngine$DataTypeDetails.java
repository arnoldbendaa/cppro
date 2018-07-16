// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.api.datatype.DataTypeRef;

class CubeUpdateEngine$DataTypeDetails {

   private DataTypeRef mDataTypeRef;
   private int mSubType;
   private int mMeasureClass;
   private int mMeasureScale;
   private int mMeasureLength;


   public CubeUpdateEngine$DataTypeDetails(DataTypeRef dataTypeRef, int subType, int measureClass, int measureScale, int measureLength) {
      this.mDataTypeRef = dataTypeRef;
      this.mSubType = subType;
      this.mMeasureClass = measureClass;
      this.mMeasureScale = measureScale;
      this.mMeasureLength = measureLength;
   }

   public DataTypeRef getDataTypeRef() {
      return this.mDataTypeRef;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public int getMeasureClass() {
      return this.mMeasureClass;
   }

   public int getMeasureScale() {
      return this.mMeasureScale;
   }

   public int getMeasureLength() {
      return this.mMeasureLength;
   }
}
