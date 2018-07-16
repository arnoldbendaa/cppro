package com.cedar.cp.api.model.globalmapping2;

import java.util.Hashtable;
import java.util.List;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.util.xmlform.XMLWritable;

public interface MappedHierarchy extends XMLWritable {

   Object getKey();

   MappingKey getFinanceHierarchyKey();

   HierarchyRef getHierarchyRef();

   String getHierarchyVisId1();

   String getHierarchyVisId2();

   String getNewHierarchyVisId();

   String getNewHierarchyDescription();

   boolean isResponsibilityAreaHierarchy();
   
   Hashtable<String, Boolean> getSelectedCompanies();
   
   void setSelectedCompanies(List<FinanceCompany> companies);
   
   void setSelectedCompany(String companyVisId, Boolean value);
   
   int isSelectedCompany(String companyVisId);
   
   void setSelectedAllCompany(String companyVisId, Boolean value);
   
   int isSelectedAllCompany(String companyVisId);
}
