// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class StructureElementEVO implements Serializable {

   private transient StructureElementPK mPK;
   private int mStructureId;
   private int mStructureElementId;
   private String mVisId;
   private String mDescription;
   private int mParentId;
   private int mChildIndex;
   private int mDepth;
   private int mPosition;
   private int mEndPosition;
   private boolean mLeaf;
   private boolean mIsCredit;
   private boolean mDisabled;
   private boolean mNotPlannable;
   private int mCalElemType;
   private String mCalVisIdPrefix;
   private Timestamp mActualDate;
   private boolean mModified;


   public StructureElementEVO() {}

   public StructureElementEVO(int newStructureId, int newStructureElementId, String newVisId, String newDescription, int newParentId, int newChildIndex, int newDepth, int newPosition, int newEndPosition, boolean newLeaf, boolean newIsCredit, boolean newDisabled, boolean newNotPlannable, int newCalElemType, String newCalVisIdPrefix, Timestamp newActualDate) {
      this.mStructureId = newStructureId;
      this.mStructureElementId = newStructureElementId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mParentId = newParentId;
      this.mChildIndex = newChildIndex;
      this.mDepth = newDepth;
      this.mPosition = newPosition;
      this.mEndPosition = newEndPosition;
      this.mLeaf = newLeaf;
      this.mIsCredit = newIsCredit;
      this.mDisabled = newDisabled;
      this.mNotPlannable = newNotPlannable;
      this.mCalElemType = newCalElemType;
      this.mCalVisIdPrefix = newCalVisIdPrefix;
      this.mActualDate = newActualDate;
   }

   public StructureElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new StructureElementPK(this.mStructureId, this.mStructureElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public int getEndPosition() {
      return this.mEndPosition;
   }

   public boolean getLeaf() {
      return this.mLeaf;
   }

   public boolean getIsCredit() {
      return this.mIsCredit;
   }

   public boolean getDisabled() {
      return this.mDisabled;
   }

   public boolean getNotPlannable() {
      return this.mNotPlannable;
   }

   public int getCalElemType() {
      return this.mCalElemType;
   }

   public String getCalVisIdPrefix() {
      return this.mCalVisIdPrefix;
   }

   public Timestamp getActualDate() {
      return this.mActualDate;
   }

   public void setStructureId(int newStructureId) {
      if(this.mStructureId != newStructureId) {
         this.mModified = true;
         this.mStructureId = newStructureId;
         this.mPK = null;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
         this.mPK = null;
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

   public void setDepth(int newDepth) {
      if(this.mDepth != newDepth) {
         this.mModified = true;
         this.mDepth = newDepth;
      }
   }

   public void setPosition(int newPosition) {
      if(this.mPosition != newPosition) {
         this.mModified = true;
         this.mPosition = newPosition;
      }
   }

   public void setEndPosition(int newEndPosition) {
      if(this.mEndPosition != newEndPosition) {
         this.mModified = true;
         this.mEndPosition = newEndPosition;
      }
   }

   public void setLeaf(boolean newLeaf) {
      if(this.mLeaf != newLeaf) {
         this.mModified = true;
         this.mLeaf = newLeaf;
      }
   }

   public void setIsCredit(boolean newIsCredit) {
      if(this.mIsCredit != newIsCredit) {
         this.mModified = true;
         this.mIsCredit = newIsCredit;
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

   public void setCalElemType(int newCalElemType) {
      if(this.mCalElemType != newCalElemType) {
         this.mModified = true;
         this.mCalElemType = newCalElemType;
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

   public void setCalVisIdPrefix(String newCalVisIdPrefix) {
      if(this.mCalVisIdPrefix != null && newCalVisIdPrefix == null || this.mCalVisIdPrefix == null && newCalVisIdPrefix != null || this.mCalVisIdPrefix != null && newCalVisIdPrefix != null && !this.mCalVisIdPrefix.equals(newCalVisIdPrefix)) {
         this.mCalVisIdPrefix = newCalVisIdPrefix;
         this.mModified = true;
      }

   }

   public void setActualDate(Timestamp newActualDate) {
      if(this.mActualDate != null && newActualDate == null || this.mActualDate == null && newActualDate != null || this.mActualDate != null && newActualDate != null && !this.mActualDate.equals(newActualDate)) {
         this.mActualDate = newActualDate;
         this.mModified = true;
      }

   }

   public void setDetails(StructureElementEVO newDetails) {
      this.setStructureId(newDetails.getStructureId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setParentId(newDetails.getParentId());
      this.setChildIndex(newDetails.getChildIndex());
      this.setDepth(newDetails.getDepth());
      this.setPosition(newDetails.getPosition());
      this.setEndPosition(newDetails.getEndPosition());
      this.setLeaf(newDetails.getLeaf());
      this.setIsCredit(newDetails.getIsCredit());
      this.setDisabled(newDetails.getDisabled());
      this.setNotPlannable(newDetails.getNotPlannable());
      this.setCalElemType(newDetails.getCalElemType());
      this.setCalVisIdPrefix(newDetails.getCalVisIdPrefix());
      this.setActualDate(newDetails.getActualDate());
   }

   public StructureElementEVO deepClone() {
      StructureElementEVO cloned = new StructureElementEVO();
      cloned.mModified = this.mModified;
      cloned.mStructureId = this.mStructureId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mParentId = this.mParentId;
      cloned.mChildIndex = this.mChildIndex;
      cloned.mDepth = this.mDepth;
      cloned.mPosition = this.mPosition;
      cloned.mEndPosition = this.mEndPosition;
      cloned.mLeaf = this.mLeaf;
      cloned.mIsCredit = this.mIsCredit;
      cloned.mDisabled = this.mDisabled;
      cloned.mNotPlannable = this.mNotPlannable;
      cloned.mCalElemType = this.mCalElemType;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mCalVisIdPrefix != null) {
         cloned.mCalVisIdPrefix = this.mCalVisIdPrefix;
      }

      if(this.mActualDate != null) {
         cloned.mActualDate = Timestamp.valueOf(this.mActualDate.toString());
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(int startKey) {
      return startKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public StructureElementRef getEntityRef() {
      return new StructureElementRefImpl(this.getPK(), this.mVisId);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("StructureId=");
      sb.append(String.valueOf(this.mStructureId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ParentId=");
      sb.append(String.valueOf(this.mParentId));
      sb.append(' ');
      sb.append("ChildIndex=");
      sb.append(String.valueOf(this.mChildIndex));
      sb.append(' ');
      sb.append("Depth=");
      sb.append(String.valueOf(this.mDepth));
      sb.append(' ');
      sb.append("Position=");
      sb.append(String.valueOf(this.mPosition));
      sb.append(' ');
      sb.append("EndPosition=");
      sb.append(String.valueOf(this.mEndPosition));
      sb.append(' ');
      sb.append("Leaf=");
      sb.append(String.valueOf(this.mLeaf));
      sb.append(' ');
      sb.append("IsCredit=");
      sb.append(String.valueOf(this.mIsCredit));
      sb.append(' ');
      sb.append("Disabled=");
      sb.append(String.valueOf(this.mDisabled));
      sb.append(' ');
      sb.append("NotPlannable=");
      sb.append(String.valueOf(this.mNotPlannable));
      sb.append(' ');
      sb.append("CalElemType=");
      sb.append(String.valueOf(this.mCalElemType));
      sb.append(' ');
      sb.append("CalVisIdPrefix=");
      sb.append(String.valueOf(this.mCalVisIdPrefix));
      sb.append(' ');
      sb.append("ActualDate=");
      sb.append(String.valueOf(this.mActualDate));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
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

      sb.append("StructureElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
