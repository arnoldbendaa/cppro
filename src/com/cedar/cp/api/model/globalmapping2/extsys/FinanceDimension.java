package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalEntity;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger;
import java.util.List;

public interface FinanceDimension extends ExternalEntity {

   int TYPE_ACCOUNT = 1;
   int TYPE_BUSINESS = 2;
   int TYPE_CALENDAR = 3;


   String getDimensionVisId();

   int getDimensionType();

   String getExtSysDimensionType();

   List<FinanceDimensionElementGroup> getFinanceDimensionElementGroups();

   List<FinanceHierarchy> getFinanceHierarchies();

   String getDimensionMask();

   boolean isDummy();

   FinanceLedger getFinanceLedger();
   
   List<FinanceCompany> getFinanceCompanies();
   
   void setFinanceCompanies(List<FinanceCompany> financeCompanies);
   
   void addFinanceCompany(FinanceCompany financeCompany);
   
   void removeFinanceCompany(FinanceCompany financeCompany);
   
   String getSuggestedCPVisId();

   boolean isMandatory();
}
