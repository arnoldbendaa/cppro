// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentEntryRef;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryRefImpl;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaEVO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;

public class FormulaDeploymentEntryEVO implements Serializable {

   private transient FormulaDeploymentEntryPK mPK;
   private int mFormulaDeploymentEntryId;
   private int mFormulaDeploymentLineId;
   private int mDimSeq;
   private int mStructureId;
   private int mStartSeId;
   private boolean mSelectedInd;
   private Integer mEndSeId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public FormulaDeploymentEntryEVO() {}

   public FormulaDeploymentEntryEVO(int newFormulaDeploymentEntryId, int newFormulaDeploymentLineId, int newDimSeq, int newStructureId, int newStartSeId, boolean newSelectedInd, Integer newEndSeId) {
      this.mFormulaDeploymentEntryId = newFormulaDeploymentEntryId;
      this.mFormulaDeploymentLineId = newFormulaDeploymentLineId;
      this.mDimSeq = newDimSeq;
      this.mStructureId = newStructureId;
      this.mStartSeId = newStartSeId;
      this.mSelectedInd = newSelectedInd;
      this.mEndSeId = newEndSeId;
   }

   public FormulaDeploymentEntryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new FormulaDeploymentEntryPK(this.mFormulaDeploymentEntryId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getFormulaDeploymentEntryId() {
      return this.mFormulaDeploymentEntryId;
   }

   public int getFormulaDeploymentLineId() {
      return this.mFormulaDeploymentLineId;
   }

   public int getDimSeq() {
      return this.mDimSeq;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStartSeId() {
      return this.mStartSeId;
   }

   public boolean getSelectedInd() {
      return this.mSelectedInd;
   }

   public Integer getEndSeId() {
      return this.mEndSeId;
   }

   public void setFormulaDeploymentEntryId(int newFormulaDeploymentEntryId) {
      if(this.mFormulaDeploymentEntryId != newFormulaDeploymentEntryId) {
         this.mModified = true;
         this.mFormulaDeploymentEntryId = newFormulaDeploymentEntryId;
         this.mPK = null;
      }
   }

   public void setFormulaDeploymentLineId(int newFormulaDeploymentLineId) {
      if(this.mFormulaDeploymentLineId != newFormulaDeploymentLineId) {
         this.mModified = true;
         this.mFormulaDeploymentLineId = newFormulaDeploymentLineId;
      }
   }

   public void setDimSeq(int newDimSeq) {
      if(this.mDimSeq != newDimSeq) {
         this.mModified = true;
         this.mDimSeq = newDimSeq;
      }
   }

   public void setStructureId(int newStructureId) {
      if(this.mStructureId != newStructureId) {
         this.mModified = true;
         this.mStructureId = newStructureId;
      }
   }

   public void setStartSeId(int newStartSeId) {
      if(this.mStartSeId != newStartSeId) {
         this.mModified = true;
         this.mStartSeId = newStartSeId;
      }
   }

   public void setSelectedInd(boolean newSelectedInd) {
      if(this.mSelectedInd != newSelectedInd) {
         this.mModified = true;
         this.mSelectedInd = newSelectedInd;
      }
   }

   public void setEndSeId(Integer newEndSeId) {
      if(this.mEndSeId != null && newEndSeId == null || this.mEndSeId == null && newEndSeId != null || this.mEndSeId != null && newEndSeId != null && !this.mEndSeId.equals(newEndSeId)) {
         this.mEndSeId = newEndSeId;
         this.mModified = true;
      }

   }

   public void setDetails(FormulaDeploymentEntryEVO newDetails) {
      this.setFormulaDeploymentEntryId(newDetails.getFormulaDeploymentEntryId());
      this.setFormulaDeploymentLineId(newDetails.getFormulaDeploymentLineId());
      this.setDimSeq(newDetails.getDimSeq());
      this.setStructureId(newDetails.getStructureId());
      this.setStartSeId(newDetails.getStartSeId());
      this.setSelectedInd(newDetails.getSelectedInd());
      this.setEndSeId(newDetails.getEndSeId());
   }

   public FormulaDeploymentEntryEVO deepClone() {
      FormulaDeploymentEntryEVO cloned = new FormulaDeploymentEntryEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mFormulaDeploymentEntryId = this.mFormulaDeploymentEntryId;
      cloned.mFormulaDeploymentLineId = this.mFormulaDeploymentLineId;
      cloned.mDimSeq = this.mDimSeq;
      cloned.mStructureId = this.mStructureId;
      cloned.mStartSeId = this.mStartSeId;
      cloned.mSelectedInd = this.mSelectedInd;
      if(this.mEndSeId != null) {
         cloned.mEndSeId = Integer.valueOf(this.mEndSeId.toString());
      }

      return cloned;
   }

   public void prepareForInsert(FormulaDeploymentLineEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mFormulaDeploymentEntryId > 0) {
         newKey = true;
         if(parent == null) {
            this.setFormulaDeploymentEntryId(-this.mFormulaDeploymentEntryId);
         } else {
            parent.changeKey(this, -this.mFormulaDeploymentEntryId);
         }
      } else if(this.mFormulaDeploymentEntryId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mFormulaDeploymentEntryId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(FormulaDeploymentLineEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mFormulaDeploymentEntryId < 1) {
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

   public FormulaDeploymentEntryRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, CubeFormulaEVO evoCubeFormula, FormulaDeploymentLineEVO evoFormulaDeploymentLine, String entityText) {
      return new FormulaDeploymentEntryRefImpl(new FormulaDeploymentEntryCK(evoModel.getPK(), evoFinanceCube.getPK(), evoCubeFormula.getPK(), evoFormulaDeploymentLine.getPK(), this.getPK()), entityText);
   }

   public FormulaDeploymentEntryCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, CubeFormulaEVO evoCubeFormula, FormulaDeploymentLineEVO evoFormulaDeploymentLine) {
      return new FormulaDeploymentEntryCK(evoModel.getPK(), evoFinanceCube.getPK(), evoCubeFormula.getPK(), evoFormulaDeploymentLine.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("FormulaDeploymentEntryId=");
      sb.append(String.valueOf(this.mFormulaDeploymentEntryId));
      sb.append(' ');
      sb.append("FormulaDeploymentLineId=");
      sb.append(String.valueOf(this.mFormulaDeploymentLineId));
      sb.append(' ');
      sb.append("DimSeq=");
      sb.append(String.valueOf(this.mDimSeq));
      sb.append(' ');
      sb.append("StructureId=");
      sb.append(String.valueOf(this.mStructureId));
      sb.append(' ');
      sb.append("StartSeId=");
      sb.append(String.valueOf(this.mStartSeId));
      sb.append(' ');
      sb.append("SelectedInd=");
      sb.append(String.valueOf(this.mSelectedInd));
      sb.append(' ');
      sb.append("EndSeId=");
      sb.append(String.valueOf(this.mEndSeId));
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

      sb.append("FormulaDeploymentEntry: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
