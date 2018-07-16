// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.LevelDateRef;
import com.cedar.cp.dto.model.LevelDateCK;
import com.cedar.cp.dto.model.LevelDatePK;
import com.cedar.cp.dto.model.LevelDateRefImpl;
import com.cedar.cp.ejb.impl.model.BudgetCycleEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class LevelDateEVO implements Serializable {

   private transient LevelDatePK mPK;
   private int mBudgetCycleId;
   private int mDepth;
   private Timestamp mPlannedEndDate;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public LevelDateEVO() {}

   public LevelDateEVO(int newBudgetCycleId, int newDepth, Timestamp newPlannedEndDate, int newVersionNum) {
      this.mBudgetCycleId = newBudgetCycleId;
      this.mDepth = newDepth;
      this.mPlannedEndDate = newPlannedEndDate;
      this.mVersionNum = newVersionNum;
   }

   public LevelDatePK getPK() {
      if(this.mPK == null) {
         this.mPK = new LevelDatePK(this.mBudgetCycleId, this.mDepth);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public Timestamp getPlannedEndDate() {
      return this.mPlannedEndDate;
   }

   public int getVersionNum() {
      return this.mVersionNum;
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

   public void setBudgetCycleId(int newBudgetCycleId) {
      if(this.mBudgetCycleId != newBudgetCycleId) {
         this.mModified = true;
         this.mBudgetCycleId = newBudgetCycleId;
         this.mPK = null;
      }
   }

   public void setDepth(int newDepth) {
      if(this.mDepth != newDepth) {
         this.mModified = true;
         this.mDepth = newDepth;
         this.mPK = null;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setPlannedEndDate(Timestamp newPlannedEndDate) {
      if(this.mPlannedEndDate != null && newPlannedEndDate == null || this.mPlannedEndDate == null && newPlannedEndDate != null || this.mPlannedEndDate != null && newPlannedEndDate != null && !this.mPlannedEndDate.equals(newPlannedEndDate)) {
         this.mPlannedEndDate = newPlannedEndDate;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(LevelDateEVO newDetails) {
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setDepth(newDetails.getDepth());
      this.setPlannedEndDate(newDetails.getPlannedEndDate());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public LevelDateEVO deepClone() {
      LevelDateEVO cloned = new LevelDateEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mBudgetCycleId = this.mBudgetCycleId;
      cloned.mDepth = this.mDepth;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mPlannedEndDate != null) {
         cloned.mPlannedEndDate = Timestamp.valueOf(this.mPlannedEndDate.toString());
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(BudgetCycleEVO parent) {
      boolean newKey = this.insertPending();
      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(BudgetCycleEVO parent, int startKey) {
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

   public LevelDateRef getEntityRef(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle, String entityText) {
      return new LevelDateRefImpl(new LevelDateCK(evoModel.getPK(), evoBudgetCycle.getPK(), this.getPK()), entityText);
   }

   public LevelDateCK getCK(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle) {
      return new LevelDateCK(evoModel.getPK(), evoBudgetCycle.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("Depth=");
      sb.append(String.valueOf(this.mDepth));
      sb.append(' ');
      sb.append("PlannedEndDate=");
      sb.append(String.valueOf(this.mPlannedEndDate));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
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

      sb.append("LevelDate: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
