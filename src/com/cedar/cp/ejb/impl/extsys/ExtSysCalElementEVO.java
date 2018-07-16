// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysCalElementRef;
import com.cedar.cp.dto.extsys.ExtSysCalElementCK;
import com.cedar.cp.dto.extsys.ExtSysCalElementPK;
import com.cedar.cp.dto.extsys.ExtSysCalElementRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalendarYearEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import java.io.Serializable;

public class ExtSysCalElementEVO implements Serializable {

   private transient ExtSysCalElementPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mCalendarYearVisId;
   private String mCalElementVisId;
   private int mCalendarYearId;
   private String mDescription;
   private int mIdx;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysCalElementEVO() {}

   public ExtSysCalElementEVO(int newExternalSystemId, String newCompanyVisId, String newCalendarYearVisId, String newCalElementVisId, int newCalendarYearId, String newDescription, int newIdx) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mCalendarYearVisId = newCalendarYearVisId;
      this.mCalElementVisId = newCalElementVisId;
      this.mCalendarYearId = newCalendarYearId;
      this.mDescription = newDescription;
      this.mIdx = newIdx;
   }

   public ExtSysCalElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysCalElementPK(this.mExternalSystemId, this.mCompanyVisId, this.mCalendarYearVisId, this.mCalElementVisId);
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

   public String getCalendarYearVisId() {
      return this.mCalendarYearVisId;
   }

   public String getCalElementVisId() {
      return this.mCalElementVisId;
   }

   public int getCalendarYearId() {
      return this.mCalendarYearId;
   }

   public String getDescription() {
      return this.mDescription;
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

   public void setCalendarYearId(int newCalendarYearId) {
      if(this.mCalendarYearId != newCalendarYearId) {
         this.mModified = true;
         this.mCalendarYearId = newCalendarYearId;
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

   public void setCalendarYearVisId(String newCalendarYearVisId) {
      if(this.mCalendarYearVisId != null && newCalendarYearVisId == null || this.mCalendarYearVisId == null && newCalendarYearVisId != null || this.mCalendarYearVisId != null && newCalendarYearVisId != null && !this.mCalendarYearVisId.equals(newCalendarYearVisId)) {
         this.mCalendarYearVisId = newCalendarYearVisId;
         this.mModified = true;
      }

   }

   public void setCalElementVisId(String newCalElementVisId) {
      if(this.mCalElementVisId != null && newCalElementVisId == null || this.mCalElementVisId == null && newCalElementVisId != null || this.mCalElementVisId != null && newCalElementVisId != null && !this.mCalElementVisId.equals(newCalElementVisId)) {
         this.mCalElementVisId = newCalElementVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysCalElementEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setCalendarYearVisId(newDetails.getCalendarYearVisId());
      this.setCalElementVisId(newDetails.getCalElementVisId());
      this.setCalendarYearId(newDetails.getCalendarYearId());
      this.setDescription(newDetails.getDescription());
      this.setIdx(newDetails.getIdx());
   }

   public ExtSysCalElementEVO deepClone() {
      ExtSysCalElementEVO cloned = new ExtSysCalElementEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mExternalSystemId = this.mExternalSystemId;
      cloned.mCalendarYearId = this.mCalendarYearId;
      cloned.mIdx = this.mIdx;
      if(this.mCompanyVisId != null) {
         cloned.mCompanyVisId = this.mCompanyVisId;
      }

      if(this.mCalendarYearVisId != null) {
         cloned.mCalendarYearVisId = this.mCalendarYearVisId;
      }

      if(this.mCalElementVisId != null) {
         cloned.mCalElementVisId = this.mCalElementVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      return cloned;
   }

   public void prepareForInsert(ExtSysCalendarYearEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ExtSysCalendarYearEVO parent, int startKey) {
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

   public ExtSysCalElementRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysCalendarYearEVO evoExtSysCalendarYear, String entityText) {
      return new ExtSysCalElementRefImpl(new ExtSysCalElementCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysCalendarYear.getPK(), this.getPK()), entityText);
   }

   public ExtSysCalElementCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysCalendarYearEVO evoExtSysCalendarYear) {
      return new ExtSysCalElementCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysCalendarYear.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
      sb.append(' ');
      sb.append("CompanyVisId=");
      sb.append(String.valueOf(this.mCompanyVisId));
      sb.append(' ');
      sb.append("CalendarYearVisId=");
      sb.append(String.valueOf(this.mCalendarYearVisId));
      sb.append(' ');
      sb.append("CalElementVisId=");
      sb.append(String.valueOf(this.mCalElementVisId));
      sb.append(' ');
      sb.append("CalendarYearId=");
      sb.append(String.valueOf(this.mCalendarYearId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
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

      sb.append("ExtSysCalElement: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
