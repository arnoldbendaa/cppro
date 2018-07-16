// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.RollUpRuleRef;
import com.cedar.cp.dto.model.RollUpRuleCK;
import com.cedar.cp.dto.model.RollUpRulePK;
import com.cedar.cp.dto.model.RollUpRuleRefImpl;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class RollUpRuleEVO implements Serializable {

   private transient RollUpRulePK mPK;
   private int mRollUpRuleId;
   private int mFinanceCubeId;
   private int mDataTypeId;
   private int mDimensionId;
   private boolean mRollUp;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public RollUpRuleEVO() {}

   public RollUpRuleEVO(int newRollUpRuleId, int newFinanceCubeId, int newDataTypeId, int newDimensionId, boolean newRollUp) {
      this.mRollUpRuleId = newRollUpRuleId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mDataTypeId = newDataTypeId;
      this.mDimensionId = newDimensionId;
      this.mRollUp = newRollUp;
   }

   public RollUpRulePK getPK() {
      if(this.mPK == null) {
         this.mPK = new RollUpRulePK(this.mRollUpRuleId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRollUpRuleId() {
      return this.mRollUpRuleId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public boolean getRollUp() {
      return this.mRollUp;
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

   public void setRollUpRuleId(int newRollUpRuleId) {
      if(this.mRollUpRuleId != newRollUpRuleId) {
         this.mModified = true;
         this.mRollUpRuleId = newRollUpRuleId;
         this.mPK = null;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
      }
   }

   public void setRollUp(boolean newRollUp) {
      if(this.mRollUp != newRollUp) {
         this.mModified = true;
         this.mRollUp = newRollUp;
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

   public void setDetails(RollUpRuleEVO newDetails) {
      this.setRollUpRuleId(newDetails.getRollUpRuleId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setRollUp(newDetails.getRollUp());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RollUpRuleEVO deepClone() {
      RollUpRuleEVO cloned = new RollUpRuleEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mRollUpRuleId = this.mRollUpRuleId;
      cloned.mFinanceCubeId = this.mFinanceCubeId;
      cloned.mDataTypeId = this.mDataTypeId;
      cloned.mDimensionId = this.mDimensionId;
      cloned.mRollUp = this.mRollUp;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(FinanceCubeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mRollUpRuleId > 0) {
         newKey = true;
         if(parent == null) {
            this.setRollUpRuleId(-this.mRollUpRuleId);
         } else {
            parent.changeKey(this, -this.mRollUpRuleId);
         }
      } else if(this.mRollUpRuleId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRollUpRuleId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(FinanceCubeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mRollUpRuleId < 1) {
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

   public RollUpRuleRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, String entityText) {
      return new RollUpRuleRefImpl(new RollUpRuleCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK()), entityText);
   }

   public RollUpRuleCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube) {
      return new RollUpRuleCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RollUpRuleId=");
      sb.append(String.valueOf(this.mRollUpRuleId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("RollUp=");
      sb.append(String.valueOf(this.mRollUp));
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

      sb.append("RollUpRule: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
