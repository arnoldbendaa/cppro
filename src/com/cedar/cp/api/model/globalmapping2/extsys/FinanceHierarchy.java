package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement;
import java.util.List;

public interface FinanceHierarchy extends ExternalEntity {

   String getHierarchyName();

   String getHierarchyType();

   List<FinanceHierarchyElement> getFinanceHierarchyElements();

   List<FinanceDimensionElement> getFinanceDimensionElements();

   FinanceDimension getFinanceDimension();

   String getSuggestedCPVisId();

   boolean isDummy();

   boolean isMandatory();
   
   List<FinanceCompany> getFinanceCompanies();
   
   void setFinanceCompanies(List<FinanceCompany> financeCompanies);
   
   void addFinanceCompany(FinanceCompany financeCompany);
   
   void removeFinanceCompany(FinanceCompany financeCompany);
}
