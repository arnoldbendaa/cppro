package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner;
import java.util.List;

public interface FinanceLedger extends ExternalEntity {

   String getLedgerVisId();

   boolean isDummy();

   List<FinanceDimension> getFinanceDimensions();
   
   void setFinanceDimensions(List<FinanceDimension> financeDimensions);
   
   FinanceDimension findFinanceDimension(String var1);

   FinanceDimension findFinanceDimension(MappingKey var1);

   List<FinanceCompany> getFinanceCompanies();
   
   void addFinanceCompany(FinanceCompany financeCompany);
   
   void removeFinanceCompany(FinanceCompany financeCompany);
   
   void setFinanceCompanies(List<FinanceCompany> financeCompanies);
   
   List<FinanceDimension> getSelectedDimensions();

   void setSelectedDimensions(List<FinanceDimension> selectedDimensions);

   void setFirstMappedYear(int var1);

   List<FinanceValueType> getFinanceValueTypes();
   
   void setFinanceValueTypes(List<FinanceValueType> financeValueTypes);

   List<FinanceValueTypeOwner> getFinanceValueTypeOwners();
   
   void setFinanceValueTypeOwners(List<FinanceValueTypeOwner> financeValueTypeOwners);
   
   List<FinanceCalendarYear> getFinanceCalendarYears();
}
