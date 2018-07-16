package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy;
import java.util.List;

public interface FinanceHierarchyElement extends ExternalEntity {

   String getHierarchyElem();

   List<FinanceHierarchyElement> getFinanceHierarchyElements();

   List<FinanceDimensionElement> getFinanceDimensionElements();

   FinanceHierarchy getFinanceHierarchy();
   
   List<FinanceCompany> getFinanceCompanies();
   
   void setFinanceCompanies(List<FinanceCompany> financeCompanies);
   
   void addFinanceCompany(FinanceCompany financeCompany);
   
   void removeFinanceCompany(FinanceCompany financeCompany);
}
