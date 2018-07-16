// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import java.io.Serializable;

public class DimensionElementEVO implements Serializable {

   private transient DimensionElementPK mPK;
   private int mDimensionElementId;
   private int mDimensionId;
   private String mVisId;
   private String mDescription;
   private int mCreditDebit;
   private boolean mDisabled;
   private boolean mNotPlannable;
   private Integer mAugCreditDebit;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int NOT_APPLICABLE = 0;
   public static final int CREDIT_TYPE = 1;
   public static final int DEBIT_TYPE = 2;


   public DimensionElementEVO() {}

   public DimensionElementEVO(int newDimensionElementId, int newDimensionId, String newVisId, String newDescription, int newCreditDebit, boolean newDisabled, boolean newNotPlannable, Integer newAugCreditDebit) {
      this.mDimensionElementId = newDimensionElementId;
      this.mDimensionId = newDimensionId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mCreditDebit = newCreditDebit;
      this.mDisabled = newDisabled;
      this.mNotPlannable = newNotPlannable;
      this.mAugCreditDebit = newAugCreditDebit;
   }

   public DimensionElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DimensionElementPK(this.mDimensionElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getDimensionElementId() {
      return this.mDimensionElementId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
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

   public boolean getDisabled() {
      return this.mDisabled;
   }

   public boolean getNotPlannable() {
      return this.mNotPlannable;
   }

   public Integer getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public void setDimensionElementId(int newDimensionElementId) {
      if(this.mDimensionElementId != newDimensionElementId) {
         this.mModified = true;
         this.mDimensionElementId = newDimensionElementId;
         this.mPK = null;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
      }
   }

   public void setCreditDebit(int newCreditDebit) {
      if(this.mCreditDebit != newCreditDebit) {
         this.mModified = true;
         this.mCreditDebit = newCreditDebit;
      }
   }

   public void setDisabled(boolean newDisabled) {
      if(this.mDisabled != newDisabled) {
         this.mModified = true;
         this.mDisabled = newDisabled;
      }
   }

   public void setNotPlannable(boolean newNotPlannable) {
      if(this.mNotPlannable != newNotPlannable) {
         this.mModified = true;
         this.mNotPlannable = newNotPlannable;
      }
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setAugCreditDebit(Integer newAugCreditDebit) {
      if(this.mAugCreditDebit != null && newAugCreditDebit == null || this.mAugCreditDebit == null && newAugCreditDebit != null || this.mAugCreditDebit != null && newAugCreditDebit != null && !this.mAugCreditDebit.equals(newAugCreditDebit)) {
         this.mAugCreditDebit = newAugCreditDebit;
         this.mModified = true;
      }

   }

   public void setDetails(DimensionElementEVO newDetails) {
      this.setDimensionElementId(newDetails.getDimensionElementId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setCreditDebit(newDetails.getCreditDebit());
      this.setDisabled(newDetails.getDisabled());
      this.setNotPlannable(newDetails.getNotPlannable());
      this.setAugCreditDebit(newDetails.getAugCreditDebit());
   }

   public DimensionElementEVO deepClone() {
      DimensionElementEVO cloned = new DimensionElementEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mDimensionElementId = this.mDimensionElementId;
      cloned.mDimensionId = this.mDimensionId;
      cloned.mCreditDebit = this.mCreditDebit;
      cloned.mDisabled = this.mDisabled;
      cloned.mNotPlannable = this.mNotPlannable;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mAugCreditDebit != null) {
         cloned.mAugCreditDebit = Integer.valueOf(this.mAugCreditDebit.toString());
      }

      return cloned;
   }

   public void prepareForInsert(DimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mDimensionElementId > 0) {
         newKey = true;
         if(parent == null) {
            this.setDimensionElementId(-this.mDimensionElementId);
         } else {
            parent.changeKey(this, -this.mDimensionElementId);
         }
      } else if(this.mDimensionElementId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDimensionElementId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(DimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mDimensionElementId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public DimensionElementRef getEntityRef(DimensionEVO evoDimension) {
      return new DimensionElementRefImpl(new DimensionElementCK(evoDimension.getPK(), this.getPK()), this.mVisId, this.mCreditDebit);
   }

   public DimensionElementCK getCK(DimensionEVO evoDimension) {
      return new DimensionElementCK(evoDimension.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DimensionElementId=");
      sb.append(String.valueOf(this.mDimensionElementId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("CreditDebit=");
      sb.append(String.valueOf(this.mCreditDebit));
      sb.append(' ');
      sb.append("Disabled=");
      sb.append(String.valueOf(this.mDisabled));
      sb.append(' ');
      sb.append("NotPlannable=");
      sb.append(String.valueOf(this.mNotPlannable));
      sb.append(' ');
      sb.append("AugCreditDebit=");
      sb.append(String.valueOf(this.mAugCreditDebit));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("DimensionElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
