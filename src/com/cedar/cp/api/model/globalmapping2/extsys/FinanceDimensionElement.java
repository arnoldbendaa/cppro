package com.cedar.cp.api.model.globalmapping2.extsys;

import java.util.List;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement;

public interface FinanceDimensionElement extends ExternalEntity {

   FinanceDimensionElementGroup getFinanceDimensionElementGroup();

   FinanceHierarchyElement getFinanceHierarchyElement();

   FinanceDimension getFinanceDimension();
   
   List<FinanceCompany> getFinanceCompanies();
   
   void setFinanceCompanies(List<FinanceCompany> financeCompanies);
   
   void addFinanceCompany(FinanceCompany financeCompany);
   
   void removeFinanceCompany(FinanceCompany financeCompany);
}
