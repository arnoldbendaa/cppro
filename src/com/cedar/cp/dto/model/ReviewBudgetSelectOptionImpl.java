// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ReviewBudgetSelectOption;
import java.io.Serializable;

public class ReviewBudgetSelectOptionImpl implements ReviewBudgetSelectOption, Serializable {

   private String mLabel;
   private int mValue;
   private int mDepth;
   private int mCalElemType;
   private int mPosition;


   public ReviewBudgetSelectOptionImpl(int depth, int value, String label, int calElemType, int position) {
      this.mDepth = depth;
      this.mLabel = label;
      this.mValue = value;
      this.mCalElemType = calElemType;
      this.mPosition = position;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public String getLabel() {
      return this.mLabel;
   }

   public int getValue() {
      return this.mValue;
   }

   public String toString() {
      return this.mLabel;
   }

   public int getCalElemType() {
      return this.mCalElemType;
   }

   public int getPosition() {
      return this.mPosition;
   }
}
