// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.api.model.mapping.extsys.FinancePeriod;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceCalendarYearImpl extends ExternalEntityImpl implements FinanceCalendarYear, Serializable {

   private int mYear;
   private FinanceCompany mFinanceCompany;
   private List<FinancePeriod> mFinancePeriods;


   public FinanceCalendarYearImpl(String yearVisId, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(yearVisId), descr));
   }

   public String getYearVisId() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public int getYear() {
      return this.mYear;
   }

   public List<FinancePeriod> getFinancePeriods() {
      return this.mFinancePeriods;
   }

   public void setYear(int year) {
      this.mYear = year;
   }

   public void setFinancePeriods(List<FinancePeriod> financePeriods) {
      this.mFinancePeriods = financePeriods;
   }

   public void setFinanceCompany(FinanceCompany financeCompany) {
      this.mFinanceCompany = financeCompany;
   }

   public FinanceCompany getFinanceCompany() {
      return this.mFinanceCompany;
   }

   public String toString() {
      return "Year: " + this.mYear;
   }
}
