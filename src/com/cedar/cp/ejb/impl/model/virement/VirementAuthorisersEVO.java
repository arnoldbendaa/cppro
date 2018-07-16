// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementAuthorisersRef;
import com.cedar.cp.dto.model.virement.VirementAuthorisersCK;
import com.cedar.cp.dto.model.virement.VirementAuthorisersPK;
import com.cedar.cp.dto.model.virement.VirementAuthorisersRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class VirementAuthorisersEVO implements Serializable {

   private transient VirementAuthorisersPK mPK;
   private int mAuthPointId;
   private int mUserId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public VirementAuthorisersEVO() {}

   public VirementAuthorisersEVO(int newAuthPointId, int newUserId) {
      this.mAuthPointId = newAuthPointId;
      this.mUserId = newUserId;
   }

   public VirementAuthorisersPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementAuthorisersPK(this.mAuthPointId, this.mUserId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAuthPointId() {
      return this.mAuthPointId;
   }

   public int getUserId() {
      return this.mUserId;
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

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
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

   public void setDetails(VirementAuthorisersEVO newDetails) {
      this.setAuthPointId(newDetails.getAuthPointId());
      this.setUserId(newDetails.getUserId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementAuthorisersEVO deepClone() {
      VirementAuthorisersEVO cloned = new VirementAuthorisersEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mAuthPointId = this.mAuthPointId;
      cloned.mUserId = this.mUserId;
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

   public VirementAuthorisersRef getEntityRef(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementAuthPointEVO evoVirementAuthPoint, String entityText) {
      return new VirementAuthorisersRefImpl(new VirementAuthorisersCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementAuthPoint.getPK(), this.getPK()), entityText);
   }

   public VirementAuthorisersCK getCK(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementAuthPointEVO evoVirementAuthPoint) {
      return new VirementAuthorisersCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementAuthPoint.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AuthPointId=");
      sb.append(String.valueOf(this.mAuthPointId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
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

      sb.append("VirementAuthorisers: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
