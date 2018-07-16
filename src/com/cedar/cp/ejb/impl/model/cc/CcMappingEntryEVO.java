// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.model.cc.CcMappingEntryRef;
import com.cedar.cp.dto.model.cc.CcMappingEntryCK;
import com.cedar.cp.dto.model.cc.CcMappingEntryPK;
import com.cedar.cp.dto.model.cc.CcMappingEntryRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.cc.CcMappingLineEVO;
import java.io.Serializable;

public class CcMappingEntryEVO implements Serializable {

   private transient CcMappingEntryPK mPK;
   private int mCcMappingEntryId;
   private int mCcMappingLineId;
   private int mStructureId;
   private int mStructureElementId;
   private int mDimSeq;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CcMappingEntryEVO() {}

   public CcMappingEntryEVO(int newCcMappingEntryId, int newCcMappingLineId, int newStructureId, int newStructureElementId, int newDimSeq) {
      this.mCcMappingEntryId = newCcMappingEntryId;
      this.mCcMappingLineId = newCcMappingLineId;
      this.mStructureId = newStructureId;
      this.mStructureElementId = newStructureElementId;
      this.mDimSeq = newDimSeq;
   }

   public CcMappingEntryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CcMappingEntryPK(this.mCcMappingEntryId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCcMappingEntryId() {
      return this.mCcMappingEntryId;
   }

   public int getCcMappingLineId() {
      return this.mCcMappingLineId;
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

   public void setCcMappingEntryId(int newCcMappingEntryId) {
      if(this.mCcMappingEntryId != newCcMappingEntryId) {
         this.mModified = true;
         this.mCcMappingEntryId = newCcMappingEntryId;
         this.mPK = null;
      }
   }

   public void setCcMappingLineId(int newCcMappingLineId) {
      if(this.mCcMappingLineId != newCcMappingLineId) {
         this.mModified = true;
         this.mCcMappingLineId = newCcMappingLineId;
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

   public void setDetails(CcMappingEntryEVO newDetails) {
      this.setCcMappingEntryId(newDetails.getCcMappingEntryId());
      this.setCcMappingLineId(newDetails.getCcMappingLineId());
      this.setStructureId(newDetails.getStructureId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setDimSeq(newDetails.getDimSeq());
   }

   public CcMappingEntryEVO deepClone() {
      CcMappingEntryEVO cloned = new CcMappingEntryEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mCcMappingEntryId = this.mCcMappingEntryId;
      cloned.mCcMappingLineId = this.mCcMappingLineId;
      cloned.mStructureId = this.mStructureId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mDimSeq = this.mDimSeq;
      return cloned;
   }

   public void prepareForInsert(CcMappingLineEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCcMappingEntryId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCcMappingEntryId(-this.mCcMappingEntryId);
         } else {
            parent.changeKey(this, -this.mCcMappingEntryId);
         }
      } else if(this.mCcMappingEntryId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCcMappingEntryId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(CcMappingLineEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCcMappingEntryId < 1) {
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

   public CcMappingEntryRef getEntityRef(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine, CcMappingLineEVO evoCcMappingLine, String entityText) {
      return new CcMappingEntryRefImpl(new CcMappingEntryCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), evoCcMappingLine.getPK(), this.getPK()), entityText);
   }

   public CcMappingEntryCK getCK(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine, CcMappingLineEVO evoCcMappingLine) {
      return new CcMappingEntryCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), evoCcMappingLine.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CcMappingEntryId=");
      sb.append(String.valueOf(this.mCcMappingEntryId));
      sb.append(' ');
      sb.append("CcMappingLineId=");
      sb.append(String.valueOf(this.mCcMappingLineId));
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

      sb.append("CcMappingEntry: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
