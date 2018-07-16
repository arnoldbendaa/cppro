// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.DimensionElementEvent;

public class UpdateDimensionElementEvent extends DimensionElementEvent {

   private String mVisId;
   private String mOrigVisId;
   private String mDescription;
   private Integer mCreditDebit;
   private Integer mAugCreditDebit;
   private Boolean mNotPlannable;
   private Boolean mDisabled;
   private Boolean mNullElement;


   public UpdateDimensionElementEvent(Object elementKey, String origVisId, String visId, String description, Integer creditDebit, Integer augCreditDebit, Boolean notPlannable, Boolean disabled, Boolean nullElement) {
      super(elementKey);
      this.mOrigVisId = origVisId;
      this.mVisId = visId;
      this.mDescription = description;
      this.mCreditDebit = creditDebit;
      this.mNotPlannable = notPlannable;
      this.mAugCreditDebit = augCreditDebit;
      this.mDisabled = disabled;
      this.mNullElement = nullElement;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getCreditDebit() {
      return this.mCreditDebit;
   }

   public Boolean isDisabled() {
      return this.mDisabled;
   }

   public String getOrigVisId() {
      return this.mOrigVisId;
   }

   public Integer getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public Boolean isNotPlannable() {
      return this.mNotPlannable;
   }

   public Boolean isNullElement() {
      return this.mNullElement;
   }
}
