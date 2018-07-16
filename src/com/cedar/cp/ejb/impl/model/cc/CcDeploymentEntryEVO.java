// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentEntryRef;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryCK;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryPK;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineEVO;
import java.io.Serializable;

public class CcDeploymentEntryEVO implements Serializable {

   private transient CcDeploymentEntryPK mPK;
   private int mCcDeploymentEntryId;
   private int mCcDeploymentLineId;
   private int mStructureId;
   private int mStructureElementId;
   private int mDimSeq;
   private boolean mSelectedInd;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CcDeploymentEntryEVO() {}

   public CcDeploymentEntryEVO(int newCcDeploymentEntryId, int newCcDeploymentLineId, int newStructureId, int newStructureElementId, int newDimSeq, boolean newSelectedInd) {
      this.mCcDeploymentEntryId = newCcDeploymentEntryId;
      this.mCcDeploymentLineId = newCcDeploymentLineId;
      this.mStructureId = newStructureId;
      this.mStructureElementId = newStructureElementId;
      this.mDimSeq = newDimSeq;
      this.mSelectedInd = newSelectedInd;
   }

   public CcDeploymentEntryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CcDeploymentEntryPK(this.mCcDeploymentEntryId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCcDeploymentEntryId() {
      return this.mCcDeploymentEntryId;
   }

   public int getCcDeploymentLineId() {
      return this.mCcDeploymentLineId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getDimSeq() {
      return this.mDimSeq;
   }

   public boolean getSelectedInd() {
      return this.mSelectedInd;
   }

   public void setCcDeploymentEntryId(int newCcDeploymentEntryId) {
      if(this.mCcDeploymentEntryId != newCcDeploymentEntryId) {
         this.mModified = true;
         this.mCcDeploymentEntryId = newCcDeploymentEntryId;
         this.mPK = null;
      }
   }

   public void setCcDeploymentLineId(int newCcDeploymentLineId) {
      if(this.mCcDeploymentLineId != newCcDeploymentLineId) {
         this.mModified = true;
         this.mCcDeploymentLineId = newCcDeploymentLineId;
      }
   }

   public void setStructureId(int newStructureId) {
      if(this.mStructureId != newStructureId) {
         this.mModified = true;
         this.mStructureId = newStructureId;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
      }
   }

   public void setDimSeq(int newDimSeq) {
      if(this.mDimSeq != newDimSeq) {
         this.mModified = true;
         this.mDimSeq = newDimSeq;
      }
   }

   public void setSelectedInd(boolean newSelectedInd) {
      if(this.mSelectedInd != newSelectedInd) {
         this.mModified = true;
         this.mSelectedInd = newSelectedInd;
      }
   }

   public void setDetails(CcDeploymentEntryEVO newDetails) {
      this.setCcDeploymentEntryId(newDetails.getCcDeploymentEntryId());
      this.setCcDeploymentLineId(newDetails.getCcDeploymentLineId());
      this.setStructureId(newDetails.getStructureId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setDimSeq(newDetails.getDimSeq());
      this.setSelectedInd(newDetails.getSelectedInd());
   }

   public CcDeploymentEntryEVO deepClone() {
      CcDeploymentEntryEVO cloned = new CcDeploymentEntryEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mCcDeploymentEntryId = this.mCcDeploymentEntryId;
      cloned.mCcDeploymentLineId = this.mCcDeploymentLineId;
      cloned.mStructureId = this.mStructureId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mDimSeq = this.mDimSeq;
      cloned.mSelectedInd = this.mSelectedInd;
      return cloned;
   }

   public void prepareForInsert(CcDeploymentLineEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCcDeploymentEntryId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCcDeploymentEntryId(-this.mCcDeploymentEntryId);
         } else {
            parent.changeKey(this, -this.mCcDeploymentEntryId);
         }
      } else if(this.mCcDeploymentEntryId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCcDeploymentEntryId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(CcDeploymentLineEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCcDeploymentEntryId < 1) {
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

   public CcDeploymentEntryRef getEntityRef(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine, String entityText) {
      return new CcDeploymentEntryRefImpl(new CcDeploymentEntryCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), this.getPK()), entityText);
   }

   public CcDeploymentEntryCK getCK(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine) {
      return new CcDeploymentEntryCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CcDeploymentEntryId=");
      sb.append(String.valueOf(this.mCcDeploymentEntryId));
      sb.append(' ');
      sb.append("CcDeploymentLineId=");
      sb.append(String.valueOf(this.mCcDeploymentLineId));
      sb.append(' ');
      sb.append("StructureId=");
      sb.append(String.valueOf(this.mStructureId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("DimSeq=");
      sb.append(String.valueOf(this.mDimSeq));
      sb.append(' ');
      sb.append("SelectedInd=");
      sb.append(String.valueOf(this.mSelectedInd));
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

      sb.append("CcDeploymentEntry: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
