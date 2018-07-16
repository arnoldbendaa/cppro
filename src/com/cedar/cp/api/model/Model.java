// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import java.util.List;
import java.util.Map;

public interface Model {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   boolean isCurrencyInUse();

   boolean isLocked();

   boolean isVirementEntryEnabled();

   CurrencyRef getCurrencyRef();

   DimensionRef getAccountRef();

   DimensionRef getCalendarRef();

   HierarchyRef getBudgetHierarchyRef();

   boolean isVirementsInUse();

   List getSelectedDimensionRefs();

   Map<String, String> getProperties();
}
