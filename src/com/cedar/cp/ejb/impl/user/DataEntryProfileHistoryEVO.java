package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.user.DataEntryProfileHistoryRef;
import com.cedar.cp.dto.user.DataEntryProfileHistoryCK;
import com.cedar.cp.dto.user.DataEntryProfileHistoryPK;
import com.cedar.cp.dto.user.DataEntryProfileHistoryRefImpl;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.io.Serializable;

public class DataEntryProfileHistoryEVO implements Serializable {

   private transient DataEntryProfileHistoryPK mPK;
   private int mUserId;
   private int mModelId;
   private int mBudgetLocationElementId;
   private int mBudgetCycleId;
   private int mDataEntryProfileId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public DataEntryProfileHistoryEVO() {}

   public DataEntryProfileHistoryEVO(int newUserId, int newModelId, int newBudgetLocationElementId, int newDataEntryProfileId, int newBudgetCycleId) {
      this.mUserId = newUserId;
      this.mModelId = newModelId;
      this.mBudgetLocationElementId = newBudgetLocationElementId;
      this.mDataEntryProfileId = newDataEntryProfileId;
      this.mBudgetCycleId = newBudgetCycleId;
   }

   public DataEntryProfileHistoryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DataEntryProfileHistoryPK(this.mUserId, this.mModelId, this.mBudgetLocationElementId, this.mBudgetCycleId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getBudgetLocationElementId() {
      return this.mBudgetLocationElementId;
   }
   
   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getDataEntryProfileId() {
      return this.mDataEntryProfileId;
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
         this.mPK = null;
      }
   }

   public void setBudgetLocationElementId(int newBudgetLocationElementId) {
      if(this.mBudgetLocationElementId != newBudgetLocationElementId) {
         this.mModified = true;
         this.mBudgetLocationElementId = newBudgetLocationElementId;
         this.mPK = null;
      }
   }
   
	public void setBudgetCycleId(int newBudgetCycleId) {
		if (this.mBudgetCycleId != newBudgetCycleId) {
			this.mModified = true;
			this.mBudgetCycleId = newBudgetCycleId;
			this.mPK = null;
		}
	}

   public void setDataEntryProfileId(int newDataEntryProfileId) {
      if(this.mDataEntryProfileId != newDataEntryProfileId) {
         this.mModified = true;
         this.mDataEntryProfileId = newDataEntryProfileId;
      }
   }

   public void setDetails(DataEntryProfileHistoryEVO newDetails) {
      this.setUserId(newDetails.getUserId());
      this.setModelId(newDetails.getModelId());
      this.setBudgetLocationElementId(newDetails.getBudgetLocationElementId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setDataEntryProfileId(newDetails.getDataEntryProfileId());
   }

   public DataEntryProfileHistoryEVO deepClone() {
      DataEntryProfileHistoryEVO cloned = new DataEntryProfileHistoryEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mUserId = this.mUserId;
      cloned.mModelId = this.mModelId;
      cloned.mBudgetLocationElementId = this.mBudgetLocationElementId;
      cloned.mBudgetCycleId = this.mBudgetCycleId;
      cloned.mDataEntryProfileId = this.mDataEntryProfileId;
      return cloned;
   }

   public void prepareForInsert(DataEntryProfileEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(DataEntryProfileEVO parent, int startKey) {
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

   public DataEntryProfileHistoryRef getEntityRef(UserEVO evoUser, DataEntryProfileEVO evoDataEntryProfile, String entityText) {
      return new DataEntryProfileHistoryRefImpl(new DataEntryProfileHistoryCK(evoUser.getPK(), evoDataEntryProfile.getPK(), this.getPK()), entityText);
   }

   public DataEntryProfileHistoryCK getCK(UserEVO evoUser, DataEntryProfileEVO evoDataEntryProfile) {
      return new DataEntryProfileHistoryCK(evoUser.getPK(), evoDataEntryProfile.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("BudgetLocationElementId=");
      sb.append(String.valueOf(this.mBudgetLocationElementId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("DataEntryProfileId=");
      sb.append(String.valueOf(this.mDataEntryProfileId));
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

      sb.append("DataEntryProfileHistory: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
