// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementLineSpreadRef;
import com.cedar.cp.dto.model.virement.VirementLineSpreadCK;
import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
import com.cedar.cp.dto.model.virement.VirementLineSpreadRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestGroupEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestLineEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class VirementLineSpreadEVO implements Serializable {

   private transient VirementLineSpreadPK mPK;
   private int mLineSpreadId;
   private int mStructureElementId;
   private int mRequestLineId;
   private int mLineIdx;
   private boolean mHeld;
   private int mWeighting;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public VirementLineSpreadEVO() {}

   public VirementLineSpreadEVO(int newLineSpreadId, int newStructureElementId, int newRequestLineId, int newLineIdx, boolean newHeld, int newWeighting) {
      this.mLineSpreadId = newLineSpreadId;
      this.mStructureElementId = newStructureElementId;
      this.mRequestLineId = newRequestLineId;
      this.mLineIdx = newLineIdx;
      this.mHeld = newHeld;
      this.mWeighting = newWeighting;
   }

   public VirementLineSpreadPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementLineSpreadPK(this.mLineSpreadId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getLineSpreadId() {
      return this.mLineSpreadId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getRequestLineId() {
      return this.mRequestLineId;
   }

   public int getLineIdx() {
      return this.mLineIdx;
   }

   public boolean getHeld() {
      return this.mHeld;
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

   public void setLineSpreadId(int newLineSpreadId) {
      if(this.mLineSpreadId != newLineSpreadId) {
         this.mModified = true;
         this.mLineSpreadId = newLineSpreadId;
         this.mPK = null;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
      }
   }

   public void setRequestLineId(int newRequestLineId) {
      if(this.mRequestLineId != newRequestLineId) {
         this.mModified = true;
         this.mRequestLineId = newRequestLineId;
      }
   }

   public void setLineIdx(int newLineIdx) {
      if(this.mLineIdx != newLineIdx) {
         this.mModified = true;
         this.mLineIdx = newLineIdx;
      }
   }

   public void setHeld(boolean newHeld) {
      if(this.mHeld != newHeld) {
         this.mModified = true;
         this.mHeld = newHeld;
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

   public void setDetails(VirementLineSpreadEVO newDetails) {
      this.setLineSpreadId(newDetails.getLineSpreadId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setRequestLineId(newDetails.getRequestLineId());
      this.setLineIdx(newDetails.getLineIdx());
      this.setHeld(newDetails.getHeld());
      this.setWeighting(newDetails.getWeighting());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementLineSpreadEVO deepClone() {
      VirementLineSpreadEVO cloned = new VirementLineSpreadEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mLineSpreadId = this.mLineSpreadId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mRequestLineId = this.mRequestLineId;
      cloned.mLineIdx = this.mLineIdx;
      cloned.mHeld = this.mHeld;
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

   public void prepareForInsert(VirementRequestLineEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mLineSpreadId > 0) {
         newKey = true;
         if(parent == null) {
            this.setLineSpreadId(-this.mLineSpreadId);
         } else {
            parent.changeKey(this, -this.mLineSpreadId);
         }
      } else if(this.mLineSpreadId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mLineSpreadId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(VirementRequestLineEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mLineSpreadId < 1) {
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

   public VirementLineSpreadRef getEntityRef(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementRequestGroupEVO evoVirementRequestGroup, VirementRequestLineEVO evoVirementRequestLine, String entityText) {
      return new VirementLineSpreadRefImpl(new VirementLineSpreadCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementRequestGroup.getPK(), evoVirementRequestLine.getPK(), this.getPK()), entityText);
   }

   public VirementLineSpreadCK getCK(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementRequestGroupEVO evoVirementRequestGroup, VirementRequestLineEVO evoVirementRequestLine) {
      return new VirementLineSpreadCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementRequestGroup.getPK(), evoVirementRequestLine.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("LineSpreadId=");
      sb.append(String.valueOf(this.mLineSpreadId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("RequestLineId=");
      sb.append(String.valueOf(this.mRequestLineId));
      sb.append(' ');
      sb.append("LineIdx=");
      sb.append(String.valueOf(this.mLineIdx));
      sb.append(' ');
      sb.append("Held=");
      sb.append(String.valueOf(this.mHeld));
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

      sb.append("VirementLineSpread: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
