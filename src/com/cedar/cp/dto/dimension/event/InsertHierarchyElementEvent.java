// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;

public class InsertHierarchyElementEvent extends HierarchyElementEvent {

   private Object mParentKey;
   private int mIndex;
   private String mDescription;
   private String mParentVisId;
   private int mCreditDebit;
   private int mAugCreditDebit;


   public InsertHierarchyElementEvent(Object parentKey, String parentVisId, String visId, String description, int index, int creditDebit, int augCreditDebit, Object elementKey) {
      super(elementKey, visId);
      this.mParentVisId = parentVisId;
      this.mDescription = description;
      this.mParentKey = parentKey;
      this.mIndex = index;
      this.mCreditDebit = creditDebit;
      this.mAugCreditDebit = augCreditDebit;
   }

   public Object getParentKey() {
      return this.mParentKey;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public String getParentVisId() {
      return this.mParentVisId;
   }

   public void setParentVisId(String parentVisId) {
      this.mParentVisId = parentVisId;
   }

   public int getAugCreditDebit() {
      return this.mAugCreditDebit;
   }
}
