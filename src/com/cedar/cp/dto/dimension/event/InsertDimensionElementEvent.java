// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.DimensionElementEvent;

public class InsertDimensionElementEvent extends DimensionElementEvent {

   private String mVisId;
   private String mDescription;
   private int mCreditDebit;
   private int mAugCreditDebit;
   private boolean mNotPlannable;
   private boolean mDisabled;
   private boolean mIsNullElement;


   public InsertDimensionElementEvent(Object elementKey, String visId, String description, int debitCredit, int augCreditDebit, boolean notPlannable, boolean disabled, boolean isNullElement) {
      super(elementKey);
      this.mVisId = visId;
      this.mDescription = description;
      this.mCreditDebit = debitCredit;
      this.mAugCreditDebit = augCreditDebit;
      this.mNotPlannable = notPlannable;
      this.mDisabled = disabled;
      this.mIsNullElement = isNullElement;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public boolean isDisabled() {
      return this.mDisabled;
   }

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public boolean isNotPlannable() {
      return this.mNotPlannable;
   }

   public boolean isIsNullElement() {
      return this.mIsNullElement;
   }
}
