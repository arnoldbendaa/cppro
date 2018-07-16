package com.cedar.cp.api.model.globalmapping2;

import java.util.Hashtable;
import java.util.List;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.util.xmlform.XMLWritable;

public interface MappedDimensionElement extends XMLWritable, Comparable {

   int MAP_TYPE_SINGLE = 0;
   int MAP_TYPE_PREFIX = 1;
   int MAP_TYPE_RANGE = 2;
   int MAP_TYPE_HIERARCHY = 3;


   Object getKey();

   MappingKey getFinanceDimensionElementKey();

   int getMappingType();

   String getVisId1();

   String getVisId2();

   String getVisId3();
   
   Hashtable<String, Boolean> getSelectedCompanies();
   
   void setSelectedCompanies(List<FinanceCompany> companies);
   
   void setSelectedCompany(String companyVisId, Boolean value);
   
   int isSelectedCompany(String companyVisId);
}
