// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.SecurityRangeRowRef;
import com.cedar.cp.dto.dimension.SecurityRangeRowCK;
import com.cedar.cp.dto.dimension.SecurityRangeRowPK;
import com.cedar.cp.dto.dimension.SecurityRangeRowRefImpl;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class SecurityRangeRowEVO implements Serializable {

   private transient SecurityRangeRowPK mPK;
   private int mSecurityRangeRowId;
   private int mSecurityRangeId;
   private int mSequence;
   private String mFromId;
   private String mToId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public SecurityRangeRowEVO() {}

   public SecurityRangeRowEVO(int newSecurityRangeRowId, int newSecurityRangeId, int newSequence, String newFromId, String newToId, int newVersionNum) {
      this.mSecurityRangeRowId = newSecurityRangeRowId;
      this.mSecurityRangeId = newSecurityRangeId;
      this.mSequence = newSequence;
      this.mFromId = newFromId;
      this.mToId = newToId;
      this.mVersionNum = newVersionNum;
   }

   public SecurityRangeRowPK getPK() {
      if(this.mPK == null) {
         this.mPK = new SecurityRangeRowPK(this.mSecurityRangeRowId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getSecurityRangeRowId() {
      return this.mSecurityRangeRowId;
   }

   public int getSecurityRangeId() {
      return this.mSecurityRangeId;
   }

   public int getSequence() {
      return this.mSequence;
   }

   public String getFromId() {
      return this.mFromId;
   }

   public String getToId() {
      return this.mToId;
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

   public void setSecurityRangeRowId(int newSecurityRangeRowId) {
      if(this.mSecurityRangeRowId != newSecurityRangeRowId) {
         this.mModified = true;
         this.mSecurityRangeRowId = newSecurityRangeRowId;
         this.mPK = null;
      }
   }

   public void setSecurityRangeId(int newSecurityRangeId) {
      if(this.mSecurityRangeId != newSecurityRangeId) {
         this.mModified = true;
         this.mSecurityRangeId = newSecurityRangeId;
      }
   }

   public void setSequence(int newSequence) {
      if(this.mSequence != newSequence) {
         this.mModified = true;
         this.mSequence = newSequence;
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

   public void setFromId(String newFromId) {
      if(this.mFromId != null && newFromId == null || this.mFromId == null && newFromId != null || this.mFromId != null && newFromId != null && !this.mFromId.equals(newFromId)) {
         this.mFromId = newFromId;
         this.mModified = true;
      }

   }

   public void setToId(String newToId) {
      if(this.mToId != null && newToId == null || this.mToId == null && newToId != null || this.mToId != null && newToId != null && !this.mToId.equals(newToId)) {
         this.mToId = newToId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(SecurityRangeRowEVO newDetails) {
      this.setSecurityRangeRowId(newDetails.getSecurityRangeRowId());
      this.setSecurityRangeId(newDetails.getSecurityRangeId());
      this.setSequence(newDetails.getSequence());
      this.setFromId(newDetails.getFromId());
      this.setToId(newDetails.getToId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public SecurityRangeRowEVO deepClone() {
      SecurityRangeRowEVO cloned = new SecurityRangeRowEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mSecurityRangeRowId = this.mSecurityRangeRowId;
      cloned.mSecurityRangeId = this.mSecurityRangeId;
      cloned.mSequence = this.mSequence;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mFromId != null) {
         cloned.mFromId = this.mFromId;
      }

      if(this.mToId != null) {
         cloned.mToId = this.mToId;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(SecurityRangeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mSecurityRangeRowId > 0) {
         newKey = true;
         if(parent == null) {
            this.setSecurityRangeRowId(-this.mSecurityRangeRowId);
         } else {
            parent.changeKey(this, -this.mSecurityRangeRowId);
         }
      } else if(this.mSecurityRangeRowId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mSecurityRangeRowId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(SecurityRangeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mSecurityRangeRowId < 1) {
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

   public SecurityRangeRowRef getEntityRef(DimensionEVO evoDimension, SecurityRangeEVO evoSecurityRange, String entityText) {
      return new SecurityRangeRowRefImpl(new SecurityRangeRowCK(evoDimension.getPK(), evoSecurityRange.getPK(), this.getPK()), entityText);
   }

   public SecurityRangeRowCK getCK(DimensionEVO evoDimension, SecurityRangeEVO evoSecurityRange) {
      return new SecurityRangeRowCK(evoDimension.getPK(), evoSecurityRange.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SecurityRangeRowId=");
      sb.append(String.valueOf(this.mSecurityRangeRowId));
      sb.append(' ');
      sb.append("SecurityRangeId=");
      sb.append(String.valueOf(this.mSecurityRangeId));
      sb.append(' ');
      sb.append("Sequence=");
      sb.append(String.valueOf(this.mSequence));
      sb.append(' ');
      sb.append("FromId=");
      sb.append(String.valueOf(this.mFromId));
      sb.append(' ');
      sb.append("ToId=");
      sb.append(String.valueOf(this.mToId));
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

      sb.append("SecurityRangeRow: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
