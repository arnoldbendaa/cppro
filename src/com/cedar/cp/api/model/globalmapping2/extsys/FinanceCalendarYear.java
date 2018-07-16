package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinancePeriod;
import java.util.List;

public interface FinanceCalendarYear extends ExternalEntity {

   int getYear();

   List<FinancePeriod> getFinancePeriods();

   List<FinanceCompany> getFinanceCompanies();
   
   void setFinanceCompanies(List<FinanceCompany> financeCompanies);
   
   void addFinanceCompany(FinanceCompany financeCompany);
   
   void removeFinanceCompany(FinanceCompany financeCompany);
}
