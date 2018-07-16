// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.DimensionImportError;
import com.cedar.cp.api.dimension.DimensionImportFormat;
import com.cedar.cp.util.i18nUtils;

public class DimensionImportErrorImpl implements DimensionImportError {

   private int mSequence;
   private int mRelatedSequence;
   private int mErrorNum;
   private int mColumn;
   private String mData;


   public DimensionImportErrorImpl(int sequence, int column, int errorNum, int relatedSequence, String data) {
      this.mSequence = sequence;
      this.mRelatedSequence = relatedSequence;
      this.mErrorNum = errorNum;
      this.mColumn = column;
      this.mData = data;
   }

   public int getSequence() {
      return this.mSequence;
   }

   public int getRelatedSequence() {
      return this.mRelatedSequence;
   }

   public int getErrorNum() {
      return this.mErrorNum;
   }

   public int getColumn() {
      return this.mColumn;
   }

   public String getData() {
      return this.mData;
   }

   public String getMessage() {
      return i18nUtils.messageFormat(DimensionImportFormat.MESSAGES[this.mErrorNum], this.mData);
   }

   public String toString() {
      return "Line[" + this.mSequence + "] Col[" + this.mColumn + "] Message[" + this.getMessage() + "]";
   }
}
