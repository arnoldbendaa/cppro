// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimension;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueType;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class FinanceLedgerImpl extends ExternalEntityImpl implements FinanceLedger, Serializable {

   private boolean mDummy;
   private FinanceCompany mFinanceCompany;
   private List<FinanceDimension> mFinanceDimensions;
   private List<FinanceValueType> mFinanceValueTypes;
   private List<FinanceValueTypeOwner> mFinanceValueTypeOwners;
   private List<FinanceDimension> mSelectedDimensions;
   private int mFirstMappedYear;


   public FinanceLedgerImpl(String ledgerVisId, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(ledgerVisId), descr));
   }

   public String getLedgerVisId() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public boolean isDummy() {
      return this.mDummy;
   }

   public List<FinanceDimension> getFinanceDimensions() {
      return this.mFinanceDimensions;
   }

   public void setDummy(boolean dummy) {
      this.mDummy = dummy;
   }

   public void setFinanceDimensions(List<FinanceDimension> financeDimensions) {
      this.mFinanceDimensions = financeDimensions;
   }

   public void setFinanceCompany(FinanceCompany financeCompany) {
      this.mFinanceCompany = financeCompany;
   }

   public FinanceCompany getFinanceCompany() {
      return this.mFinanceCompany;
   }

   public List<FinanceDimension> getSelectedDimensions() {
      return this.mSelectedDimensions;
   }

   public void setSelectedDimensions(List<FinanceDimension> selectedDimensions) {
      this.mSelectedDimensions = selectedDimensions;
      this.mFinanceValueTypes = null;
   }

   public List<FinanceValueType> getFinanceValueTypes() {
      return this.mFinanceValueTypes;
   }

   public void setFinanceValueTypes(List<FinanceValueType> financeValueTypes) {
      this.mFinanceValueTypes = financeValueTypes;
   }

   public List<FinanceValueTypeOwner> getFinanceValueTypeOwners() {
      return this.mFinanceValueTypeOwners;
   }

   public void setFinanceValueTypeOwners(List<FinanceValueTypeOwner> financeValueTypeOwners) {
      this.mFinanceValueTypeOwners = financeValueTypeOwners;
   }

   public int getFirstMappedYear() {
      return this.mFirstMappedYear;
   }

   public void setFirstMappedYear(int firstMappedYear) {
      if(this.mFirstMappedYear != firstMappedYear) {
         this.mFirstMappedYear = firstMappedYear;
         this.mFinanceValueTypes = null;
         this.mFinanceValueTypeOwners = null;
      }

   }

   public FinanceDimension findFinanceDimension(String name) {
      Iterator i$ = this.mFinanceDimensions.iterator();

      FinanceDimension fd;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         fd = (FinanceDimension)i$.next();
      } while(!fd.getDimensionVisId().equals(name));

      return fd;
   }

   public FinanceDimension findFinanceDimension(MappingKey mk) {
      Iterator i$ = this.mFinanceDimensions.iterator();

      FinanceDimension fd;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         fd = (FinanceDimension)i$.next();
      } while(!fd.getEntityRef().getPrimaryKey().equals(mk));

      return fd;
   }
}
