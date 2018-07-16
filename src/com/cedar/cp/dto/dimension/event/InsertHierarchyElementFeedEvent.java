// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;

public class InsertHierarchyElementFeedEvent extends HierarchyElementEvent {

   private Object mParentKey;
   private int mIndex;
   private DimensionElementRef mDimensionElementRef;
   private String mDescription;
   private boolean mDisabled;
   private int mCreditDebit;
   private int mAugCreditDebit;


   public InsertHierarchyElementFeedEvent(Object parentKey, int index, String visId, String description, DimensionElementRef dimensionElement, Object elementKey, boolean disabled, int creditDebit, int augCreditDebit) {
      super(elementKey, visId);
      this.mParentKey = parentKey;
      this.mIndex = index;
      this.mDescription = description;
      this.mDimensionElementRef = dimensionElement;
      this.mDisabled = disabled;
      this.mCreditDebit = creditDebit;
      this.mAugCreditDebit = augCreditDebit;
   }

   public InsertHierarchyElementFeedEvent(Object parentKey, String visId, int index, DimensionElementRef dimensionRef) {
      super((Object)null, visId);
      this.mParentKey = parentKey;
      this.mIndex = index;
      this.mDimensionElementRef = dimensionRef;
      this.mCreditDebit = 2;
      this.mAugCreditDebit = 0;
   }

   public Object getParentKey() {
      return this.mParentKey;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public DimensionElementRef getDimensionElementRef() {
      return this.mDimensionElementRef;
   }

   public boolean isDisabled() {
      return this.mDisabled;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public void setAugCreditDebit(int augCreditDebit) {
      this.mAugCreditDebit = augCreditDebit;
   }
}
