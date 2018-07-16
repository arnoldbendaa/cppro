// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.HierarchyElementFeedRef;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedRefImpl;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import java.io.Serializable;

public class HierarchyElementFeedEVO implements Serializable {

   private transient HierarchyElementFeedPK mPK;
   private int mHierarchyElementId;
   private int mDimensionElementId;
   private int mChildIndex;
   private Integer mAugHierarchyElementId;
   private Integer mAugChildIndex;
   private Integer mCalElemType;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public HierarchyElementFeedEVO() {}

   public HierarchyElementFeedEVO(int newHierarchyElementId, int newDimensionElementId, int newChildIndex, Integer newAugHierarchyElementId, Integer newAugChildIndex, Integer newCalElemType) {
      this.mHierarchyElementId = newHierarchyElementId;
      this.mDimensionElementId = newDimensionElementId;
      this.mChildIndex = newChildIndex;
      this.mAugHierarchyElementId = newAugHierarchyElementId;
      this.mAugChildIndex = newAugChildIndex;
      this.mCalElemType = newCalElemType;
   }

   public HierarchyElementFeedPK getPK() {
      if(this.mPK == null) {
         this.mPK = new HierarchyElementFeedPK(this.mHierarchyElementId, this.mDimensionElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getHierarchyElementId() {
      return this.mHierarchyElementId;
   }

   public int getDimensionElementId() {
      return this.mDimensionElementId;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }

   public Integer getAugHierarchyElementId() {
      return this.mAugHierarchyElementId;
   }

   public Integer getAugChildIndex() {
      return this.mAugChildIndex;
   }

   public Integer getCalElemType() {
      return this.mCalElemType;
   }

   public void setHierarchyElementId(int newHierarchyElementId) {
      if(this.mHierarchyElementId != newHierarchyElementId) {
         this.mModified = true;
         this.mHierarchyElementId = newHierarchyElementId;
         this.mPK = null;
      }
   }

   public void setDimensionElementId(int newDimensionElementId) {
      if(this.mDimensionElementId != newDimensionElementId) {
         this.mModified = true;
         this.mDimensionElementId = newDimensionElementId;
         this.mPK = null;
      }
   }

   public void setChildIndex(int newChildIndex) {
      if(this.mChildIndex != newChildIndex) {
         this.mModified = true;
         this.mChildIndex = newChildIndex;
      }
   }

   public void setAugHierarchyElementId(Integer newAugHierarchyElementId) {
      if(this.mAugHierarchyElementId != null && newAugHierarchyElementId == null || this.mAugHierarchyElementId == null && newAugHierarchyElementId != null || this.mAugHierarchyElementId != null && newAugHierarchyElementId != null && !this.mAugHierarchyElementId.equals(newAugHierarchyElementId)) {
         this.mAugHierarchyElementId = newAugHierarchyElementId;
         this.mModified = true;
      }

   }

   public void setAugChildIndex(Integer newAugChildIndex) {
      if(this.mAugChildIndex != null && newAugChildIndex == null || this.mAugChildIndex == null && newAugChildIndex != null || this.mAugChildIndex != null && newAugChildIndex != null && !this.mAugChildIndex.equals(newAugChildIndex)) {
         this.mAugChildIndex = newAugChildIndex;
         this.mModified = true;
      }

   }

   public void setCalElemType(Integer newCalElemType) {
      if(this.mCalElemType != null && newCalElemType == null || this.mCalElemType == null && newCalElemType != null || this.mCalElemType != null && newCalElemType != null && !this.mCalElemType.equals(newCalElemType)) {
         this.mCalElemType = newCalElemType;
         this.mModified = true;
      }

   }

   public void setDetails(HierarchyElementFeedEVO newDetails) {
      this.setHierarchyElementId(newDetails.getHierarchyElementId());
      this.setDimensionElementId(newDetails.getDimensionElementId());
      this.setChildIndex(newDetails.getChildIndex());
      this.setAugHierarchyElementId(newDetails.getAugHierarchyElementId());
      this.setAugChildIndex(newDetails.getAugChildIndex());
      this.setCalElemType(newDetails.getCalElemType());
   }

   public HierarchyElementFeedEVO deepClone() {
      HierarchyElementFeedEVO cloned = new HierarchyElementFeedEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mHierarchyElementId = this.mHierarchyElementId;
      cloned.mDimensionElementId = this.mDimensionElementId;
      cloned.mChildIndex = this.mChildIndex;
      if(this.mAugHierarchyElementId != null) {
         cloned.mAugHierarchyElementId = Integer.valueOf(this.mAugHierarchyElementId.toString());
      }

      if(this.mAugChildIndex != null) {
         cloned.mAugChildIndex = Integer.valueOf(this.mAugChildIndex.toString());
      }

      if(this.mCalElemType != null) {
         cloned.mCalElemType = Integer.valueOf(this.mCalElemType.toString());
      }

      return cloned;
   }

   public void prepareForInsert(HierarchyElementEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(HierarchyElementEVO parent, int startKey) {
      return startKey;
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

   public HierarchyElementFeedRef getEntityRef(DimensionEVO evoDimension, HierarchyEVO evoHierarchy, HierarchyElementEVO evoHierarchyElement, String entityText) {
      return new HierarchyElementFeedRefImpl(new HierarchyElementFeedCK(evoDimension.getPK(), evoHierarchy.getPK(), evoHierarchyElement.getPK(), this.getPK()), entityText);
   }

   public HierarchyElementFeedCK getCK(DimensionEVO evoDimension, HierarchyEVO evoHierarchy, HierarchyElementEVO evoHierarchyElement) {
      return new HierarchyElementFeedCK(evoDimension.getPK(), evoHierarchy.getPK(), evoHierarchyElement.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("HierarchyElementId=");
      sb.append(String.valueOf(this.mHierarchyElementId));
      sb.append(' ');
      sb.append("DimensionElementId=");
      sb.append(String.valueOf(this.mDimensionElementId));
      sb.append(' ');
      sb.append("ChildIndex=");
      sb.append(String.valueOf(this.mChildIndex));
      sb.append(' ');
      sb.append("AugHierarchyElementId=");
      sb.append(String.valueOf(this.mAugHierarchyElementId));
      sb.append(' ');
      sb.append("AugChildIndex=");
      sb.append(String.valueOf(this.mAugChildIndex));
      sb.append(' ');
      sb.append("CalElemType=");
      sb.append(String.valueOf(this.mCalElemType));
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

      sb.append("HierarchyElementFeed: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
