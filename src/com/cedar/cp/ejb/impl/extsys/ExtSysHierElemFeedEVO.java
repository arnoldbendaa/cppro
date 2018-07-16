// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysHierElemFeedRef;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedCK;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElementEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierarchyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import java.io.Serializable;

public class ExtSysHierElemFeedEVO implements Serializable {

   private transient ExtSysHierElemFeedPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mDimensionVisId;
   private String mHierarchyVisId;
   private String mHierElementVisId;
   private String mDimElementVisId;
   private int mIdx;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysHierElemFeedEVO() {}

   public ExtSysHierElemFeedEVO(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierarchyVisId, String newHierElementVisId, String newDimElementVisId, int newIdx) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mDimensionVisId = newDimensionVisId;
      this.mHierarchyVisId = newHierarchyVisId;
      this.mHierElementVisId = newHierElementVisId;
      this.mDimElementVisId = newDimElementVisId;
      this.mIdx = newIdx;
   }

   public ExtSysHierElemFeedPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysHierElemFeedPK(this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, this.mHierElementVisId, this.mDimElementVisId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }

   public String getCompanyVisId() {
      return this.mCompanyVisId;
   }

   public String getLedgerVisId() {
      return this.mLedgerVisId;
   }

   public String getDimensionVisId() {
      return this.mDimensionVisId;
   }

   public String getHierarchyVisId() {
      return this.mHierarchyVisId;
   }

   public String getHierElementVisId() {
      return this.mHierElementVisId;
   }

   public String getDimElementVisId() {
      return this.mDimElementVisId;
   }

   public int getIdx() {
      return this.mIdx;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
      }
   }

   public void setIdx(int newIdx) {
      if(this.mIdx != newIdx) {
         this.mModified = true;
         this.mIdx = newIdx;
      }
   }

   public void setCompanyVisId(String newCompanyVisId) {
      if(this.mCompanyVisId != null && newCompanyVisId == null || this.mCompanyVisId == null && newCompanyVisId != null || this.mCompanyVisId != null && newCompanyVisId != null && !this.mCompanyVisId.equals(newCompanyVisId)) {
         this.mCompanyVisId = newCompanyVisId;
         this.mModified = true;
      }

   }

   public void setLedgerVisId(String newLedgerVisId) {
      if(this.mLedgerVisId != null && newLedgerVisId == null || this.mLedgerVisId == null && newLedgerVisId != null || this.mLedgerVisId != null && newLedgerVisId != null && !this.mLedgerVisId.equals(newLedgerVisId)) {
         this.mLedgerVisId = newLedgerVisId;
         this.mModified = true;
      }

   }

   public void setDimensionVisId(String newDimensionVisId) {
      if(this.mDimensionVisId != null && newDimensionVisId == null || this.mDimensionVisId == null && newDimensionVisId != null || this.mDimensionVisId != null && newDimensionVisId != null && !this.mDimensionVisId.equals(newDimensionVisId)) {
         this.mDimensionVisId = newDimensionVisId;
         this.mModified = true;
      }

   }

   public void setHierarchyVisId(String newHierarchyVisId) {
      if(this.mHierarchyVisId != null && newHierarchyVisId == null || this.mHierarchyVisId == null && newHierarchyVisId != null || this.mHierarchyVisId != null && newHierarchyVisId != null && !this.mHierarchyVisId.equals(newHierarchyVisId)) {
         this.mHierarchyVisId = newHierarchyVisId;
         this.mModified = true;
      }

   }

   public void setHierElementVisId(String newHierElementVisId) {
      if(this.mHierElementVisId != null && newHierElementVisId == null || this.mHierElementVisId == null && newHierElementVisId != null || this.mHierElementVisId != null && newHierElementVisId != null && !this.mHierElementVisId.equals(newHierElementVisId)) {
         this.mHierElementVisId = newHierElementVisId;
         this.mModified = true;
      }

   }

   public void setDimElementVisId(String newDimElementVisId) {
      if(this.mDimElementVisId != null && newDimElementVisId == null || this.mDimElementVisId == null && newDimElementVisId != null || this.mDimElementVisId != null && newDimElementVisId != null && !this.mDimElementVisId.equals(newDimElementVisId)) {
         this.mDimElementVisId = newDimElementVisId;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysHierElemFeedEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setDimensionVisId(newDetails.getDimensionVisId());
      this.setHierarchyVisId(newDetails.getHierarchyVisId());
      this.setHierElementVisId(newDetails.getHierElementVisId());
      this.setDimElementVisId(newDetails.getDimElementVisId());
      this.setIdx(newDetails.getIdx());
   }

   public ExtSysHierElemFeedEVO deepClone() {
      ExtSysHierElemFeedEVO cloned = new ExtSysHierElemFeedEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mExternalSystemId = this.mExternalSystemId;
      cloned.mIdx = this.mIdx;
      if(this.mCompanyVisId != null) {
         cloned.mCompanyVisId = this.mCompanyVisId;
      }

      if(this.mLedgerVisId != null) {
         cloned.mLedgerVisId = this.mLedgerVisId;
      }

      if(this.mDimensionVisId != null) {
         cloned.mDimensionVisId = this.mDimensionVisId;
      }

      if(this.mHierarchyVisId != null) {
         cloned.mHierarchyVisId = this.mHierarchyVisId;
      }

      if(this.mHierElementVisId != null) {
         cloned.mHierElementVisId = this.mHierElementVisId;
      }

      if(this.mDimElementVisId != null) {
         cloned.mDimElementVisId = this.mDimElementVisId;
      }

      return cloned;
   }

   public void prepareForInsert(ExtSysHierElementEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ExtSysHierElementEVO parent, int startKey) {
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

   public ExtSysHierElemFeedRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension, ExtSysHierarchyEVO evoExtSysHierarchy, ExtSysHierElementEVO evoExtSysHierElement, String entityText) {
      return new ExtSysHierElemFeedRefImpl(new ExtSysHierElemFeedCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), evoExtSysHierarchy.getPK(), evoExtSysHierElement.getPK(), this.getPK()), entityText);
   }

   public ExtSysHierElemFeedCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension, ExtSysHierarchyEVO evoExtSysHierarchy, ExtSysHierElementEVO evoExtSysHierElement) {
      return new ExtSysHierElemFeedCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), evoExtSysHierarchy.getPK(), evoExtSysHierElement.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
      sb.append(' ');
      sb.append("CompanyVisId=");
      sb.append(String.valueOf(this.mCompanyVisId));
      sb.append(' ');
      sb.append("LedgerVisId=");
      sb.append(String.valueOf(this.mLedgerVisId));
      sb.append(' ');
      sb.append("DimensionVisId=");
      sb.append(String.valueOf(this.mDimensionVisId));
      sb.append(' ');
      sb.append("HierarchyVisId=");
      sb.append(String.valueOf(this.mHierarchyVisId));
      sb.append(' ');
      sb.append("HierElementVisId=");
      sb.append(String.valueOf(this.mHierElementVisId));
      sb.append(' ');
      sb.append("DimElementVisId=");
      sb.append(String.valueOf(this.mDimElementVisId));
      sb.append(' ');
      sb.append("Idx=");
      sb.append(String.valueOf(this.mIdx));
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

      sb.append("ExtSysHierElemFeed: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
