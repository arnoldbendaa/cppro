// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionLinkRef;
import com.cedar.cp.dto.report.distribution.DistributionLinkCK;
import com.cedar.cp.dto.report.distribution.DistributionLinkPK;
import com.cedar.cp.dto.report.distribution.DistributionLinkRefImpl;
import com.cedar.cp.ejb.impl.report.distribution.DistributionEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class DistributionLinkEVO implements Serializable {

   private transient DistributionLinkPK mPK;
   private int mDistributionId;
   private int mDistributionLinkId;
   private int mDestinationId;
   private Integer mDestinationType;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final Integer INTERNAL = Integer.valueOf(0);
   public static final Integer EXTERNAL = Integer.valueOf(1);


   public DistributionLinkEVO() {}

   public DistributionLinkEVO(int newDistributionId, int newDistributionLinkId, int newDestinationId, Integer newDestinationType) {
      this.mDistributionId = newDistributionId;
      this.mDistributionLinkId = newDistributionLinkId;
      this.mDestinationId = newDestinationId;
      this.mDestinationType = newDestinationType;
   }

   public DistributionLinkPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DistributionLinkPK(this.mDistributionId, this.mDistributionLinkId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getDistributionId() {
      return this.mDistributionId;
   }

   public int getDistributionLinkId() {
      return this.mDistributionLinkId;
   }

   public int getDestinationId() {
      return this.mDestinationId;
   }

   public Integer getDestinationType() {
      return this.mDestinationType;
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

   public void setDistributionId(int newDistributionId) {
      if(this.mDistributionId != newDistributionId) {
         this.mModified = true;
         this.mDistributionId = newDistributionId;
         this.mPK = null;
      }
   }

   public void setDistributionLinkId(int newDistributionLinkId) {
      if(this.mDistributionLinkId != newDistributionLinkId) {
         this.mModified = true;
         this.mDistributionLinkId = newDistributionLinkId;
         this.mPK = null;
      }
   }

   public void setDestinationId(int newDestinationId) {
      if(this.mDestinationId != newDestinationId) {
         this.mModified = true;
         this.mDestinationId = newDestinationId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setDestinationType(Integer newDestinationType) {
      if(this.mDestinationType != null && newDestinationType == null || this.mDestinationType == null && newDestinationType != null || this.mDestinationType != null && newDestinationType != null && !this.mDestinationType.equals(newDestinationType)) {
         this.mDestinationType = newDestinationType;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(DistributionLinkEVO newDetails) {
      this.setDistributionId(newDetails.getDistributionId());
      this.setDistributionLinkId(newDetails.getDistributionLinkId());
      this.setDestinationId(newDetails.getDestinationId());
      this.setDestinationType(newDetails.getDestinationType());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public DistributionLinkEVO deepClone() {
      DistributionLinkEVO cloned = new DistributionLinkEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mDistributionId = this.mDistributionId;
      cloned.mDistributionLinkId = this.mDistributionLinkId;
      cloned.mDestinationId = this.mDestinationId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mDestinationType != null) {
         cloned.mDestinationType = Integer.valueOf(this.mDestinationType.toString());
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(DistributionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mDistributionLinkId > 0) {
         newKey = true;
         if(parent == null) {
            this.setDistributionLinkId(-this.mDistributionLinkId);
         } else {
            parent.changeKey(this, this.mDistributionId, -this.mDistributionLinkId);
         }
      } else if(this.mDistributionLinkId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDistributionLinkId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(DistributionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mDistributionLinkId < 1) {
         parent.changeKey(this, this.mDistributionId, startKey);
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

   public DistributionLinkRef getEntityRef(DistributionEVO evoDistribution, String entityText) {
      return new DistributionLinkRefImpl(new DistributionLinkCK(evoDistribution.getPK(), this.getPK()), entityText);
   }

   public DistributionLinkCK getCK(DistributionEVO evoDistribution) {
      return new DistributionLinkCK(evoDistribution.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DistributionId=");
      sb.append(String.valueOf(this.mDistributionId));
      sb.append(' ');
      sb.append("DistributionLinkId=");
      sb.append(String.valueOf(this.mDistributionLinkId));
      sb.append(' ');
      sb.append("DestinationId=");
      sb.append(String.valueOf(this.mDestinationId));
      sb.append(' ');
      sb.append("DestinationType=");
      sb.append(String.valueOf(this.mDestinationType));
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

      sb.append("DistributionLink: ");
      sb.append(this.toString());
      return sb.toString();
   }

}
