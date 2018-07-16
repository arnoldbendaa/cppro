// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.dimension.DimensionElementRef;

public interface DimensionElement {

   int CREDIT = 1;
   int DEBIT = 2;
   int NO_OVERRIDE = 0;


   String getVisId();

   String getDescription();

   Object getKey();

   int getCreditDebit();

   int getAugCreditDebit();

   int getActiveCreditDebit();

   boolean isDisabled();

   DimensionElementRef getEntityRef();

   Dimension getDimension();

   boolean isNotPlannable();

   boolean isNullElement();
}
