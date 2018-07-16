// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:36
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.budgetlimit;

import com.cedar.cp.api.model.budgetlimit.BudgetLimitRef;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitRefImpl;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class BudgetLimitEVO implements Serializable {

   private transient BudgetLimitPK mPK;
   private int mBudgetLimitId;
   private int mFinanceCubeId;
   private int mBudgetLocationElementId;
   private int mDim0;
   private int mDim1;
   private int mDim2;
   private int mDim3;
   private int mDim4;
   private int mDim5;
   private int mDim6;
   private int mDim7;
   private int mDim8;
   private int mDim9;
   private String mDataType;
   private Long mMinValue;
   private Long mMaxValue;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public BudgetLimitEVO() {}

   public BudgetLimitEVO(int newBudgetLimitId, int newFinanceCubeId, int newBudgetLocationElementId, int newDim0, int newDim1, int newDim2, int newDim3, int newDim4, int newDim5, int newDim6, int newDim7, int newDim8, int newDim9, String newDataType, Long newMinValue, Long newMaxValue, int newVersionNum) {
      this.mBudgetLimitId = newBudgetLimitId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mBudgetLocationElementId = newBudgetLocationElementId;
      this.mDim0 = newDim0;
      this.mDim1 = newDim1;
      this.mDim2 = newDim2;
      this.mDim3 = newDim3;
      this.mDim4 = newDim4;
      this.mDim5 = newDim5;
      this.mDim6 = newDim6;
      this.mDim7 = newDim7;
      this.mDim8 = newDim8;
      this.mDim9 = newDim9;
      this.mDataType = newDataType;
      this.mMinValue = newMinValue;
      this.mMaxValue = newMaxValue;
      this.mVersionNum = newVersionNum;
   }

   public BudgetLimitPK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetLimitPK(this.mBudgetLimitId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBudgetLimitId() {
      return this.mBudgetLimitId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getBudgetLocationElementId() {
      return this.mBudgetLocationElementId;
   }

   public int getDim0() {
      return this.mDim0;
   }

   public int getDim1() {
      return this.mDim1;
   }

   public int getDim2() {
      return this.mDim2;
   }

   public int getDim3() {
      return this.mDim3;
   }

   public int getDim4() {
      return this.mDim4;
   }

   public int getDim5() {
      return this.mDim5;
   }

   public int getDim6() {
      return this.mDim6;
   }

   public int getDim7() {
      return this.mDim7;
   }

   public int getDim8() {
      return this.mDim8;
   }

   public int getDim9() {
      return this.mDim9;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public Long getMinValue() {
      return this.mMinValue;
   }

   public Long getMaxValue() {
      return this.mMaxValue;
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

   public void setBudgetLimitId(int newBudgetLimitId) {
      if(this.mBudgetLimitId != newBudgetLimitId) {
         this.mModified = true;
         this.mBudgetLimitId = newBudgetLimitId;
         this.mPK = null;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setBudgetLocationElementId(int newBudgetLocationElementId) {
      if(this.mBudgetLocationElementId != newBudgetLocationElementId) {
         this.mModified = true;
         this.mBudgetLocationElementId = newBudgetLocationElementId;
      }
   }

   public void setDim0(int newDim0) {
      if(this.mDim0 != newDim0) {
         this.mModified = true;
         this.mDim0 = newDim0;
      }
   }

   public void setDim1(int newDim1) {
      if(this.mDim1 != newDim1) {
         this.mModified = true;
         this.mDim1 = newDim1;
      }
   }

   public void setDim2(int newDim2) {
      if(this.mDim2 != newDim2) {
         this.mModified = true;
         this.mDim2 = newDim2;
      }
   }

   public void setDim3(int newDim3) {
      if(this.mDim3 != newDim3) {
         this.mModified = true;
         this.mDim3 = newDim3;
      }
   }

   public void setDim4(int newDim4) {
      if(this.mDim4 != newDim4) {
         this.mModified = true;
         this.mDim4 = newDim4;
      }
   }

   public void setDim5(int newDim5) {
      if(this.mDim5 != newDim5) {
         this.mModified = true;
         this.mDim5 = newDim5;
      }
   }

   public void setDim6(int newDim6) {
      if(this.mDim6 != newDim6) {
         this.mModified = true;
         this.mDim6 = newDim6;
      }
   }

   public void setDim7(int newDim7) {
      if(this.mDim7 != newDim7) {
         this.mModified = true;
         this.mDim7 = newDim7;
      }
   }

   public void setDim8(int newDim8) {
      if(this.mDim8 != newDim8) {
         this.mModified = true;
         this.mDim8 = newDim8;
      }
   }

   public void setDim9(int newDim9) {
      if(this.mDim9 != newDim9) {
         this.mModified = true;
         this.mDim9 = newDim9;
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

   public void setDataType(String newDataType) {
      if(this.mDataType != null && newDataType == null || this.mDataType == null && newDataType != null || this.mDataType != null && newDataType != null && !this.mDataType.equals(newDataType)) {
         this.mDataType = newDataType;
         this.mModified = true;
      }

   }

   public void setMinValue(Long newMinValue) {
      if(this.mMinValue != null && newMinValue == null || this.mMinValue == null && newMinValue != null || this.mMinValue != null && newMinValue != null && !this.mMinValue.equals(newMinValue)) {
         this.mMinValue = newMinValue;
         this.mModified = true;
      }

   }

   public void setMaxValue(Long newMaxValue) {
      if(this.mMaxValue != null && newMaxValue == null || this.mMaxValue == null && newMaxValue != null || this.mMaxValue != null && newMaxValue != null && !this.mMaxValue.equals(newMaxValue)) {
         this.mMaxValue = newMaxValue;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(BudgetLimitEVO newDetails) {
      this.setBudgetLimitId(newDetails.getBudgetLimitId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setBudgetLocationElementId(newDetails.getBudgetLocationElementId());
      this.setDim0(newDetails.getDim0());
      this.setDim1(newDetails.getDim1());
      this.setDim2(newDetails.getDim2());
      this.setDim3(newDetails.getDim3());
      this.setDim4(newDetails.getDim4());
      this.setDim5(newDetails.getDim5());
      this.setDim6(newDetails.getDim6());
      this.setDim7(newDetails.getDim7());
      this.setDim8(newDetails.getDim8());
      this.setDim9(newDetails.getDim9());
      this.setDataType(newDetails.getDataType());
      this.setMinValue(newDetails.getMinValue());
      this.setMaxValue(newDetails.getMaxValue());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public BudgetLimitEVO deepClone() {
      BudgetLimitEVO cloned = new BudgetLimitEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mBudgetLimitId = this.mBudgetLimitId;
      cloned.mFinanceCubeId = this.mFinanceCubeId;
      cloned.mBudgetLocationElementId = this.mBudgetLocationElementId;
      cloned.mDim0 = this.mDim0;
      cloned.mDim1 = this.mDim1;
      cloned.mDim2 = this.mDim2;
      cloned.mDim3 = this.mDim3;
      cloned.mDim4 = this.mDim4;
      cloned.mDim5 = this.mDim5;
      cloned.mDim6 = this.mDim6;
      cloned.mDim7 = this.mDim7;
      cloned.mDim8 = this.mDim8;
      cloned.mDim9 = this.mDim9;
      cloned.mVersionNum = this.mVersionNum;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mDataType != null) {
         cloned.mDataType = this.mDataType;
      }

      if(this.mMinValue != null) {
         cloned.mMinValue = Long.valueOf(this.mMinValue.toString());
      }

      if(this.mMaxValue != null) {
         cloned.mMaxValue = Long.valueOf(this.mMaxValue.toString());
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(FinanceCubeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mBudgetLimitId > 0) {
         newKey = true;
         if(parent == null) {
            this.setBudgetLimitId(-this.mBudgetLimitId);
         } else {
            parent.changeKey(this, -this.mBudgetLimitId);
         }
      } else if(this.mBudgetLimitId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mBudgetLimitId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(FinanceCubeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mBudgetLimitId < 1) {
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

   public BudgetLimitRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, String entityText) {
      return new BudgetLimitRefImpl(new BudgetLimitCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK()), entityText);
   }

   public BudgetLimitCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube) {
      return new BudgetLimitCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BudgetLimitId=");
      sb.append(String.valueOf(this.mBudgetLimitId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("BudgetLocationElementId=");
      sb.append(String.valueOf(this.mBudgetLocationElementId));
      sb.append(' ');
      sb.append("Dim0=");
      sb.append(String.valueOf(this.mDim0));
      sb.append(' ');
      sb.append("Dim1=");
      sb.append(String.valueOf(this.mDim1));
      sb.append(' ');
      sb.append("Dim2=");
      sb.append(String.valueOf(this.mDim2));
      sb.append(' ');
      sb.append("Dim3=");
      sb.append(String.valueOf(this.mDim3));
      sb.append(' ');
      sb.append("Dim4=");
      sb.append(String.valueOf(this.mDim4));
      sb.append(' ');
      sb.append("Dim5=");
      sb.append(String.valueOf(this.mDim5));
      sb.append(' ');
      sb.append("Dim6=");
      sb.append(String.valueOf(this.mDim6));
      sb.append(' ');
      sb.append("Dim7=");
      sb.append(String.valueOf(this.mDim7));
      sb.append(' ');
      sb.append("Dim8=");
      sb.append(String.valueOf(this.mDim8));
      sb.append(' ');
      sb.append("Dim9=");
      sb.append(String.valueOf(this.mDim9));
      sb.append(' ');
      sb.append("DataType=");
      sb.append(String.valueOf(this.mDataType));
      sb.append(' ');
      sb.append("MinValue=");
      sb.append(String.valueOf(this.mMinValue));
      sb.append(' ');
      sb.append("MaxValue=");
      sb.append(String.valueOf(this.mMaxValue));
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

      sb.append("BudgetLimit: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
