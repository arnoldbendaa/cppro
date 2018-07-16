// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.BudgetStateHistoryRef;
import com.cedar.cp.dto.model.BudgetStateHistoryCK;
import com.cedar.cp.dto.model.BudgetStateHistoryPK;
import com.cedar.cp.dto.model.BudgetStateHistoryRefImpl;
import com.cedar.cp.ejb.impl.model.BudgetCycleEVO;
import com.cedar.cp.ejb.impl.model.BudgetStateEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class BudgetStateHistoryEVO implements Serializable {

   private transient BudgetStateHistoryPK mPK;
   private int mBudgetStateHistoryId;
   private int mBudgetCycleId;
   private int mStructureElementId;
   private int mPreviousState;
   private int mNewState;
   private Timestamp mChangedTime;
   private int mUserId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public BudgetStateHistoryEVO() {}

   public BudgetStateHistoryEVO(int newBudgetStateHistoryId, int newBudgetCycleId, int newStructureElementId, int newPreviousState, int newNewState, Timestamp newChangedTime, int newUserId) {
      this.mBudgetStateHistoryId = newBudgetStateHistoryId;
      this.mBudgetCycleId = newBudgetCycleId;
      this.mStructureElementId = newStructureElementId;
      this.mPreviousState = newPreviousState;
      this.mNewState = newNewState;
      this.mChangedTime = newChangedTime;
      this.mUserId = newUserId;
   }

   public BudgetStateHistoryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetStateHistoryPK(this.mBudgetStateHistoryId, this.mBudgetCycleId, this.mStructureElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBudgetStateHistoryId() {
      return this.mBudgetStateHistoryId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getPreviousState() {
      return this.mPreviousState;
   }

   public int getNewState() {
      return this.mNewState;
   }

   public Timestamp getChangedTime() {
      return this.mChangedTime;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setBudgetStateHistoryId(int newBudgetStateHistoryId) {
      if(this.mBudgetStateHistoryId != newBudgetStateHistoryId) {
         this.mModified = true;
         this.mBudgetStateHistoryId = newBudgetStateHistoryId;
         this.mPK = null;
      }
   }

   public void setBudgetCycleId(int newBudgetCycleId) {
      if(this.mBudgetCycleId != newBudgetCycleId) {
         this.mModified = true;
         this.mBudgetCycleId = newBudgetCycleId;
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

   public void setPreviousState(int newPreviousState) {
      if(this.mPreviousState != newPreviousState) {
         this.mModified = true;
         this.mPreviousState = newPreviousState;
      }
   }

   public void setNewState(int newNewState) {
      if(this.mNewState != newNewState) {
         this.mModified = true;
         this.mNewState = newNewState;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
      }
   }

   public void setChangedTime(Timestamp newChangedTime) {
      if(this.mChangedTime != null && newChangedTime == null || this.mChangedTime == null && newChangedTime != null || this.mChangedTime != null && newChangedTime != null && !this.mChangedTime.equals(newChangedTime)) {
         this.mChangedTime = newChangedTime;
         this.mModified = true;
      }

   }

   public void setDetails(BudgetStateHistoryEVO newDetails) {
      this.setBudgetStateHistoryId(newDetails.getBudgetStateHistoryId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setPreviousState(newDetails.getPreviousState());
      this.setNewState(newDetails.getNewState());
      this.setChangedTime(newDetails.getChangedTime());
      this.setUserId(newDetails.getUserId());
   }

   public BudgetStateHistoryEVO deepClone() {
      BudgetStateHistoryEVO cloned = new BudgetStateHistoryEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mBudgetStateHistoryId = this.mBudgetStateHistoryId;
      cloned.mBudgetCycleId = this.mBudgetCycleId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mPreviousState = this.mPreviousState;
      cloned.mNewState = this.mNewState;
      cloned.mUserId = this.mUserId;
      if(this.mChangedTime != null) {
         cloned.mChangedTime = Timestamp.valueOf(this.mChangedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(BudgetStateEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mBudgetStateHistoryId > 0) {
         newKey = true;
         if(parent == null) {
            this.setBudgetStateHistoryId(-this.mBudgetStateHistoryId);
         } else {
            parent.changeKey(this, -this.mBudgetStateHistoryId, this.mBudgetCycleId, this.mStructureElementId);
         }
      } else if(this.mBudgetStateHistoryId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mBudgetStateHistoryId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(BudgetStateEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mBudgetStateHistoryId < 1) {
         parent.changeKey(this, startKey, this.mBudgetCycleId, this.mStructureElementId);
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

   public BudgetStateHistoryRef getEntityRef(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle, BudgetStateEVO evoBudgetState, String entityText) {
      return new BudgetStateHistoryRefImpl(new BudgetStateHistoryCK(evoModel.getPK(), evoBudgetCycle.getPK(), evoBudgetState.getPK(), this.getPK()), entityText);
   }

   public BudgetStateHistoryCK getCK(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle, BudgetStateEVO evoBudgetState) {
      return new BudgetStateHistoryCK(evoModel.getPK(), evoBudgetCycle.getPK(), evoBudgetState.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BudgetStateHistoryId=");
      sb.append(String.valueOf(this.mBudgetStateHistoryId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("PreviousState=");
      sb.append(String.valueOf(this.mPreviousState));
      sb.append(' ');
      sb.append("NewState=");
      sb.append(String.valueOf(this.mNewState));
      sb.append(' ');
      sb.append("ChangedTime=");
      sb.append(String.valueOf(this.mChangedTime));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
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

      sb.append("BudgetStateHistory: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
