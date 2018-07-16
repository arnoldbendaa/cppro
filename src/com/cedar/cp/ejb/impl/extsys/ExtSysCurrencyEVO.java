// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysCurrencyRef;
import com.cedar.cp.dto.extsys.ExtSysCurrencyCK;
import com.cedar.cp.dto.extsys.ExtSysCurrencyPK;
import com.cedar.cp.dto.extsys.ExtSysCurrencyRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import java.io.Serializable;

public class ExtSysCurrencyEVO implements Serializable {

   private transient ExtSysCurrencyPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mCurrencyVisId;
   private String mDescription;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysCurrencyEVO() {}

   public ExtSysCurrencyEVO(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newCurrencyVisId, String newDescription) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mCurrencyVisId = newCurrencyVisId;
      this.mDescription = newDescription;
   }

   public ExtSysCurrencyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysCurrencyPK(this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mCurrencyVisId);
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

   public String getCurrencyVisId() {
      return this.mCurrencyVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
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

   public void setCurrencyVisId(String newCurrencyVisId) {
      if(this.mCurrencyVisId != null && newCurrencyVisId == null || this.mCurrencyVisId == null && newCurrencyVisId != null || this.mCurrencyVisId != null && newCurrencyVisId != null && !this.mCurrencyVisId.equals(newCurrencyVisId)) {
         this.mCurrencyVisId = newCurrencyVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysCurrencyEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setCurrencyVisId(newDetails.getCurrencyVisId());
      this.setDescription(newDetails.getDescription());
   }

   public ExtSysCurrencyEVO deepClone() {
      ExtSysCurrencyEVO cloned = new ExtSysCurrencyEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mExternalSystemId = this.mExternalSystemId;
      if(this.mCompanyVisId != null) {
         cloned.mCompanyVisId = this.mCompanyVisId;
      }

      if(this.mLedgerVisId != null) {
         cloned.mLedgerVisId = this.mLedgerVisId;
      }

      if(this.mCurrencyVisId != null) {
         cloned.mCurrencyVisId = this.mCurrencyVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      return cloned;
   }

   public void prepareForInsert(ExtSysLedgerEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ExtSysLedgerEVO parent, int startKey) {
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

   public ExtSysCurrencyRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, String entityText) {
      return new ExtSysCurrencyRefImpl(new ExtSysCurrencyCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), this.getPK()), entityText);
   }

   public ExtSysCurrencyCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger) {
      return new ExtSysCurrencyCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), this.getPK());
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
      sb.append("CurrencyVisId=");
      sb.append(String.valueOf(this.mCurrencyVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
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

      sb.append("ExtSysCurrency: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
