// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.tran;

import com.cedar.cp.api.report.tran.CubePendingTranRef;
import com.cedar.cp.dto.report.tran.CubePendingTranCK;
import com.cedar.cp.dto.report.tran.CubePendingTranPK;
import com.cedar.cp.dto.report.tran.CubePendingTranRefImpl;
import com.cedar.cp.ejb.impl.report.ReportEVO;
import java.io.Serializable;

public class CubePendingTranEVO implements Serializable {

   private transient CubePendingTranPK mPK;
   private int mReportId;
   private int mFinanceCubeId;
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
   private long mValue;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CubePendingTranEVO() {}

   public CubePendingTranEVO(int newReportId, int newFinanceCubeId, int newRowNo, int newDim0, int newDim1, int newDim2, int newDim3, int newDim4, int newDim5, int newDim6, int newDim7, int newDim8, int newDim9, String newDataType, long newValue) {
      this.mReportId = newReportId;
      this.mFinanceCubeId = newFinanceCubeId;
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
      this.mValue = newValue;
   }

   public CubePendingTranPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CubePendingTranPK(this.mReportId, this.mFinanceCubeId, this.mRowNo);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportId() {
      return this.mReportId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getRowNo() {
      return this.mRowNo;
   }

   public int[] getDimArray() {
      return new int[]{this.getDim0(), this.getDim1(), this.getDim2(), this.getDim3(), this.getDim4(), this.getDim5(), this.getDim6(), this.getDim7(), this.getDim8(), this.getDim9()};
   }

   public void setDimArray(int[] p) {
      this.setDim0(p[0]);
      this.setDim1(p[1]);
      this.setDim2(p[2]);
      this.setDim3(p[3]);
      this.setDim4(p[4]);
      this.setDim5(p[5]);
      this.setDim6(p[6]);
      this.setDim7(p[7]);
      this.setDim8(p[8]);
      this.setDim9(p[9]);
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

   public long getValue() {
      return this.mValue;
   }

   public void setReportId(int newReportId) {
      if(this.mReportId != newReportId) {
         this.mModified = true;
         this.mReportId = newReportId;
         this.mPK = null;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
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

   public void setValue(long newValue) {
      if(this.mValue != newValue) {
         this.mModified = true;
         this.mValue = newValue;
      }
   }

   public void setDataType(String newDataType) {
      if(this.mDataType != null && newDataType == null || this.mDataType == null && newDataType != null || this.mDataType != null && newDataType != null && !this.mDataType.equals(newDataType)) {
         this.mDataType = newDataType;
         this.mModified = true;
      }

   }

   public void setDetails(CubePendingTranEVO newDetails) {
      this.setReportId(newDetails.getReportId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
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
      this.setValue(newDetails.getValue());
   }

   public CubePendingTranEVO deepClone() {
      CubePendingTranEVO cloned = new CubePendingTranEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mReportId = this.mReportId;
      cloned.mFinanceCubeId = this.mFinanceCubeId;
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
      cloned.mValue = this.mValue;
      if(this.mDataType != null) {
         cloned.mDataType = this.mDataType;
      }

      return cloned;
   }

   public void prepareForInsert(ReportEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ReportEVO parent, int startKey) {
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

   public CubePendingTranRef getEntityRef(ReportEVO evoReport, String entityText) {
      return new CubePendingTranRefImpl(new CubePendingTranCK(evoReport.getPK(), this.getPK()), entityText);
   }

   public CubePendingTranCK getCK(ReportEVO evoReport) {
      return new CubePendingTranCK(evoReport.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportId=");
      sb.append(String.valueOf(this.mReportId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
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
      sb.append("Value=");
      sb.append(String.valueOf(this.mValue));
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

      sb.append("CubePendingTran: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
