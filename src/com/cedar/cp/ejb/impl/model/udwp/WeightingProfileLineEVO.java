// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingProfileLineRef;
import com.cedar.cp.dto.model.udwp.WeightingProfileLineCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLineRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class WeightingProfileLineEVO implements Serializable {

   private transient WeightingProfileLinePK mPK;
   private int mProfileId;
   private int mLineIdx;
   private int mWeighting;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public WeightingProfileLineEVO() {}

   public WeightingProfileLineEVO(int newProfileId, int newLineIdx, int newWeighting) {
      this.mProfileId = newProfileId;
      this.mLineIdx = newLineIdx;
      this.mWeighting = newWeighting;
   }

   public WeightingProfileLinePK getPK() {
      if(this.mPK == null) {
         this.mPK = new WeightingProfileLinePK(this.mProfileId, this.mLineIdx);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getProfileId() {
      return this.mProfileId;
   }

   public int getLineIdx() {
      return this.mLineIdx;
   }

   public int getWeighting() {
      return this.mWeighting;
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

   public void setProfileId(int newProfileId) {
      if(this.mProfileId != newProfileId) {
         this.mModified = true;
         this.mProfileId = newProfileId;
         this.mPK = null;
      }
   }

   public void setLineIdx(int newLineIdx) {
      if(this.mLineIdx != newLineIdx) {
         this.mModified = true;
         this.mLineIdx = newLineIdx;
         this.mPK = null;
      }
   }

   public void setWeighting(int newWeighting) {
      if(this.mWeighting != newWeighting) {
         this.mModified = true;
         this.mWeighting = newWeighting;
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

   public void setDetails(WeightingProfileLineEVO newDetails) {
      this.setProfileId(newDetails.getProfileId());
      this.setLineIdx(newDetails.getLineIdx());
      this.setWeighting(newDetails.getWeighting());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public WeightingProfileLineEVO deepClone() {
      WeightingProfileLineEVO cloned = new WeightingProfileLineEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mProfileId = this.mProfileId;
      cloned.mLineIdx = this.mLineIdx;
      cloned.mWeighting = this.mWeighting;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(WeightingProfileEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(WeightingProfileEVO parent, int startKey) {
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

   public WeightingProfileLineRef getEntityRef(ModelEVO evoModel, WeightingProfileEVO evoWeightingProfile, String entityText) {
      return new WeightingProfileLineRefImpl(new WeightingProfileLineCK(evoModel.getPK(), evoWeightingProfile.getPK(), this.getPK()), entityText);
   }

   public WeightingProfileLineCK getCK(ModelEVO evoModel, WeightingProfileEVO evoWeightingProfile) {
      return new WeightingProfileLineCK(evoModel.getPK(), evoWeightingProfile.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ProfileId=");
      sb.append(String.valueOf(this.mProfileId));
      sb.append(' ');
      sb.append("LineIdx=");
      sb.append(String.valueOf(this.mLineIdx));
      sb.append(' ');
      sb.append("Weighting=");
      sb.append(String.valueOf(this.mWeighting));
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

      sb.append("WeightingProfileLine: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
