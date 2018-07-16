// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.impexp;

import com.cedar.cp.api.impexp.ImpExpRowRef;
import com.cedar.cp.dto.impexp.ImpExpRowCK;
import com.cedar.cp.dto.impexp.ImpExpRowPK;
import com.cedar.cp.dto.impexp.ImpExpRowRefImpl;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ImpExpRowEVO implements Serializable {

   private transient ImpExpRowPK mPK;
   private int mBatchId;
   private int mRowNo;
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
   private long mNumberValue;
   private String mStringValue;
   private Timestamp mDateValue;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ImpExpRowEVO() {}

   public ImpExpRowEVO(int newBatchId, int newRowNo, int newDim0, int newDim1, int newDim2, int newDim3, int newDim4, int newDim5, int newDim6, int newDim7, int newDim8, int newDim9, String newDataType, long newNumberValue, String newStringValue, Timestamp newDateValue) {
      this.mBatchId = newBatchId;
      this.mRowNo = newRowNo;
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
      this.mNumberValue = newNumberValue;
      this.mStringValue = newStringValue;
      this.mDateValue = newDateValue;
   }

   public ImpExpRowPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ImpExpRowPK(this.mBatchId, this.mRowNo);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBatchId() {
      return this.mBatchId;
   }

   public int getRowNo() {
      return this.mRowNo;
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

   public long getNumberValue() {
      return this.mNumberValue;
   }

   public String getStringValue() {
      return this.mStringValue;
   }

   public Timestamp getDateValue() {
      return this.mDateValue;
   }

   public void setBatchId(int newBatchId) {
      if(this.mBatchId != newBatchId) {
         this.mModified = true;
         this.mBatchId = newBatchId;
         this.mPK = null;
      }
   }

   public void setRowNo(int newRowNo) {
      if(this.mRowNo != newRowNo) {
         this.mModified = true;
         this.mRowNo = newRowNo;
         this.mPK = null;
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

   public void setNumberValue(long newNumberValue) {
      if(this.mNumberValue != newNumberValue) {
         this.mModified = true;
         this.mNumberValue = newNumberValue;
      }
   }

   public void setDataType(String newDataType) {
      if(this.mDataType != null && newDataType == null || this.mDataType == null && newDataType != null || this.mDataType != null && newDataType != null && !this.mDataType.equals(newDataType)) {
         this.mDataType = newDataType;
         this.mModified = true;
      }

   }

   public void setStringValue(String newStringValue) {
      if(this.mStringValue != null && newStringValue == null || this.mStringValue == null && newStringValue != null || this.mStringValue != null && newStringValue != null && !this.mStringValue.equals(newStringValue)) {
         this.mStringValue = newStringValue;
         this.mModified = true;
      }

   }

   public void setDateValue(Timestamp newDateValue) {
      if(this.mDateValue != null && newDateValue == null || this.mDateValue == null && newDateValue != null || this.mDateValue != null && newDateValue != null && !this.mDateValue.equals(newDateValue)) {
         this.mDateValue = newDateValue;
         this.mModified = true;
      }

   }

   public void setDetails(ImpExpRowEVO newDetails) {
      this.setBatchId(newDetails.getBatchId());
      this.setRowNo(newDetails.getRowNo());
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
      this.setNumberValue(newDetails.getNumberValue());
      this.setStringValue(newDetails.getStringValue());
      this.setDateValue(newDetails.getDateValue());
   }

   public ImpExpRowEVO deepClone() {
      ImpExpRowEVO cloned = new ImpExpRowEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mBatchId = this.mBatchId;
      cloned.mRowNo = this.mRowNo;
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
      cloned.mNumberValue = this.mNumberValue;
      if(this.mDataType != null) {
         cloned.mDataType = this.mDataType;
      }

      if(this.mStringValue != null) {
         cloned.mStringValue = this.mStringValue;
      }

      if(this.mDateValue != null) {
         cloned.mDateValue = Timestamp.valueOf(this.mDateValue.toString());
      }

      return cloned;
   }

   public void prepareForInsert(ImpExpHdrEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ImpExpHdrEVO parent, int startKey) {
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

   public ImpExpRowRef getEntityRef(ImpExpHdrEVO evoImpExpHdr, String entityText) {
      return new ImpExpRowRefImpl(new ImpExpRowCK(evoImpExpHdr.getPK(), this.getPK()), entityText);
   }

   public ImpExpRowCK getCK(ImpExpHdrEVO evoImpExpHdr) {
      return new ImpExpRowCK(evoImpExpHdr.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BatchId=");
      sb.append(String.valueOf(this.mBatchId));
      sb.append(' ');
      sb.append("RowNo=");
      sb.append(String.valueOf(this.mRowNo));
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
      sb.append("NumberValue=");
      sb.append(String.valueOf(this.mNumberValue));
      sb.append(' ');
      sb.append("StringValue=");
      sb.append(String.valueOf(this.mStringValue));
      sb.append(' ');
      sb.append("DateValue=");
      sb.append(String.valueOf(this.mDateValue));
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

      sb.append("ImpExpRow: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
