// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementAuthPointLinkRef;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkCK;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkPK;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class VirementAuthPointLinkEVO implements Serializable {

   private transient VirementAuthPointLinkPK mPK;
   private int mAuthPointId;
   private int mVirementLineId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public VirementAuthPointLinkEVO() {}

   public VirementAuthPointLinkEVO(int newAuthPointId, int newVirementLineId) {
      this.mAuthPointId = newAuthPointId;
      this.mVirementLineId = newVirementLineId;
   }

   public VirementAuthPointLinkPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementAuthPointLinkPK(this.mAuthPointId, this.mVirementLineId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAuthPointId() {
      return this.mAuthPointId;
   }

   public int getVirementLineId() {
      return this.mVirementLineId;
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

   public void setAuthPointId(int newAuthPointId) {
      if(this.mAuthPointId != newAuthPointId) {
         this.mModified = true;
         this.mAuthPointId = newAuthPointId;
         this.mPK = null;
      }
   }

   public void setVirementLineId(int newVirementLineId) {
      if(this.mVirementLineId != newVirementLineId) {
         this.mModified = true;
         this.mVirementLineId = newVirementLineId;
         this.mPK = null;
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

   public void setDetails(VirementAuthPointLinkEVO newDetails) {
      this.setAuthPointId(newDetails.getAuthPointId());
      this.setVirementLineId(newDetails.getVirementLineId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementAuthPointLinkEVO deepClone() {
      VirementAuthPointLinkEVO cloned = new VirementAuthPointLinkEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mAuthPointId = this.mAuthPointId;
      cloned.mVirementLineId = this.mVirementLineId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(VirementAuthPointEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(VirementAuthPointEVO parent, int startKey) {
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

   public VirementAuthPointLinkRef getEntityRef(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementAuthPointEVO evoVirementAuthPoint, String entityText) {
      return new VirementAuthPointLinkRefImpl(new VirementAuthPointLinkCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementAuthPoint.getPK(), this.getPK()), entityText);
   }

   public VirementAuthPointLinkCK getCK(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementAuthPointEVO evoVirementAuthPoint) {
      return new VirementAuthPointLinkCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementAuthPoint.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AuthPointId=");
      sb.append(String.valueOf(this.mAuthPointId));
      sb.append(' ');
      sb.append("VirementLineId=");
      sb.append(String.valueOf(this.mVirementLineId));
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

      sb.append("VirementAuthPointLink: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
