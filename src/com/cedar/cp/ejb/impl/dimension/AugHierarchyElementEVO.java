// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.AugHierarchyElementRef;
import com.cedar.cp.dto.dimension.AugHierarchyElementCK;
import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
import com.cedar.cp.dto.dimension.AugHierarchyElementRefImpl;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import java.io.Serializable;

public class AugHierarchyElementEVO implements Serializable {

   private transient AugHierarchyElementPK mPK;
   private int mHierarchyElementId;
   private int mHierarchyId;
   private int mParentId;
   private int mChildIndex;
   private String mVisId;
   private String mDescription;
   private int mCreditDebit;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int NOT_APPLICABLE = 0;
   public static final int CREDIT_TYPE = 1;
   public static final int DEBIT_TYPE = 2;


   public AugHierarchyElementEVO() {}

   public AugHierarchyElementEVO(int newHierarchyElementId, int newHierarchyId, int newParentId, int newChildIndex, String newVisId, String newDescription, int newCreditDebit) {
      this.mHierarchyElementId = newHierarchyElementId;
      this.mHierarchyId = newHierarchyId;
      this.mParentId = newParentId;
      this.mChildIndex = newChildIndex;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mCreditDebit = newCreditDebit;
   }

   public AugHierarchyElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new AugHierarchyElementPK(this.mHierarchyElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getHierarchyElementId() {
      return this.mHierarchyElementId;
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public int getChildIndex() {
      return this.mChildIndex;
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

   public void setHierarchyElementId(int newHierarchyElementId) {
      if(this.mHierarchyElementId != newHierarchyElementId) {
         this.mModified = true;
         this.mHierarchyElementId = newHierarchyElementId;
         this.mPK = null;
      }
   }

   public void setHierarchyId(int newHierarchyId) {
      if(this.mHierarchyId != newHierarchyId) {
         this.mModified = true;
         this.mHierarchyId = newHierarchyId;
      }
   }

   public void setParentId(int newParentId) {
      if(this.mParentId != newParentId) {
         this.mModified = true;
         this.mParentId = newParentId;
      }
   }

   public void setChildIndex(int newChildIndex) {
      if(this.mChildIndex != newChildIndex) {
         this.mModified = true;
         this.mChildIndex = newChildIndex;
      }
   }

   public void setCreditDebit(int newCreditDebit) {
      if(this.mCreditDebit != newCreditDebit) {
         this.mModified = true;
         this.mCreditDebit = newCreditDebit;
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

   public void setDetails(AugHierarchyElementEVO newDetails) {
      this.setHierarchyElementId(newDetails.getHierarchyElementId());
      this.setHierarchyId(newDetails.getHierarchyId());
      this.setParentId(newDetails.getParentId());
      this.setChildIndex(newDetails.getChildIndex());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setCreditDebit(newDetails.getCreditDebit());
   }

   public AugHierarchyElementEVO deepClone() {
      AugHierarchyElementEVO cloned = new AugHierarchyElementEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mHierarchyElementId = this.mHierarchyElementId;
      cloned.mHierarchyId = this.mHierarchyId;
      cloned.mParentId = this.mParentId;
      cloned.mChildIndex = this.mChildIndex;
      cloned.mCreditDebit = this.mCreditDebit;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      return cloned;
   }

   public void prepareForInsert(HierarchyEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mHierarchyElementId > 0) {
         newKey = true;
         if(parent == null) {
            this.setHierarchyElementId(-this.mHierarchyElementId);
         } else {
            parent.changeKey(this, -this.mHierarchyElementId);
         }
      } else if(this.mHierarchyElementId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mHierarchyElementId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(HierarchyEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mHierarchyElementId < 1) {
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

   public AugHierarchyElementRef getEntityRef(DimensionEVO evoDimension, HierarchyEVO evoHierarchy) {
      return new AugHierarchyElementRefImpl(new AugHierarchyElementCK(evoDimension.getPK(), evoHierarchy.getPK(), this.getPK()), this.mVisId);
   }

   public AugHierarchyElementCK getCK(DimensionEVO evoDimension, HierarchyEVO evoHierarchy) {
      return new AugHierarchyElementCK(evoDimension.getPK(), evoHierarchy.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("HierarchyElementId=");
      sb.append(String.valueOf(this.mHierarchyElementId));
      sb.append(' ');
      sb.append("HierarchyId=");
      sb.append(String.valueOf(this.mHierarchyId));
      sb.append(' ');
      sb.append("ParentId=");
      sb.append(String.valueOf(this.mParentId));
      sb.append(' ');
      sb.append("ChildIndex=");
      sb.append(String.valueOf(this.mChildIndex));
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

      sb.append("AugHierarchyElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
