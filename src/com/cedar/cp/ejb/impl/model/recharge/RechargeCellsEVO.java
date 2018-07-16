// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.api.model.recharge.RechargeCellsRef;
import com.cedar.cp.dto.model.recharge.RechargeCellsCK;
import com.cedar.cp.dto.model.recharge.RechargeCellsPK;
import com.cedar.cp.dto.model.recharge.RechargeCellsRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeEVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class RechargeCellsEVO implements Serializable {

   private transient RechargeCellsPK mPK;
   private int mRechargeCellId;
   private int mRechargeId;
   private int mCellType;
   private int mDim0StructureId;
   private int mDim0StructureElementId;
   private int mDim1StructureId;
   private int mDim1StructureElementId;
   private int mDim2StructureId;
   private int mDim2StructureElementId;
   private int mDim3StructureId;
   private int mDim3StructureElementId;
   private int mDim4StructureId;
   private int mDim4StructureElementId;
   private int mDim5StructureId;
   private int mDim5StructureElementId;
   private int mDim6StructureId;
   private int mDim6StructureElementId;
   private int mDim7StructureId;
   private int mDim7StructureElementId;
   private int mDim8StructureId;
   private int mDim8StructureElementId;
   private int mDim9StructureId;
   private int mDim9StructureElementId;
   private String mDataType;
   private BigDecimal mRatio;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int SOURCE = 0;
   public static final int TARGET = 1;
   public static final int OFFSET = 2;


   public RechargeCellsEVO() {}

   public RechargeCellsEVO(int newRechargeCellId, int newRechargeId, int newCellType, int newDim0StructureId, int newDim0StructureElementId, int newDim1StructureId, int newDim1StructureElementId, int newDim2StructureId, int newDim2StructureElementId, int newDim3StructureId, int newDim3StructureElementId, int newDim4StructureId, int newDim4StructureElementId, int newDim5StructureId, int newDim5StructureElementId, int newDim6StructureId, int newDim6StructureElementId, int newDim7StructureId, int newDim7StructureElementId, int newDim8StructureId, int newDim8StructureElementId, int newDim9StructureId, int newDim9StructureElementId, String newDataType, BigDecimal newRatio) {
      this.mRechargeCellId = newRechargeCellId;
      this.mRechargeId = newRechargeId;
      this.mCellType = newCellType;
      this.mDim0StructureId = newDim0StructureId;
      this.mDim0StructureElementId = newDim0StructureElementId;
      this.mDim1StructureId = newDim1StructureId;
      this.mDim1StructureElementId = newDim1StructureElementId;
      this.mDim2StructureId = newDim2StructureId;
      this.mDim2StructureElementId = newDim2StructureElementId;
      this.mDim3StructureId = newDim3StructureId;
      this.mDim3StructureElementId = newDim3StructureElementId;
      this.mDim4StructureId = newDim4StructureId;
      this.mDim4StructureElementId = newDim4StructureElementId;
      this.mDim5StructureId = newDim5StructureId;
      this.mDim5StructureElementId = newDim5StructureElementId;
      this.mDim6StructureId = newDim6StructureId;
      this.mDim6StructureElementId = newDim6StructureElementId;
      this.mDim7StructureId = newDim7StructureId;
      this.mDim7StructureElementId = newDim7StructureElementId;
      this.mDim8StructureId = newDim8StructureId;
      this.mDim8StructureElementId = newDim8StructureElementId;
      this.mDim9StructureId = newDim9StructureId;
      this.mDim9StructureElementId = newDim9StructureElementId;
      this.mDataType = newDataType;
      this.mRatio = newRatio;
   }

   public RechargeCellsPK getPK() {
      if(this.mPK == null) {
         this.mPK = new RechargeCellsPK(this.mRechargeCellId, this.mRechargeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRechargeCellId() {
      return this.mRechargeCellId;
   }

   public int getRechargeId() {
      return this.mRechargeId;
   }

   public int getCellType() {
      return this.mCellType;
   }

   public int getDim0StructureId() {
      return this.mDim0StructureId;
   }

   public int getDim0StructureElementId() {
      return this.mDim0StructureElementId;
   }

   public int getDim1StructureId() {
      return this.mDim1StructureId;
   }

   public int getDim1StructureElementId() {
      return this.mDim1StructureElementId;
   }

   public int getDim2StructureId() {
      return this.mDim2StructureId;
   }

   public int getDim2StructureElementId() {
      return this.mDim2StructureElementId;
   }

   public int getDim3StructureId() {
      return this.mDim3StructureId;
   }

   public int getDim3StructureElementId() {
      return this.mDim3StructureElementId;
   }

   public int getDim4StructureId() {
      return this.mDim4StructureId;
   }

   public int getDim4StructureElementId() {
      return this.mDim4StructureElementId;
   }

   public int getDim5StructureId() {
      return this.mDim5StructureId;
   }

   public int getDim5StructureElementId() {
      return this.mDim5StructureElementId;
   }

   public int getDim6StructureId() {
      return this.mDim6StructureId;
   }

   public int getDim6StructureElementId() {
      return this.mDim6StructureElementId;
   }

   public int getDim7StructureId() {
      return this.mDim7StructureId;
   }

   public int getDim7StructureElementId() {
      return this.mDim7StructureElementId;
   }

   public int getDim8StructureId() {
      return this.mDim8StructureId;
   }

   public int getDim8StructureElementId() {
      return this.mDim8StructureElementId;
   }

   public int getDim9StructureId() {
      return this.mDim9StructureId;
   }

   public int getDim9StructureElementId() {
      return this.mDim9StructureElementId;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public BigDecimal getRatio() {
      return this.mRatio;
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

   public void setRechargeCellId(int newRechargeCellId) {
      if(this.mRechargeCellId != newRechargeCellId) {
         this.mModified = true;
         this.mRechargeCellId = newRechargeCellId;
         this.mPK = null;
      }
   }

   public void setRechargeId(int newRechargeId) {
      if(this.mRechargeId != newRechargeId) {
         this.mModified = true;
         this.mRechargeId = newRechargeId;
         this.mPK = null;
      }
   }

   public void setCellType(int newCellType) {
      if(this.mCellType != newCellType) {
         this.mModified = true;
         this.mCellType = newCellType;
      }
   }

   public void setDim0StructureId(int newDim0StructureId) {
      if(this.mDim0StructureId != newDim0StructureId) {
         this.mModified = true;
         this.mDim0StructureId = newDim0StructureId;
      }
   }

   public void setDim0StructureElementId(int newDim0StructureElementId) {
      if(this.mDim0StructureElementId != newDim0StructureElementId) {
         this.mModified = true;
         this.mDim0StructureElementId = newDim0StructureElementId;
      }
   }

   public void setDim1StructureId(int newDim1StructureId) {
      if(this.mDim1StructureId != newDim1StructureId) {
         this.mModified = true;
         this.mDim1StructureId = newDim1StructureId;
      }
   }

   public void setDim1StructureElementId(int newDim1StructureElementId) {
      if(this.mDim1StructureElementId != newDim1StructureElementId) {
         this.mModified = true;
         this.mDim1StructureElementId = newDim1StructureElementId;
      }
   }

   public void setDim2StructureId(int newDim2StructureId) {
      if(this.mDim2StructureId != newDim2StructureId) {
         this.mModified = true;
         this.mDim2StructureId = newDim2StructureId;
      }
   }

   public void setDim2StructureElementId(int newDim2StructureElementId) {
      if(this.mDim2StructureElementId != newDim2StructureElementId) {
         this.mModified = true;
         this.mDim2StructureElementId = newDim2StructureElementId;
      }
   }

   public void setDim3StructureId(int newDim3StructureId) {
      if(this.mDim3StructureId != newDim3StructureId) {
         this.mModified = true;
         this.mDim3StructureId = newDim3StructureId;
      }
   }

   public void setDim3StructureElementId(int newDim3StructureElementId) {
      if(this.mDim3StructureElementId != newDim3StructureElementId) {
         this.mModified = true;
         this.mDim3StructureElementId = newDim3StructureElementId;
      }
   }

   public void setDim4StructureId(int newDim4StructureId) {
      if(this.mDim4StructureId != newDim4StructureId) {
         this.mModified = true;
         this.mDim4StructureId = newDim4StructureId;
      }
   }

   public void setDim4StructureElementId(int newDim4StructureElementId) {
      if(this.mDim4StructureElementId != newDim4StructureElementId) {
         this.mModified = true;
         this.mDim4StructureElementId = newDim4StructureElementId;
      }
   }

   public void setDim5StructureId(int newDim5StructureId) {
      if(this.mDim5StructureId != newDim5StructureId) {
         this.mModified = true;
         this.mDim5StructureId = newDim5StructureId;
      }
   }

   public void setDim5StructureElementId(int newDim5StructureElementId) {
      if(this.mDim5StructureElementId != newDim5StructureElementId) {
         this.mModified = true;
         this.mDim5StructureElementId = newDim5StructureElementId;
      }
   }

   public void setDim6StructureId(int newDim6StructureId) {
      if(this.mDim6StructureId != newDim6StructureId) {
         this.mModified = true;
         this.mDim6StructureId = newDim6StructureId;
      }
   }

   public void setDim6StructureElementId(int newDim6StructureElementId) {
      if(this.mDim6StructureElementId != newDim6StructureElementId) {
         this.mModified = true;
         this.mDim6StructureElementId = newDim6StructureElementId;
      }
   }

   public void setDim7StructureId(int newDim7StructureId) {
      if(this.mDim7StructureId != newDim7StructureId) {
         this.mModified = true;
         this.mDim7StructureId = newDim7StructureId;
      }
   }

   public void setDim7StructureElementId(int newDim7StructureElementId) {
      if(this.mDim7StructureElementId != newDim7StructureElementId) {
         this.mModified = true;
         this.mDim7StructureElementId = newDim7StructureElementId;
      }
   }

   public void setDim8StructureId(int newDim8StructureId) {
      if(this.mDim8StructureId != newDim8StructureId) {
         this.mModified = true;
         this.mDim8StructureId = newDim8StructureId;
      }
   }

   public void setDim8StructureElementId(int newDim8StructureElementId) {
      if(this.mDim8StructureElementId != newDim8StructureElementId) {
         this.mModified = true;
         this.mDim8StructureElementId = newDim8StructureElementId;
      }
   }

   public void setDim9StructureId(int newDim9StructureId) {
      if(this.mDim9StructureId != newDim9StructureId) {
         this.mModified = true;
         this.mDim9StructureId = newDim9StructureId;
      }
   }

   public void setDim9StructureElementId(int newDim9StructureElementId) {
      if(this.mDim9StructureElementId != newDim9StructureElementId) {
         this.mModified = true;
         this.mDim9StructureElementId = newDim9StructureElementId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setDataType(String newDataType) {
      if(this.mDataType != null && newDataType == null || this.mDataType == null && newDataType != null || this.mDataType != null && newDataType != null && !this.mDataType.equals(newDataType)) {
         this.mDataType = newDataType;
         this.mModified = true;
      }

   }

   public void setRatio(BigDecimal newRatio) {
      if(this.mRatio != null && newRatio == null || this.mRatio == null && newRatio != null || this.mRatio != null && newRatio != null && !this.mRatio.equals(newRatio)) {
         this.mRatio = newRatio;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(RechargeCellsEVO newDetails) {
      this.setRechargeCellId(newDetails.getRechargeCellId());
      this.setRechargeId(newDetails.getRechargeId());
      this.setCellType(newDetails.getCellType());
      this.setDim0StructureId(newDetails.getDim0StructureId());
      this.setDim0StructureElementId(newDetails.getDim0StructureElementId());
      this.setDim1StructureId(newDetails.getDim1StructureId());
      this.setDim1StructureElementId(newDetails.getDim1StructureElementId());
      this.setDim2StructureId(newDetails.getDim2StructureId());
      this.setDim2StructureElementId(newDetails.getDim2StructureElementId());
      this.setDim3StructureId(newDetails.getDim3StructureId());
      this.setDim3StructureElementId(newDetails.getDim3StructureElementId());
      this.setDim4StructureId(newDetails.getDim4StructureId());
      this.setDim4StructureElementId(newDetails.getDim4StructureElementId());
      this.setDim5StructureId(newDetails.getDim5StructureId());
      this.setDim5StructureElementId(newDetails.getDim5StructureElementId());
      this.setDim6StructureId(newDetails.getDim6StructureId());
      this.setDim6StructureElementId(newDetails.getDim6StructureElementId());
      this.setDim7StructureId(newDetails.getDim7StructureId());
      this.setDim7StructureElementId(newDetails.getDim7StructureElementId());
      this.setDim8StructureId(newDetails.getDim8StructureId());
      this.setDim8StructureElementId(newDetails.getDim8StructureElementId());
      this.setDim9StructureId(newDetails.getDim9StructureId());
      this.setDim9StructureElementId(newDetails.getDim9StructureElementId());
      this.setDataType(newDetails.getDataType());
      this.setRatio(newDetails.getRatio());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RechargeCellsEVO deepClone() {
      RechargeCellsEVO cloned = new RechargeCellsEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mRechargeCellId = this.mRechargeCellId;
      cloned.mRechargeId = this.mRechargeId;
      cloned.mCellType = this.mCellType;
      cloned.mDim0StructureId = this.mDim0StructureId;
      cloned.mDim0StructureElementId = this.mDim0StructureElementId;
      cloned.mDim1StructureId = this.mDim1StructureId;
      cloned.mDim1StructureElementId = this.mDim1StructureElementId;
      cloned.mDim2StructureId = this.mDim2StructureId;
      cloned.mDim2StructureElementId = this.mDim2StructureElementId;
      cloned.mDim3StructureId = this.mDim3StructureId;
      cloned.mDim3StructureElementId = this.mDim3StructureElementId;
      cloned.mDim4StructureId = this.mDim4StructureId;
      cloned.mDim4StructureElementId = this.mDim4StructureElementId;
      cloned.mDim5StructureId = this.mDim5StructureId;
      cloned.mDim5StructureElementId = this.mDim5StructureElementId;
      cloned.mDim6StructureId = this.mDim6StructureId;
      cloned.mDim6StructureElementId = this.mDim6StructureElementId;
      cloned.mDim7StructureId = this.mDim7StructureId;
      cloned.mDim7StructureElementId = this.mDim7StructureElementId;
      cloned.mDim8StructureId = this.mDim8StructureId;
      cloned.mDim8StructureElementId = this.mDim8StructureElementId;
      cloned.mDim9StructureId = this.mDim9StructureId;
      cloned.mDim9StructureElementId = this.mDim9StructureElementId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mDataType != null) {
         cloned.mDataType = this.mDataType;
      }

      if(this.mRatio != null) {
         cloned.mRatio = new BigDecimal(this.mRatio.toString());
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(RechargeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mRechargeCellId > 0) {
         newKey = true;
         if(parent == null) {
            this.setRechargeCellId(-this.mRechargeCellId);
         } else {
            parent.changeKey(this, -this.mRechargeCellId, this.mRechargeId);
         }
      } else if(this.mRechargeCellId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRechargeCellId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(RechargeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mRechargeCellId < 1) {
         parent.changeKey(this, startKey, this.mRechargeId);
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

   public RechargeCellsRef getEntityRef(ModelEVO evoModel, RechargeEVO evoRecharge, String entityText) {
      return new RechargeCellsRefImpl(new RechargeCellsCK(evoModel.getPK(), evoRecharge.getPK(), this.getPK()), entityText);
   }

   public RechargeCellsCK getCK(ModelEVO evoModel, RechargeEVO evoRecharge) {
      return new RechargeCellsCK(evoModel.getPK(), evoRecharge.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RechargeCellId=");
      sb.append(String.valueOf(this.mRechargeCellId));
      sb.append(' ');
      sb.append("RechargeId=");
      sb.append(String.valueOf(this.mRechargeId));
      sb.append(' ');
      sb.append("CellType=");
      sb.append(String.valueOf(this.mCellType));
      sb.append(' ');
      sb.append("Dim0StructureId=");
      sb.append(String.valueOf(this.mDim0StructureId));
      sb.append(' ');
      sb.append("Dim0StructureElementId=");
      sb.append(String.valueOf(this.mDim0StructureElementId));
      sb.append(' ');
      sb.append("Dim1StructureId=");
      sb.append(String.valueOf(this.mDim1StructureId));
      sb.append(' ');
      sb.append("Dim1StructureElementId=");
      sb.append(String.valueOf(this.mDim1StructureElementId));
      sb.append(' ');
      sb.append("Dim2StructureId=");
      sb.append(String.valueOf(this.mDim2StructureId));
      sb.append(' ');
      sb.append("Dim2StructureElementId=");
      sb.append(String.valueOf(this.mDim2StructureElementId));
      sb.append(' ');
      sb.append("Dim3StructureId=");
      sb.append(String.valueOf(this.mDim3StructureId));
      sb.append(' ');
      sb.append("Dim3StructureElementId=");
      sb.append(String.valueOf(this.mDim3StructureElementId));
      sb.append(' ');
      sb.append("Dim4StructureId=");
      sb.append(String.valueOf(this.mDim4StructureId));
      sb.append(' ');
      sb.append("Dim4StructureElementId=");
      sb.append(String.valueOf(this.mDim4StructureElementId));
      sb.append(' ');
      sb.append("Dim5StructureId=");
      sb.append(String.valueOf(this.mDim5StructureId));
      sb.append(' ');
      sb.append("Dim5StructureElementId=");
      sb.append(String.valueOf(this.mDim5StructureElementId));
      sb.append(' ');
      sb.append("Dim6StructureId=");
      sb.append(String.valueOf(this.mDim6StructureId));
      sb.append(' ');
      sb.append("Dim6StructureElementId=");
      sb.append(String.valueOf(this.mDim6StructureElementId));
      sb.append(' ');
      sb.append("Dim7StructureId=");
      sb.append(String.valueOf(this.mDim7StructureId));
      sb.append(' ');
      sb.append("Dim7StructureElementId=");
      sb.append(String.valueOf(this.mDim7StructureElementId));
      sb.append(' ');
      sb.append("Dim8StructureId=");
      sb.append(String.valueOf(this.mDim8StructureId));
      sb.append(' ');
      sb.append("Dim8StructureElementId=");
      sb.append(String.valueOf(this.mDim8StructureElementId));
      sb.append(' ');
      sb.append("Dim9StructureId=");
      sb.append(String.valueOf(this.mDim9StructureId));
      sb.append(' ');
      sb.append("Dim9StructureElementId=");
      sb.append(String.valueOf(this.mDim9StructureElementId));
      sb.append(' ');
      sb.append("DataType=");
      sb.append(String.valueOf(this.mDataType));
      sb.append(' ');
      sb.append("Ratio=");
      sb.append(String.valueOf(this.mRatio));
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

      sb.append("RechargeCells: ");
      sb.append(this.toString());
      return sb.toString();
   }

   public int getStructureId(int index) {
      switch(index) {
      case 0:
         return this.mDim0StructureId;
      case 1:
         return this.mDim1StructureId;
      case 2:
         return this.mDim2StructureId;
      case 3:
         return this.mDim3StructureId;
      case 4:
         return this.mDim4StructureId;
      case 5:
         return this.mDim5StructureId;
      case 6:
         return this.mDim6StructureId;
      case 7:
         return this.mDim7StructureId;
      case 8:
         return this.mDim8StructureId;
      case 9:
         return this.mDim9StructureId;
      default:
         throw new IllegalArgumentException("index out of range " + index);
      }
   }

   public int getStructureElementId(int index) {
      switch(index) {
      case 0:
         return this.mDim0StructureElementId;
      case 1:
         return this.mDim1StructureElementId;
      case 2:
         return this.mDim2StructureElementId;
      case 3:
         return this.mDim3StructureElementId;
      case 4:
         return this.mDim4StructureElementId;
      case 5:
         return this.mDim5StructureElementId;
      case 6:
         return this.mDim6StructureElementId;
      case 7:
         return this.mDim7StructureElementId;
      case 8:
         return this.mDim8StructureElementId;
      case 9:
         return this.mDim9StructureElementId;
      default:
         throw new IllegalArgumentException("index out of range " + index);
      }
   }
}
