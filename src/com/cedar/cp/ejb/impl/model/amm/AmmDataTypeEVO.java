// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.model.amm.AmmDataTypeRef;
import com.cedar.cp.dto.model.amm.AmmDataTypeCK;
import com.cedar.cp.dto.model.amm.AmmDataTypePK;
import com.cedar.cp.dto.model.amm.AmmDataTypeRefImpl;
import com.cedar.cp.ejb.impl.model.amm.AmmFinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class AmmDataTypeEVO implements Serializable {

   private transient AmmDataTypePK mPK;
   private int mAmmDataTypeId;
   private int mAmmFinanceCubeId;
   private int mDataTypeId;
   private int mSrcDataTypeId;
   private int mSrcStartYearOffset;
   private int mSrcEndYearOffset;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public AmmDataTypeEVO() {}

   public AmmDataTypeEVO(int newAmmDataTypeId, int newAmmFinanceCubeId, int newDataTypeId, int newSrcDataTypeId, int newSrcStartYearOffset, int newSrcEndYearOffset) {
      this.mAmmDataTypeId = newAmmDataTypeId;
      this.mAmmFinanceCubeId = newAmmFinanceCubeId;
      this.mDataTypeId = newDataTypeId;
      this.mSrcDataTypeId = newSrcDataTypeId;
      this.mSrcStartYearOffset = newSrcStartYearOffset;
      this.mSrcEndYearOffset = newSrcEndYearOffset;
   }

   public AmmDataTypePK getPK() {
      if(this.mPK == null) {
         this.mPK = new AmmDataTypePK(this.mAmmDataTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAmmDataTypeId() {
      return this.mAmmDataTypeId;
   }

   public int getAmmFinanceCubeId() {
      return this.mAmmFinanceCubeId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public int getSrcDataTypeId() {
      return this.mSrcDataTypeId;
   }

   public int getSrcStartYearOffset() {
      return this.mSrcStartYearOffset;
   }

   public int getSrcEndYearOffset() {
      return this.mSrcEndYearOffset;
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

   public void setAmmDataTypeId(int newAmmDataTypeId) {
      if(this.mAmmDataTypeId != newAmmDataTypeId) {
         this.mModified = true;
         this.mAmmDataTypeId = newAmmDataTypeId;
         this.mPK = null;
      }
   }

   public void setAmmFinanceCubeId(int newAmmFinanceCubeId) {
      if(this.mAmmFinanceCubeId != newAmmFinanceCubeId) {
         this.mModified = true;
         this.mAmmFinanceCubeId = newAmmFinanceCubeId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setSrcDataTypeId(int newSrcDataTypeId) {
      if(this.mSrcDataTypeId != newSrcDataTypeId) {
         this.mModified = true;
         this.mSrcDataTypeId = newSrcDataTypeId;
      }
   }

   public void setSrcStartYearOffset(int newSrcStartYearOffset) {
      if(this.mSrcStartYearOffset != newSrcStartYearOffset) {
         this.mModified = true;
         this.mSrcStartYearOffset = newSrcStartYearOffset;
      }
   }

   public void setSrcEndYearOffset(int newSrcEndYearOffset) {
      if(this.mSrcEndYearOffset != newSrcEndYearOffset) {
         this.mModified = true;
         this.mSrcEndYearOffset = newSrcEndYearOffset;
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

   public void setDetails(AmmDataTypeEVO newDetails) {
      this.setAmmDataTypeId(newDetails.getAmmDataTypeId());
      this.setAmmFinanceCubeId(newDetails.getAmmFinanceCubeId());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setSrcDataTypeId(newDetails.getSrcDataTypeId());
      this.setSrcStartYearOffset(newDetails.getSrcStartYearOffset());
      this.setSrcEndYearOffset(newDetails.getSrcEndYearOffset());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public AmmDataTypeEVO deepClone() {
      AmmDataTypeEVO cloned = new AmmDataTypeEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mAmmDataTypeId = this.mAmmDataTypeId;
      cloned.mAmmFinanceCubeId = this.mAmmFinanceCubeId;
      cloned.mDataTypeId = this.mDataTypeId;
      cloned.mSrcDataTypeId = this.mSrcDataTypeId;
      cloned.mSrcStartYearOffset = this.mSrcStartYearOffset;
      cloned.mSrcEndYearOffset = this.mSrcEndYearOffset;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(AmmFinanceCubeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mAmmDataTypeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setAmmDataTypeId(-this.mAmmDataTypeId);
         } else {
            parent.changeKey(this, -this.mAmmDataTypeId);
         }
      } else if(this.mAmmDataTypeId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAmmDataTypeId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(AmmFinanceCubeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mAmmDataTypeId < 1) {
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

   public AmmDataTypeRef getEntityRef(AmmModelEVO evoAmmModel, AmmFinanceCubeEVO evoAmmFinanceCube, String entityText) {
      return new AmmDataTypeRefImpl(new AmmDataTypeCK(evoAmmModel.getPK(), evoAmmFinanceCube.getPK(), this.getPK()), entityText);
   }

   public AmmDataTypeCK getCK(AmmModelEVO evoAmmModel, AmmFinanceCubeEVO evoAmmFinanceCube) {
      return new AmmDataTypeCK(evoAmmModel.getPK(), evoAmmFinanceCube.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AmmDataTypeId=");
      sb.append(String.valueOf(this.mAmmDataTypeId));
      sb.append(' ');
      sb.append("AmmFinanceCubeId=");
      sb.append(String.valueOf(this.mAmmFinanceCubeId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("SrcDataTypeId=");
      sb.append(String.valueOf(this.mSrcDataTypeId));
      sb.append(' ');
      sb.append("SrcStartYearOffset=");
      sb.append(String.valueOf(this.mSrcStartYearOffset));
      sb.append(' ');
      sb.append("SrcEndYearOffset=");
      sb.append(String.valueOf(this.mSrcEndYearOffset));
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

      sb.append("AmmDataType: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
