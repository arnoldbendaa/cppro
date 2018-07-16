// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;

public class UpdateHierarchyElementEvent extends HierarchyElementEvent {

   private String mOrigVisId;
   private String mDescription;
   private Integer mCreditDebit;
   private Integer mAugCreditDebit;


   public UpdateHierarchyElementEvent(Object elementKey, String origVisId, String visId, String description, Integer creditDebit, Integer augCreditDebit) {
      super(elementKey, visId);
      this.mOrigVisId = origVisId;
      this.mDescription = description;
      this.mCreditDebit = creditDebit;
      this.mAugCreditDebit = augCreditDebit;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getCreditDebit() {
      return this.mCreditDebit;
   }

   public String getOrigVisId() {
      return this.mOrigVisId;
   }

   public Integer getAugCreditDebit() {
      return this.mAugCreditDebit;
   }
}
