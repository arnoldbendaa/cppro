// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormulaPackageRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackageRefImpl;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;

public class CubeFormulaPackageEVO implements Serializable {

   private transient CubeFormulaPackagePK mPK;
   private int mCubeFormulaPackageId;
   private int mFinanceCubeId;
   private int mPackageGroupIndex;
   private int mLineCount;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CubeFormulaPackageEVO() {}

   public CubeFormulaPackageEVO(int newCubeFormulaPackageId, int newFinanceCubeId, int newPackageGroupIndex, int newLineCount) {
      this.mCubeFormulaPackageId = newCubeFormulaPackageId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mPackageGroupIndex = newPackageGroupIndex;
      this.mLineCount = newLineCount;
   }

   public CubeFormulaPackagePK getPK() {
      if(this.mPK == null) {
         this.mPK = new CubeFormulaPackagePK(this.mCubeFormulaPackageId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCubeFormulaPackageId() {
      return this.mCubeFormulaPackageId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getPackageGroupIndex() {
      return this.mPackageGroupIndex;
   }

   public int getLineCount() {
      return this.mLineCount;
   }

   public void setCubeFormulaPackageId(int newCubeFormulaPackageId) {
      if(this.mCubeFormulaPackageId != newCubeFormulaPackageId) {
         this.mModified = true;
         this.mCubeFormulaPackageId = newCubeFormulaPackageId;
         this.mPK = null;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setPackageGroupIndex(int newPackageGroupIndex) {
      if(this.mPackageGroupIndex != newPackageGroupIndex) {
         this.mModified = true;
         this.mPackageGroupIndex = newPackageGroupIndex;
      }
   }

   public void setLineCount(int newLineCount) {
      if(this.mLineCount != newLineCount) {
         this.mModified = true;
         this.mLineCount = newLineCount;
      }
   }

   public void setDetails(CubeFormulaPackageEVO newDetails) {
      this.setCubeFormulaPackageId(newDetails.getCubeFormulaPackageId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setPackageGroupIndex(newDetails.getPackageGroupIndex());
      this.setLineCount(newDetails.getLineCount());
   }

   public CubeFormulaPackageEVO deepClone() {
      CubeFormulaPackageEVO cloned = new CubeFormulaPackageEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mCubeFormulaPackageId = this.mCubeFormulaPackageId;
      cloned.mFinanceCubeId = this.mFinanceCubeId;
      cloned.mPackageGroupIndex = this.mPackageGroupIndex;
      cloned.mLineCount = this.mLineCount;
      return cloned;
   }

   public void prepareForInsert(FinanceCubeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCubeFormulaPackageId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCubeFormulaPackageId(-this.mCubeFormulaPackageId);
         } else {
            parent.changeKey(this, -this.mCubeFormulaPackageId);
         }
      } else if(this.mCubeFormulaPackageId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCubeFormulaPackageId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(FinanceCubeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCubeFormulaPackageId < 1) {
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

   public CubeFormulaPackageRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, String entityText) {
      return new CubeFormulaPackageRefImpl(new CubeFormulaPackageCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK()), entityText);
   }

   public CubeFormulaPackageCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube) {
      return new CubeFormulaPackageCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CubeFormulaPackageId=");
      sb.append(String.valueOf(this.mCubeFormulaPackageId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("PackageGroupIndex=");
      sb.append(String.valueOf(this.mPackageGroupIndex));
      sb.append(' ');
      sb.append("LineCount=");
      sb.append(String.valueOf(this.mLineCount));
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

      sb.append("CubeFormulaPackage: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
