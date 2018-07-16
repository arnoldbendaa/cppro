// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.api.report.pack.ReportPackLinkRef;
import com.cedar.cp.dto.report.pack.ReportPackLinkCK;
import com.cedar.cp.dto.report.pack.ReportPackLinkPK;
import com.cedar.cp.dto.report.pack.ReportPackLinkRefImpl;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ReportPackLinkEVO implements Serializable {

   private transient ReportPackLinkPK mPK;
   private int mReportPackId;
   private int mReportPackLinkId;
   private int mReportDefId;
   private int mDistributionId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ReportPackLinkEVO() {}

   public ReportPackLinkEVO(int newReportPackId, int newReportPackLinkId, int newReportDefId, int newDistributionId) {
      this.mReportPackId = newReportPackId;
      this.mReportPackLinkId = newReportPackLinkId;
      this.mReportDefId = newReportDefId;
      this.mDistributionId = newDistributionId;
   }

   public ReportPackLinkPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportPackLinkPK(this.mReportPackId, this.mReportPackLinkId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportPackId() {
      return this.mReportPackId;
   }

   public int getReportPackLinkId() {
      return this.mReportPackLinkId;
   }

   public int getReportDefId() {
      return this.mReportDefId;
   }

   public int getDistributionId() {
      return this.mDistributionId;
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

   public void setReportPackId(int newReportPackId) {
      if(this.mReportPackId != newReportPackId) {
         this.mModified = true;
         this.mReportPackId = newReportPackId;
         this.mPK = null;
      }
   }

   public void setReportPackLinkId(int newReportPackLinkId) {
      if(this.mReportPackLinkId != newReportPackLinkId) {
         this.mModified = true;
         this.mReportPackLinkId = newReportPackLinkId;
         this.mPK = null;
      }
   }

   public void setReportDefId(int newReportDefId) {
      if(this.mReportDefId != newReportDefId) {
         this.mModified = true;
         this.mReportDefId = newReportDefId;
      }
   }

   public void setDistributionId(int newDistributionId) {
      if(this.mDistributionId != newDistributionId) {
         this.mModified = true;
         this.mDistributionId = newDistributionId;
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

   public void setDetails(ReportPackLinkEVO newDetails) {
      this.setReportPackId(newDetails.getReportPackId());
      this.setReportPackLinkId(newDetails.getReportPackLinkId());
      this.setReportDefId(newDetails.getReportDefId());
      this.setDistributionId(newDetails.getDistributionId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportPackLinkEVO deepClone() {
      ReportPackLinkEVO cloned = new ReportPackLinkEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mReportPackId = this.mReportPackId;
      cloned.mReportPackLinkId = this.mReportPackLinkId;
      cloned.mReportDefId = this.mReportDefId;
      cloned.mDistributionId = this.mDistributionId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(ReportPackEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mReportPackLinkId > 0) {
         newKey = true;
         if(parent == null) {
            this.setReportPackLinkId(-this.mReportPackLinkId);
         } else {
            parent.changeKey(this, this.mReportPackId, -this.mReportPackLinkId);
         }
      } else if(this.mReportPackLinkId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportPackLinkId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(ReportPackEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mReportPackLinkId < 1) {
         parent.changeKey(this, this.mReportPackId, startKey);
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

   public ReportPackLinkRef getEntityRef(ReportPackEVO evoReportPack, String entityText) {
      return new ReportPackLinkRefImpl(new ReportPackLinkCK(evoReportPack.getPK(), this.getPK()), entityText);
   }

   public ReportPackLinkCK getCK(ReportPackEVO evoReportPack) {
      return new ReportPackLinkCK(evoReportPack.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportPackId=");
      sb.append(String.valueOf(this.mReportPackId));
      sb.append(' ');
      sb.append("ReportPackLinkId=");
      sb.append(String.valueOf(this.mReportPackLinkId));
      sb.append(' ');
      sb.append("ReportDefId=");
      sb.append(String.valueOf(this.mReportDefId));
      sb.append(' ');
      sb.append("DistributionId=");
      sb.append(String.valueOf(this.mDistributionId));
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

      sb.append("ReportPackLink: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
