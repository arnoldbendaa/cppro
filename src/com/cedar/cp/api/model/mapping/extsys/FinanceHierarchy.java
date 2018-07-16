// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.ExternalEntity;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimension;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement;
import java.util.List;

public interface FinanceHierarchy extends ExternalEntity {

   String getHierarchyName();

   String getHierarchyType();

   List<FinanceHierarchyElement> getFinanceHierarchyElements();

   List<FinanceDimensionElement> getFinanceDimensionElements();

   FinanceDimension getFinanceDimension();

   String getSuggestedCPVisId();
   
   String getOnlySuggestedCPVisId();

   boolean isDummy();

   boolean isMandatory();
}
