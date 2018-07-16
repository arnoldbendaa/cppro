// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysDimElementRef;
import com.cedar.cp.dto.extsys.ExtSysDimElementCK;
import com.cedar.cp.dto.extsys.ExtSysDimElementPK;
import com.cedar.cp.dto.extsys.ExtSysDimElementRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import java.io.Serializable;

public class ExtSysDimElementEVO implements Serializable {

   private transient ExtSysDimElementPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mDimensionVisId;
   private String mDimElementVisId;
   private String mDescription;
   private int mCreditDebit;
   private boolean mDisabled;
   private boolean mNotPlannable;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysDimElementEVO() {}

   public ExtSysDimElementEVO(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newDimElementVisId, String newDescription, int newCreditDebit, boolean newDisabled, boolean newNotPlannable) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mDimensionVisId = newDimensionVisId;
      this.mDimElementVisId = newDimElementVisId;
      this.mDescription = newDescription;
      this.mCreditDebit = newCreditDebit;
      this.mDisabled = newDisabled;
      this.mNotPlannable = newNotPlannable;
   }

   public ExtSysDimElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysDimElementPK(this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, this.mDimElementVisId);
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

   public String getDimElementVisId() {
      return this.mDimElementVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public boolean getDisabled() {
      return this.mDisabled;
   }

   public boolean getNotPlannable() {
      return this.mNotPlannable;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
      }
   }

   public void setCreditDebit(int newCreditDebit) {
      if(this.mCreditDebit != newCreditDebit) {
         this.mModified = true;
         this.mCreditDebit = newCreditDebit;
      }
   }

   public void setDisabled(boolean newDisabled) {
      if(this.mDisabled != newDisabled) {
         this.mModified = true;
         this.mDisabled = newDisabled;
      }
   }

   public void setNotPlannable(boolean newNotPlannable) {
      if(this.mNotPlannable != newNotPlannable) {
         this.mModified = true;
         this.mNotPlannable = newNotPlannable;
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

   public void setDimElementVisId(String newDimElementVisId) {
      if(this.mDimElementVisId != null && newDimElementVisId == null || this.mDimElementVisId == null && newDimElementVisId != null || this.mDimElementVisId != null && newDimElementVisId != null && !this.mDimElementVisId.equals(newDimElementVisId)) {
         this.mDimElementVisId = newDimElementVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysDimElementEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setDimensionVisId(newDetails.getDimensionVisId());
      this.setDimElementVisId(newDetails.getDimElementVisId());
      this.setDescription(newDetails.getDescription());
      this.setCreditDebit(newDetails.getCreditDebit());
      this.setDisabled(newDetails.getDisabled());
      this.setNotPlannable(newDetails.getNotPlannable());
   }

   public ExtSysDimElementEVO deepClone() {
      ExtSysDimElementEVO cloned = new ExtSysDimElementEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mExternalSystemId = this.mExternalSystemId;
      cloned.mCreditDebit = this.mCreditDebit;
      cloned.mDisabled = this.mDisabled;
      cloned.mNotPlannable = this.mNotPlannable;
      if(this.mCompanyVisId != null) {
         cloned.mCompanyVisId = this.mCompanyVisId;
      }

      if(this.mLedgerVisId != null) {
         cloned.mLedgerVisId = this.mLedgerVisId;
      }

      if(this.mDimensionVisId != null) {
         cloned.mDimensionVisId = this.mDimensionVisId;
      }

      if(this.mDimElementVisId != null) {
         cloned.mDimElementVisId = this.mDimElementVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      return cloned;
   }

   public void prepareForInsert(ExtSysDimensionEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ExtSysDimensionEVO parent, int startKey) {
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

   public ExtSysDimElementRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension, String entityText) {
      return new ExtSysDimElementRefImpl(new ExtSysDimElementCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), this.getPK()), entityText);
   }

   public ExtSysDimElementCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension) {
      return new ExtSysDimElementCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), this.getPK());
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
      sb.append("DimElementVisId=");
      sb.append(String.valueOf(this.mDimElementVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("CreditDebit=");
      sb.append(String.valueOf(this.mCreditDebit));
      sb.append(' ');
      sb.append("Disabled=");
      sb.append(String.valueOf(this.mDisabled));
      sb.append(' ');
      sb.append("NotPlannable=");
      sb.append(String.valueOf(this.mNotPlannable));
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

      sb.append("ExtSysDimElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
