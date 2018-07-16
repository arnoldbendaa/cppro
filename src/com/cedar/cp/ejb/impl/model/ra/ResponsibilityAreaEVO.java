// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.ra;

import com.cedar.cp.api.model.ra.ResponsibilityAreaRef;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ResponsibilityAreaEVO implements Serializable {

   private transient ResponsibilityAreaPK mPK;
   private int mResponsibilityAreaId;
   private int mModelId;
   private int mStructureId;
   private int mStructureElementId;
   private int mVirementAuthStatus;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int AUTH_INHERIT_PARENT = 0;
   public static final int AUTH_REQUIRED = 1;
   public static final int AUTH_NOT_REQUIRED = 2;


   public ResponsibilityAreaEVO() {}

   public ResponsibilityAreaEVO(int newResponsibilityAreaId, int newModelId, int newStructureId, int newStructureElementId, int newVirementAuthStatus, int newVersionNum) {
      this.mResponsibilityAreaId = newResponsibilityAreaId;
      this.mModelId = newModelId;
      this.mStructureId = newStructureId;
      this.mStructureElementId = newStructureElementId;
      this.mVirementAuthStatus = newVirementAuthStatus;
      this.mVersionNum = newVersionNum;
   }

   public ResponsibilityAreaPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ResponsibilityAreaPK(this.mResponsibilityAreaId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getResponsibilityAreaId() {
      return this.mResponsibilityAreaId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getVirementAuthStatus() {
      return this.mVirementAuthStatus;
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

   public void setResponsibilityAreaId(int newResponsibilityAreaId) {
      if(this.mResponsibilityAreaId != newResponsibilityAreaId) {
         this.mModified = true;
         this.mResponsibilityAreaId = newResponsibilityAreaId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setStructureId(int newStructureId) {
      if(this.mStructureId != newStructureId) {
         this.mModified = true;
         this.mStructureId = newStructureId;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
      }
   }

   public void setVirementAuthStatus(int newVirementAuthStatus) {
      if(this.mVirementAuthStatus != newVirementAuthStatus) {
         this.mModified = true;
         this.mVirementAuthStatus = newVirementAuthStatus;
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

   public void setDetails(ResponsibilityAreaEVO newDetails) {
      this.setResponsibilityAreaId(newDetails.getResponsibilityAreaId());
      this.setModelId(newDetails.getModelId());
      this.setStructureId(newDetails.getStructureId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setVirementAuthStatus(newDetails.getVirementAuthStatus());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ResponsibilityAreaEVO deepClone() {
      ResponsibilityAreaEVO cloned = new ResponsibilityAreaEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mResponsibilityAreaId = this.mResponsibilityAreaId;
      cloned.mModelId = this.mModelId;
      cloned.mStructureId = this.mStructureId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mVirementAuthStatus = this.mVirementAuthStatus;
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

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mResponsibilityAreaId > 0) {
         newKey = true;
         if(parent == null) {
            this.setResponsibilityAreaId(-this.mResponsibilityAreaId);
         } else {
            parent.changeKey(this, -this.mResponsibilityAreaId);
         }
      } else if(this.mResponsibilityAreaId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mResponsibilityAreaId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mResponsibilityAreaId < 1) {
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

   public ResponsibilityAreaRef getEntityRef(ModelEVO evoModel, String entityText) {
      return new ResponsibilityAreaRefImpl(new ResponsibilityAreaCK(evoModel.getPK(), this.getPK()), entityText);
   }

   public ResponsibilityAreaCK getCK(ModelEVO evoModel) {
      return new ResponsibilityAreaCK(evoModel.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ResponsibilityAreaId=");
      sb.append(String.valueOf(this.mResponsibilityAreaId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("StructureId=");
      sb.append(String.valueOf(this.mStructureId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("VirementAuthStatus=");
      sb.append(String.valueOf(this.mVirementAuthStatus));
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

      sb.append("ResponsibilityArea: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
