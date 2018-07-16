// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimension;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement;
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
}
