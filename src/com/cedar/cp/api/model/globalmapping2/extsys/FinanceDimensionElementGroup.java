package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import java.util.List;

public interface FinanceDimensionElementGroup {

   int MAPPING_TYPE_NONE = -1;
   int MAPPING_TYPE_SINGLE = 0;
   int MAPPING_TYPE_PREFIX = 1;
   int MAPPING_TYPE_RANGE = 2;
   int MAPPING_TYPE_HIERARCHY = 3;


   List<FinanceDimensionElementGroup> getFinanceDimensionElementGroups();

   List<FinanceDimensionElement> getFinanceDimensionElements();

   FinanceDimensionElementGroup getFinanceDimensionElementGroup();

   FinanceDimension getFinanceDimension();

   Integer getMappingType();

   String getExtSysAccountType();

   int getGroupType();

   EntityRef getEntityRef();
   
   void setEntityRef(EntityRef entityRef);
   
   List<FinanceCompany> getFinanceCompanies();
   
   void setFinanceCompanies(List<FinanceCompany> financeCompanies);
   
   void addFinanceCompany(FinanceCompany financeCompany);
   
   void removeFinanceCompany(FinanceCompany financeCompany);
}
