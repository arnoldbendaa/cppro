// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentDtRef;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtRefImpl;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaEVO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;

public class FormulaDeploymentDtEVO implements Serializable {

   private transient FormulaDeploymentDtPK mPK;
   private int mFormulaDeploymentDtId;
   private int mFormulaDeploymentLineId;
   private int mDataTypeId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public FormulaDeploymentDtEVO() {}

   public FormulaDeploymentDtEVO(int newFormulaDeploymentDtId, int newFormulaDeploymentLineId, int newDataTypeId) {
      this.mFormulaDeploymentDtId = newFormulaDeploymentDtId;
      this.mFormulaDeploymentLineId = newFormulaDeploymentLineId;
      this.mDataTypeId = newDataTypeId;
   }

   public FormulaDeploymentDtPK getPK() {
      if(this.mPK == null) {
         this.mPK = new FormulaDeploymentDtPK(this.mFormulaDeploymentDtId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getFormulaDeploymentDtId() {
      return this.mFormulaDeploymentDtId;
   }

   public int getFormulaDeploymentLineId() {
      return this.mFormulaDeploymentLineId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public void setFormulaDeploymentDtId(int newFormulaDeploymentDtId) {
      if(this.mFormulaDeploymentDtId != newFormulaDeploymentDtId) {
         this.mModified = true;
         this.mFormulaDeploymentDtId = newFormulaDeploymentDtId;
         this.mPK = null;
      }
   }

   public void setFormulaDeploymentLineId(int newFormulaDeploymentLineId) {
      if(this.mFormulaDeploymentLineId != newFormulaDeploymentLineId) {
         this.mModified = true;
         this.mFormulaDeploymentLineId = newFormulaDeploymentLineId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setDetails(FormulaDeploymentDtEVO newDetails) {
      this.setFormulaDeploymentDtId(newDetails.getFormulaDeploymentDtId());
      this.setFormulaDeploymentLineId(newDetails.getFormulaDeploymentLineId());
      this.setDataTypeId(newDetails.getDataTypeId());
   }

   public FormulaDeploymentDtEVO deepClone() {
      FormulaDeploymentDtEVO cloned = new FormulaDeploymentDtEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mFormulaDeploymentDtId = this.mFormulaDeploymentDtId;
      cloned.mFormulaDeploymentLineId = this.mFormulaDeploymentLineId;
      cloned.mDataTypeId = this.mDataTypeId;
      return cloned;
   }

   public void prepareForInsert(FormulaDeploymentLineEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mFormulaDeploymentDtId > 0) {
         newKey = true;
         if(parent == null) {
            this.setFormulaDeploymentDtId(-this.mFormulaDeploymentDtId);
         } else {
            parent.changeKey(this, -this.mFormulaDeploymentDtId);
         }
      } else if(this.mFormulaDeploymentDtId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mFormulaDeploymentDtId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(FormulaDeploymentLineEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mFormulaDeploymentDtId < 1) {
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

   public FormulaDeploymentDtRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, CubeFormulaEVO evoCubeFormula, FormulaDeploymentLineEVO evoFormulaDeploymentLine, String entityText) {
      return new FormulaDeploymentDtRefImpl(new FormulaDeploymentDtCK(evoModel.getPK(), evoFinanceCube.getPK(), evoCubeFormula.getPK(), evoFormulaDeploymentLine.getPK(), this.getPK()), entityText);
   }

   public FormulaDeploymentDtCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, CubeFormulaEVO evoCubeFormula, FormulaDeploymentLineEVO evoFormulaDeploymentLine) {
      return new FormulaDeploymentDtCK(evoModel.getPK(), evoFinanceCube.getPK(), evoCubeFormula.getPK(), evoFormulaDeploymentLine.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("FormulaDeploymentDtId=");
      sb.append(String.valueOf(this.mFormulaDeploymentDtId));
      sb.append(' ');
      sb.append("FormulaDeploymentLineId=");
      sb.append(String.valueOf(this.mFormulaDeploymentLineId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
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

      sb.append("FormulaDeploymentDt: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
