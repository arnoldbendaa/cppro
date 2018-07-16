package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElement;
import com.cedar.cp.api.model.globalmapping2.MappedHierarchy;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.util.xmlform.XMLWritable;

import java.util.Hashtable;
import java.util.List;
import java.util.SortedSet;

public interface MappedDimension extends XMLWritable {

   Object getKey();

   MappingKey getFinanceDimensionKey();

   DimensionRef getDimension();

   String getDimensionVisId();

   String getDimensionDescription();

   int getDimensionType();

   GlobalMappedModel2 getModelMapping();

   String getPathVisId();

   List<MappedHierarchy> getMappedHierarchies();

   MappedHierarchy findMappedHierarchy(MappingKey var1);

   SortedSet<MappedDimensionElement> getMappedDimensionElements();

   String getNullDimensionElementVisId();

   String getNullDimensionElementDescription();

   Integer getNullDimensionElementCreditDebit();

   boolean isDisabledLeafNodesExcluded();
   
   Hashtable<String, Boolean> getSelectedCompanies();
   
   void setSelectedCompanies(List<FinanceCompany> companies);
   
   void setSelectedCompany(String companyVisId, Boolean value);
   
   int isSelectedCompany(String companyVisId);
}
