// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.ModelDimensionRelRef;
import com.cedar.cp.dto.model.ModelDimensionRelCK;
import com.cedar.cp.dto.model.ModelDimensionRelPK;
import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ModelDimensionRelEVO implements Serializable {

   private transient ModelDimensionRelPK mPK;
   private int mModelId;
   private int mDimensionId;
   private int mDimensionType;
   private int mDimensionSeqNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ModelDimensionRelEVO() {}

   public ModelDimensionRelEVO(int newModelId, int newDimensionId, int newDimensionType, int newDimensionSeqNum) {
      this.mModelId = newModelId;
      this.mDimensionId = newDimensionId;
      this.mDimensionType = newDimensionType;
      this.mDimensionSeqNum = newDimensionSeqNum;
   }

   public ModelDimensionRelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ModelDimensionRelPK(this.mModelId, this.mDimensionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public int getDimensionType() {
      return this.mDimensionType;
   }

   public int getDimensionSeqNum() {
      return this.mDimensionSeqNum;
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

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
         this.mPK = null;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
         this.mPK = null;
      }
   }

   public void setDimensionType(int newDimensionType) {
      if(this.mDimensionType != newDimensionType) {
         this.mModified = true;
         this.mDimensionType = newDimensionType;
      }
   }

   public void setDimensionSeqNum(int newDimensionSeqNum) {
      if(this.mDimensionSeqNum != newDimensionSeqNum) {
         this.mModified = true;
         this.mDimensionSeqNum = newDimensionSeqNum;
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

   public void setDetails(ModelDimensionRelEVO newDetails) {
      this.setModelId(newDetails.getModelId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setDimensionType(newDetails.getDimensionType());
      this.setDimensionSeqNum(newDetails.getDimensionSeqNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ModelDimensionRelEVO deepClone() {
      ModelDimensionRelEVO cloned = new ModelDimensionRelEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mModelId = this.mModelId;
      cloned.mDimensionId = this.mDimensionId;
      cloned.mDimensionType = this.mDimensionType;
      cloned.mDimensionSeqNum = this.mDimensionSeqNum;
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
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
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

   public ModelDimensionRelRef getEntityRef(ModelEVO evoModel, String entityText) {
      return new ModelDimensionRelRefImpl(new ModelDimensionRelCK(evoModel.getPK(), this.getPK()), entityText);
   }

   public ModelDimensionRelCK getCK(ModelEVO evoModel) {
      return new ModelDimensionRelCK(evoModel.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("DimensionType=");
      sb.append(String.valueOf(this.mDimensionType));
      sb.append(' ');
      sb.append("DimensionSeqNum=");
      sb.append(String.valueOf(this.mDimensionSeqNum));
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

      sb.append("ModelDimensionRel: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
