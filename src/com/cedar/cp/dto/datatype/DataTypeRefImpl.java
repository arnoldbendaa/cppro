// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import java.io.Serializable;

public class DataTypeRefImpl extends EntityRefImpl implements DataTypeRef, Serializable {

   String mDescription;
   int mSubType;
   Integer mMeasureClass;
   Integer mMeasureLength;
   Integer mMeasureScale;


   public DataTypeRefImpl(DataTypePK key, String narrative, String paramDescription, int paramSubType, Integer paramMeasureClass, Integer paramMeasureLength) {
      super(key, narrative);
      this.mDescription = paramDescription;
      this.mSubType = paramSubType;
      this.mMeasureClass = paramMeasureClass;
      this.mMeasureLength = paramMeasureLength;
   }
   
   public DataTypeRefImpl(DataTypePK key, String narrative, String paramDescription, int paramSubType, Integer paramMeasureClass, Integer paramMeasureLength, Integer paramMeasureScale) {
      super(key, narrative);
      this.mDescription = paramDescription;
      this.mSubType = paramSubType;
      this.mMeasureClass = paramMeasureClass;
      this.mMeasureLength = paramMeasureLength;
      this.mMeasureScale = paramMeasureScale;
   }

   public DataTypePK getDataTypePK() {
      return (DataTypePK)this.mKey;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public Integer getMeasureClass() {
      return this.mMeasureClass;
   }

   public Integer getMeasureLength() {
      return this.mMeasureLength;
   }
   
   public Integer getMeasureScale() {
      return this.mMeasureScale;
   }

   public String toString() {
      return this.getNarrative();
   }

   public String getDisplayText() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getNarrative());
      if(this.getDescription() != null) {
         sb.append(this.getRefDelimiter());
         sb.append(this.getDescription());
      }

      return sb.toString();
   }

   public boolean allowsConfigrableRollUp() {
      return this.mSubType == 4 && this.mMeasureClass != null && this.mMeasureClass.intValue() == 1;
   }
}
