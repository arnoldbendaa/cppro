// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.model.amm.AmmSrcStructureElementRef;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementCK;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementRefImpl;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionElementEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class AmmSrcStructureElementEVO implements Serializable {

   private transient AmmSrcStructureElementPK mPK;
   private int mAmmSrcStructureElementId;
   private int mAmmDimensionElementId;
   private int mSrcStructureElementId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public AmmSrcStructureElementEVO() {}

   public AmmSrcStructureElementEVO(int newAmmSrcStructureElementId, int newAmmDimensionElementId, int newSrcStructureElementId) {
      this.mAmmSrcStructureElementId = newAmmSrcStructureElementId;
      this.mAmmDimensionElementId = newAmmDimensionElementId;
      this.mSrcStructureElementId = newSrcStructureElementId;
   }

   public AmmSrcStructureElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new AmmSrcStructureElementPK(this.mAmmSrcStructureElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAmmSrcStructureElementId() {
      return this.mAmmSrcStructureElementId;
   }

   public int getAmmDimensionElementId() {
      return this.mAmmDimensionElementId;
   }

   public int getSrcStructureElementId() {
      return this.mSrcStructureElementId;
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

   public void setAmmSrcStructureElementId(int newAmmSrcStructureElementId) {
      if(this.mAmmSrcStructureElementId != newAmmSrcStructureElementId) {
         this.mModified = true;
         this.mAmmSrcStructureElementId = newAmmSrcStructureElementId;
         this.mPK = null;
      }
   }

   public void setAmmDimensionElementId(int newAmmDimensionElementId) {
      if(this.mAmmDimensionElementId != newAmmDimensionElementId) {
         this.mModified = true;
         this.mAmmDimensionElementId = newAmmDimensionElementId;
      }
   }

   public void setSrcStructureElementId(int newSrcStructureElementId) {
      if(this.mSrcStructureElementId != newSrcStructureElementId) {
         this.mModified = true;
         this.mSrcStructureElementId = newSrcStructureElementId;
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

   public void setDetails(AmmSrcStructureElementEVO newDetails) {
      this.setAmmSrcStructureElementId(newDetails.getAmmSrcStructureElementId());
      this.setAmmDimensionElementId(newDetails.getAmmDimensionElementId());
      this.setSrcStructureElementId(newDetails.getSrcStructureElementId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public AmmSrcStructureElementEVO deepClone() {
      AmmSrcStructureElementEVO cloned = new AmmSrcStructureElementEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mAmmSrcStructureElementId = this.mAmmSrcStructureElementId;
      cloned.mAmmDimensionElementId = this.mAmmDimensionElementId;
      cloned.mSrcStructureElementId = this.mSrcStructureElementId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(AmmDimensionElementEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mAmmSrcStructureElementId > 0) {
         newKey = true;
         if(parent == null) {
            this.setAmmSrcStructureElementId(-this.mAmmSrcStructureElementId);
         } else {
            parent.changeKey(this, -this.mAmmSrcStructureElementId);
         }
      } else if(this.mAmmSrcStructureElementId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAmmSrcStructureElementId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(AmmDimensionElementEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mAmmSrcStructureElementId < 1) {
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

   public AmmSrcStructureElementRef getEntityRef(AmmModelEVO evoAmmModel, AmmDimensionEVO evoAmmDimension, AmmDimensionElementEVO evoAmmDimensionElement, String entityText) {
      return new AmmSrcStructureElementRefImpl(new AmmSrcStructureElementCK(evoAmmModel.getPK(), evoAmmDimension.getPK(), evoAmmDimensionElement.getPK(), this.getPK()), entityText);
   }

   public AmmSrcStructureElementCK getCK(AmmModelEVO evoAmmModel, AmmDimensionEVO evoAmmDimension, AmmDimensionElementEVO evoAmmDimensionElement) {
      return new AmmSrcStructureElementCK(evoAmmModel.getPK(), evoAmmDimension.getPK(), evoAmmDimensionElement.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AmmSrcStructureElementId=");
      sb.append(String.valueOf(this.mAmmSrcStructureElementId));
      sb.append(' ');
      sb.append("AmmDimensionElementId=");
      sb.append(String.valueOf(this.mAmmDimensionElementId));
      sb.append(' ');
      sb.append("SrcStructureElementId=");
      sb.append(String.valueOf(this.mSrcStructureElementId));
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

      sb.append("AmmSrcStructureElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
