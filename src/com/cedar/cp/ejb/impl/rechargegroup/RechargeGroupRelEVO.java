// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.rechargegroup;

import com.cedar.cp.api.rechargegroup.RechargeGroupRelRef;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelCK;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelRefImpl;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class RechargeGroupRelEVO implements Serializable {

   private transient RechargeGroupRelPK mPK;
   private int mRechargeGroupRelId;
   private int mRechargeGroupId;
   private int mRechargeId;
   private int mProcessOrder;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public RechargeGroupRelEVO() {}

   public RechargeGroupRelEVO(int newRechargeGroupRelId, int newRechargeGroupId, int newRechargeId, int newProcessOrder) {
      this.mRechargeGroupRelId = newRechargeGroupRelId;
      this.mRechargeGroupId = newRechargeGroupId;
      this.mRechargeId = newRechargeId;
      this.mProcessOrder = newProcessOrder;
   }

   public RechargeGroupRelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new RechargeGroupRelPK(this.mRechargeGroupRelId, this.mRechargeGroupId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRechargeGroupRelId() {
      return this.mRechargeGroupRelId;
   }

   public int getRechargeGroupId() {
      return this.mRechargeGroupId;
   }

   public int getRechargeId() {
      return this.mRechargeId;
   }

   public int getProcessOrder() {
      return this.mProcessOrder;
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

   public void setRechargeGroupRelId(int newRechargeGroupRelId) {
      if(this.mRechargeGroupRelId != newRechargeGroupRelId) {
         this.mModified = true;
         this.mRechargeGroupRelId = newRechargeGroupRelId;
         this.mPK = null;
      }
   }

   public void setRechargeGroupId(int newRechargeGroupId) {
      if(this.mRechargeGroupId != newRechargeGroupId) {
         this.mModified = true;
         this.mRechargeGroupId = newRechargeGroupId;
         this.mPK = null;
      }
   }

   public void setRechargeId(int newRechargeId) {
      if(this.mRechargeId != newRechargeId) {
         this.mModified = true;
         this.mRechargeId = newRechargeId;
      }
   }

   public void setProcessOrder(int newProcessOrder) {
      if(this.mProcessOrder != newProcessOrder) {
         this.mModified = true;
         this.mProcessOrder = newProcessOrder;
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

   public void setDetails(RechargeGroupRelEVO newDetails) {
      this.setRechargeGroupRelId(newDetails.getRechargeGroupRelId());
      this.setRechargeGroupId(newDetails.getRechargeGroupId());
      this.setRechargeId(newDetails.getRechargeId());
      this.setProcessOrder(newDetails.getProcessOrder());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RechargeGroupRelEVO deepClone() {
      RechargeGroupRelEVO cloned = new RechargeGroupRelEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mRechargeGroupRelId = this.mRechargeGroupRelId;
      cloned.mRechargeGroupId = this.mRechargeGroupId;
      cloned.mRechargeId = this.mRechargeId;
      cloned.mProcessOrder = this.mProcessOrder;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(RechargeGroupEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mRechargeGroupRelId > 0) {
         newKey = true;
         if(parent == null) {
            this.setRechargeGroupRelId(-this.mRechargeGroupRelId);
         } else {
            parent.changeKey(this, -this.mRechargeGroupRelId, this.mRechargeGroupId);
         }
      } else if(this.mRechargeGroupRelId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRechargeGroupRelId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(RechargeGroupEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mRechargeGroupRelId < 1) {
         parent.changeKey(this, startKey, this.mRechargeGroupId);
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

   public RechargeGroupRelRef getEntityRef(RechargeGroupEVO evoRechargeGroup, String entityText) {
      return new RechargeGroupRelRefImpl(new RechargeGroupRelCK(evoRechargeGroup.getPK(), this.getPK()), entityText);
   }

   public RechargeGroupRelCK getCK(RechargeGroupEVO evoRechargeGroup) {
      return new RechargeGroupRelCK(evoRechargeGroup.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RechargeGroupRelId=");
      sb.append(String.valueOf(this.mRechargeGroupRelId));
      sb.append(' ');
      sb.append("RechargeGroupId=");
      sb.append(String.valueOf(this.mRechargeGroupId));
      sb.append(' ');
      sb.append("RechargeId=");
      sb.append(String.valueOf(this.mRechargeId));
      sb.append(' ');
      sb.append("ProcessOrder=");
      sb.append(String.valueOf(this.mProcessOrder));
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

      sb.append("RechargeGroupRel: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
