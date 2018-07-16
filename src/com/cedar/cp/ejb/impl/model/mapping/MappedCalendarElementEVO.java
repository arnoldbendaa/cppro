// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:44
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedCalendarElementRef;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementCK;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementPK;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementRefImpl;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarYearEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class MappedCalendarElementEVO implements Serializable {

   private transient MappedCalendarElementPK mPK;
   private int mMappedCalendarElementId;
   private int mMappedCalendarYearId;
   private Integer mTmpDimensionElementIdx;
   private Integer mDimensionElementId;
   private Integer mPeriod;
   private String mCalendarElementVisId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public MappedCalendarElementEVO() {}

   public MappedCalendarElementEVO(int newMappedCalendarElementId, int newMappedCalendarYearId, Integer newTmpDimensionElementIdx, Integer newDimensionElementId, Integer newPeriod, String newCalendarElementVisId) {
      this.mMappedCalendarElementId = newMappedCalendarElementId;
      this.mMappedCalendarYearId = newMappedCalendarYearId;
      this.mTmpDimensionElementIdx = newTmpDimensionElementIdx;
      this.mDimensionElementId = newDimensionElementId;
      this.mPeriod = newPeriod;
      this.mCalendarElementVisId = newCalendarElementVisId;
   }

   public MappedCalendarElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedCalendarElementPK(this.mMappedCalendarElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedCalendarElementId() {
      return this.mMappedCalendarElementId;
   }

   public int getMappedCalendarYearId() {
      return this.mMappedCalendarYearId;
   }

   public Integer getTmpDimensionElementIdx() {
      return this.mTmpDimensionElementIdx;
   }

   public Integer getDimensionElementId() {
      return this.mDimensionElementId;
   }

   public Integer getPeriod() {
      return this.mPeriod;
   }

   public String getCalendarElementVisId() {
      return this.mCalendarElementVisId;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setMappedCalendarElementId(int newMappedCalendarElementId) {
      if(this.mMappedCalendarElementId != newMappedCalendarElementId) {
         this.mModified = true;
         this.mMappedCalendarElementId = newMappedCalendarElementId;
         this.mPK = null;
      }
   }

   public void setMappedCalendarYearId(int newMappedCalendarYearId) {
      if(this.mMappedCalendarYearId != newMappedCalendarYearId) {
         this.mModified = true;
         this.mMappedCalendarYearId = newMappedCalendarYearId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setTmpDimensionElementIdx(Integer newTmpDimensionElementIdx) {
      if(this.mTmpDimensionElementIdx != null && newTmpDimensionElementIdx == null || this.mTmpDimensionElementIdx == null && newTmpDimensionElementIdx != null || this.mTmpDimensionElementIdx != null && newTmpDimensionElementIdx != null && !this.mTmpDimensionElementIdx.equals(newTmpDimensionElementIdx)) {
         this.mTmpDimensionElementIdx = newTmpDimensionElementIdx;
         this.mModified = true;
      }

   }

   public void setDimensionElementId(Integer newDimensionElementId) {
      if(this.mDimensionElementId != null && newDimensionElementId == null || this.mDimensionElementId == null && newDimensionElementId != null || this.mDimensionElementId != null && newDimensionElementId != null && !this.mDimensionElementId.equals(newDimensionElementId)) {
         this.mDimensionElementId = newDimensionElementId;
         this.mModified = true;
      }

   }

   public void setPeriod(Integer newPeriod) {
      if(this.mPeriod != null && newPeriod == null || this.mPeriod == null && newPeriod != null || this.mPeriod != null && newPeriod != null && !this.mPeriod.equals(newPeriod)) {
         this.mPeriod = newPeriod;
         this.mModified = true;
      }

   }

   public void setCalendarElementVisId(String newCalendarElementVisId) {
      if(this.mCalendarElementVisId != null && newCalendarElementVisId == null || this.mCalendarElementVisId == null && newCalendarElementVisId != null || this.mCalendarElementVisId != null && newCalendarElementVisId != null && !this.mCalendarElementVisId.equals(newCalendarElementVisId)) {
         this.mCalendarElementVisId = newCalendarElementVisId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MappedCalendarElementEVO newDetails) {
      this.setMappedCalendarElementId(newDetails.getMappedCalendarElementId());
      this.setMappedCalendarYearId(newDetails.getMappedCalendarYearId());
      this.setTmpDimensionElementIdx(newDetails.getTmpDimensionElementIdx());
      this.setDimensionElementId(newDetails.getDimensionElementId());
      this.setPeriod(newDetails.getPeriod());
      this.setCalendarElementVisId(newDetails.getCalendarElementVisId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedCalendarElementEVO deepClone() {
      MappedCalendarElementEVO cloned = new MappedCalendarElementEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mMappedCalendarElementId = this.mMappedCalendarElementId;
      cloned.mMappedCalendarYearId = this.mMappedCalendarYearId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mTmpDimensionElementIdx != null) {
         cloned.mTmpDimensionElementIdx = Integer.valueOf(this.mTmpDimensionElementIdx.toString());
      }

      if(this.mDimensionElementId != null) {
         cloned.mDimensionElementId = Integer.valueOf(this.mDimensionElementId.toString());
      }

      if(this.mPeriod != null) {
         cloned.mPeriod = Integer.valueOf(this.mPeriod.toString());
      }

      if(this.mCalendarElementVisId != null) {
         cloned.mCalendarElementVisId = this.mCalendarElementVisId;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(MappedCalendarYearEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMappedCalendarElementId > 0) {
         newKey = true;
         if(parent == null) {
            this.setMappedCalendarElementId(-this.mMappedCalendarElementId);
         } else {
            parent.changeKey(this, -this.mMappedCalendarElementId);
         }
      } else if(this.mMappedCalendarElementId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedCalendarElementId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(MappedCalendarYearEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMappedCalendarElementId < 1) {
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

   public MappedCalendarElementRef getEntityRef(MappedModelEVO evoMappedModel, MappedCalendarYearEVO evoMappedCalendarYear, String entityText) {
      return new MappedCalendarElementRefImpl(new MappedCalendarElementCK(evoMappedModel.getPK(), evoMappedCalendarYear.getPK(), this.getPK()), entityText);
   }

   public MappedCalendarElementCK getCK(MappedModelEVO evoMappedModel, MappedCalendarYearEVO evoMappedCalendarYear) {
      return new MappedCalendarElementCK(evoMappedModel.getPK(), evoMappedCalendarYear.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedCalendarElementId=");
      sb.append(String.valueOf(this.mMappedCalendarElementId));
      sb.append(' ');
      sb.append("MappedCalendarYearId=");
      sb.append(String.valueOf(this.mMappedCalendarYearId));
      sb.append(' ');
      sb.append("TmpDimensionElementIdx=");
      sb.append(String.valueOf(this.mTmpDimensionElementIdx));
      sb.append(' ');
      sb.append("DimensionElementId=");
      sb.append(String.valueOf(this.mDimensionElementId));
      sb.append(' ');
      sb.append("Period=");
      sb.append(String.valueOf(this.mPeriod));
      sb.append(' ');
      sb.append("CalendarElementVisId=");
      sb.append(String.valueOf(this.mCalendarElementVisId));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
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

      sb.append("MappedCalendarElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
