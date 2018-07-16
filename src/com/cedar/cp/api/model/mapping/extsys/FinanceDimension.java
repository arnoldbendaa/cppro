// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.ExternalEntity;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
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

   String getSuggestedCPVisId();

   boolean isMandatory();
}
