// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementLocationRef;
import com.cedar.cp.dto.model.virement.VirementLocationCK;
import com.cedar.cp.dto.model.virement.VirementLocationPK;
import com.cedar.cp.dto.model.virement.VirementLocationRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementCategoryEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class VirementLocationEVO implements Serializable {

   private transient VirementLocationPK mPK;
   private int mVirementCategoryId;
   private int mStructureId;
   private int mStructureElementId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public VirementLocationEVO() {}

   public VirementLocationEVO(int newVirementCategoryId, int newStructureId, int newStructureElementId, int newVersionNum) {
      this.mVirementCategoryId = newVirementCategoryId;
      this.mStructureId = newStructureId;
      this.mStructureElementId = newStructureElementId;
      this.mVersionNum = newVersionNum;
   }

   public VirementLocationPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementLocationPK(this.mVirementCategoryId, this.mStructureId, this.mStructureElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getVirementCategoryId() {
      return this.mVirementCategoryId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
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

   public void setVirementCategoryId(int newVirementCategoryId) {
      if(this.mVirementCategoryId != newVirementCategoryId) {
         this.mModified = true;
         this.mVirementCategoryId = newVirementCategoryId;
         this.mPK = null;
      }
   }

   public void setStructureId(int newStructureId) {
      if(this.mStructureId != newStructureId) {
         this.mModified = true;
         this.mStructureId = newStructureId;
         this.mPK = null;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
         this.mPK = null;
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

   public void setDetails(VirementLocationEVO newDetails) {
      this.setVirementCategoryId(newDetails.getVirementCategoryId());
      this.setStructureId(newDetails.getStructureId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementLocationEVO deepClone() {
      VirementLocationEVO cloned = new VirementLocationEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mVirementCategoryId = this.mVirementCategoryId;
      cloned.mStructureId = this.mStructureId;
      cloned.mStructureElementId = this.mStructureElementId;
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

   public void prepareForInsert(VirementCategoryEVO parent) {
      boolean newKey = this.insertPending();
      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(VirementCategoryEVO parent, int startKey) {
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

   public VirementLocationRef getEntityRef(ModelEVO evoModel, VirementCategoryEVO evoVirementCategory, String entityText) {
      return new VirementLocationRefImpl(new VirementLocationCK(evoModel.getPK(), evoVirementCategory.getPK(), this.getPK()), entityText);
   }

   public VirementLocationCK getCK(ModelEVO evoModel, VirementCategoryEVO evoVirementCategory) {
      return new VirementLocationCK(evoModel.getPK(), evoVirementCategory.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("VirementCategoryId=");
      sb.append(String.valueOf(this.mVirementCategoryId));
      sb.append(' ');
      sb.append("StructureId=");
      sb.append(String.valueOf(this.mStructureId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
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

      sb.append("VirementLocation: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
