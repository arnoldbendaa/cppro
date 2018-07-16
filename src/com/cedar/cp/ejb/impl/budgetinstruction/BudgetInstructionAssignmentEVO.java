// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignmentRef;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentRefImpl;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class BudgetInstructionAssignmentEVO implements Serializable {

   private transient BudgetInstructionAssignmentPK mPK;
   private int mAssignmentId;
   private int mBudgetInstructionId;
   private int mModelId;
   private int mFinanceCubeId;
   private int mBudgetCycleId;
   private int mBudgetLocationHierId;
   private int mBudgetLocationElementId;
   private boolean mSelectChildren;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public BudgetInstructionAssignmentEVO() {}

   public BudgetInstructionAssignmentEVO(int newAssignmentId, int newBudgetInstructionId, int newModelId, int newFinanceCubeId, int newBudgetCycleId, int newBudgetLocationHierId, int newBudgetLocationElementId, boolean newSelectChildren, int newVersionNum) {
      this.mAssignmentId = newAssignmentId;
      this.mBudgetInstructionId = newBudgetInstructionId;
      this.mModelId = newModelId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mBudgetCycleId = newBudgetCycleId;
      this.mBudgetLocationHierId = newBudgetLocationHierId;
      this.mBudgetLocationElementId = newBudgetLocationElementId;
      this.mSelectChildren = newSelectChildren;
      this.mVersionNum = newVersionNum;
   }

   public BudgetInstructionAssignmentPK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetInstructionAssignmentPK(this.mAssignmentId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAssignmentId() {
      return this.mAssignmentId;
   }

   public int getBudgetInstructionId() {
      return this.mBudgetInstructionId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getBudgetLocationHierId() {
      return this.mBudgetLocationHierId;
   }

   public int getBudgetLocationElementId() {
      return this.mBudgetLocationElementId;
   }

   public boolean getSelectChildren() {
      return this.mSelectChildren;
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

   public void setAssignmentId(int newAssignmentId) {
      if(this.mAssignmentId != newAssignmentId) {
         this.mModified = true;
         this.mAssignmentId = newAssignmentId;
         this.mPK = null;
      }
   }

   public void setBudgetInstructionId(int newBudgetInstructionId) {
      if(this.mBudgetInstructionId != newBudgetInstructionId) {
         this.mModified = true;
         this.mBudgetInstructionId = newBudgetInstructionId;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setBudgetCycleId(int newBudgetCycleId) {
      if(this.mBudgetCycleId != newBudgetCycleId) {
         this.mModified = true;
         this.mBudgetCycleId = newBudgetCycleId;
      }
   }

   public void setBudgetLocationHierId(int newBudgetLocationHierId) {
      if(this.mBudgetLocationHierId != newBudgetLocationHierId) {
         this.mModified = true;
         this.mBudgetLocationHierId = newBudgetLocationHierId;
      }
   }

   public void setBudgetLocationElementId(int newBudgetLocationElementId) {
      if(this.mBudgetLocationElementId != newBudgetLocationElementId) {
         this.mModified = true;
         this.mBudgetLocationElementId = newBudgetLocationElementId;
      }
   }

   public void setSelectChildren(boolean newSelectChildren) {
      if(this.mSelectChildren != newSelectChildren) {
         this.mModified = true;
         this.mSelectChildren = newSelectChildren;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(BudgetInstructionAssignmentEVO newDetails) {
      this.setAssignmentId(newDetails.getAssignmentId());
      this.setBudgetInstructionId(newDetails.getBudgetInstructionId());
      this.setModelId(newDetails.getModelId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setBudgetLocationHierId(newDetails.getBudgetLocationHierId());
      this.setBudgetLocationElementId(newDetails.getBudgetLocationElementId());
      this.setSelectChildren(newDetails.getSelectChildren());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public BudgetInstructionAssignmentEVO deepClone() {
      BudgetInstructionAssignmentEVO cloned = new BudgetInstructionAssignmentEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mAssignmentId = this.mAssignmentId;
      cloned.mBudgetInstructionId = this.mBudgetInstructionId;
      cloned.mModelId = this.mModelId;
      cloned.mFinanceCubeId = this.mFinanceCubeId;
      cloned.mBudgetCycleId = this.mBudgetCycleId;
      cloned.mBudgetLocationHierId = this.mBudgetLocationHierId;
      cloned.mBudgetLocationElementId = this.mBudgetLocationElementId;
      cloned.mSelectChildren = this.mSelectChildren;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(BudgetInstructionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mAssignmentId > 0) {
         newKey = true;
         if(parent == null) {
            this.setAssignmentId(-this.mAssignmentId);
         } else {
            parent.changeKey(this, -this.mAssignmentId);
         }
      } else if(this.mAssignmentId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAssignmentId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(BudgetInstructionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mAssignmentId < 1) {
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

   public BudgetInstructionAssignmentRef getEntityRef(BudgetInstructionEVO evoBudgetInstruction, String entityText) {
      return new BudgetInstructionAssignmentRefImpl(new BudgetInstructionAssignmentCK(evoBudgetInstruction.getPK(), this.getPK()), entityText);
   }

   public BudgetInstructionAssignmentCK getCK(BudgetInstructionEVO evoBudgetInstruction) {
      return new BudgetInstructionAssignmentCK(evoBudgetInstruction.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AssignmentId=");
      sb.append(String.valueOf(this.mAssignmentId));
      sb.append(' ');
      sb.append("BudgetInstructionId=");
      sb.append(String.valueOf(this.mBudgetInstructionId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("BudgetLocationHierId=");
      sb.append(String.valueOf(this.mBudgetLocationHierId));
      sb.append(' ');
      sb.append("BudgetLocationElementId=");
      sb.append(String.valueOf(this.mBudgetLocationElementId));
      sb.append(' ');
      sb.append("SelectChildren=");
      sb.append(String.valueOf(this.mSelectChildren));
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

      sb.append("BudgetInstructionAssignment: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
