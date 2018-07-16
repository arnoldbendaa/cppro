// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelEditor;

public interface ModelEditorSession extends BusinessSession {

   ModelEditor getModelEditor();

   CurrencyRef[] getAvailableCurrencyRefs();

   DimensionRef[] getAvailableAccountRefs();

   DimensionRef[] getAvailableCalendarRefs();

   HierarchyRef[] getAvailableBudgetHierarchyRefs();

   DimensionRef[] getAvailableBusinessDimensionRefs();
}
